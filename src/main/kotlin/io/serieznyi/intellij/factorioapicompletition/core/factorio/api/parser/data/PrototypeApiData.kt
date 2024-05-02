package io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data

import io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data.child.prototype.Prototype
import io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data.child.prototype.Type

data class PrototypeApiData(
    val prototypes: List<Prototype>,
    val types: List<Type>,
)