package io.serieznyi.intellij.factorioapicompletion.intellij.service;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import lombok.CustomLog;
import io.serieznyi.intellij.factorioapicompletion.intellij.NotificationService;
import io.serieznyi.intellij.factorioapicompletion.core.PrototypesService;
import io.serieznyi.intellij.factorioapicompletion.core.parser.prototype.PrototypeParser;
import io.serieznyi.intellij.factorioapicompletion.core.version.FactorioApiVersion;
import io.serieznyi.intellij.factorioapicompletion.intellij.FactorioLibraryProvider;
import io.serieznyi.intellij.factorioapicompletion.intellij.FactorioState;
import io.serieznyi.intellij.factorioapicompletion.intellij.util.FilesystemUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@CustomLog
public class PrototypeService {
    private final AtomicBoolean downloadInProgress = new AtomicBoolean(false);
    private final Project project;
    private final PrototypeParser prototypeParser;

    private PrototypeService(Project project) {
        this.project = project;

        Path pluginDir = FilesystemUtil.getPluginDir();
        Path prototypesRootPath = pluginDir.resolve("factorio_prototypes");
        prototypeParser = new PrototypeParser(prototypesRootPath);
    }

    public static PrototypeService getInstance(Project project)
    {
        return new PrototypeService(project);
    }


    /**
     * @return return path only if it not empty
     */
    public Optional<Path> getPrototypePath() {
        if (downloadInProgress.get()) {
            return Optional.empty();
        }

        FactorioApiVersion version = FactorioState.getInstance(project).selectedFactorioVersion;

        var path = prototypeParser.getPrototypePath(version);

        if (path.isEmpty() && downloadInProgress.compareAndSet(false, true)) {
            ProgressManager.getInstance().run(new PrototypeService.PrototypeTask());
        }

        return path;
    }

    public void removeLibraryFiles() {
        if (downloadInProgress.get()) {
            return;
        }

        prototypeParser.removeFiles();
        PrototypesService.getInstance(project).reloadIndex();
    }

    public void checkForUpdate() {
        FactorioApiVersion selectedVersion = FactorioState.getInstance(project).selectedFactorioVersion;

        Optional<Path> path = prototypeParser.getPrototypePath(selectedVersion);

        if (path.isEmpty() && downloadInProgress.compareAndSet(false, true)) {
            ProgressManager.getInstance().run(new PrototypeService.PrototypeTask());
        }
    }

    public List<String> parsePrototypeTypes() throws IOException {
        return prototypeParser.parsePrototypeTypes();
    }

    private class PrototypeTask extends Task.Backgroundable {
        public PrototypeTask() {
            super(project, "Download and Parse Factorio Prototypes", false);
        }

        @Override
        public void run(@NotNull ProgressIndicator indicator) {
            try {
                FactorioApiVersion selectedVersion = FactorioState.getInstance(project).selectedFactorioVersion;

                prototypeParser.parse(selectedVersion);

                ApplicationManager.getApplication().invokeLater(FactorioLibraryProvider::reload);
            } catch (Throwable e) {
                log.error(e);
                NotificationService.getInstance(project).notifyErrorPrototypeUpdating();
            } finally {
                downloadInProgress.set(false);
            }
        }
    }
}
