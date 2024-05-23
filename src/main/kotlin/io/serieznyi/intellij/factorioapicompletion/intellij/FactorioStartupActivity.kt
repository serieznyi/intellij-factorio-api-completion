package io.serieznyi.intellij.factorioapicompletion.intellij

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.EDT
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.application.runInEdt
import com.intellij.openapi.components.service
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FactorioStartupActivity: ProjectActivity {
    override suspend fun execute(project: Project) {
//        ProgressManager.getInstance().run { project.service<ApiService>().downloadApi() }
//
//        runInEdt {
//            project.service<ApiService>().downloadApi()
//        }
//
//        WriteAction.run<RuntimeException> {
//            ApplicationManager.getApplication().runWriteAction {
//
//            }
//        }
    }
}