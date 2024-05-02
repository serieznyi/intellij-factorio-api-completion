package io.serieznyi.intellij.factorioapicompletition.core.factorio.version

import java.util.*

class ApiVersionCollection : TreeSet<ApiVersion>() {
    fun latestVersion(): ApiVersion {
        return this.first()
    }
}
