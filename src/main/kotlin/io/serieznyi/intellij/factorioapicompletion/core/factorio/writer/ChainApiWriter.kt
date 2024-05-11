package io.serieznyi.intellij.factorioapicompletion.core.factorio.writer

import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.data.ApiData
import java.nio.file.Path

class ChainApiWriter(private val writers: List<ApiWriter>): ApiWriter {
    override fun write(apiData: ApiData, outputDir: Path) {
        for (writer in this.writers) {
            writer.write(apiData, outputDir)
        }
    }
}