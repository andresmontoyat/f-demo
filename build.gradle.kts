import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    java
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
    id("org.hibernate.orm") version "6.5.2.Final"
    id("org.graalvm.buildtools.native") version "0.10.2"
    id("jacoco")
}

group = "co.com.flypass"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(22)
    }
}

configurations {
    runtimeClasspath {
        extendsFrom(configurations.developmentOnly.get())
    }

    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}


subprojects {
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "java")
    apply(plugin = "jacoco")

    dependencies {
        implementation("org.slf4j:slf4j-api")
        implementation("ch.qos.logback:logback-classic")

        implementation("com.fasterxml.jackson.core:jackson-databind")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

        compileOnly("org.projectlombok:lombok")
        implementation("org.mapstruct:mapstruct:${property("mapstruct.version")}")
        implementation("com.github.javafaker:javafaker:${property("javafaker.version")}") {
            exclude(group = "org.yaml")
        }
        implementation("org.yaml:snakeyaml")

        annotationProcessor("org.projectlombok:lombok")
        annotationProcessor("org.mapstruct:mapstruct-processor:${property("mapstruct.version")}")

        testImplementation("org.mockito:mockito-junit-jupiter")
        testImplementation("org.junit.jupiter:junit-jupiter-engine")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.0")
        }
    }


    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events = setOf(PASSED, SKIPPED, FAILED)
        }
        finalizedBy(tasks.jacocoTestReport)
    }

    tasks.jacocoTestReport {
        dependsOn(tasks.test)
        finalizedBy(tasks.jacocoTestCoverageVerification)

        reports {
            xml.required.set(true)
            xml.outputLocation.set(file("${layout.buildDirectory.get()}/reports/jacoco.xml"))
            csv.required.set(false)
            html.outputLocation.set(file("${layout.buildDirectory.get()}/reports/jacocoHtml"))
        }
    }

}

tasks.register<JacocoReport>("jacocoMergedReport") {
    dependsOn(subprojects.map { project -> project.tasks.jacocoTestReport })
    additionalSourceDirs.setFrom(files(subprojects.map { project -> project.sourceSets.main.get().allSource.srcDirs }))
    sourceDirectories.setFrom(files(subprojects.map { project -> project.sourceSets.main.get().allSource.srcDirs }))
    classDirectories.setFrom(files(subprojects.map { project -> project.sourceSets.main.get().output }))
    executionData.setFrom(
        project.fileTree(project.buildDir) {
            include("**/build/jacoco/test.exec")
        }
    )
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }
}

hibernate {
    enhancement {
        enableAssociationManagement = true
    }
}

