#!make
SHELL = /bin/sh
.DEFAULT: help

-include .env .env.local .env.*.local

# Defaults
BUILD_VERSION ?= "1.0.0-SNAPSHOT"
IMAGE_NAME := ${DOCKER_REPO}/${SERVICE_NAME}:${BUILD_VERSION}
IMAGE_NAME_LATEST := ${DOCKER_REPO}/${SERVICE_NAME}:latest
DOCKER_COMPOSE = USERID=$(shell id -u):$(shell id -g) docker-compose ${compose-files}
#DOCKER_HOST=$(ifconfig | grep -E "([0-9]{1,3}\.){3}[0-9]{1,3}" | grep -v 127.0.0.1 | awk '{ print $2 }' | cut -f2 -d: | head -n1)
DOCKER_HOST="host.docker.internal"
ALL_ENVS := local ci
env ?= local
gk-instances ?= 2
docker-snapshot ?= true

ifndef SERVICE_NAME
$(error SERVICE_NAME is not set)
endif

#ifeq (${env}, ci)
#compose-files=-f docker-compose.yml -f docker-compose.ci.yml
#endif

.PHONY: help
help:
	@echo "GraphKeeper (GK) pipeline"
	@echo ""
	@echo "Usage:"
	@echo "  build                          - Build artifact and docker image"
	@echo "  build.native                   - Build artifact and native docker image"
	@echo "  test.unit                      - Run unit tests"
	@echo "  test.integration               - Run integration tests"
	@echo "  test.api                       - Run api tests"
	@echo "  test.resiliency                - Run resiliency tests"
	@echo "  docker.run                 	- Spin up all container defined in docker compose for Demo"
	@echo "  docker.run.gk                 	- Run only GraphKeeper docker container"
	@echo "  docker.wait                    - Waits until all docker containers have exited successfully and/or are healthy. Timeout: 180 seconds"
	@echo "  stop.world               		- Stop all docker containers running"
	@echo ""
	@echo "Project-level environment variables are set in .env file:"
	@echo "  SERVICE_NAME=graphkeeper"
	@echo "  DOCKER_PROJECT_NAME=graphkeeper"
	@echo "  COMPOSE_PROJECT_NAME=graphkeeper"
	@echo "  DOCKER_REPO="
	@echo "  COMPOSE_HTTP_TIMEOUT=360"
	@echo ""
	@echo "Note: Store protected environment variables in .env.local or .env.*.local"
	@echo ""

.PHONY: build
build: prepare.config b.clean b.build d.build

.PHONY: build.native
build.native: prepare.config b.clean b.build d.build.native

.PHONY: test.unit
test.unit:
	./gradlew test

.PHONY: test.integration
test.integration:
	./gradlew cleanIntegrationTest integrationTest

.PHONY: test.api
test.api:
	### TODO

.PHONY: test.resiliency
test.resiliency:
	### TODO

.PHONY: build.run
build.run: build
	make docker.run

.PHONY: docker.run
docker.run: d.compose.down
	make d.compose.up
	make docker.wait

.PHONY: docker.run.dependencies
docker.run.dependencies: d.compose.down
	make d.compose.up
	make docker.wait
	docker-compose ps

.PHONY: docker.run.gk
docker.run.gk:
	$(call DOCKER_COMPOSE) up -d --force-recreate --build graphkeeper_1

.PHONY: docker.stop
docker.stop: d.compose.down

.PHONY: d.compose.up
d.compose.up:
	$(call DOCKER_COMPOSE) up -d --remove-orphans --build

.PHONY: d.compose.down
d.compose.down:
	$(call DOCKER_COMPOSE) down -v || true
	$(call DOCKER_COMPOSE) rm --force || true
	docker rm "$(docker ps -a -q)" -f || true

.PHONY: docker.wait
docker.wait:
	./scripts/docker wait_and_validate

.PHONY: docker.logs
docker.logs:
	./scripts/docker-logs

prepare.config:
	DOCKER_HOST=$(value DOCKER_HOST) ./scripts/build prepare_config_files

.PHONY: stop.world
stop.world:
	DOCKER_PROJECT_NAME=${DOCKER_PROJECT_NAME} ./scripts/docker stop_and_clean_docker_world

b.clean:
	./gradlew -no-build-cache -PbuildVersion=${BUILD_VERSION} clean

b.build:
	./gradlew quarkusBuild -Dquarkus.profile=docker -PbuildVersion=${BUILD_VERSION} -x :test

d.login:
	docker login -u ${DOCKER_USER} ${DOCKER_PASSWORD} ${DOCKER_REPO}

# d.build:
# 	docker build --no-cache --build-arg BUILD_VERSION=${BUILD_VERSION} -t ${image-name} .

#d.build:
#	docker build -f src/main/docker/Dockerfile -t graphkeeper/graphkeeper .

d.build:
	docker build -f src/main/docker/Dockerfile.jvm -t ${IMAGE_NAME} .

d.build.native:
	docker build -f src/main/docker/Dockerfile.native -t ${IMAGE_NAME} .

