package com.yan.asmlocal;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.ApplicationVariant;

import org.gradle.api.DomainObjectSet;
import org.gradle.api.Project;
import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

                KernelLog.info("BaseVariantOutput   " + manifest.getAbsolutePath());
                try {
                    parseXml(manifest);
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                try {
//                    XmlParser parser = new XmlParser(new SAXParser())
//                    BufferedReader bufferedReader = new BufferedReader(new FileReader(manifest));
//
//                    StringBuilder manifestStr = new StringBuilder();
//                    bufferedReader.lines().forEach(manifestStr::append);
//                    bufferedReader.close();
//                    KernelLog.info("manifestStr  " + manifestStr.toString());
//
//                    Pattern pattern = Pattern.compile("<provider\\s*android:name\\s*=\\s*\"[^\"]+\"");
//                    Matcher matcher = pattern.matcher(manifestStr);
//                    while (matcher.find()) {
//                        String providerClass = matcher.group().replaceAll("<provider\\s*android:name\\s*=\\s*", "").replace("\"", "").trim();
//                        if (providers.contains(providerClass)) continue;
//                        providers.add(providerClass);
//                    }
//                    KernelLog.info("providers  " + providers.toString());
//
//                    pattern = Pattern.compile("<service\\s*android:name\\s*=\\s*\"[^\"]+\"");
//                    matcher = pattern.matcher(manifestStr);
//                    while (matcher.find()) {
//                        String service = matcher.group().replaceAll("<service\\s*android:name\\s*=\\s*", "").replace("\"", "").trim();
//                        if (services.contains(service)) continue;
//                        services.add(service);
//                    }
//                    KernelLog.info("services  " + services.toString());
//
//                    pattern = Pattern.compile("<receiver\\s*android:name\\s*=\\s*\"[^\"]+\"");
//                    matcher = pattern.matcher(manifestStr);
//                    while (matcher.find()) {
//                        String broadcast = matcher.group().replaceAll("<receiver\\s*android:name\\s*=\\s*", "").replace("\"", "").trim();
//                        if (broadcasts.contains(broadcast)) continue;
//                        broadcasts.add(broadcast);
//                    }
//                    KernelLog.info("broadcasts  " + broadcasts.toString());
//
//                    pattern = Pattern.compile("<activity\\s*android:name\\s*=\\s*\"[^\"]+\"");
//                    matcher = pattern.matcher(manifestStr);
//                    while (matcher.find()) {
//                        String broadcast = matcher.group().replaceAll("<activity\\s*android:name\\s*=\\s*", "").replace("\"", "").trim();
//                        if (activities.contains(broadcast)) continue;
//                        activities.add(broadcast);
//                    }
//                    KernelLog.info("activities  " + activities.toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            });
        }));

    }

    private static Element getNode(Element root, String tag) {
        List<Content> children = root.getContent();
        for (Content child : children) {
            if (child instanceof Element) {
                Element tmp = (Element) child;
                String name = tmp.getName();
                if (tag.equals(name)) return tmp;
            }
        }
        return null;
    }

    private static String getAttrName(Element element, String tag) {
        List<Attribute> children = element.getAttributes();
        for (Attribute child : children) {
            if (tag.equals(child.getName())) return child.getValue();
        }
        return null;
    }

    private static void parseXml(File manifest) throws Exception {
        SAXBuilder saxBuilder = new SAXBuilder();
        InputStream is = new FileInputStream(manifest);
        Document document = saxBuilder.build(is);
        is.close();

        Element rootElement = document.getRootElement();
        Element application = getNode(rootElement, "application");
        if (application == null) return;

        List<Content> children = application.getContent();
        for (Content child : children) {
            if (!(child instanceof Element)) continue;
            Element tmp = (Element) child;
            String className;
            switch (tmp.getName()) {
                case "provider":
                    className = getAttrName(tmp, "name");
                    if (!providers.contains(className)) {
                        providers.add(className);
                    }
                    break;
                case "service":
                    className = getAttrName(tmp, "name");
                    if (!services.contains(className)) {
                        services.add(className);
                    }
                    break;
                case "receiver":
                    className = getAttrName(tmp, "name");
                    if (!broadcasts.contains(className)) {
                        broadcasts.add(className);
                    }
                    break;
                case "activity":
                    className = getAttrName(tmp, "name");
                    if (!activities.contains(className)) {
                        activities.add(className);
                    }
                    break;
            }
        }
        KernelLog.info("parseXml   " + providers + " \n" + services + "  \n"
                + broadcasts + "  \n" + activities);
    }

    public static ArrayList<String> activities = new ArrayList<>();
    public static ArrayList<String> providers = new ArrayList<>();
    public static ArrayList<String> services = new ArrayList<>();
    public static ArrayList<String> broadcasts = new ArrayList<>();

}
