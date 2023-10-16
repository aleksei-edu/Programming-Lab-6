package com.lapin.common.utility;


import com.lapin.common.commands.Command;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class HistoryQueue extends ConcurrentLinkedQueue {
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    int capacity;
    /**
     * Метод возвращает последние 10 команд
     */
    public HistoryQueue(){
        this(DEFAULT_INITIAL_CAPACITY);
    }
    public HistoryQueue(int capacity){
        this.capacity = capacity;
    }
    public synchronized ArrayList<Command> last10() {
        return (ArrayList<Command>) stream().limit(10).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public boolean add(Object o) {
        if(size() == capacity){
            poll();
        }
        return super.add(o);
    }
}
