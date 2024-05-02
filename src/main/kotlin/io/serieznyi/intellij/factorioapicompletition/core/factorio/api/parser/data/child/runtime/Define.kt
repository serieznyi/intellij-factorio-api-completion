package io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data.child.runtime

data class Define(
    val name: String,
    val order: Int,
    val description: String,
    val values: List<DefineValue>
) {
    class DefineValue(
        val name: String,
        val order: Int,
        val description: String,
    )
}
