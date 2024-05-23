package io.serieznyi.intellij.factorioapicompletion.core.factorio.api.source

import io.serieznyi.intellij.factorioapicompletion.core.cache.Cache
import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersion
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.function.Supplier

class FileCacheDataSourceFactory(
    private val dataSourceFactory: DataSourceFactory,
    private val cache: Cache
) : DataSourceFactory {
    override fun createJsonStreamForRuntime(apiVersion: ApiVersion): InputStream {
        return cacheWrap("runtime.$apiVersion") { dataSourceFactory.createJsonStreamForRuntime(apiVersion) }
    }

    override fun createJsonStreamForPrototype(apiVersion: ApiVersion): InputStream {
        return cacheWrap("prototype.$apiVersion") { dataSourceFactory.createJsonStreamForPrototype(apiVersion) }
    }

    private fun cacheWrap(key: String, supplier: Supplier<InputStream>): InputStream {

        val value = cache.get(key)

        if (value != null) {
            return value.byteInputStream()
        }

        val bytes = supplier.get().readAllBytes()

        cache.set(key, String(bytes))

        return ByteArrayInputStream(bytes)
    }
}
