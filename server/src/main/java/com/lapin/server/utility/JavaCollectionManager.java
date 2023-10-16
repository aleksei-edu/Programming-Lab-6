package com.lapin.server.utility;


import com.lapin.common.utility.CollectionManager;
import com.lapin.common.utility.FileManager;
import com.lapin.di.annotation.Inject;
import com.lapin.common.data.Route;
import com.lapin.common.exception.IdOverflowException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс управляет коллекцией
 */
public class JavaCollectionManager implements CollectionManager {
    /**
     * Свободный номер для уникального id.
     */
    private static int freeNumberForId = 1;
    /**
     * Дата последней инициализации коллекции.
     */
    private static LocalDate lastInitTime;
    /**
     * Дата последнего сохранения коллекции.
     */
    private static LocalDate saveTime;
    /**
     * Коллекция в которой хранятся {@link Route}
     */
    private static final LinkedHashSet<Route> routeCollection = new LinkedHashSet<>();
    private static final ArrayList<String[]> stringRouteCollection = new ArrayList<>();
    private FileManager fileManager;
    public JavaCollectionManager(FileManager fileManager){
        this.fileManager = fileManager;
    }

    public ArrayList<String[]> getStringRouteCollection() {
        ArrayList<String[]> strRouteCol = new ArrayList<>();
        for(Route route : routeCollection){
            List<String> routeList = new ArrayList<>();
            routeList.add(((Integer) route.getId()).toString());
            routeList.add(route.getName());
            routeList.add(route.getCoordinates().getX().toString());
            routeList.add(((Double) route.getCoordinates().getY()).toString());
            routeList.add(route.getCreationDate().toString());
            routeList.add(route.getFrom().getX().toString());
            routeList.add(((Float) route.getFrom().getY()).toString());
            routeList.add(((Double) route.getFrom().getZ()).toString());
            routeList.add(route.getTo().getX().toString());
            routeList.add(route.getTo().getY().toString());
            routeList.add(route.getTo().getName());
            routeList.add(route.getDistance().toString());
            String[] str = {routeList.get(0), routeList.get(1), routeList.get(2), routeList.get(3), routeList.get(4),
                    routeList.get(5), routeList.get(6), routeList.get(7), routeList.get(8), routeList.get(9), routeList.get(10),
                    routeList.get(11)};
            strRouteCol.add(str);
        }
        return strRouteCol;
    }

    /**
     * Метод ищет новое уникальное id
     *
     * @return уникальное id
     */
    public int getFreeNumberForId() {
        while (true) {
            try {
                boolean flag = true;
                for (Route route : routeCollection) {
                    if (route.getId() == freeNumberForId) {
                        flag = false;
                        break;
                    }
                }
                if (!flag) {
                    freeNumberForId += 1;
                    if (freeNumberForId < 0) {
                        throw new IdOverflowException();
                    }
                } else {
                    return freeNumberForId;
                }
            } catch (IdOverflowException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Возвращает коллекцию из <b>Route</b>.
     *
     * @return коллекция из Route.
     */
    public Set<Route> getRouteCollection() {
        return routeCollection;
    }

    /**
     * Записывает последнее время сохранения коллекции.
     */
    public void saveTimeCollection() {
        saveTime = LocalDate.now();
    }

    @Override
    public void add(Route route) {
        routeCollection.add(route);
    }

    @Override
    public boolean update(Route newRoute,Integer id) {
        for (Route route : routeCollection) {
            if (route.getId() == id) {
                this.getRouteCollection().remove(route);
                this.add(newRoute);
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает последнюю дату сохранения коллекции.
     *
     * @return Последнюю дату сохранения.
     */
    public LocalDate getSaveTimeCollection() {
        return saveTime;
    }

    /**
     * Возвращает последнюю дату инициализации коллекции.
     *
     * @return последнюю дату инициализации.
     */
    public LocalDate getLastInitTime() {
        return lastInitTime;
    }

    /**
     * Загружает коллекцию из файла
     */
    public void loadCollection() {
        lastInitTime = LocalDate.now();
        fileManager.readCollection();
    }

    /**
     * Очищает коллекцию
     */
    public void clear() {
        routeCollection.clear();
        System.out.println("Коллекция успешно очищена.");
    }

    public void addStringRouteCollection(String[] stringRoute) {
        stringRouteCollection.add(stringRoute);
    }
}
