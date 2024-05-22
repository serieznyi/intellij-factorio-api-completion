package io.serieznyi.intellij.factorioapicompletion.core.factory

import io.serieznyi.intellij.factorioapicompletion.core.cache.Cache
import io.serieznyi.intellij.factorioapicompletion.core.cache.FileCache
import io.serieznyi.intellij.factorioapicompletion.intellij.FilesystemUtil
import java.nio.file.Path

class CacheFactory {
    companion object {
        fun fileCache(cacheDir: Path = FilesystemUtil.cacheDir()): Cache {
            return FileCache(cacheDir)
        }
    }
}