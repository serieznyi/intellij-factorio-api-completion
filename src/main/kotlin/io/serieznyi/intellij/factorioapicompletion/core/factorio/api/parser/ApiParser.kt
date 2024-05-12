package io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser

import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.ApiData
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.PrototypeApiData
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.RuntimeApiData
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.util.GsonBuilderHolder
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.source.DataSourceFactory
import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersion
import java.io.InputStreamReader

class ApiParser(
    private val dataSourceFactory: DataSourceFactory,
    private val throwOnUnknownType: Boolean = false
) {
    fun parse(apiVersion: ApiVersion): ApiData {
        return ApiData(
            parsePrototypeApiData(apiVersion),
            parseRuntimeApi(apiVersion)
        )
    }

    private fun parseRuntimeApi(apiVersion: ApiVersion): RuntimeApiData {
        InputStreamReader(dataSourceFactory.createJsonStreamForRuntime(apiVersion)).use { reader ->
            return GsonBuilderHolder
                .gson(this.throwOnUnknownType)
                .create()
                .fromJson(reader, RuntimeApiData::class.java)
        }
    }

    private fun parsePrototypeApiData(apiVersion: ApiVersion): PrototypeApiData {
        InputStreamReader(dataSourceFactory.createJsonStreamForPrototype(apiVersion)).use { reader ->
            return GsonBuilderHolder
                .gson(this.throwOnUnknownType)
                .create()
                .fromJson(reader, PrototypeApiData::class.java)
        }
    }
}
