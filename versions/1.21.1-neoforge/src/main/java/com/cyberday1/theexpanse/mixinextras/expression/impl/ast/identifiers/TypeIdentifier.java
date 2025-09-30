package com.cyberday1.theexpanse.mixinextras.expression.impl.ast.identifiers;

import com.cyberday1.theexpanse.mixinextras.expression.impl.pool.IdentifierPool;
import org.objectweb.asm.Type;

public interface TypeIdentifier {
    boolean matches(IdentifierPool pool, Type type);
}
