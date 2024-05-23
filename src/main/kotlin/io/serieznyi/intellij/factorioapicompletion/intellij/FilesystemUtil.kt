package io.serieznyi.intellij.factorioapicompletion.intellij

import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.extensions.PluginId
import io.serieznyi.intellij.factorioapicompletion.core.util.io.findOrCreateDirectory
import java.nio.file.Path

class FilesystemUtil {
    companion object {
        private val PLUGIN_ID: PluginId = PluginId.getId("io.serieznyi.factorio-api-completion")

        fun pluginDir(): Path {
            val descriptor = PluginManagerCore.getPlugin(PLUGIN_ID) ?: throw RuntimeException("Unexpected error. Plugin dir not found")

            return descriptor.pluginPath
        }

        fun cacheDir(): Path {
            val pluginDir = this.pluginDir()

            return pluginDir.resolve("cache").findOrCreateDirectory()
        }

        fun apiVersionsDir(): Path {
            val pluginDir = this.pluginDir()

            return pluginDir.resolve("cache").findOrCreateDirectory()
        }
    }
}