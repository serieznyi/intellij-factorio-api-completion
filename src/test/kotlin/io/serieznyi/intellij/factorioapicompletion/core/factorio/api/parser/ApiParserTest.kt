package io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser

import io.serieznyi.intellij.factorioapicompletion.core.cache.FileCache
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.source.FileCacheDataSourceFactory
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.source.HttpDataSourceFactory
import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersion
import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersionResolver
import io.serieznyi.intellij.factorioapicompletion.core.factory.ApiVersionResolverFactory
import io.serieznyi.intellij.factorioapicompletion.core.util.io.findOrCreateDirectory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.io.CleanupMode
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Duration
import java.util.stream.Stream

class ApiParserTest {
    private lateinit var apiParser: ApiParser
    private lateinit var apiVersionResolver: ApiVersionResolver

    @BeforeEach
    fun setUp() {
        apiParser = ApiParser(
            FileCacheDataSourceFactory(
                HttpDataSourceFactory(),
                FileCache(cacheDir(), Duration.ofDays(14))
            ),
            throwOnUnknownType = true
        )
        apiVersionResolver = ApiVersionResolverFactory.create(cacheDir())
    }

    @AfterEach
    fun tearDown() {
    }

    @ParameterizedTest
    @MethodSource("parseProvider")
    fun parse(apiVersion: ApiVersion) {
        assertDoesNotThrow {
            apiParser.parse(apiVersion)
        }
    }

    companion object {
        @JvmStatic
        fun parseProvider(): Stream<ApiVersion> {
            return ApiVersionResolverFactory.create(cacheDir()).supportedVersions().reversed().stream()
        }

        @JvmStatic
        private fun cacheDir(): Path {
            return Paths.get(System.getenv("BUILD_DIR_PATH")).resolve("cache").findOrCreateDirectory()
        }
    }
}