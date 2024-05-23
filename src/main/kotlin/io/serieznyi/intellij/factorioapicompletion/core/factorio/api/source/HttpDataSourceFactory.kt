package io.serieznyi.intellij.factorioapicompletion.core.factorio.api.source

import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersion
import java.io.IOException
import java.io.InputStream
import java.net.URL

class HttpDataSourceFactory: DataSourceFactory  {
    override fun createJsonStreamForRuntime(apiVersion: ApiVersion): InputStream {
        return createJsonStream(RUNTIME_API_BASE_URL, apiVersion);
    }

    override fun createJsonStreamForPrototype(apiVersion: ApiVersion): InputStream {
        return createJsonStream(PROTOTYPE_API_BASE_URL, apiVersion);
    }

    @Throws(IOException::class)
    private fun createJsonStream(template: String, version: ApiVersion): InputStream {
        val url: String = String.format(template, version.version)

        return URL(url).openStream()
    }

    companion object {
        private const val PROTOTYPE_API_BASE_URL = "https://lua-api.factorio.com/%s/prototype-api.json"
        private const val RUNTIME_API_BASE_URL = "https://lua-api.factorio.com/%s/runtime-api.json"
    }
}
