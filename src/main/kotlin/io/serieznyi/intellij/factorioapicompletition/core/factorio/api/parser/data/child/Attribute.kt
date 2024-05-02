package io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data.child

data class Attribute(
    val name: String,
    val order: Int,
    val description: String,
    val type: ValueType,
    val optional: Boolean,
    val read: Boolean,
    val write: Boolean,
)
