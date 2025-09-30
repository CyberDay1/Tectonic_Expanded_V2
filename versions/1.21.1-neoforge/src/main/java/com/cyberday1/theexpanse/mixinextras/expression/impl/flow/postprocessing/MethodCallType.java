package com.cyberday1.theexpanse.mixinextras.expression.impl.flow.postprocessing;

import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.FlowValue;
import com.cyberday1.theexpanse.mixinextras.expression.impl.utils.FlowDecorations;

public enum MethodCallType {
    NORMAL,
    SUPER,
    STATIC;

    public boolean matches(FlowValue node) {
        return node.getDecoration(FlowDecorations.METHOD_CALL_TYPE) == this;
    }
}
