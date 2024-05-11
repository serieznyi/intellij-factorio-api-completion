package io.serieznyi.intellij.factorioapicompletion.core.factorio.writer

import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.ApiData
import java.nio.file.Path

interface ApiWriter {
    fun write(apiData: ApiData, outputDir: Path);
}