package com.yan.asmlocal;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.LocalVariablesSorter;

import static com.yan.asmlocal.AsmPrivacyClassPathConfig.ACTIVITY_CONTENT_CLASS_PATH;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.ACTIVITY_METHOD_FINISH;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.ACTIVITY_METHOD_FINISH_DES;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.ACTIVITY_METHOD_ON_CREATE;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.ACTIVITY_METHOD_ON_CREATE_DES;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.BROADCAST_RECEIVE_METHOD_FINISH;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.BROADCAST_RECEIVE_METHOD_FINISH_DES;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.BROADCAST_RECEIVE_METHOD_GO_ASYNC;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.BROADCAST_RECEIVE_METHOD_GO_ASYNC_DES;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.BROADCAST_RECEIVE_RENDING_RESULT;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.EXTRA_AUTH_DISPATCHER_CLASS_PATH;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.EXTRA_AUTH_DISPATCHER_METHOD_GET;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.EXTRA_AUTH_DISPATCHER_METHOD_GET_DES;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.EXTRA_AUTH_DISPATCHER_METHOD_OBSERVE;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.EXTRA_AUTH_DISPATCHER_METHOD_OBSERVE_DES;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.OBJECT_CLASS;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.OBJECT_METHOD_GET_CLASS;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.OBJECT_METHOD_GET_CLASS_DES;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.PRIVACY_CLASS_PATH;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.PRIVACY_METHOD_GET_DES;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.PRIVACY_METHOD_GET_PRIVACY;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.PRIVACY_METHOD_IS_AUTH;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.PRIVACY_METHOD_IS_AUTH_DES;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.PRIVACY_MGR_CLASS_PATH;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.PROVIDER_CONTENT_CLASS_PATH;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.PROVIDER_METHOD_ATTACH_INFO;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.PROVIDER_METHOD_ATTACH_INFO_DES;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.SERVICE_CLASS_PATH;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.SERVICE_METHOD_ATTACH_BASE_CONTEXT;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.SERVICE_METHOD_ATTACH_BASE_CONTEXT_DES;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.SERVICE_METHOD_GET_INS;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.SERVICE_METHOD_GET_INS_DES;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.SERVICE_METHOD_STOP_SELF;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.SERVICE_METHOD_STOP_SELF_DES;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.START_ACT_PROCESS_METHOD;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.START_ACT_PROCESS_METHOD_DES;
import static com.yan.asmlocal.AsmPrivacyClassPathConfig.START_ASM_HOCK_CLASS;

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since 2020/9/26
 */
public class AsmPrivacyMethodCommon extends LocalVariablesSorter implements Opcodes {
    private String classNamePath;
    private String superClassNamePath;
    private String methodDes;
    private String methodName;

    enum Type {
        Receiver,
        Service,
        Provider,
        Activity
    }

    private Type type;

    public AsmPrivacyMethodCommon(String classNamePath, String superClassNamePath, String methodName, int access,
                                  String desc, MethodVisitor mv, Type type) {
        super(Opcodes.ASM5, access, desc, mv);
        this.classNamePath = classNamePath + "";
        this.superClassNamePath = superClassNamePath + "";
        this.methodName = methodName + "";
        this.methodDes = desc + "";
        this.type = type;
    }

    private Label label1;
    private Label label3;

    @Override
    public void visitCode() {
        super.visitCode();


        if (type == Type.Service) {
            visitAuthIfPart();
            visitServiceSuperStop();
        } else if (type == Type.Receiver) {
            visitAuthIfPart();
            visitReceiverSuperFinish();
        } else if (type == Type.Provider) {
            visitAuthIfPart();
            visitProviderDispatchDelay();
        } else if (type == Type.Activity) {
            visitActivityAmsHock();
            visitActivitySuperCreate();
            visitActivitySuperFinish();
        }

        visitElse();
    }

