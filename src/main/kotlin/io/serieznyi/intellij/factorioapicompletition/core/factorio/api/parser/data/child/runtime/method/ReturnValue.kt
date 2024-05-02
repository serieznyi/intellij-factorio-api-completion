package io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data.child.runtime.method

import io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data.child.ValueType

data class ReturnValue(
    val order: Int,
    val description: String,
    val type: ValueType,
    val optional: Boolean,
)
