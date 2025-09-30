package com.cyberday1.theexpanse.mixinextras.expression.impl.ast.identifiers;

import com.cyberday1.theexpanse.mixinextras.expression.impl.pool.IdentifierPool;
import org.objectweb.asm.Type;

public class ArrayTypeIdentifier implements TypeIdentifier {
    public final int dims;
    public final TypeIdentifier elementType;

    public ArrayTypeIdentifier(int dims, TypeIdentifier elementType) {
        this.dims = dims;
        this.elementType = elementType;
    }

    @Override
    public boolean matches(IdentifierPool pool, Type type) {
        return type.getSort() == Type.ARRAY && type.getDimensions() >= dims && elementType.matches(
                pool,
                Type.getType(type.getDescriptor().substring(dims))
        );
    }
}
