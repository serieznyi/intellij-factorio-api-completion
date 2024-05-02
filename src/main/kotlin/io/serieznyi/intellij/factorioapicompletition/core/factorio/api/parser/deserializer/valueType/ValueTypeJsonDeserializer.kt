package io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.deserializer.valueType

import com.google.gson.*
import io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.data.child.ValueType
import io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.util.GsonBuilderHolder
import java.lang.reflect.Type as ReflectType

class ValueTypeJsonDeserializer : JsonDeserializer<ValueType> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        jsonElement: JsonElement,
        type: ReflectType,
        jsonDeserializationContext: JsonDeserializationContext
    ): ValueType? {
        if (jsonElement.isJsonPrimitive) {
            return ValueType.Simple(jsonElement.asString)
        }

        if (jsonElement.isJsonObject && jsonElement.asJsonObject.has("complex_type")) {
            return deserializeComplexType(jsonElement)
        }

        return null
    }

    private fun deserializeComplexType(jsonElement: JsonElement): ValueType {
        try {
            val jsonObject = jsonElement.asJsonObject
            val complexTypeNativeName = jsonObject["complex_type"].asString
            val clazz = getTypeClass(complexTypeNativeName)

            return GsonBuilderHolder
                .gson()
                .create()
                .fromJson(jsonElement, clazz)
        } catch (e: UnknownComplexTypeException) {
            // todo enable dynamicaly for tests
            if (false) {
                throw e
            }

            return ValueType.Unknown
        }
    }

    private fun getTypeClass(complexTypeNativeName: String): Class<out ValueType> {
        return ValueType.classFromName(complexTypeNativeName)
    }
}
