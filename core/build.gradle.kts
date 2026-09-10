import org.gradle.jvm.toolchain.JavaLanguageVersion

val jvmToolchainVersion: String = providers.gradleProperty("jvmToolchainVersion").get()

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.4.20"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(jvmToolchainVersion.toInt())
    }
}

kotlin {
    jvmToolchain(jvmToolchainVersion.toInt())
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}