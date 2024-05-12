package io.serieznyi.intellij.factorioapicompletion.core.factorio.version

import io.serieznyi.intellij.factorioapicompletion.core.cache.Cache

class FileCacheApiVersionResolver(
    private val apiVersionResolver: ApiVersionResolver,
    private val cache: Cache,
) : ApiVersionResolver {
    override fun supportedVersions(): ApiVersionCollection {
        val key = "api_versions"
        val value = cache.get(key)

        if (value != null) {
            return value.lines().map { ApiVersion(it) }.toCollection(ApiVersionCollection())
        }

        val result = apiVersionResolver.supportedVersions();

        cache.set(key, result.joinToString(System.lineSeparator()) { it.toString() })

        return result
    }
}