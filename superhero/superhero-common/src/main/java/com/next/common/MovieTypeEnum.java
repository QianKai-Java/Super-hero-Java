package com.next.common;

public enum  MovieTypeEnum {
    SUPERHERO("superhero","热门超英"),
    TRAILER("trailer","热门预告");

    public final String type;
    public final String value;

    MovieTypeEnum(String type, String value) {
        this.type = type;
        this.value = value;
    }
}
