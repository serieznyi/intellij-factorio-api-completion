package io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.runtime

import com.google.gson.annotations.SerializedName
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.Parameter
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.runtime.method.ReturnValue

data class GlobalFunction(
    val name: String,
    val order: Int,
    val description: String,
    val parameters: List<Parameter>,
    @field:SerializedName("takes_table")
    val takesTable: Boolean,
    @field:SerializedName("return_values")
    val returnValues: List<ReturnValue>,
)
