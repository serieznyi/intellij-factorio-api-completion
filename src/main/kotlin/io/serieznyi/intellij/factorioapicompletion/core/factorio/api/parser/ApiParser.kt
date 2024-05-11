package io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser

import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.ApiData
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.PrototypeApiData
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.RuntimeApiData
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.util.GsonBuilderHolder
import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersion
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL

class ApiParser(private val throwOnUnknownType: Boolean = false) {
    fun parse(apiVersion: ApiVersion): ApiData {
        return ApiData(
            parsePrototypeApiData(apiVersion),
            parseRuntimeApi(apiVersion)
        )
    }

    private fun parseRuntimeApi(apiVersion: ApiVersion): RuntimeApiData {
        InputStreamReader(createJsonStream(RUNTIME_API_BASE_URL, apiVersion)).use { reader ->
            return GsonBuilderHolder
                .gson(this.throwOnUnknownType)
                .create()
                .fromJson(reader, RuntimeApiData::class.java)
        }
    }

    private fun parsePrototypeApiData(apiVersion: ApiVersion): PrototypeApiData {
        InputStreamReader(createJsonStream(PROTOTYPE_API_BASE_URL, apiVersion)).use { reader ->
            return GsonBuilderHolder
                .gson(this.throwOnUnknownType)
                .create()
                .fromJson(reader, PrototypeApiData::class.java)
        }
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
