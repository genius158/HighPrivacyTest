package com.yan.asmlocal;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 实现什么？
 * 原 provider attachInfo 方法转成 callOriginalAttachInfoFromPrivacy
 * 由我们的PrivacyContentProviderProxy 实现
 */
public final class AsmPrivacyClassAdapter extends ClassVisitor {

    static final String SUPER_CLASS_PATH = "com/yan/highprivacy/PrivacyContentProviderProxy";
    static final String NEED_REPLACE_CLASS_PATH = "android/content/ContentProvider";

    static final String METHOD_NAME_NEED_REPLACE_TO="callOriginalAttachInfoFromPrivacy";
    static final String METHOD_NAME_NEED_REPLACE="attachInfo";
    static final String METHOD_DES_NAME_NEED_REPLACE="(Landroid/content/Context;Landroid/content/pm/ProviderInfo;)V";
    AsmPrivacyClassAdapter(final ClassVisitor cv) {
        super(Opcodes.ASM6, cv);
    }

    private String superClassName;
    private String className;

    @Override
    public void visit(int version, int access, String name, String signature, String superName,
                      String[] interfaces) {
        if (NEED_REPLACE_CLASS_PATH.equals(superName)) {
            superName = SUPER_CLASS_PATH;
        }
        superClassName = superName;
        className = name;
        KernelLog.info("AsmPrivacyClassAdapter   " + superClassName + "  " + className);
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (METHOD_NAME_NEED_REPLACE.equals(name) && METHOD_DES_NAME_NEED_REPLACE.equals(descriptor)) {
            name = METHOD_NAME_NEED_REPLACE_TO;
        }
        return new AsmPrivacyMethodAdapter(className, superClassName, name, access, descriptor, super.visitMethod(access, name, descriptor, signature, exceptions));
    }

}