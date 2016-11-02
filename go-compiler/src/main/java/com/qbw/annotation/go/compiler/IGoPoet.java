package com.qbw.annotation.go.compiler;

import com.qbw.annotation.go.compiler.common.AbstractPoet;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

import static com.qbw.annotation.go.compiler.common.ClassNames.I_GO;

/**
 * @author qbw
 * @createtime 2016/08/29 17:23
 */

@Deprecated
public class IGoPoet extends AbstractPoet {

    public IGoPoet(Filer filer) {
        super(filer, I_GO.packageName(), I_GO.simpleName(), I_GO.simpleName());
    }

    @Override
    protected List<MethodSpec> getMethods() {
        List<MethodSpec> methodSpecs = new ArrayList<>();

        MethodSpec methodSpec = MethodSpec.methodBuilder(M_GO).addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.ABSTRACT).
                build();
        methodSpecs.add(methodSpec);

        return methodSpecs;
    }

    @Override
    protected TypeSpec.Kind getKind() {
        return TypeSpec.Kind.INTERFACE;
    }
}
