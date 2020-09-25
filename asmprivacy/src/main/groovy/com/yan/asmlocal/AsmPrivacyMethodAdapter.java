package com.yan.asmlocal;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.LocalVariablesSorter;

public final class AsmPrivacyMethodAdapter extends LocalVariablesSorter implements Opcodes {
    private String classNamePath;
    private String className;
    private String superClassName;
    private String methodDes;
    private String methodName;

    public AsmPrivacyMethodAdapter(String className, String superClassName, String methodName, int access,
                                   String desc,
                                   MethodVisitor mv) {
        super(Opcodes.ASM5, access, desc, mv);
        this.classNamePath = className.replace(".", "/");
        this.className = className;
        this.superClassName = superClassName;
        this.methodName = methodName;
        this.methodDes = desc;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        if (AsmPrivacyClassAdapter.NEED_REPLACE_CLASS_PATH.equals(owner)
                && AsmPrivacyClassAdapter.METHOD_NAME_NEED_REPLACE.equals(name)
                && AsmPrivacyClassAdapter.METHOD_DES_NAME_NEED_REPLACE.equals(descriptor)) {
            super.visitMethodInsn(opcode, AsmPrivacyClassAdapter.SUPER_CLASS_PATH, AsmPrivacyClassAdapter.METHOD_NAME_NEED_REPLACE_TO, descriptor, isInterface);
            return;
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }
}
