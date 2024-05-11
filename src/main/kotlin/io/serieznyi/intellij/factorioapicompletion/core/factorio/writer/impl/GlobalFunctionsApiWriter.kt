package io.serieznyi.intellij.factorioapicompletion.core.factorio.writer.impl

import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.ApiData
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.Parameter
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.child.function.Function
import io.serieznyi.intellij.factorioapicompletion.core.factorio.writer.ApiWriter
import io.serieznyi.intellij.factorioapicompletion.core.factorio.writer.WriteHelper
import io.serieznyi.intellij.factorioapicompletion.core.factorio.writer.WriteHelper.Companion.type
import java.nio.file.Path
import kotlin.io.path.appendText
import kotlin.io.path.createFile

class GlobalFunctionsApiWriter: ApiWriter {
    override fun write(apiData: ApiData, outputDir: Path) {
        val outputPath = outputDir.resolve(LUA_FILE_NAME).createFile()

        outputPath.appendText(WriteHelper.headerBlock("Global functions"))

        for (value in apiData.runtimeApiData.globalFunctions) {
            val description = renderDescription(value.description)
            val parameters = renderParameters(value.parameters)
            val arguments = renderArguments(value.parameters)
            val returning = renderReturning(value.returnValues)

            outputPath.appendText(
                """
                |$description
                |$parameters
                |$returning
                |function ${value.name}($arguments) end
                |
                |
                |
                """.trimMargin()
            )
        }
    }

    private fun renderReturning(returnValues: List<Function.ReturnValue>): String {
        var types = "void"
        var description = ""

        if (returnValues.isNotEmpty()) {
            types = returnValues.joinToString("|") { type(it.type) }
            description = returnValues.joinToString (". ") { it.description }
        }

        return "--- @return $types $description"
    }

    private fun renderParameters(parameters: List<Parameter>): String {
        return parameters.joinToString("\n") { "--- @param ${it.name} ${type(it.type)} ${it.description}" }
    }

    private fun renderDescription(description: String): String {
        return description.split("\n").joinToString("\n") { "--- ".plus(it) }
    }

    private fun renderArguments(parameters: List<Parameter>): String {
        return parameters.map { it.name }.joinToString(",")
    }

    companion object {
        private const val LUA_FILE_NAME = "global_functions.lua"
    }
}