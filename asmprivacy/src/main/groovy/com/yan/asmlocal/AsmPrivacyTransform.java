package com.yan.asmlocal;

import com.android.build.api.transform.Context;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformOutputProvider;
import com.quinn.hunter.transform.HunterTransform;

import org.gradle.api.Project;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class AsmPrivacyTransform extends HunterTransform {

    public AsmPrivacyTransform(Project project) {
        super(project);

        this.bytecodeWeaver = new AsmPrivacyWeaver();
    }

    @Override
    public void transform(Context context, Collection<TransformInput> inputs,
                          Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider,
                          boolean isIncremental) throws IOException, TransformException, InterruptedException {
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental);
    }

    private static final Set<QualifiedContent.Scope> SCOPES = new HashSet<>();

    static {
        SCOPES.add(QualifiedContent.Scope.PROJECT);
        SCOPES.add(QualifiedContent.Scope.SUB_PROJECTS);
        SCOPES.add(QualifiedContent.Scope.EXTERNAL_LIBRARIES);

    }

    @Override
    public Set<QualifiedContent.Scope> getScopes() {
        return SCOPES;
    }

}
