package io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser.deserializer.valueType

class UnknownComplexTypeException(valueType: String) : RuntimeException("Unknown complex type: $valueType")
