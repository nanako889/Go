package com.qbw.annotation.go.compiler;

import com.qbw.annotation.go.compiler.common.AbstractPoet;

import javax.annotation.processing.Filer;

/**
 * @author qbw
 * @createtime 2016/08/29 17:23
 */

@Deprecated
public class AbstractGoPoet extends AbstractPoet {

    public AbstractGoPoet(Filer filer, String packageName, String complexClassName, String simpleClassName) {
        super(filer, packageName, complexClassName, simpleClassName);
    }
}
