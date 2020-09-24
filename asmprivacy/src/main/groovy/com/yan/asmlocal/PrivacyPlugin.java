package com.yan.asmlocal;

import com.android.build.gradle.AppExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.Collections;

public class PrivacyPlugin implements Plugin<Project> {

    @SuppressWarnings("NullableProblems")
    @Override
    public void apply(Project project) {
        KernelLog.info("PrivacyPlugin PrivacyPlugin PrivacyPlugin PrivacyPlugin  ");

        //check is library or application
        boolean hasAppPlugin = project.getPlugins().hasPlugin("com.android.application");
        if (!hasAppPlugin) return;

        AppExtension appExtension = (AppExtension) project.getProperties().get("android");
        appExtension.registerTransform(new AsmPrivacyTransform(project), Collections.EMPTY_LIST);

        PrivacyProviderFind.find(project);
    }
}
