package io.serieznyi.intellij.factorioapicompletition.core.factorio.version

import com.intellij.util.text.SemVer
import io.serieznyi.intellij.factorioapicompletition.core.factorio.version.ApiVersion.Companion.createVersion
import org.jsoup.Jsoup
import java.io.IOException
import java.util.*
import java.util.stream.Collectors

/**
 * Return collection of supported api versions
 *
 * @see ApiVersion
 */
class ApiVersionResolver {
    private val minimalSupportedVersion = SemVer("1.1.100", 1, 1, 100)
    private val maximalSupportedVersion = SemVer("1.1.107", 1, 1, 107)

    @Throws(IOException::class)
    fun supportedVersions(): ApiVersionCollection {
        val allSupportedVersions = allVersions()
            .stream()
            .filter { version: SemVer -> this.isSupported(version) }
            .collect(
                Collectors.toCollection { TreeSet() }
            )
        val collection = ApiVersionCollection()

        for (v in allSupportedVersions) {
            collection.add(createVersion(v.toString()))
        }

        return collection
    }

    private fun isSupported(version: SemVer): Boolean {
        return version in minimalSupportedVersion..maximalSupportedVersion
    }

    @Throws(IOException::class)
    private fun allVersions(): Set<SemVer> {
        val versions = TreeSet<SemVer>()

        val mainPageDoc = Jsoup.connect(VERSION_HTML_PAGE).get()
        val allLinks = mainPageDoc.select("a")
        for (link in allLinks) {
            val semVer = SemVer.parseFromText(link.text()) ?: continue

            versions.add(semVer)
        }

        return versions
    }

    companion object {
        private const val VERSION_HTML_PAGE = "https://lua-api.factorio.com/"
    }
}
