package io.serieznyi.intellij.factorioapicompletion.core.factorio.version

import java.io.IOException

/**
 * Return collection of supported api versions
 *
 * @see ApiVersion
 */
interface ApiVersionResolver {
    @Throws(IOException::class)
    fun supportedVersions(): ApiVersionCollection
}