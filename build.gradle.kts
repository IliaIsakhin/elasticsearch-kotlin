import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val esVersion: String by project
val coroutinesVersion: String by project
val kotlinVersion: String by project

plugins {
    id("org.springframework.boot") version "2.4.3"
    id ("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jetbrains.kotlin.plugin.spring") version "1.4.20"
    kotlin("jvm") version "1.4.20"
}

group = "ilia.isakhin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.elasticsearch:elasticsearch:$esVersion")
    implementation("org.elasticsearch.client:elasticsearch-rest-client:$esVersion")
    implementation("org.elasticsearch.client:elasticsearch-rest-high-level-client:$esVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
