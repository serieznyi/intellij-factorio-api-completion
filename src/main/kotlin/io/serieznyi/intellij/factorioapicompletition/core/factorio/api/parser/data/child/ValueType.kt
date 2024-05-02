package io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data.child

import com.google.gson.annotations.SerializedName
import io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.deserializer.valueType.UnknownComplexTypeException
import java.util.function.Consumer

interface ValueType {
    fun arrangeElements() {
    }

    fun getDescription(): String {
        return "";
    }

    val nativeName: String
        get() = nativeNamesPerType().getValue(javaClass)

    @JvmRecord
    data class Simple(val value: String) : ValueType

    @JvmRecord
    data class Array(val value: ValueType) : ValueType

    @JvmRecord
    data class Literal(val value: String, val description: String?) : ValueType {
    }

    @JvmRecord
    data class Type(val value: String, val description: String) : ValueType {
    }

    @JvmRecord
    data class Function(val parameters: List<ValueType>) : ValueType

    @JvmRecord
    data class Tuple(val values: List<ValueType>?) : ValueType

    object Struct : ValueType

    @JvmRecord
    data class LuaStruct(
        val attributes: List<Attribute>,
    ) : ValueType

    @JvmRecord
    data class Union(
        val options: List<ValueType>,
        @field:SerializedName("full_format")
        val fullFormat: Boolean
    ) : ValueType

    @JvmRecord
    data class Dictionary(val key: ValueType, val value: ValueType) : ValueType

    @JvmRecord
    data class LuaCustomTable(val key: ValueType, val value: ValueType) : ValueType

    @JvmRecord
    data class Table(
        val parameters: List<Parameter>,
        val variantParameterGroups: List<ParameterGroup>?,
        val variantParameterDescription: String?
    ) : ValueType {
        override fun getDescription(): String {
            return variantParameterDescription.toString()
        }

        data class ParameterGroup(
            val name: String?,
            val order: Double = 0.0,
            val description: String,
            val parameters: List<Parameter>
        ) : Arrangeable {
            override fun arrangeElements() {
                if (parameters.isNotEmpty()) {
                    parameters.sortedWith(Comparator.comparingDouble { parameter: Parameter -> parameter.order })
                    parameters.forEach(Consumer { obj: Parameter -> obj.arrangeElements() })
                }
            }
        }
    }

    @JvmRecord
    data class LuaLazyLoadedValue(val value: ValueType) : ValueType

    object Unknown : ValueType

    companion object {
        private fun nativeNamesPerType(): Map<Class<out ValueType>, String> {
            return TYPES_PER_NATIVE_NAME.map { (k, v) -> v to k }.toMap()
        }

        fun classFromName(typeNativeName: String): Class<out ValueType> {
            return TYPES_PER_NATIVE_NAME[typeNativeName] ?: throw UnknownComplexTypeException(typeNativeName)
        }

        private val TYPES_PER_NATIVE_NAME: HashMap<String, Class<out ValueType>> =
            object : HashMap<String, Class<out ValueType>>() {
                init {
                    put("simple", Simple::class.java)
                    put("array", Array::class.java)
                    put("literal", Literal::class.java)
                    put("type", Type::class.java)
                    put("function", Function::class.java)
                    put("tuple", Tuple::class.java)
                    put("struct", Struct::class.java)
                    put("union", Union::class.java)
                    put("dictionary", Dictionary::class.java)
                    put("LuaCustomTable", LuaCustomTable::class.java)
                    put("table", Table::class.java)
                    put("LuaLazyLoadedValue", LuaLazyLoadedValue::class.java)
                    put("LuaStruct", LuaStruct::class.java)
                }
            }
    }
}
