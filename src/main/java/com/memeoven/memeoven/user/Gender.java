package com.memeoven.memeoven.user;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    NON_BINARY("Non binary"),
    NOT_SPECIFIED("Not specified");

    private final String name;

    Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
