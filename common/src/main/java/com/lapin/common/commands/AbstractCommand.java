package com.lapin.common.commands;


import com.lapin.network.AccessType;
import com.lapin.di.annotation.ClassMeta;

import java.io.Serializable;

/**
 * Абстрактный класс, от которого наследуются все команды.
 */

public abstract class AbstractCommand implements Command {
    private String name;
    protected AccessType accessType = AccessType.NO_ONE;
    protected boolean NeedObj = false;
    protected boolean executingLocal = false;
    @Override
    public void execute(String arg, Serializable argObj) {

    }
    @Override
    public String toString() {
        ClassMeta commandMeta = this.getClass().getAnnotation(ClassMeta.class);
        return commandMeta.name() + " – " + commandMeta.description();
    }
    @Override
    public boolean getNeedObj(){
        return NeedObj;
    }
    @Override
    public boolean getExecutingLocal(){return executingLocal;}

    @Override
    public String getName() {
        return this.getClass().getAnnotation(ClassMeta.class).name();
    }

    @Override
    public AccessType getAccessType() {
        return accessType;
    }
    protected void setAccessType(AccessType accessType){
        this.accessType = accessType;
    }
}
