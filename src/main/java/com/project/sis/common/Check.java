package com.project.sis.common;

import java.util.Optional;

public class Check {

    public static void throwIfEmpty(Optional optional, RuntimeException exception) {
        if(optional.isEmpty() || optional.get() == null) {
            throw exception;
        }
    }
}
