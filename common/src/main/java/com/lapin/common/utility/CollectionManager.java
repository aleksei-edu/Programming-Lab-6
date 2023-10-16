package com.lapin.common.utility;

import com.lapin.common.data.Route;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

public interface CollectionManager {

    public int getFreeNumberForId();

    public Set<Route> getRouteCollection();

    public ArrayList<String[]> getStringRouteCollection();

    public void saveTimeCollection();

    public void add(Route route);

    public boolean update(Route newRoute,Integer id);

    public LocalDate getLastInitTime();

    public LocalDate getSaveTimeCollection();

    public void loadCollection();

    public void clear();

    public void addStringRouteCollection(String[] stringRoute);
}
