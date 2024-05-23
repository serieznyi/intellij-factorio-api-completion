package io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser

import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersion
import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersionResolver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class ApiParserTest {
    private val apiParser = ApiParser(throwOnUnknownType = true);

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @ParameterizedTest
    @MethodSource("parseProvider")
    fun parse(apiVersion: ApiVersion) {
        val apiData = apiParser.parse(apiVersion)
        val runtimeApiData = apiData.runtimeApiData
        val prototypeApiData = apiData.prototypeApiData

        assertFalse(runtimeApiData.events.isEmpty())
        assertFalse(runtimeApiData.classes.isEmpty())
        assertFalse(runtimeApiData.defines.isEmpty())
        assertFalse(runtimeApiData.concepts.isEmpty())
        assertFalse(runtimeApiData.builtinTypes.isEmpty())
        assertFalse(runtimeApiData.globalFunctions.isEmpty())
        assertFalse(runtimeApiData.globalObjects.isEmpty())

        assertFalse(prototypeApiData.prototypes.isEmpty())
        assertFalse(prototypeApiData.types.isEmpty())
    }

    companion object {
        @JvmStatic
        fun parseProvider(): Stream<ApiVersion> {
            return ApiVersionResolver.instance().supportedVersions().reversed().stream()
        }
    }
}