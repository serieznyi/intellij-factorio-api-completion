package io.serieznyi.intellij.factorioapicompletion.core.parser.api;

import io.serieznyi.intellij.factorioapicompletion.core.parser.api.data.RuntimeApi;
import io.serieznyi.intellij.factorioapicompletion.core.version.ApiVersionResolver;
import io.serieznyi.intellij.factorioapicompletion.core.version.FactorioApiVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RuntimeApiParserTest {
    private RuntimeApiParser service;

    public static Set<FactorioApiVersion> providerVersions() throws IOException {
        return (new ApiVersionResolver()).supportedVersions();
    }

    @BeforeEach
    protected void setUp() {
        service = new RuntimeApiParser();
    }

    @ParameterizedTest
    @MethodSource("providerVersions")
    void parse(FactorioApiVersion version) throws IOException {
        RuntimeApi runtimeApi = service.parse(version);

        assertNotNull(runtimeApi);
        assertNotNull(runtimeApi.api_version);
        assertNotNull(runtimeApi.classes);
        assertNotNull(runtimeApi.concepts);
        assertNotNull(runtimeApi.defines);
    }
}