package io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser.deserializer.valueType

class UnknownComplexTypeException(valueType: String) : RuntimeException("Unknown complex type: $valueType")
