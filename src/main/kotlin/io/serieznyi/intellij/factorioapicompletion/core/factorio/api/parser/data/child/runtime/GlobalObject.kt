package io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data.child.runtime

import io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data.child.ValueType

data class GlobalObject(
    val name: String,
    val order: Int,
    val description: String,
    val type: ValueType,
)
