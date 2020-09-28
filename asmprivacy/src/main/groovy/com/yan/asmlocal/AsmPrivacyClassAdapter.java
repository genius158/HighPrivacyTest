package com.yan.asmlocal;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static com.yan.asmlocal.AsmPrivacyClassPathConfig.ACTIVITY_METHOD_ON_CREATE;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.ACTIVITY_METHOD_ON_CREATE_DES;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.BROADCAST_RECEIVE_METHOD_REPLACE;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.BROADCAST_RECEIVE_METHOD_REPLACE_DES;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.PROVIDER_METHOD_ATTACH_INFO;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.PROVIDER_METHOD_ATTACH_INFO_DES;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.SERVICE_METHOD_ATTACH_BASE_CONTEXT;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.SERVICE_METHOD_ATTACH_BASE_CONTEXT_DES;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.SERVICE_METHOD_START_COMMAND;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.SERVICE_METHOD_START_COMMAND_DES;
import static org.objectweb.asm.Opcodes.ACC_PROTECTED;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.RETURN;

/**
 * 实现什么？
 * <p>
 * 在四大组件做启动拦截
 */
public final class AsmPrivacyClassAdapter extends ClassVisitor {

    AsmPrivacyClassAdapter(final ClassVisitor cv) {
        super(Opcodes.ASM6, cv);
    }

    private String superClassPath;
    private String classPath;
    private String className;

    @Override
    public void visit(int version, int access, String name, String signature, String superName,
                      String[] interfaces) {
        superClassPath = superName;
        classPath = name;
        className = name.replace("/", ".");
        KernelLog.info("AsmPrivacyClassAdapter ---- " + superClassPath + "  " + classPath);
        super.visit(version, access, name, signature, superName, interfaces);
    }

    //  methodVisitor = classWriter.visitMethod(ACC_PROTECTED, "onCreate", "(Landroid/os/Bundle;)V", null, null);
//
    private boolean isVisitActivityHockMethod = false;
    private boolean isVisitProviderHockMethod = false;
    private boolean isVisitServiceHockMethod = false;
    private boolean isVisitServiceStartHockMethod = false;
    private boolean isVisitReceiverHockMethod = false;

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        KernelLog.info("AsmPrivacyClassAdapter   " + superClassPath + "  " + classPath + "   " + className + "   " + name + "  " + descriptor);

        if (PrivacyProviderFind.providers.contains(className) &&
                PROVIDER_METHOD_ATTACH_INFO.equals(name) &&
                PROVIDER_METHOD_ATTACH_INFO_DES.equals(descriptor)) {
            isVisitProviderHockMethod = true;
            return new AsmPrivacyMethodCommon(classPath, superClassPath, name, access, descriptor, super.visitMethod(access, name, descriptor, signature, exceptions), AsmPrivacyMethodCommon.Type.Provider);
        }

        if (PrivacyProviderFind.services.contains(className) &&
                SERVICE_METHOD_ATTACH_BASE_CONTEXT.equals(name) &&
                SERVICE_METHOD_ATTACH_BASE_CONTEXT_DES.equals(descriptor)
        ) {
            isVisitServiceHockMethod = true;
            return new AsmPrivacyMethodCommon(classPath, superClassPath, name, access, descriptor, super.visitMethod(access, name, descriptor, signature, exceptions), AsmPrivacyMethodCommon.Type.Service);
        }

        if (PrivacyProviderFind.services.contains(className) &&
                SERVICE_METHOD_START_COMMAND.equals(name) &&
                SERVICE_METHOD_START_COMMAND_DES.equals(descriptor)
        ) {
            isVisitServiceStartHockMethod = true;
            return new AsmPrivacyMethodCommon(classPath, superClassPath, name, access, descriptor, super.visitMethod(access, name, descriptor, signature, exceptions), AsmPrivacyMethodCommon.Type.Service);
        }

