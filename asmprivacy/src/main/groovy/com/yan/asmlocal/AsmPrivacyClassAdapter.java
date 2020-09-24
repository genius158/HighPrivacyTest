package com.yan.asmlocal;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public final class AsmPrivacyClassAdapter extends ClassVisitor {

    static final String SUPER_CLASS_PATH = "com/yan/highprivacy/ContentProviderPrivacy";
    static final String NEED_REPLACE_CLASS_PATH = "android/content/ContentProvider";

    AsmPrivacyClassAdapter(final ClassVisitor cv) {
        super(Opcodes.ASM6, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName,
                      String[] interfaces) {
        KernelLog.info("AsmPrivacyClassAdapter   " + superName + "  " + NEED_REPLACE_CLASS_PATH + "  " + SUPER_CLASS_PATH);
        if (NEED_REPLACE_CLASS_PATH.equals(superName)) {
            superName = SUPER_CLASS_PATH;
        }
        super.visit(version, access, name, signature, superName, interfaces);

    }

}