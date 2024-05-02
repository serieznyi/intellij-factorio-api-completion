package io.serieznyi.intellij.factorioapicompletition.core.factorio.version

import org.junit.jupiter.api.Assertions

class ApiVersionResolverTest {
    private var apiVersionResolver: ApiVersionResolver = ApiVersionResolver()

    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    fun tearDown() {
    }

    @org.junit.jupiter.api.Test
    fun supportedVersions() {
        val versions = apiVersionResolver.supportedVersions()
        val minVersion = ApiVersion.Companion.createVersion("1.1.100")
        val maxVersion = ApiVersion.Companion.createVersion("1.1.107")

        Assertions.assertFalse(versions.isEmpty(), "Versions cant be empty")
        Assertions.assertTrue(versions.first() == minVersion)
        Assertions.assertTrue(versions.last() == maxVersion)
    }
}