package io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.runtime

import com.google.gson.annotations.SerializedName
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.Arrangeable

data class Define(
    val name: String,
    val order: Int,
    val description: String,
    val values: List<DefineValue>?,
    @field:SerializedName("subkeys")
    val subKeys: List<Define>?
): Arrangeable {
    class DefineValue(
        val name: String,
        val order: Int,
        val description: String,
    )

    override fun arrangeElements() {
        values?.sortedWith(compareBy { it.order })
        subKeys?.sortedWith(compareBy { it.order })
    }
}
