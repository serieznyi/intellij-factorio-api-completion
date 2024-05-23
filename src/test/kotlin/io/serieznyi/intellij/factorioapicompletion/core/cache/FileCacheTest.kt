package io.serieznyi.intellij.factorioapicompletion.core.cache

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.io.CleanupMode
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import java.time.Duration

class FileCacheTest {

    private lateinit var cache: Cache

    @BeforeEach
    fun setUp(@TempDir(cleanup = CleanupMode.NEVER) tempDir: Path) {
        cache = FileCache(tempDir)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun has() {
        assertFalse(cache.has("key1"))

        cache.set("key1", "value1")

        assertTrue(cache.has("key1"))

    }

    @Test
    fun set() {
        assertFalse(cache.has("key1"))

        cache.set("key1", "value1")

        assertTrue(cache.has("key1"))
    }

    @Test
    fun get() {
        assertNull(cache.get("key1"))

        cache.set("key1", "value1")

        assertEquals("value1", cache.get("key1"))
    }

    @Test
    fun getWithTtl() {
        assertNull(cache.get("key1"))

        cache.set("key1", "value1", Duration.ofMillis(500))

        assertEquals("value1", cache.get("key1"))

        Thread.sleep(Duration.ofMillis(750).toMillis())

        assertNull(cache.get("key1"))
    }
}