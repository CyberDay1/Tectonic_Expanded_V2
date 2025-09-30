package com.cyberday1.theexpanse.mixinextras.service;

class Versioned<T> {
    final int version;
    final T value;

    Versioned(int version, T value) {
        this.version = version;
        this.value = value;
    }
}
