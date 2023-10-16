package com.lapin.common.impl;


import com.lapin.common.utility.CollectionManager;
import com.lapin.common.utility.CommandManager;
import com.lapin.common.utility.OutManager;
import com.lapin.di.annotation.ClassMeta;
import com.lapin.common.data.Route;
import com.lapin.common.commands.AbstractCommand;
import com.lapin.network.AccessType;
import com.lapin.network.StatusCodes;



import java.io.Serializable;

/**
 * Команда удаляет все элементы меньшие, чем переданный ей
 */
@ClassMeta(name = "remove_lower", description = "удалить из коллекции все элементы, меньшие, чем заданный")
public class RemoveLower extends AbstractCommand {
    private CollectionManager collectionManager = CommandManager.getInstance().getCollectionManager();
    {
        super.accessType = AccessType.ALL;
        super.NeedObj = true;
    }
    @Override
    public void execute(String argument, Serializable argObj) {
        try {
            String response = "";
            if(!(argObj instanceof Route)) throw new RuntimeException();
            Route route = (Route) argObj;
            collectionManager
                    .getRouteCollection()
                    .removeIf(route1 -> (route1.compareTo(route)<1));
            OutManager.push(StatusCodes.OK,"Элементы успешно удалены");
        } catch (RuntimeException e) {
            OutManager.push(StatusCodes.ERROR, "The command ended with an error. Try again.");
        }
    }
}
