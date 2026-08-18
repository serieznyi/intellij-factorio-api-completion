package io.serieznyi.intellij.factorioapicompletion.core.parser.prototype;

import io.serieznyi.intellij.factorioapicompletion.core.version.ApiVersionResolver;
import io.serieznyi.intellij.factorioapicompletion.core.version.FactorioApiVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.CleanupMode;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

public class PrototypeParserTest {
    private PrototypeParser prototypeParser;
    private FactorioApiVersion version;

    @BeforeEach
    protected void setUp(@TempDir(cleanup = CleanupMode.NEVER) Path tempDir) throws IOException {
        prototypeParser = new PrototypeParser(tempDir);
        version = (new ApiVersionResolver()).supportedVersions().latestVersion();
    }

    void parse(@TempDir(cleanup = CleanupMode.NEVER) Path tempDirArg) throws IOException {
        prototypeParser = new PrototypeParser(tempDirArg);

        prototypeParser.parse(version);
    }
}