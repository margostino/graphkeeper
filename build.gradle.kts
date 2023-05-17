plugins {
    java
    id("io.quarkus")
}

allprojects {}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.quarkus")

    repositories {
        mavenCentral()
        mavenLocal()
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    group = "org.margostino"

    tasks.withType<Test> {
        systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
    }
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
    }

    val quarkusPlatformGroupId: String by project
    val quarkusPlatformArtifactId: String by project
    val quarkusPlatformVersion: String by project
    val buildVersion: String by project

    if (buildVersion != "") {
        version = buildVersion
    } else {
        version = "1.0.0-SNAPSHOT"
    }

    dependencies {
        implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
        implementation("io.quarkus:quarkus-micrometer-registry-prometheus")
        implementation("io.quarkus:quarkus-micrometer")
        implementation("io.quarkus:quarkus-arc")
    }
}

