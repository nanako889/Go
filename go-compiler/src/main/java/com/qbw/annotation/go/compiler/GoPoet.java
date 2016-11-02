package com.qbw.annotation.go.compiler;

import com.qbw.annotation.go.compiler.common.AbstractPoet;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

import static com.qbw.annotation.go.compiler.common.ClassNames.GO;

/**
 * @author qbw
 * @createtime 2016/08/29 16:09
 */


public class GoPoet extends AbstractPoet {

    private Map<String, ParasitePoet> mPoetMap;

    public GoPoet(Filer filer, Map<String, ParasitePoet> poetMap) {
        super(filer, GO.packageName(), GO.simpleName(), GO.simpleName());
        mPoetMap = poetMap;
    }

    @Override
    protected List<MethodSpec> getMethods() {

        List<MethodSpec> methodSpecs = new ArrayList<>();

        for (Map.Entry<String, ParasitePoet> poetEntry : mPoetMap.entrySet()) {
            MethodSpec.Builder mb = MethodSpec.methodBuilder(poetEntry.getValue().getHostClassName().simpleName().replace(".", ""));
            mb.addModifiers(Modifier.PUBLIC, Modifier.STATIC);
            ClassName tcn = poetEntry.getValue().getTargetClassName();
            mb.returns(tcn);
            mb.addStatement("return new $T()", tcn);
            methodSpecs.add(mb.build());
        }

        return methodSpecs;
    }
}
