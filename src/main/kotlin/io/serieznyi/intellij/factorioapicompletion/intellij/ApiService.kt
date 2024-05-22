package io.serieznyi.intellij.factorioapicompletion.intellij

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.ApiParser
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.source.FileCacheDataSourceFactory
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.source.HttpDataSourceFactory
import io.serieznyi.intellij.factorioapicompletion.core.factorio.writer.ApiWriterWrapper
import io.serieznyi.intellij.factorioapicompletion.core.factory.ApiVersionResolverFactory
import io.serieznyi.intellij.factorioapicompletion.core.factory.CacheFactory
import io.serieznyi.intellij.factorioapicompletion.core.util.io.findOrCreateDirectory
import java.nio.file.Path

@Service(Service.Level.PROJECT)
class ApiService(private val project: Project) {
    fun downloadApi() {
        val apiDir = FilesystemUtil.apiVersionsDir()
        val cache = CacheFactory.fileCache()
        val apiParser = ApiParser(FileCacheDataSourceFactory(HttpDataSourceFactory(), cache), false)
        val apiVersion = ApiVersionResolverFactory.create().supportedVersions().latestVersion()
        val writer = ApiWriterWrapper()
        val apiData = apiParser.parse(apiVersion)

        writer.writeAll(apiVersion, apiData, apiDir)

        this.triggerReindex()
    }

    fun triggerReindex() {
        FactorioApiProvider.reload(project)
    }

    fun apiDir(): Path {
        val settings = project.service<PluginSettings>()
        val pluginDir = FilesystemUtil.pluginDir()
        val apiDir = pluginDir.resolve("api").findOrCreateDirectory()

        return apiDir.resolve(settings.selectedApiVersion.version)
    }
}
