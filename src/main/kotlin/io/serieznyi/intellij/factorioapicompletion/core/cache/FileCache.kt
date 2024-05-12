package io.serieznyi.intellij.factorioapicompletion.core.cache

import com.google.common.hash.Hashing
import java.lang.System.currentTimeMillis
import java.nio.file.Files
import java.nio.file.Path
import java.time.Duration
import kotlin.io.path.*

class FileCache(private val cacheDir: Path, private val defaultTtl: Duration = Duration.ofMinutes(1)): Cache {
    init {
        require(cacheDir.isDirectory()) { "cachePath is not dir: $cacheDir" }
        require(cacheDir.exists()) { "cache dir is not exists: $cacheDir" }
    }

    override fun has(key: String): Boolean {
        val cacheFile = cacheDir.resolve(hashKey(key))

        return !isExpired(cacheFile)
    }

    override fun set(key: String, value: String, ttl: Duration?) {
        val cacheFile = cacheDir.resolve(hashKey(key))
        val expiredAt = (currentTimeMillis() + resolveTtl(ttl))

        try {
            cacheFile.writeLines(listOf(
                expiredAt.toString(),
                value
            ))
        } catch (e: Throwable) {
            cacheFile.deleteIfExists()
        }

        garbageCollect()
    }

    private fun resolveTtl(ttl: Duration?): Long {
        if (ttl == null) {
            return defaultTtl.toMillis()
        }

        return ttl.toMillis()
    }

    override fun get(key: String): String? {
        val cacheFile = cacheDir.resolve(hashKey(key))

        if (isExpired(cacheFile)) {
            return null
        }

        return cacheFile.readLines().last()
    }

    private fun garbageCollect() {
        Files.list(cacheDir).toList().filter { it.isRegularFile() } . forEach { if (isExpired(it)) it.deleteExisting() }
    }

    private fun isExpired(cacheFile: Path): Boolean {
        if (!cacheFile.exists()) {
            return true
        }

        val expiredTime = cacheFile.readLines().first().toLong()

        return currentTimeMillis() > expiredTime
    }

    private fun hashKey(key: String) = Hashing.sha256().hashString(key, Charsets.UTF_8).toString()
}