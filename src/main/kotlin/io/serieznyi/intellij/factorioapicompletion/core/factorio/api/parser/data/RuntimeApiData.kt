package io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data

import com.google.gson.annotations.SerializedName
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.function.Function
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.Arrangeable
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.runtime.*

data class RuntimeApiData(
    val classes: List<Class>,
    val events: List<Event>,
    val defines: List<Define>,
    @field:SerializedName("builtin_types")
    val builtinTypes: List<BuiltinType>,
    val concepts: List<Concept>,
    @field:SerializedName("global_objects")
    val globalObjects: List<GlobalObject>,
    @field:SerializedName("global_functions")
    val globalFunctions: List<Function>,
): Arrangeable {
    override fun arrangeElements() {
        classes.sortedWith(compareBy { it.order })
        events.sortedWith(compareBy { it.order })
        defines.sortedWith(compareBy { it.order })
        builtinTypes.sortedWith(compareBy { it.order })
        concepts.sortedWith(compareBy { it.order })
        globalObjects.sortedWith(compareBy { it.order })
        globalFunctions.sortedWith(compareBy { it.order })
    }

}