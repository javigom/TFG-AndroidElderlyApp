package com.example.simplemedicine.util;

public enum WeekDayEnum {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    public static WeekDayEnum getWeekDay(int index) {
        switch(index)  {
            case 0:
                return MONDAY;
            case 1:
                return TUESDAY;
            case 2:
                return WEDNESDAY;
            case 3:
                return THURSDAY;
            case 4:
                return FRIDAY;
            case 5:
                return SATURDAY;
            case 6:
                return SUNDAY;
            default:
                break;
        }
        return null;
    }

    public static String getWeekDayString(int index) {
        switch(index)  {
            case 0:
                return "Lunes";
            case 1:
                return "Martes";
            case 2:
                return "Miércoles";
            case 3:
                return "Jueves";
            case 4:
                return "Viernes";
            case 5:
                return "Sábado";
            case 6:
                return "Domingo";
        }
        return "";
    }

    public static int getWeekDayInt(WeekDayEnum weekDay) {
        switch(weekDay)  {
            case MONDAY:
                return 0;
            case TUESDAY:
                return 1;
            case WEDNESDAY:
                return 2;
            case THURSDAY:
                return 3;
            case FRIDAY:
                return 4;
            case SATURDAY:
                return 5;
            case SUNDAY:
                return 6;
        }
        return -1;
    }

}
