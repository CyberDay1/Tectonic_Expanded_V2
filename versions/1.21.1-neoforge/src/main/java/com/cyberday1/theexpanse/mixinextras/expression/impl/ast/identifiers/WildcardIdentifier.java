package com.cyberday1.theexpanse.mixinextras.expression.impl.ast.identifiers;

import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.FlowValue;
import com.cyberday1.theexpanse.mixinextras.expression.impl.pool.IdentifierPool;
import org.objectweb.asm.Type;

public class WildcardIdentifier implements MemberIdentifier, TypeIdentifier {
    @Override
    public boolean matches(IdentifierPool pool, FlowValue node) {
        return true;
    }

    @Override
    public boolean matches(IdentifierPool pool, Type type) {
        return true;
    }
}
