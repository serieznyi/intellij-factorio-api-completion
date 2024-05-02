package io.serieznyi.intellij.factorioapicompletition.intellij.ui;

import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import io.serieznyi.intellij.factorioapicompletition.intellij.ApiService;
import io.serieznyi.intellij.factorioapicompletition.core.factorio.version.ApiVersion;
import io.serieznyi.intellij.factorioapicompletition.core.factorio.version.ApiVersionResolver;
import io.serieznyi.intellij.factorioapicompletition.intellij.PluginSettings;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;

class PluginConfigurable implements SearchableConfigurable {
    private final PluginSettings pluginSettings;
    private final Project project;
    private final ApiVersionResolver apiVersionResolver;
    private JPanel rootPanel;
    private JComboBox<DropdownVersion> selectApiVersionDropdown;
    private JLabel loadApiErrorLabel;
    private JButton reloadButton;
    private JLabel selectApiVersionLabel;

    public PluginConfigurable(Project project) {
        pluginSettings = project.getService(PluginSettings.class);
        this.project = project;

        apiVersionResolver = new ApiVersionResolver();

        buildUiComponents();
    }

    @Override
    public JComponent createComponent() {
        return rootPanel;
    }

    @Override
    public boolean isModified() {
        try {
            return isVersionChanged();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void apply() {
        try {
            var apiService = project.getService(ApiService.class);

            if (isVersionChanged()) {
                apiService.reloadApi();
            }

            pluginSettings.setUseLatestApiVersion(isUseLatestVersion());
            pluginSettings.setSelectedApiVersion(getSelectedVersion());

            apiService.triggerReindex();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull String getDisplayName() {
        return "Factorio Api Completion";
    }

    @Override
    public @NotNull String getId() {
        return "preference.PluginConfig";
    }

    private void buildUiComponents() {
        try {
            selectApiVersionDropdown.addItem(DropdownVersion.createLatest());
            apiVersionResolver
                    .supportedVersions()
                    .stream()
                    .sorted(Comparator.reverseOrder())
                    .map(DropdownVersion::fromApiVersion)
                    .forEach(v -> selectApiVersionDropdown.addItem(v))
            ;

            if (pluginSettings.getUseLatestApiVersion()) {
                selectApiVersionDropdown.setSelectedItem(selectApiVersionDropdown.getItemAt(0));
            } else {
                var dropdownVersion = DropdownVersion.fromApiVersion(pluginSettings.getSelectedApiVersion());

                selectApiVersionDropdown.setSelectedItem(dropdownVersion);
            }

            selectApiVersionDropdown.setEnabled(true);
            loadApiErrorLabel.setVisible(false);
            reloadButton.setEnabled(true);
        }
        catch (Exception e) {
            selectApiVersionDropdown.setEnabled(false);
            loadApiErrorLabel.setText(
                "Error loading Factorio versions. You need to have active internet connection to change these settings"
            );
            loadApiErrorLabel.setVisible(true);
            reloadButton.setEnabled(false);
        }

        reloadButton.addActionListener(actionEvent -> {
            project.getService(ApiService.class).reloadApi();
        });
    }

    private boolean isUseLatestVersion() {
        var version = (DropdownVersion) selectApiVersionDropdown.getSelectedItem();

        Objects.requireNonNull(version);

        return version.isLatest();
    }

    private boolean isVersionChanged() throws IOException {
        ApiVersion selectedApiVersion = pluginSettings.getSelectedApiVersion();

        return selectedApiVersion != getSelectedVersion();
    }

    private ApiVersion getSelectedVersion() throws IOException {
        var dropdownVersion = (DropdownVersion) selectApiVersionDropdown.getSelectedItem();

        Objects.requireNonNull(dropdownVersion);

        if (dropdownVersion.isLatest()) {
            return getLatestExistingVersion();
        }

        return ApiVersion.Companion.createVersion(dropdownVersion.version);
    }

    private ApiVersion getLatestExistingVersion() throws IOException {
        return apiVersionResolver.supportedVersions().latestVersion();
    }

    private record DropdownVersion(String version, String name) {
        public static DropdownVersion createLatest() {
            return new DropdownVersion("latest", "Latest version");
        }

        public static DropdownVersion fromApiVersion(ApiVersion v) {
            return new DropdownVersion(v.version(), v.version());
        }

        public boolean isLatest() {
            return version.equals("latest");
        }

        @Override
        public String toString() {
            return name;
        }
    }
}