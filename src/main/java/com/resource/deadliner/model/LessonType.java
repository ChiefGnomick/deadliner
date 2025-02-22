package com.resource.deadliner.model;

public enum LessonType {
    LABORATORY("Лабораторная"),   
    PRACTICE("Практика"),     
    LECTURE("Лекция"),      
    EXAM("Экзамен"),     
    CREDIT("Зачет"),   
    CONSULTATION("Консультация"); 

    private final String displayName;

    LessonType(String displayName) {
        this.displayName = displayName;
    }

    public static LessonType fromDisplayName(String displayName) {
        for (LessonType lesson : values()) {
            if (lesson.displayName.equalsIgnoreCase(displayName)) {
                return lesson;
            } else if (displayName == "" || displayName == null) {
                return null;
            }
        }
        throw new IllegalArgumentException();
    }
}