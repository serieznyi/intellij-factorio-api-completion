package io.serieznyi.intellij.factorioapicompletion.core.factorio.version

import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersion.Companion.createVersion
import io.serieznyi.intellij.factorioapicompletion.core.versioning.SemVer
import io.serieznyi.intellij.factorioapicompletion.intellij.FactorioApiCompletionBundle
import org.jsoup.Jsoup
import java.io.IOException
import java.util.*
import java.util.stream.Collectors

/**
 * Return collection of supported api versions
 *
 * @see ApiVersion
 */
class ApiVersionResolver(
    private val minimalSupportedVersion: SemVer,
    private val maximalSupportedVersion: SemVer
) {
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
            val rawVersion = link.text()

            if (SemVer.isValid(rawVersion)) {
                versions.add(SemVer(rawVersion))
            }
        }

        return versions
    }

    companion object {
        private const val VERSION_HTML_PAGE = "https://lua-api.factorio.com/"

        @JvmStatic
        fun instance(): ApiVersionResolver {
            val minVersionRaw = FactorioApiCompletionBundle.INSTANCE.message("supportedApiVersions.max")
            val maxVersionRaw = FactorioApiCompletionBundle.INSTANCE.message("supportedApiVersions.min")

            return ApiVersionResolver(SemVer(maxVersionRaw), SemVer(minVersionRaw))
        }
    }
}
