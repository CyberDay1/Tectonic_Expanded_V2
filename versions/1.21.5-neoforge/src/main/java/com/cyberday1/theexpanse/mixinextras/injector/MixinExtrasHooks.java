package com.cyberday1.theexpanse.mixinextras.injector;

@SuppressWarnings("unused")
public class MixinExtrasHooks {
    public static StringBuilder replaceContents(StringBuilder builder, String contents) {
        return builder.replace(0, builder.length(), contents);
    }
}
