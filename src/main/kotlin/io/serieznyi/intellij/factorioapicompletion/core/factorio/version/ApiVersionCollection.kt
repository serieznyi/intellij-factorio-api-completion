package io.serieznyi.intellij.factorioapicompletion.core.factorio.version

import java.util.*

class ApiVersionCollection : TreeSet<ApiVersion>() {
    fun latestVersion(): ApiVersion {
        return this.maxOf { it }
    }
}
