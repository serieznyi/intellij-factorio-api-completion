package io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.function

import com.google.gson.annotations.SerializedName
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.Arrangeable
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.Parameter
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.ValueType

data class Function(
    val name: String,
    val order: Int,
    val description: String,
    val parameters: List<Parameter>,
    @field:SerializedName("takes_table")
    val takesTable: Boolean,
    @field:SerializedName("return_values")
    val returnValues: List<ReturnValue>,
): Arrangeable {
    override fun arrangeElements() {
        parameters.sortedWith(compareBy { it.order })
        returnValues.sortedWith(compareBy { it.order })
    }

    data class ReturnValue(
        val order: Int,
        val description: String,
        val type: ValueType,
        val optional: Boolean,
    )
}
