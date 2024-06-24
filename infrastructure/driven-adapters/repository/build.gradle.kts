dependencies {
    implementation(project(":domain"))

    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
