package com.goit.javacore5.feature.skill;

public class Skill {
    private long id;
    private Branch branch;
    private Level level;

    public enum Branch {
        java,
        c,
        javaScript
    }

    public enum Level{
        junior,
        middle,
        senior
    }

}
