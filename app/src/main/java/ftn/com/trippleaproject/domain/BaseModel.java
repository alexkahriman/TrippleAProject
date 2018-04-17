package ftn.com.trippleaproject.domain;


import java.io.Serializable;

public abstract class BaseModel implements Serializable {

    private long id;

    public BaseModel(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
