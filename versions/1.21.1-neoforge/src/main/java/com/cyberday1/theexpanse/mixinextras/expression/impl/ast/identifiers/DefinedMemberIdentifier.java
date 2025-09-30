package com.cyberday1.theexpanse.mixinextras.expression.impl.ast.identifiers;

import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.FlowValue;
import com.cyberday1.theexpanse.mixinextras.expression.impl.pool.IdentifierPool;

public class DefinedMemberIdentifier implements MemberIdentifier {
    public final String name;

    public DefinedMemberIdentifier(String name) {
        this.name = name;
    }

    @Override
    public boolean matches(IdentifierPool pool, FlowValue node) {
        return pool.matchesMember(name, node);
    }
}
