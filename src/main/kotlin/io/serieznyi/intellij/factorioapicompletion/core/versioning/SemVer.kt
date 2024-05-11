package io.serieznyi.intellij.factorioapicompletion.core.versioning

class SemVer(): Comparable<SemVer> {
    private lateinit var rawVersion: String

    constructor(rawVersion: String) : this() {
        this.rawVersion = checkRawVersion(rawVersion)
    }

    override operator fun compareTo(other: SemVer): Int {
        val thisVersion = com.intellij.util.text.SemVer.parseFromText(rawVersion)
        val otherVersion = com.intellij.util.text.SemVer.parseFromText(other.rawVersion)

        return thisVersion!!.compareTo(otherVersion)
    }

    override fun toString(): String {
        return this.rawVersion
    }

    companion object {
        fun checkRawVersion(rawVersion: String): String {
            if (!isValid(rawVersion)) {
                throw IllegalArgumentException("Wrong version")
            }

            return rawVersion
        }

        fun isValid(rawVersion: String): Boolean {
            val semVer = com.intellij.util.text.SemVer.parseFromText(rawVersion)

            return semVer != null
        }
    }

}