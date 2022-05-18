package andr.legacy.db;

import android.arch.persistence.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateConverters {
    @TypeConverter
    public static LocalDateTime timestampToLocalDateTime(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();

    }

    @TypeConverter
    public static Long localDateTimeToTimestamp(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000;
    }

    @TypeConverter
    public static Long localDateToTimestamp(LocalDate date) {
        if (date == null) {
            return null;
        }
        return localDateTimeToTimestamp(date.atStartOfDay());
    }


    @TypeConverter
    public static LocalDate timestampToLocalDate(Long timestamp) {
        return timestampToLocalDateTime(timestamp).toLocalDate();
    }
}