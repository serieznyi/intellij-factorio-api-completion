import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.changelog.Changelog
import java.text.SimpleDateFormat
import java.util.*

project.ext["repositoryUrl"] = "https://github.com/serieznyi/intellij-factorio-api-completion"

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.23"
    id("org.jetbrains.intellij") version "1.17.3"
    id("jacoco")
    id("org.jetbrains.changelog") version "2.2.0"
    id("org.sonarqube") version "5.0.0.4638"
}

group = "io.serieznyi.intellij"
version = "1.0.0-IDEA231-eap-1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.11.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

fun sonarqube() {}

changelog {
    path.set(file("CHANGELOG.md").canonicalPath)
    header = "[${version.get()}] - ${SimpleDateFormat("yyyy-MM-dd").format(Date())}"
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2023.1")
    type.set("IC") // Target IDE Platform

    plugins.set(
        listOf(
            // See https://plugins.jetbrains.com/plugin/9768-emmylua/versions
            "com.tang:1.4.7-IDEA231"
        )
    )
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

        changeNotes.set(
            changelog.getAll().values
                .filter { version -> !version.isUnreleased }
                .subList(0, 9.coerceAtMost(changelog.getAll().size - 1)) // newest 10 versions
                .joinToString("") { changelog.renderItem(it, Changelog.OutputType.HTML) }
                .plus("\nLearn more in the <a href='${project.ext["repositoryUrl"]}/blob/master/CHANGELOG.md'>full changelog</a>")
        )
    }

    signPlugin {
        val chainFilePath = System.getenv("INTELLIJ_PLUGIN_CERTIFICATE_CHAIN_FILE_PATH")
        val privateKeyFilePath = System.getenv("INTELLIJ_PLUGIN_PRIVATE_KEY_FILE_PATH")

        if (chainFilePath != null && privateKeyFilePath !== null) {
            certificateChainFile.set(file(chainFilePath))
            privateKeyFile.set(file(privateKeyFilePath))
            password.set(System.getenv("INTELLIJ_PLUGIN_PRIVATE_KEY_PASSWORD"))
        }
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
