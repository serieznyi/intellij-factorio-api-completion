package io.serieznyi.intellij.factorioapicompletion.core.factorio.version

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class ApiVersionCollectionTest {

    @ParameterizedTest
    @MethodSource("latestVersionProvider")
    fun latestVersion(versions: Array<ApiVersion>, latestVersion: ApiVersion) {
        val collection = ApiVersionCollection()
        collection.addAll(versions)

        assertEquals(latestVersion, collection.latestVersion())
    }

    companion object {
        @JvmStatic
        fun latestVersionProvider(): Array<Array<Any>> {
            return arrayOf(
                arrayOf(
                    arrayOf(
                        ApiVersion("0.0.1"),
                        ApiVersion("0.1.0")
                    ),
                    ApiVersion("0.1.0")
                ),
                arrayOf(
                    arrayOf(
                        ApiVersion("1.0.0"),
                        ApiVersion("2.0.0")
                    ),
                    ApiVersion("2.0.0")
                ),
                arrayOf(
                    arrayOf(
                        ApiVersion("1.0.0"),
                        ApiVersion("0.0.1")
                    ),
                    ApiVersion("1.0.0")
                ),
                arrayOf(
                    arrayOf(
                        ApiVersion("0.1.0"),
                        ApiVersion("0.0.2")
                    ),
                    ApiVersion("0.1.0")
                ),
            )
        }
    }
}