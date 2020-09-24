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
        List<String> classes = PrivacyProviderFind.replaceClass;
        if (classes.contains(fullQualifiedClassName.replace(".class", ""))) {
            KernelLog.info("AsmPrivacyWeaver   " + classes + "   " + fullQualifiedClassName);
            return true;
        }
        return false;
    }

    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new AsmPrivacyClassAdapter(classWriter);
    }
}
