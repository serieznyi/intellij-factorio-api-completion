package io.serieznyi.intellij.factorioapicompletion.core.factorio.version

import com.intellij.util.text.SemVer

@JvmRecord
data class ApiVersion(val version: String) : Comparable<ApiVersion?> {
    override fun equals(other: Any?): Boolean {
        if (other !is ApiVersion) {
            return false
        }

        return version == other.version
    }

    override fun compareTo(other: ApiVersion?): Int {
        if (other == null) {
            return 0
        }

        val semVerCurrent = SemVer.parseFromText(version)
        val semVerOther = SemVer.parseFromText(other.version)

        if (semVerCurrent == null || semVerOther == null) {
            throw IllegalArgumentException("Wrong semVer for current or other version");
        }

        return semVerCurrent.compareTo(semVerOther)
    }

    override fun toString(): String {
        return version
    }

    override fun hashCode(): Int {
        return version.hashCode()
    }

    companion object {
        fun createVersion(version: String): ApiVersion {
            return ApiVersion(version)
        }
    }
}