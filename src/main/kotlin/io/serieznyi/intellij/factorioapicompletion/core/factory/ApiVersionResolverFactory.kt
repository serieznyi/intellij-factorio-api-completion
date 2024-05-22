package io.serieznyi.intellij.factorioapicompletion.core.factory

import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersionResolver
import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.FileCacheApiVersionResolver
import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.HttpApiVersionResolver
import io.serieznyi.intellij.factorioapicompletion.core.versioning.SemVer
import io.serieznyi.intellij.factorioapicompletion.intellij.FactorioApiCompletionBundle

class ApiVersionResolverFactory {
    companion object {
        fun create(): ApiVersionResolver {
            val minVersionRaw = FactorioApiCompletionBundle.INSTANCE.message("supportedApiVersions.max")
            val maxVersionRaw = FactorioApiCompletionBundle.INSTANCE.message("supportedApiVersions.min")

            return FileCacheApiVersionResolver(
                HttpApiVersionResolver(SemVer(maxVersionRaw), SemVer(minVersionRaw)),
                CacheFactory.fileCache()
            )
        }
    }
}