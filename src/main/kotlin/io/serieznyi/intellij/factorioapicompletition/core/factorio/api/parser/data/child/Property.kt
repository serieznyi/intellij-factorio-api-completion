package io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data.child

data class Property(
    val name: String,
    val order: Int,
    val description: String,
    val override: Boolean,
    val type: ValueType,
    val optional: Boolean,
    val default: ValueType?
)
