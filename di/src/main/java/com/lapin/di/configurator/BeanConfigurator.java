package com.lapin.di.configurator;

import org.reflections.Reflections;

/**
 * Класс ищет реализации заданных объектов
 */
public interface BeanConfigurator {
    Class<?> getImplementationClass(String commandName) throws ClassNotFoundException;

    <T> Class<? extends T> getImplementationClass(Class<T> interfaceClass);

    public Reflections getScanner();
    public void setScanner(String packageToScan);
}
