package com.example.redditclone.utility;

import java.time.DayOfWeek;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;


public class DateUtility {

    private final DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
    private final DayOfWeek lastDayOfWeek = DayOfWeek.of(((firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);
    public final static Date FAR_PAST  = new Date(0);
    public final static Date FAR_FUTURE = new Date(193444070400000L);

    public Date firstDayOfWeek(){
        return Date.from(LocalDate.now().with(TemporalAdjusters.previousOrSame(this.firstDayOfWeek)).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    public Date lastDayOfWeek(){
        return Date.from(LocalDate.now().with(TemporalAdjusters.nextOrSame(this.lastDayOfWeek)).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


    public Date firstDayOfYear(){
        return Date.from(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    public Date lastDayOfYear(){
        return Date.from(LocalDate.now().with(TemporalAdjusters.lastDayOfYear()).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date firstDayOfMonth(){
        return Date.from(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    public Date lastDayOfMonth(){
        return Date.from(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date previousDay(){
        return Date.from(Instant.now().minus(1, ChronoUnit.DAYS));
    }
    public  Date nextDay(){
        return Date.from(Instant.now().plus(1,ChronoUnit.DAYS));
    }





}
