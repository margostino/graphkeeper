#!/bin/sh

CONTAINER_IP=$(awk 'END{print $1}' /etc/hosts)

export CONTAINER_IP_ADDRESS=${CONTAINER_IP}

sh /opt/run-java/run/run-java.sh

exec "$@"
