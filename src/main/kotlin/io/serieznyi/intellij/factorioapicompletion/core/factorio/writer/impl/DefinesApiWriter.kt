package io.serieznyi.intellij.factorioapicompletion.core.factorio.writer.impl

import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.ApiData
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.runtime.Define
import io.serieznyi.intellij.factorioapicompletion.core.factorio.writer.ApiWriter
import io.serieznyi.intellij.factorioapicompletion.core.factorio.writer.WriteHelper
import java.nio.file.Path
import kotlin.io.path.appendText
import kotlin.io.path.createFile

class DefinesApiWriter : ApiWriter {
    override fun write(apiData: ApiData, outputDir: Path) {
        val outputPath = outputDir.resolve(LUA_FILE_NAME).createFile()

        outputPath.appendText(WriteHelper.headerBlock("Defines"))

        outputPath.appendText(
            """
            |---@class defines
            |defines = {}
            |
            |
            """.trimMargin()
        )

        for (define in apiData.runtimeApiData.defines) {
            for (innerDefine in toFlatDefine(define)) {
                writeDefine(innerDefine, outputPath)
            }
        }
    }

    private fun writeDefine(define: InnerDefine, outputPath: Path) {
        outputPath.appendText(WriteHelper.headerBlock(define.name))

        outputPath.appendText(
            """
            |--- ${define.description}
            |---@class ${define.name} 
            |${define.name} = {}
            |
            |
            """.trimMargin()
        )

        outputPath.appendText(
            define.values.joinToString(System.lineSeparator()) {
                """
                |--- ${it.description}
                |---@type nil
                |${it.name} = nil
                |
                """.trimMargin()
            }
        )
    }

    private fun toFlatDefine(define: Define, path: Array<String> = arrayOf("defines")): List<InnerDefine> {
        if (define.values != null) {
            val name = (path + define.name).joinToString(".")

            return listOf(
                InnerDefine(
                    name,
                    define.description,
                    define.values.map {
                        InnerDefine.InnerDefineValue(
                            (path + define.name + it.name).joinToString("."),
                            it.description
                        )
                    }
                )
            )
        }

        if (define.subKeys != null) {
            return define.subKeys.map { toFlatDefine(it, path + arrayOf(define.name)) }.reduce { a, b -> a + b }
        }

        return listOf(
            InnerDefine(
                (path + define.name).joinToString("."),
                define.description,
                emptyList()
            )
        )
    }

    companion object {
        private const val LUA_FILE_NAME = "defines.lua"
    }

    private data class InnerDefine(val name: String, val description: String, val values: List<InnerDefineValue>) {
        data class InnerDefineValue(val name: String, val description: String)
    }
}