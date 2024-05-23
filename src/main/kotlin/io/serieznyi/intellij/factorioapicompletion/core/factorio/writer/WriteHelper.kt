package io.serieznyi.intellij.factorioapicompletion.core.factorio.writer

import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.ValueType
import java.io.IOException

class WriteHelper {
    companion object {
        @Throws(IOException::class)
        fun headerBlock(blockName: String): String {
            return """
            |
            |------------------------------------------------------------------------------------------------------------------------
            |${"-".repeat(10) + " ".repeat(20) + blockName}
            |------------------------------------------------------------------------------------------------------------------------
            |
            |
            |
            """.trimMargin();
        }

        fun type(type: ValueType): String {
            return when(type) {
                is ValueType.Simple -> type.value
                else -> throw IllegalArgumentException("Unknown type")
            }
        }
    }
}