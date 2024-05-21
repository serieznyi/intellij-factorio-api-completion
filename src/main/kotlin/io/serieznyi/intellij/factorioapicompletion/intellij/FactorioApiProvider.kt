package io.serieznyi.intellij.factorioapicompletion.intellij

import com.intellij.navigation.ItemPresentation
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.AdditionalLibraryRootsProvider
import com.intellij.openapi.roots.SyntheticLibrary
import com.intellij.openapi.roots.ex.ProjectRootManagerEx
import com.intellij.openapi.util.EmptyRunnable
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.stubs.StubIndex
import com.tang.intellij.lua.lang.LuaIcons
import java.nio.file.Path
import javax.swing.Icon

class FactorioApiProvider: AdditionalLibraryRootsProvider() {
    override fun getAdditionalProjectLibraries(project: Project): Collection<SyntheticLibrary> {
//        val apiPath = project.service<ApiService>().apiDir()
        val libs = ArrayList<FactorioLibrary>()

//        if (apiPath.exists()) {
//            libs.add(createLibrary(apiPath, "Factorio API"))
//        }

        return libs
    }

    companion object {
        fun reload(project: Project) {
//            ApplicationManager.getApplication().invokeLater({
//                StubIndex.getInstance().forceRebuild(Throwable("Factorio API changed."))
//
//                ProjectRootManagerEx.getInstanceEx(project).makeRootsChange(EmptyRunnable.getInstance(), false, true)
//            }, project.disposed)


            // todo trigger update index event

//            StubIndex.getInstance().forceRebuild(Throwable("Factorio API changed."))
//
//            WriteAction.run<RuntimeException> {
//                ProjectRootManagerEx.getInstanceEx(project).makeRootsChange(EmptyRunnable.getInstance(), false, true)
//            }
        }
    }

    private fun createLibrary(libraryDir: Path, libraryName: String): FactorioLibrary {
        val virtualLibraryDir = VfsUtil.findFileByIoFile(libraryDir.toFile(), true) ?: throw IllegalArgumentException("Wrong lib dir: $libraryDir")

//        virtualLibraryDir.children.forEach { it.putUserData(LuaFileUtil.PREDEFINED_KEY, true) }

        return FactorioLibrary(virtualLibraryDir, libraryName)
    }

    private class FactorioLibrary(var root: VirtualFile, var libraryName: String) : SyntheticLibrary(), ItemPresentation {
        override fun hashCode(): Int {
            return root.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            return (other is FactorioLibrary) && other.root == root
        }

        override fun getSourceRoots(): Collection<VirtualFile> {
            return listOf(root)
        }

        override fun getLocationString(): String {
            return "Factorio library"
        }

        override fun getIcon(unused: Boolean): Icon? {
            return LuaIcons.FILE
        }

        override fun getPresentableText(): String {
            return libraryName
        }
    }
}