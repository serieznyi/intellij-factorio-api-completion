package io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.runtime

import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.Arrangeable
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.Attribute
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.ValueType
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.function.Function

data class Class(
    val name: String,
    val order: Int,
    val description: String,
    val methods: List<Function>,
    val attributes: List<Attribute>,
    val operators: List<Operator>,
    val optional: Boolean
): Arrangeable {
    data class Operator(
        val name: String,
        val order: Int,
        val description: String,
        val type: ValueType,
        val optional: Boolean,
        val read: Boolean,
        val write: Boolean,
    )

    override fun arrangeElements() {
        methods.sortedWith(compareBy { it.order })
        attributes.sortedWith(compareBy { it.order })
        operators.sortedWith(compareBy { it.order })
    }
}
