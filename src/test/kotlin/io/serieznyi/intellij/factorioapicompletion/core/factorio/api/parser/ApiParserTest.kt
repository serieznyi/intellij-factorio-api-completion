package io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser

import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersion
import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersionResolver
import org.junit.jupiter.api.AfterEach
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
        assertDoesNotThrow {
            apiParser.parse(apiVersion)
        }
    }

    companion object {
        @JvmStatic
        fun parseProvider(): Stream<ApiVersion> {
            return ApiVersionResolver.instance().supportedVersions().reversed().stream()
        }
    }
}