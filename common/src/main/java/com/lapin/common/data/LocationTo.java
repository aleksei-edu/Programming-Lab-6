package com.lapin.common.data;

import com.lapin.di.annotation.ClassMeta;
import lombok.Getter;

import java.io.Serializable;

/**
 * Объекты, которые используются Route
 */
@ClassMeta(name = "locationTo")
@Getter
public class LocationTo implements Serializable {
    private Float x; //Поле не может быть null
    private Long y; //Поле не может быть null
    private String name; //Поле не может быть null

    public LocationTo(Float x, Long y, String name) {
        try {
            if (x == null || y == null || name == null) {
                throw new NullPointerException();
            } else {
                this.x = x;
                this.y = y;
                this.name = name;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return "LocationTo x: " + x.toString() + ", LocationTo y: " + y.toString() + ", LocationTo name: " + name;
    }

    public void updateLocationTo(Float x, Long y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }
}
