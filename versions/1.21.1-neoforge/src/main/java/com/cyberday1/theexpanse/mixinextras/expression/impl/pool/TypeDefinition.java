package com.cyberday1.theexpanse.mixinextras.expression.impl.pool;

import org.objectweb.asm.Type;

public interface TypeDefinition {
    boolean matches(Type type);
}
