package com.cyberday1.theexpanse.mixinextras.expression.impl.ast.identifiers;

import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.FlowValue;
import com.cyberday1.theexpanse.mixinextras.expression.impl.pool.IdentifierPool;

public interface MemberIdentifier {
    boolean matches(IdentifierPool pool, FlowValue node);
}
