package io.serieznyi.intellij.factorioapicompletion.core.parser.api.data.desirealizer;

public class UnknownComplexTypeException extends RuntimeException {
    private final String complexTypeNativeName;

    public UnknownComplexTypeException(String complexTypeNativeName) {
        super();

        this.complexTypeNativeName = complexTypeNativeName;
    }

    public String getValueType() {
        return complexTypeNativeName;
    }
}
