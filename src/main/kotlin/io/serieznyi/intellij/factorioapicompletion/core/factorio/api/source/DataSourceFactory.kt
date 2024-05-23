package io.serieznyi.intellij.factorioapicompletion.core.factorio.api.source

import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersion
import java.io.InputStream

interface DataSourceFactory {
    fun createJsonStreamForRuntime(apiVersion: ApiVersion): InputStream
    fun createJsonStreamForPrototype(apiVersion: ApiVersion): InputStream
}