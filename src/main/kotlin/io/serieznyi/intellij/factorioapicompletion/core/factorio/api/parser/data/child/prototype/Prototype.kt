package io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.prototype

import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.Property

data class Prototype(
    val name: String,
    val order: Int,
    val description: String,
    val examples: List<String>,
    val parent: String?,
    val abstract: Boolean,
    val properties: List<Property>,
    val typename: String,
    val deprecated: Boolean,
)
