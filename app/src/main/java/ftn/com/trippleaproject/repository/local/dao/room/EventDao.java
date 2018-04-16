package ftn.com.trippleaproject.repository.local.dao.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ftn.com.trippleaproject.repository.local.model.EventDb;
import io.reactivex.Flowable;

@Dao
public interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long create(EventDb eventDb);

    @Update
    void update(EventDb eventDb);

    @Query("SELECT * FROM event")
    Flowable<List<EventDb>> readAll();
}
