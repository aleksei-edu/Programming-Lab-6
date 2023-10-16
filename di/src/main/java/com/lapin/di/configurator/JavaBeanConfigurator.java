package com.lapin.di.configurator;


import com.lapin.di.annotation.ClassMeta;


import org.reflections.Reflections;

import java.nio.file.Path;
import java.util.Set;


public class JavaBeanConfigurator implements BeanConfigurator {
    Reflections scanner;

    public JavaBeanConfigurator(String packageToScan) {
        this.scanner = new Reflections(packageToScan);
    }

    public Reflections getScanner() {
        return scanner;
    }

    public void setScanner(String packageToScan){this.scanner = new Reflections(packageToScan);}

    @Override
    public <T> Class<? extends T> getImplementationClass(Class<T> interfaceClass) {
        Set<Class<? extends T>> implementationClasses = scanner.getSubTypesOf(interfaceClass);
        if (implementationClasses.size() != 1) {
            throw new RuntimeException("Interface has 0 or more than 1 implementation");
        }
        return implementationClasses.stream().findFirst().get();
    }

    @Override
    public Class<?> getImplementationClass(String className) throws ClassNotFoundException {
        Set<Class<?>> implementationClasses = scanner.getTypesAnnotatedWith(ClassMeta.class);
        Class<?> aClass = implementationClasses
                .stream()
                .filter((clazz) -> clazz.getAnnotation(ClassMeta.class)
                        .name()
                        .equals(className))
                .findFirst()
                .orElseThrow(ClassNotFoundException::new);
        return aClass;
    }
}
