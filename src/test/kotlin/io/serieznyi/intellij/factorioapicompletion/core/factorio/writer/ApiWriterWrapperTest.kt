package io.serieznyi.intellij.factorioapicompletion.core.factorio.writer

import io.serieznyi.intellij.factorioapicompletion.core.cache.FileCache
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.ApiParser
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.source.FileCacheDataSourceFactory
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.source.HttpDataSourceFactory
import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersionResolver
import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersionResolverHolder
import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.FileCacheApiVersionResolver
import io.serieznyi.intellij.factorioapicompletion.core.util.io.findOrCreateDirectory
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.io.CleanupMode
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Duration


class ApiWriterWrapperTest {
    private lateinit var apiVersionResolver: ApiVersionResolver
    private lateinit var apiParser: ApiParser
    private val writer = ApiWriterWrapper()
    private lateinit var writeResultDir: Path

    @org.junit.jupiter.api.BeforeEach
    fun setUp(@TempDir(cleanup = CleanupMode.NEVER) tempDir: Path) {
        val buildDir = Paths.get(System.getenv("BUILD_DIR_PATH"))
        val cacheDir = buildDir.resolve("cache").findOrCreateDirectory()
        writeResultDir = buildDir.resolve("luaWriteResult").findOrCreateDirectory()

        val fileCache = FileCache(cacheDir, Duration.ofDays(14))

        apiVersionResolver = FileCacheApiVersionResolver(
            ApiVersionResolverHolder.get(),
            fileCache
        )

        apiParser = ApiParser(
            FileCacheDataSourceFactory(
                FileCacheDataSourceFactory(HttpDataSourceFactory(), fileCache),
                fileCache
            ),
            true
        )
    }

    @Test
    fun writeAll() {
        assertDoesNotThrow {
            val apiVersion = apiVersionResolver.supportedVersions().latestVersion()
            val apiData = apiParser.parse(apiVersion)

            writer.writeAll(apiVersion, apiData, writeResultDir)
        }
    }
}