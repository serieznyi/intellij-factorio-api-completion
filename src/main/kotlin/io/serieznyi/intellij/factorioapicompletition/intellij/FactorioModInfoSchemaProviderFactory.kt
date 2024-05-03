package io.serieznyi.intellij.factorioapicompletition.intellij

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider
import com.jetbrains.jsonSchema.extension.JsonSchemaProviderFactory
import com.jetbrains.jsonSchema.extension.SchemaType

class FactorioModInfoSchemaProviderFactory : JsonSchemaProviderFactory {
    override fun getProviders(project: Project): List<JsonSchemaFileProvider> {
        return listOf(FactorioSchemaFileProvider())
    }

    internal inner class FactorioSchemaFileProvider : JsonSchemaFileProvider {
        override fun isAvailable(file: VirtualFile): Boolean {
            return true
        }

        override fun getName(): String {
            return "Factorio mod info"
        }

        override fun getSchemaFile(): VirtualFile? {
            return JsonSchemaProviderFactory.getResourceFile(
                FactorioModInfoSchemaProviderFactory::class.java,
                "/mod-info-schema.json"
            )
        }

        override fun getSchemaType(): SchemaType {
            return SchemaType.embeddedSchema
        }
    }
}
