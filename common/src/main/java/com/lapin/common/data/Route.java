package com.lapin.common.data;

import com.lapin.di.annotation.ClassMeta;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Элементы коллекцией, которыми управляет программа
 */
@Getter
@ClassMeta(name = "route")
public class Route implements Comparable<Route>, Serializable {
    @Getter
    @Setter
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @Getter
    private String name; //Поле не может быть null, Строка не может быть пустой
    @Getter
    private Coordinates coordinates; //Поле не может быть null
    @Getter
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @Getter
    private LocationFrom from; //Поле не может быть null
    @Getter
    private LocationTo to; //Поле не может быть null
    @Getter
    private Long distance; //Поле не может быть null, Значение поля должно быть больше 1
    @Getter
    private static int lastIdNum =0;
    /**
     * Все поля Route в виде String
     */
    private List<String> routeList = new ArrayList<>();

    /**
     * @param id           если есть, иначе ""
     * @param name         Route name
     * @param coordinates  Route coordinates
     * @param creationDate если есть, иначе ""
     * @param from         Route from
     * @param to           Route to
     * @param distance     Route distance
     */
    public Route(String id, String name, Coordinates coordinates, String creationDate,
                 LocationFrom from, LocationTo to, Long distance) {
        try {
            if (id.equals("")) this.id = Route.getNewId();
            else {
                this.id = Integer.parseInt(id);
                lastIdNum = this.id;
            }

            if (name == null || name.equals("")) throw new IllegalArgumentException();
            else this.name = name;
            if (coordinates == null) throw new IllegalArgumentException();
            else this.coordinates = coordinates;
            if (creationDate.equals("")) this.creationDate = LocalDate.now();
            else this.creationDate = LocalDate.parse(creationDate);
            if (from == null) throw new IllegalArgumentException();
            else this.from = from;
            if (to == null) throw new IllegalArgumentException();
            else this.to = to;
            if (distance < 1 || distance == null) throw new IllegalArgumentException("distance incorrect");
            else this.distance = distance;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


    public static int getNewId(){
        lastIdNum=lastIdNum+1;
        return lastIdNum;
    }

    @Override
    public String toString() {
        return "id: " + id + ", name: " + name + ", " + coordinates.toString() +
                ", creationDate: " + creationDate + ", " + from.toString() + ", " + to.toString() +
                ", distance: " + distance;
    }

    public String toStringWithoutId() {
        return  "name: " + name + ", " + coordinates.toString() +
                ", creationDate: " + creationDate + ", " + from.toString() + ", " + to.toString() +
                ", distance: " + distance;
    }

    @Override
    public int compareTo(Route routeObj) {
        Integer compareId = id;
        return compareId.compareTo(routeObj.getId());
    }

    /**
     * Класс для сравнения элементов Route по дистанции
     */
    public static class ComparatorByDistance implements Comparator<Route> {
        @Override
        public int compare(Route o1, Route o2) {
            return o1.getDistance().compareTo(o2.getDistance());
        }
    }

    /**
     * Класc для сравнения элементов Route по дате создания
     */
    public static class ComparatorByCreationDate implements Comparator<Route> {
        @Override
        public int compare(Route o1, Route o2) {
            return o1.getCreationDate().compareTo(o2.getCreationDate());
        }
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public Long getDistance(){
        return distance;
    }
}
