package com.resource.deadliner.model;

public enum DayOfWeek {
    MONDAY("Понедельник"),
    TUESDAY("Вторник"),
    WEDNESDAY("Среда"),
    THURSDAY("Четверг"),
    FRIDAY("Пятница"),
    SATURDAY("Суббота"),
    SUNDAY("Воскресенье");

    private final String displayName;

    DayOfWeek(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static DayOfWeek fromIndex(int index) {
        index = index % 7;
        if (index < 0) {
            index += 7;
        }
        return values()[index];
    }

    public static DayOfWeek fromDisplayName(String displayName) {
        for (DayOfWeek day : values()) {
            if (day.displayName.equalsIgnoreCase(displayName)) {
                return day;
            }
        }
        throw new IllegalArgumentException("Неизвестный день недели: " + displayName);
    }
}
