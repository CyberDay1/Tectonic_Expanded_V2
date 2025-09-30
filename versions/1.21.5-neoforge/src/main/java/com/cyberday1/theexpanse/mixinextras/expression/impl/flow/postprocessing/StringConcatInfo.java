package com.cyberday1.theexpanse.mixinextras.expression.impl.flow.postprocessing;

import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.FlowValue;

public class StringConcatInfo {
    public final boolean isFirstConcat;
    public final boolean isBuilder;
    public final FlowValue initialComponent;
    public final FlowValue toStringCall;

    public StringConcatInfo(boolean isFirstConcat, boolean isBuilder, FlowValue initialComponent, FlowValue toStringCall) {
        this.isFirstConcat = isFirstConcat;
        this.isBuilder = isBuilder;
        this.initialComponent = initialComponent;
        this.toStringCall = toStringCall;
    }
}
