package io.serieznyi.intellij.factorioapicompletion.core.factorio.version

import io.serieznyi.intellij.factorioapicompletion.core.versioning.SemVer
import io.serieznyi.intellij.factorioapicompletion.intellij.FactorioApiCompletionBundle

class ApiVersionResolverHolder() {
    var apiVersionResolver: ApiVersionResolver

    init {
        val minVersionRaw = FactorioApiCompletionBundle.INSTANCE.message("supportedApiVersions.max")
        val maxVersionRaw = FactorioApiCompletionBundle.INSTANCE.message("supportedApiVersions.min")

        apiVersionResolver = HttpApiVersionResolver(SemVer(maxVersionRaw), SemVer(minVersionRaw))
    }

    companion object {
        private val INSTANCE = ApiVersionResolverHolder();
        @JvmStatic
        fun get(): ApiVersionResolver {
            return INSTANCE.apiVersionResolver
        }
    }
}