        if (PrivacyProviderFind.broadcasts.contains(className) &&
                BROADCAST_RECEIVE_METHOD_REPLACE.equals(name) &&
                BROADCAST_RECEIVE_METHOD_REPLACE_DES.equals(descriptor)
        ) {
            isVisitReceiverHockMethod = true;
            return new AsmPrivacyMethodCommon(classPath, superClassPath, name, access, descriptor, super.visitMethod(access, name, descriptor, signature, exceptions), AsmPrivacyMethodCommon.Type.Receiver);
        }

        if (PrivacyProviderFind.activities.contains(className) &&
                ACTIVITY_METHOD_ON_CREATE.equals(name) &&
                ACTIVITY_METHOD_ON_CREATE_DES.equals(descriptor)
        ) {
            isVisitActivityHockMethod = true;
            return new AsmPrivacyMethodCommon(classPath, superClassPath, name, access, descriptor, super.visitMethod(access, name, descriptor, signature, exceptions), AsmPrivacyMethodCommon.Type.Activity);
        }

        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        if (PrivacyProviderFind.services.contains(className) && !isVisitServiceHockMethod) {
            String des = SERVICE_METHOD_ATTACH_BASE_CONTEXT_DES;
            String method = SERVICE_METHOD_ATTACH_BASE_CONTEXT;
            AsmPrivacyMethodCommon mv = new AsmPrivacyMethodCommon(classPath, superClassPath, method, ACC_PROTECTED, des,
                    super.visitMethod(ACC_PROTECTED, method, des, null, null), AsmPrivacyMethodCommon.Type.Service);
            mv.visitCode();
            mv.visitAppSuperAttachBaseContext();
            mv.visitInsn(RETURN);
        }
        if (PrivacyProviderFind.services.contains(className) && !isVisitServiceStartHockMethod) {
            String des = SERVICE_METHOD_START_COMMAND_DES;
            String method = SERVICE_METHOD_START_COMMAND;
            AsmPrivacyMethodCommon mv = new AsmPrivacyMethodCommon(classPath, superClassPath, method, ACC_PUBLIC, des,
                    super.visitMethod(ACC_PUBLIC, method, des, null, null), AsmPrivacyMethodCommon.Type.Service);
            mv.visitCode();
            mv.visitServiceSuperStartCommand2End();
        }
        if (PrivacyProviderFind.broadcasts.contains(className) && !isVisitReceiverHockMethod) {
            String des = BROADCAST_RECEIVE_METHOD_REPLACE_DES;
            String method = BROADCAST_RECEIVE_METHOD_REPLACE;
            AsmPrivacyMethodCommon mv = new AsmPrivacyMethodCommon(classPath, superClassPath, method, ACC_PROTECTED, des,
                    super.visitMethod(ACC_PUBLIC, method, des, null, null), AsmPrivacyMethodCommon.Type.Receiver);
            mv.visitCode();
            mv.visitInsn(RETURN);
        }
        if (PrivacyProviderFind.providers.contains(className) && !isVisitProviderHockMethod) {
            String des = PROVIDER_METHOD_ATTACH_INFO_DES;
            String method = PROVIDER_METHOD_ATTACH_INFO;
            AsmPrivacyMethodCommon mv = new AsmPrivacyMethodCommon(classPath, superClassPath, method, ACC_PROTECTED, des,
                    super.visitMethod(ACC_PUBLIC, method, des, null, null), AsmPrivacyMethodCommon.Type.Provider);
            mv.visitCode();
            mv.visitProviderSuperAttachInfo();
            mv.visitInsn(RETURN);
        }
        if (PrivacyProviderFind.activities.contains(className) && !isVisitActivityHockMethod) {
            String des = ACTIVITY_METHOD_ON_CREATE_DES;
            String method = ACTIVITY_METHOD_ON_CREATE;
            AsmPrivacyMethodCommon mv = new AsmPrivacyMethodCommon(classPath, superClassPath, method, ACC_PROTECTED, des,
                    super.visitMethod(ACC_PUBLIC, method, des, null, null), AsmPrivacyMethodCommon.Type.Activity);
            mv.visitCode();
            mv.visitInsn(RETURN);
        }
        super.visitEnd();
    }
}