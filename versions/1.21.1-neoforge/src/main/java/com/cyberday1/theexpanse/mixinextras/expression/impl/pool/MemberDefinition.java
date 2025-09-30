package com.cyberday1.theexpanse.mixinextras.expression.impl.pool;

import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.FlowValue;

public interface MemberDefinition {
    boolean matches(FlowValue node);
}
