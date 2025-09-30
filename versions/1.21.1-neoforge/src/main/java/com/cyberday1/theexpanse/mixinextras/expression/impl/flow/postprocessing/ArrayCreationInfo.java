package com.cyberday1.theexpanse.mixinextras.expression.impl.flow.postprocessing;

import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.FlowValue;
import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.utils.InsnReference;

public class ArrayCreationInfo {
    public final InsnReference initialized;

    public ArrayCreationInfo(FlowValue initialized) {
        this.initialized = new InsnReference(initialized);
    }
}
