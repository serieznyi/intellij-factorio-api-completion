package io.serieznyi.intellij.factorioapicompletion.core.cache

import java.time.Duration

interface Cache {
    fun has(key: String): Boolean
    fun set(key: String, value: String, ttl: Duration? = null)
    fun get(key: String): String?
}