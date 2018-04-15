package ftn.com.trippleaproject.domain;


import java.io.Serializable;

public abstract class BaseModel implements Serializable {

    private int id;

    public BaseModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
