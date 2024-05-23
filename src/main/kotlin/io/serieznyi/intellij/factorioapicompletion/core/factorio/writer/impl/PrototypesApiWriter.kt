package io.serieznyi.intellij.factorioapicompletion.core.factorio.writer.impl

import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.ApiData
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.prototype.Prototype
import io.serieznyi.intellij.factorioapicompletion.core.factorio.writer.ApiWriter
import io.serieznyi.intellij.factorioapicompletion.core.factorio.writer.WriteHelper
import java.nio.file.Path
import kotlin.io.path.appendText
import kotlin.io.path.createDirectory
import kotlin.io.path.createFile

class PrototypesApiWriter : ApiWriter {
    override fun write(apiData: ApiData, outputDir: Path) {
        val outputPath = outputDir.resolve(LUA_FILES_DIR).createDirectory()

        for (prototype in apiData.prototypeApiData.prototypes) {
            writePrototype(prototype, outputPath)
        }
    }

    private fun writePrototype(prototype: Prototype, outputDir: Path) {
        val outputPath = outputDir.resolve("${prototype.name}.lua").createFile()

        outputPath.appendText(WriteHelper.headerBlock(prototype.name))

        outputPath.appendText(
            """
            |--- ${prototype.description}
            |---@class ${prototype.name} = {}
            |
            |
            |
            """.trimMargin()
        )

        for (property in prototype.properties) {
            outputPath.appendText(
                """
                |--- ${property.description}
                |---@type ${prototype.name}.${property.name} = nil
                |
                |
                """.trimMargin()
            )
        }
    }

    companion object {
        private const val LUA_FILES_DIR = "prototypes"
    }
}