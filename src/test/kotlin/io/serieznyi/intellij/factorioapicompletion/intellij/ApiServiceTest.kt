package io.serieznyi.intellij.factorioapicompletion.intellij

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertDoesNotThrow

class ApiServiceTest : BasePlatformTestCase() {
    lateinit var apiService: ApiService

    @BeforeEach
    override fun setUp() {
        super.setUp()

        apiService = ApiService(project)
    }

    @Test
    fun downloadApi() {
        assertDoesNotThrow {
            apiService.downloadApi()
        }
    }
}