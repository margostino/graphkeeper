//plugins {
//    java
//    id("io.quarkus")
//}
//
//repositories {
//    mavenCentral()
//    mavenLocal()
//}
//
//val quarkusPlatformGroupId: String by project
//val quarkusPlatformArtifactId: String by project
//val quarkusPlatformVersion: String by project
//val buildVersion: String by project

dependencies {
    //implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation("io.quarkus:quarkus-mutiny")
    implementation("io.quarkus:quarkus-vertx")
    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-resteasy-reactive")
    implementation("io.quarkus:quarkus-smallrye-context-propagation")
    implementation("io.quarkus:quarkus-reactive-routes")
    implementation("com.hazelcast:quarkus-hazelcast-client:4.0.0")
//    implementation("io.smallrye.config:smallrye-config-source-yaml:3.2.1")
    implementation("io.quarkus:quarkus-config-yaml:3.0.2.Final")
    implementation("io.quarkus:quarkus-logging-json:3.0.2.Final")
    compileOnly("jakarta.enterprise:jakarta.enterprise.cdi-api:4.0.1")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}