import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.21"
    id("com.github.johnrengelman.shadow") version "4.0.4"
}

group = "com.gt22"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "polygons.MainKt"
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect", "1.3.20"))
    implementation("no.tornado:tornadofx:1.7.17")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}