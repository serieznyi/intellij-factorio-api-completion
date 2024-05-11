package io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data

import com.google.gson.annotations.SerializedName
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
    val globalFunctions: List<GlobalFunction>,
) {

}