package andr.legacy.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.time.LocalDate;

@Entity(tableName = "weather")
public class Weather {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "weather")
    private String weather;

    @ColumnInfo(name = "note", typeAffinity = ColumnInfo.TEXT)
    private String note;

    @ColumnInfo(name = "temperature")
    private Integer temperature;

    @NonNull
    @ColumnInfo(name = "created_at")
    private LocalDate createdAt = LocalDate.now();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
