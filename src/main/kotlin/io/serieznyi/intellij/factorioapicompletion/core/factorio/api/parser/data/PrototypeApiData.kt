package io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data

import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.Arrangeable
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.prototype.Prototype
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.prototype.Type

data class PrototypeApiData(
    val prototypes: List<Prototype>,
    val types: List<Type>,
): Arrangeable {
    override fun arrangeElements() {
        types.sortedWith(compareBy { it.order })
        prototypes.sortedWith(compareBy { it.order })
    }
}