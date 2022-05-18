package andr.legacy.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WeatherDao {
    @Query("SELECT * FROM weather")
    List<Weather> getAll();

    @Delete
    void delete(Weather weather);

    @Insert
    void insert(Weather... weathers);

    @Query("SELECT * FROM weather WHERE id = :id LIMIT 1")
    Weather getOne(int id);

    @Update
    void update(Weather weather);
}
