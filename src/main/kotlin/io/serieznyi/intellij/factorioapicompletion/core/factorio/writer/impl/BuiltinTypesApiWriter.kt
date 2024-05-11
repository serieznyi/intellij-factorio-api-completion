package io.serieznyi.intellij.factorioapicompletion.core.factorio.writer.impl

import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.ApiData
import io.serieznyi.intellij.factorioapicompletion.core.factorio.writer.ApiWriter
import io.serieznyi.intellij.factorioapicompletion.core.factorio.writer.WriteHelper
import java.nio.file.Path
import kotlin.io.path.appendText
import kotlin.io.path.createFile

class BuiltinTypesApiWriter: ApiWriter {
    override fun write(apiData: ApiData, outputDir: Path) {
        val outputPath = outputDir.resolve(LUA_FILE_NAME).createFile()

        outputPath.appendText(WriteHelper.headerBlock("Builtin types"))

        // TODO add later
    }

    companion object {
        private const val LUA_FILE_NAME = "builtin_types.lua"
    }
}