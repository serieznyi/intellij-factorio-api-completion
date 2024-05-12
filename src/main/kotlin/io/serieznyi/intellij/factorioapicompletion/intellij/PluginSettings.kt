package io.serieznyi.intellij.factorioapicompletion.intellij

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.Converter
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.util.xmlb.annotations.OptionTag
import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersion
import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersionResolverHolder

@Service(Service.Level.PROJECT)
@State(name = "FactorioApiCompletionSettings", storages = [(Storage("factorio-api-completion-settings.xml"))])
class PluginSettings : PersistentStateComponent<PluginSettings> {
    var useLatestApiVersion: Boolean = true

    @OptionTag(converter = ApiVersionConverter::class)
    var selectedApiVersion: ApiVersion = (ApiVersionResolverHolder.get().supportedVersions().latestVersion())

    override fun getState(): PluginSettings {
        return this
    }

    override fun loadState(state: PluginSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }

    private class ApiVersionConverter : Converter<ApiVersion?>() {
        override fun fromString(value: String): ApiVersion {
            return ApiVersion.createVersion(value)
        }

        override fun toString(value: ApiVersion): String {
            return value.version
        }
    }
}