    private void visitAuthIfPart() {
        Label label0 = new Label();
        mv.visitLabel(label0);
        mv.visitMethodInsn(INVOKESTATIC, PRIVACY_MGR_CLASS_PATH, PRIVACY_METHOD_GET_PRIVACY, PRIVACY_METHOD_GET_DES, false);
        mv.visitMethodInsn(INVOKEINTERFACE, PRIVACY_CLASS_PATH, PRIVACY_METHOD_IS_AUTH, PRIVACY_METHOD_IS_AUTH_DES, true);
        label1 = new Label();
        mv.visitJumpInsn(IFNE, label1);
    }

    private void visitActivityAmsHock() {
        Label label0 = new Label();
        mv.visitLabel(label0);
        mv.visitMethodInsn(INVOKESTATIC, START_ASM_HOCK_CLASS, SERVICE_METHOD_GET_INS, SERVICE_METHOD_GET_INS_DES, false);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, OBJECT_CLASS, OBJECT_METHOD_GET_CLASS, OBJECT_METHOD_GET_CLASS_DES, false);
        mv.visitMethodInsn(INVOKEVIRTUAL, START_ASM_HOCK_CLASS, START_ACT_PROCESS_METHOD, START_ACT_PROCESS_METHOD_DES, false);
        label1 = new Label();
        mv.visitJumpInsn(IFEQ, label1);
    }

    private void visitElse() {
        label3 = new Label();
        mv.visitJumpInsn(GOTO, label3);
        mv.visitLabel(label1);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
    }


    public void visitActivitySuperCreate() {
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKESPECIAL, ACTIVITY_CONTENT_CLASS_PATH, ACTIVITY_METHOD_ON_CREATE, ACTIVITY_METHOD_ON_CREATE_DES, false);
    }

    public void visitActivitySuperFinish() {
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, ACTIVITY_CONTENT_CLASS_PATH, ACTIVITY_METHOD_FINISH, ACTIVITY_METHOD_FINISH_DES, false);
    }

    private void visitProviderDispatchDelay() {
        Label label2 = new Label();
        mv.visitLabel(label2);
        mv.visitLineNumber(26, label2);
        mv.visitMethodInsn(INVOKESTATIC, EXTRA_AUTH_DISPATCHER_CLASS_PATH, EXTRA_AUTH_DISPATCHER_METHOD_GET, EXTRA_AUTH_DISPATCHER_METHOD_GET_DES, false);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitVarInsn(ALOAD, 2);
        mv.visitMethodInsn(INVOKEVIRTUAL, EXTRA_AUTH_DISPATCHER_CLASS_PATH, EXTRA_AUTH_DISPATCHER_METHOD_OBSERVE, EXTRA_AUTH_DISPATCHER_METHOD_OBSERVE_DES, false);
    }

    public void visitProviderSuperAttachInfo() {
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitVarInsn(ALOAD, 2);
        mv.visitMethodInsn(INVOKESPECIAL, PROVIDER_CONTENT_CLASS_PATH, PROVIDER_METHOD_ATTACH_INFO, PROVIDER_METHOD_ATTACH_INFO_DES, false);
    }

    private void visitReceiverSuperFinish() {
        Label label2 = new Label();
        mv.visitLabel(label2);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, classNamePath, BROADCAST_RECEIVE_METHOD_GO_ASYNC, BROADCAST_RECEIVE_METHOD_GO_ASYNC_DES, false);
        mv.visitMethodInsn(INVOKEVIRTUAL, BROADCAST_RECEIVE_RENDING_RESULT, BROADCAST_RECEIVE_METHOD_FINISH, BROADCAST_RECEIVE_METHOD_FINISH_DES, false);

    }


    private void visitServiceSuperStop() {
        Label label2 = new Label();
        mv.visitLabel(label2);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, classNamePath, SERVICE_METHOD_STOP_SELF, SERVICE_METHOD_STOP_SELF_DES, false);

    }

    public void visitAppSuperAttachBaseContext() {
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKESPECIAL, SERVICE_CLASS_PATH, SERVICE_METHOD_ATTACH_BASE_CONTEXT, SERVICE_METHOD_ATTACH_BASE_CONTEXT_DES, false);
    }


    @Override
    public void visitInsn(int opcode) {
        if (((opcode >= IRETURN && opcode <= RETURN))) {
            mv.visitLabel(label3);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        }
        super.visitInsn(opcode);

    }
}
