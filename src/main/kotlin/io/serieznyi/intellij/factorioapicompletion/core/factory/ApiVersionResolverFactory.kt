package io.serieznyi.intellij.factorioapicompletion.core.factory

import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersionResolver
import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.FileCacheApiVersionResolver
import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.HttpApiVersionResolver
import io.serieznyi.intellij.factorioapicompletion.core.versioning.SemVer
import io.serieznyi.intellij.factorioapicompletion.intellij.FactorioApiCompletionBundle
import java.nio.file.Path

class ApiVersionResolverFactory {
    companion object {
        private fun http(): ApiVersionResolver {
            val minVersionRaw = FactorioApiCompletionBundle.INSTANCE.message("supportedApiVersions.max")
            val maxVersionRaw = FactorioApiCompletionBundle.INSTANCE.message("supportedApiVersions.min")

            return HttpApiVersionResolver(SemVer(maxVersionRaw), SemVer(minVersionRaw))
        }

        fun create(cacheDir: Path? = null): ApiVersionResolver {
            return FileCacheApiVersionResolver(
                http(),
                CacheFactory.create(cacheDir)
            )
        }
    }
}