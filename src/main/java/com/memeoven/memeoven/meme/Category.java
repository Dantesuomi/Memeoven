package com.memeoven.memeoven.meme;

public enum Category {
    FOOD("food"),
    PEPEGA("pepega"),
    WORK("work"),
    POLITICS("politics"),
    ANIME("anime"),
    ANIMALS("animals"),
    GAMING("gaming"),
    ADULT("adult");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
