package io.serieznyi.intellij.factorioapicompletion.intellij

import com.intellij.navigation.ItemPresentation
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ex.ProjectManagerEx
import com.intellij.openapi.roots.AdditionalLibraryRootsProvider
import com.intellij.openapi.roots.SyntheticLibrary
import com.intellij.openapi.roots.ex.ProjectRootManagerEx
import com.intellij.openapi.util.EmptyRunnable
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.stubs.StubIndex
import com.tang.intellij.lua.lang.LuaIcons
import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersion
import javax.swing.Icon

class FactorioApiProvider: AdditionalLibraryRootsProvider() {

    override fun getAdditionalProjectLibraries(project: Project): Collection<SyntheticLibrary> {
        return listOf()
    }

    companion object {
        fun reload() {
            WriteAction.run<RuntimeException> {
                val openProjects = ProjectManagerEx.getInstanceEx().openProjects
                for (openProject in openProjects) {
                    ProjectRootManagerEx
                        .getInstanceEx(openProject)
                        .makeRootsChange(EmptyRunnable.getInstance(), false, true)
                }

                StubIndex.getInstance().forceRebuild(Throwable("Factorio API changed"))
            }
        }
    }

    class FactorioLibrary : SyntheticLibrary(), ItemPresentation {
        lateinit var root: VirtualFile
        lateinit var apiVersion: ApiVersion

        fun FactorioLibrary(root: VirtualFile, apiVersion: ApiVersion) {
            this.root = root
            this.apiVersion = apiVersion
        }

        override fun equals(other: Any?): Boolean {
            return (other is FactorioLibrary) && other.root == root
        }

        override fun hashCode(): Int {
            return root.hashCode()
        }

        override fun getPresentableText(): String {
            return apiVersion.toString()
        }

        override fun getIcon(unused: Boolean): Icon? {
            return LuaIcons.FILE
        }

        override fun getSourceRoots(): List<VirtualFile> {
            return listOf(root)
        }
    }
}