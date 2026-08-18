package io.serieznyi.intellij.factorioapicompletion.intellij;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import io.serieznyi.intellij.factorioapicompletion.core.PrototypesService;
import io.serieznyi.intellij.factorioapicompletion.intellij.service.ApiService;
import io.serieznyi.intellij.factorioapicompletion.intellij.service.FactorioDataService;
import io.serieznyi.intellij.factorioapicompletion.intellij.service.PrototypeService;
import org.jetbrains.annotations.NotNull;

public class FactorioStartupActivity implements StartupActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        FactorioState config = FactorioState.getInstance(project);

        if (config.integrationActive) {
            boolean update = FactorioDataService.getInstance(project).checkForUpdate();
            ApiService.getInstance(project).checkForUpdate();

            if (update) {
                PrototypeService.getInstance(project).removeLibraryFiles();
                PrototypeService.getInstance(project).checkForUpdate();
            }

            // reload core/base prototypes
            PrototypesService.getInstance(project).reloadIndex();
        }
    }
}
