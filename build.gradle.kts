import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.23"
    id("org.jetbrains.intellij") version "1.17.2"
    id("jacoco")
    id("org.sonarqube") version "5.0.0.4638"
}

group = "io.serieznyi.intellij"
version = "1.0.0-IDEA231-eap-1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

fun sonarqube() {}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2023.1")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(
        // See https://plugins.jetbrains.com/plugin/9768-emmylua/versions
        "com.tang:1.4.7-IDEA231"
    ))
}

configure<SourceSetContainer> {
    named("main") {
        java.srcDir("src/main/kotlin")
        java.destinationDirectory.set(file("build/classes/kotlin/main/"))
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(JavaVersion.VERSION_17.toString())
    }
}

jacoco {
    toolVersion = "0.8.12"
}

tasks {
    withType<Test> {
        configure<JacocoTaskExtension> {
            // https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin-faq.html#jacoco-reports-0-coverage
            isIncludeNoLocationClasses = true
            excludes = listOf("jdk.internal.*")
        }
    }

    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_17.toString()
        targetCompatibility = JavaVersion.VERSION_17.toString()
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    }

    patchPluginXml {
        sinceBuild.set("231")
    }

    signPlugin {
        certificateChainFile.set(file(System.getenv("INTELLIJ_PLUGIN_CERTIFICATE_CHAIN_FILE_PATH")))
        privateKeyFile.set(file(System.getenv("INTELLIJ_PLUGIN_PRIVATE_KEY_FILE_PATH")))
        password.set(System.getenv("INTELLIJ_PLUGIN_PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("INTELLIJ_PLUGIN_PUBLISH_TOKEN"))
        channels.set(listOf("EAP"))
    }

    test {
        useJUnitPlatform()

        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
            showStandardStreams = true
        }

        finalizedBy(jacocoTestReport)

        reports {
            junitXml.outputLocation.set(layout.buildDirectory.dir("reports/junit/xml"))
            html.outputLocation.set(layout.buildDirectory.dir("reports/junit/html"))
        }

        systemProperty("junit.jupiter.execution.parallel.enabled", true)
        systemProperty("junit.jupiter.execution.parallel.mode.default", "concurrent")
    }

    jacocoTestReport {
        dependsOn(test)

        classDirectories.setFrom(instrumentCode)

        reports {
            xml.required = true
            xml.outputLocation.set(layout.buildDirectory.file("reports/jacoco/report.xml"))
            html.required = true
            html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/html"))
        }
    }

    jacocoTestCoverageVerification {
        classDirectories.setFrom(instrumentCode)
    }
}
