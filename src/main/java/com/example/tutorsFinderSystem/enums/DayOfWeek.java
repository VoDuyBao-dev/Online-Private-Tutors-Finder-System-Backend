package com.example.tutorsFinderSystem.enums;

public enum DayOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    public java.time.DayOfWeek toJavaDayOfWeek() {
        return java.time.DayOfWeek.valueOf(this.name());
    }
}
