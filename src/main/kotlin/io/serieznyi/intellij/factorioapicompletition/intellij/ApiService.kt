package io.serieznyi.intellij.factorioapicompletition.intellij

import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.components.Service

@Service(Service.Level.PROJECT)
class ApiService {
    fun reloadApi() {
        // todo remove api
        // todo download new api
    }

    fun triggerReindex() {
        WriteAction.run<RuntimeException>(FactorioApiProvider::reload)
    }
}
