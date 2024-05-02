package io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data.child

data class Parameter(
    val name: String? = null,
    val order: Double = 0.0,
    val description: String,
    val type: ValueType,
    val optional: Boolean = false,
) : Arrangeable {
    override fun arrangeElements() {
        type.arrangeElements()
    }
}