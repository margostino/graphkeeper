
dependencies {
    //implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-smallrye-fault-tolerance")
    implementation("io.quarkus:quarkus-rest-client-mutiny")
    implementation("io.quarkus:quarkus-rest-client")
    //implementation "io.quarkus:quarkus-rest-client-jackson"
    implementation("io.quarkus:quarkus-resteasy-jackson")
    implementation("io.quarkus:quarkus-smallrye-graphql")
    implementation("io.quarkus:quarkus-resteasy")
    //implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-config-yaml")
    implementation("io.quarkus:quarkus-logging-json")
    implementation("io.smallrye.config:smallrye-config-source-yaml:2.9.1")
    //implementation "io.smallrye.config:smallrye-config-source-injection:2.9.1"
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("io.netty:netty-transport-native-epoll:4.1.74.Final:linux-x86_64")
    implementation("io.netty:netty-transport-native-kqueue:4.1.74.Final:osx-x86_64")
    //implementation "com.fasterxml.jackson.core:jackson-annotations"
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("io.quarkus:quarkus-junit5-mockito:2.7.2.Final")
}