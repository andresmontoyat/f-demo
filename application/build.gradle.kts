apply(plugin = "org.springframework.boot")

dependencies {
    implementation(project(":domain"))
    implementation(project(":repository"))
    implementation(project(":rest-api"))
    implementation(project(":security"))

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.nimbusds:nimbus-jose-jwt:9.40")

    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.springframework.boot:spring-boot-docker-compose")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    this.archiveFileName.set("${project.parent?.name}.${archiveExtension.get()}")
    this.mainClass = "co.com.flypass.FlypassDemoApplication"
    this.enabled = true
}

tasks.getByName<Jar>("jar") {
    enabled = false
}
