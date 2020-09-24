package com.yan.asmlocal;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.ApplicationVariant;

import org.gradle.api.DomainObjectSet;
import org.gradle.api.Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since 2020/9/24
 */
public class PrivacyProviderFind {

    public static void find(Project project) {
        AppExtension appExtension = (AppExtension) project.getProperties().get("android");
        DomainObjectSet<ApplicationVariant> variants = appExtension.getApplicationVariants();

        variants.all(bv -> bv.getOutputs().all(baseVariantOutput -> {
            KernelLog.info("BaseVariantOutput   " + baseVariantOutput);
            baseVariantOutput.getProcessManifestProvider().get().doLast(task -> {
                KernelLog.info("BaseVariantOutput   " + task);
                File manifest = baseVariantOutput.getProcessManifestProvider()
                        .get()
                        .getManifestOutputDirectory()
                        .get()
                        .file("AndroidManifest.xml")
                        .getAsFile();

                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(manifest));

                    StringBuilder manifestStr = new StringBuilder();
                    bufferedReader.lines().forEach(manifestStr::append);
                    bufferedReader.close();
                    KernelLog.info("manifestStr  " + manifestStr.toString());


                    Pattern pattern = Pattern.compile("<provider\\s*android:name\\s*=\\s*\"[^\"]+\"");

                    Matcher matcher = pattern.matcher(manifestStr);
                    while (matcher.find()) {
                        String providerClass = matcher.group().replaceAll("<provider\\s*android:name\\s*=\\s*", "").replace("\"", "").trim();
                        if (replaceClass.contains(providerClass)) continue;

                        replaceClass.add(providerClass);
                    }
                    KernelLog.info("replaceClass  " + replaceClass.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }));

    }

    public static ArrayList<String> replaceClass = new ArrayList<>();

}
