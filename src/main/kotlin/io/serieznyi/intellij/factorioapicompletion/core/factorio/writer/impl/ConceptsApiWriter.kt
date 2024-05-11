package io.serieznyi.intellij.factorioapicompletion.core.factorio.writer.impl

import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.ApiData
import io.serieznyi.intellij.factorioapicompletion.core.factorio.writer.ApiWriter
import java.nio.file.Path

class ConceptsApiWriter: ApiWriter {
    override fun write(apiData: ApiData, outputDir: Path) {
        // concepts/*.lua
    }
}