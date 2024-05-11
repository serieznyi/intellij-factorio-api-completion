package io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.util

import com.google.gson.GsonBuilder
import io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data.child.ValueType
import io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.deserializer.valueType.ValueTypeJsonDeserializer

class GsonBuilderHolder {
    companion object {
        fun gson(throwOnUnknownType: Boolean = false): GsonBuilder {
            return GsonBuilder()
                .registerTypeAdapter(ValueType::class.java, ValueTypeJsonDeserializer(throwOnUnknownType))
        }
    }
}