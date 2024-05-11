package io.serieznyi.intellij.factorioapicompletion.core.util.io

import com.intellij.util.io.createDirectories
import java.nio.file.Path
import kotlin.io.path.exists


fun Path.findOrCreateDirectory(): Path {
    if (!this.exists()) {
        this.createDirectories()
    }

    return this
}


fun Path.isDirectoryNotEmpty(): Boolean {
    if (!this.exists()) {
        return false
    }

    val children = this.toFile().list()

    return children != null && children.isNotEmpty()
}