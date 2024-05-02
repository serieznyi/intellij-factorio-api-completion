package io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data.child.runtime

import io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data.child.ValueType

data class Event(
    val name: String,
    val order: Int,
    val description: String,
    val examples: List<String>,
    val data: List<EventData>,
) {
    data class EventData(
        val name: String,
        val order: Int,
        val description: String,
        val type: ValueType,
        val optional: Boolean,
    )
}
