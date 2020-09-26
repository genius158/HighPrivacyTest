package com.yan.asmlocal;

import com.quinn.hunter.transform.asm.BaseWeaver;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.util.List;

public final class AsmPrivacyWeaver extends BaseWeaver {


    public AsmPrivacyWeaver() {
    }

    @Override
    public boolean isWeavableClass(String fullQualifiedClassName) {
        List<String> classes = PrivacyProviderFind.providers;
        fullQualifiedClassName = fullQualifiedClassName.replace(".class", "");
        if (classes.contains(fullQualifiedClassName)) {
            KernelLog.info("AsmPrivacyWeaver providers  " + classes + "   " + fullQualifiedClassName);
            return true;
        }
        classes = PrivacyProviderFind.services;
        if (classes.contains(fullQualifiedClassName)) {
            KernelLog.info("AsmPrivacyWeaver services  " + classes + "   " + fullQualifiedClassName);
            return true;
        }
        classes = PrivacyProviderFind.broadcasts;
        if (classes.contains(fullQualifiedClassName)) {
            KernelLog.info("AsmPrivacyWeaver broadcasts  " + classes + "   " + fullQualifiedClassName);
            return true;
        }
        classes = PrivacyProviderFind.activities;
        if (classes.contains(fullQualifiedClassName)) {
            KernelLog.info("AsmPrivacyWeaver activity  " + classes + "   " + fullQualifiedClassName);
            return true;
        }
        return false;
    }

    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new AsmPrivacyClassAdapter(classWriter);
    }
}
