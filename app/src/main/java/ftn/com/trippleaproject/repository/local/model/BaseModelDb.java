package ftn.com.trippleaproject.repository.local.model;


import android.arch.persistence.room.PrimaryKey;

public abstract class BaseModelDb {

    @PrimaryKey(autoGenerate = true)
    private long id;

    public BaseModelDb() {
    }

    public BaseModelDb(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
