package io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data

import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.Arrangeable

data class ApiData(
    val prototypeApiData: PrototypeApiData,
    val runtimeApiData: RuntimeApiData
): Arrangeable {
    override fun arrangeElements() {
        prototypeApiData.arrangeElements()
        runtimeApiData.arrangeElements()
    }

}
