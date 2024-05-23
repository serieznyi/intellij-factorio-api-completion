package io.serieznyi.intellij.factorioapicompletion.core.factorio.version

import io.serieznyi.intellij.factorioapicompletion.core.factory.ApiVersionResolverFactory
import io.serieznyi.intellij.factorioapicompletion.core.util.io.findOrCreateDirectory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.io.CleanupMode
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import java.nio.file.Paths

class HttpApiVersionResolverTest {
    private lateinit var apiVersionResolver: ApiVersionResolver

    @org.junit.jupiter.api.BeforeEach
    fun setUp(@TempDir(cleanup = CleanupMode.NEVER) tempDir: Path) {
        val cacheDir = Paths.get(System.getenv("BUILD_DIR_PATH")).resolve("cache")

        apiVersionResolver = ApiVersionResolverFactory.create(cacheDir.findOrCreateDirectory())
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