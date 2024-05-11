package io.serieznyi.intellij.factorioapicompletion.intellij

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.jetbrains.jsonSchema.extension.SchemaType

class FactorioModInfoSchemaProviderFactoryTest : BasePlatformTestCase() {

    @org.junit.jupiter.api.BeforeEach
    override fun setUp() {
        super.setUp()
    }

    @org.junit.jupiter.api.AfterEach
    override fun tearDown() {
        super.tearDown()
    }

    @org.junit.jupiter.api.Test
    fun testProviderWork() {
        val service = FactorioModInfoSchemaProviderFactory();
        val project = getProject()
        val providers = service.getProviders(project)

        assertNotEmpty(providers)

        val provider = providers.first()

        assertNotNull(provider.schemaFile) // schema file defined
        assertNotEmpty(provider.name.toList()) // name not empty
        assertTrue(provider.schemaFile!!.exists()) // json file exists
        assertEquals(SchemaType.embeddedSchema, provider.schemaType)
    }
}