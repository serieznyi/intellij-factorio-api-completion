package io.serieznyi.intellij.factorioapicompletition.intellij

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class FactorioModInfoSchemaProviderFactoryTest: BasePlatformTestCase() {

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
        val project = getProject();
        val providers = service.getProviders(project)
        val provider = providers[0];

        assertNotNull(provider.schemaFile);
        assertTrue(provider.schemaFile!!.exists());
    }
}