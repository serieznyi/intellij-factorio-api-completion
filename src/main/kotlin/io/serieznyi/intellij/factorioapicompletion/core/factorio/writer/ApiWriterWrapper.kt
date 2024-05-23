package io.serieznyi.intellij.factorioapicompletion.core.factorio.writer

import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.ApiData
import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersion
import io.serieznyi.intellij.factorioapicompletion.core.factorio.writer.impl.*
import io.serieznyi.intellij.factorioapicompletion.core.util.io.findOrCreateDirectory
import io.serieznyi.intellij.factorioapicompletion.core.util.io.isDirectoryNotEmpty
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.isDirectory

class ApiWriterWrapper {
    fun writeAll(version: ApiVersion, apiData: ApiData, dirPath: Path, force: Boolean = false) {
        if (!dirPath.isDirectory()) {
            throw IllegalArgumentException("Path is not directory: $dirPath")
        }

        val versionDir = dirPath.resolve(version.version).findOrCreateDirectory()

        if (!force && versionDir.isDirectoryNotEmpty()) {
            return
        }

        clearDirectory(dirPath)

        val apiWriter: ApiWriter = ChainApiWriter(listOf(
            GlobalObjectsApiWriter(),
            GlobalFunctionsApiWriter(),
            DefinesApiWriter(),
//            BuiltinTypesApiWriter(), // TODO add later
//            ClassesApiWriter(),
//            ConceptsApiWriter(),
//            EventsApiWriter(),
//            TypesApiWriter(),
            PrototypesApiWriter(),
        ))

        apiWriter.write(apiData, versionDir.findOrCreateDirectory())
    }

    private fun clearDirectory(dirPath: Path) {
        Files.list(dirPath).toList().forEach { it.toFile().deleteRecursively() }
    }
}