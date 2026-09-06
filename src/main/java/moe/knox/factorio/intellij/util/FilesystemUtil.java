package moe.knox.factorio.intellij.util;

import com.intellij.ide.plugins.cl.PluginAwareClassLoader;
import com.intellij.openapi.application.PathManager;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class FilesystemUtil {
    public static @NotNull Path getPluginDir() {
        return PathManager.getPluginsDir().resolve(getPluginId());
    }

    public static String getPluginId() {
        return ((PluginAwareClassLoader) FilesystemUtil.class.getClassLoader()).getPluginDescriptor().getPluginId().getIdString();
    }
}
