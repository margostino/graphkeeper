# GraphKeeper

GraphKeeper is a highly reliable distributed coordination service for maintaining GraphQL Schema and Data Fetcher configuration providing distributed synchronization.
GraphKeeper implements a [sidecar pattern](https://learn.microsoft.com/en-us/azure/architecture/patterns/sidecar) for GraphQL Server applications.

## When to use GraphKeeper

Use this service when:

GraphKeeper is suitable for GraphQL Servers with Schemas and Data Fetcher configurations which mutate very often (i.e. daily, weekly), business logic does not change and Data Fetchers share same API contract downstream. 
Traditionally this operation would involve a deployment to update a new GraphQL Schema, Data Fetcher configurations or both. 
GraphKeeper might fit for cases where the amount of time and effort for deploying a GraphQL server application is significant and repetitive so integrating 
your application side-by-side with GraphKeeper may save time and make your process more agile and flexible.

1. GraphQL Schema changes very often (i.e. daily, weekly).
2. GraphQL Schema only for Query Types 
3. Data Fetchers share same API contract downstream.

GraphKeeper can be containerized and deployed side-by-side with its client application server, or as a remote service.

This service may not be suitable when:

1. GraphQL Schema rarely change.
2. GraphQL Schema is highly complex.
3. Data Fetchers have their own API contract downstream.
4. Data Fetchers have complex and not common logic each others. 

## Issues and considerations
TBD

## Stack

This project uses [Quarkus](https://quarkus.io/) as Java Framework and [Hazelcast IMDG](https://docs.hazelcast.com/imdg/4.2/overview/what-is-imdg) for data replication.
You can build a [native](https://quarkus.io/guides/building-native-image) (disabled temporarily) or JVM docker image.  

## Demo
Build JVM mode
```shell script
> make build
```
Build native (disabled temporarily)
```shell script
> make native
```
Build JVM mode & Run at once
```shell script
> make build.run
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
> ./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./gradlew build
```
It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./gradlew build -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using:
```shell script
./gradlew build -Dquarkus.package.type=native
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:
```shell script
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/graphkeeper-1.0.0-SNAPSHOT-runner`

