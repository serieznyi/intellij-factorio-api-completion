package io.serieznyi.intellij.factorioapicompletion.core.versioning

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class SemVerTest {
    @ParameterizedTest
    @MethodSource("compareToProvider")
    fun compareTo(v1: SemVer, v2: SemVer, result: Int) {
        Assertions.assertEquals(result, v1.compareTo(v2))
    }

    companion object {
        @JvmStatic
        fun compareToProvider(): Array<Array<Any>> {
            return arrayOf(
                arrayOf(SemVer("1.0.0"), SemVer("1.0.0"), 0),
                arrayOf(SemVer("0.1.0"), SemVer("0.1.0"), 0),
                arrayOf(SemVer("0.0.1"), SemVer("0.0.1"), 0),
                arrayOf(SemVer("0.0.2"), SemVer("0.0.1"), 1),
                arrayOf(SemVer("1.0.0"), SemVer("0.0.1"), 1),
                arrayOf(SemVer("2.0.0"), SemVer("1.0.0"), 1),
                arrayOf(SemVer("0.0.1"), SemVer("0.0.2"), -1),
                arrayOf(SemVer("0.0.1"), SemVer("1.0.0"), -1),
                arrayOf(SemVer("1.0.0"), SemVer("2.0.0"), -1),
            )
        }
    }
}