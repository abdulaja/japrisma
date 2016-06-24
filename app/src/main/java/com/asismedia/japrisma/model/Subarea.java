package com.asismedia.japrisma.model;

/**
 * Created by Abdulaja on 07/08/2015.
 */
public class Subarea {

    private long id_sub;
    private long id_area;
    private String subarea;

    public Subarea() {

    }


    public long getId_sub() {
        return id_sub;
    }

    public void setId_sub(long id_sub) {
        this.id_sub = id_sub;
    }


    public long getId_area() {
        return id_area;
    }

    public void setId_area(long id_area) {
        this.id_area = id_area;
    }


    public String getSubarea() {
        return subarea;
    }

    public void setSubarea(String subarea) {
        this.subarea = subarea;
    }

}
