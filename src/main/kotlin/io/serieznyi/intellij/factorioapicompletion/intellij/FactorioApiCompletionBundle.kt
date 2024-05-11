package io.serieznyi.intellij.factorioapicompletion.intellij

import com.intellij.DynamicBundle
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.PropertyKey


class FactorioApiCompletionBundle() : DynamicBundle(BUNDLE_FILE) {
    fun message(
        @NotNull @PropertyKey(resourceBundle = BUNDLE_FILE) key: String,
        @NotNull vararg params: Any
    ): String {
        return INSTANCE.getMessage(key, params)
    }

    companion object {
        private const val BUNDLE_FILE = "FactorioApiCompletion"
        val INSTANCE = FactorioApiCompletionBundle()
    }
}