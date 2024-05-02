package io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data.child.runtime

import com.google.gson.annotations.SerializedName
import io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data.child.Attribute
import io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data.child.Parameter
import io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data.child.ValueType
import io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data.child.runtime.method.ReturnValue

data class Class(
    val name: String,
    val order: Int,
    val description: String,
    val methods: List<Method>,
    val attributes: List<Attribute>,
    val operators: List<Operator>,
    val optional: Boolean
) {
    data class Method(
        val name: String,
        val order: Int,
        val description: String,
        val parameters: List<Parameter>,
        @field:SerializedName("takes_table")
        val takesTable: Boolean,
        @field:SerializedName("return_values")
        val returnValues: List<ReturnValue>,
    )

    data class Operator(
        val name: String,
        val order: Int,
        val description: String,
        val type: ValueType,
        val optional: Boolean,
        val read: Boolean,
        val write: Boolean,
    )
}
