import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val esVersion: String by project
val coroutinesVersion: String by project

plugins {
    id("org.springframework.boot") version "2.4.3"
    id ("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jetbrains.kotlin.plugin.spring") version "1.5.0-RC"
    kotlin("jvm") version "1.4.32"
}

group = "ilia.isakhin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$coroutinesVersion")

    implementation("org.elasticsearch:elasticsearch:$esVersion")
    implementation("org.elasticsearch.client:elasticsearch-rest-client:$esVersion")
    implementation("org.elasticsearch.client:elasticsearch-rest-high-level-client:$esVersion")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testImplementation("io.projectreactor:reactor-test")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
