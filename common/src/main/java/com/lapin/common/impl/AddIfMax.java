package com.lapin.common.impl;


import com.lapin.common.data.Route;
import com.lapin.common.exception.AccessDeniedException;
import com.lapin.common.utility.CollectionManager;
import com.lapin.common.utility.CommandManager;
import com.lapin.common.utility.OutManager;
import com.lapin.di.annotation.ClassMeta;
import com.lapin.common.commands.AbstractCommand;
import com.lapin.network.AccessType;
import com.lapin.network.StatusCodes;

import java.io.Serializable;


/**
 * Команда добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
 */
@ClassMeta(
        name = "add_if_max",
        description = "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции"
)
public class AddIfMax extends AbstractCommand {
    private CollectionManager collectionManager = CommandManager.getInstance().getCollectionManager();
    {
        super.accessType = AccessType.ALL;
        super.NeedObj = true;
    }

    @Override
    public void execute(String argument, Serializable argObj) {
        try {
            if(!(argObj instanceof Route)) throw new RuntimeException();
            String response = "";
            boolean flag = true;
            ((Route) argObj).setId(Route.getNewId());
            Route route = (Route) argObj;
            for (Route index : collectionManager.getRouteCollection()) {
                if (route.compareTo(index) < 1) {
                    flag = false;
                }
            }
            if (flag) {
                collectionManager.add(route);
                response += "Элемент добавлен";
            } else {
                response += "Элемент НЕ добавлен";
            }
            OutManager.push(StatusCodes.OK, response);
        } catch (AccessDeniedException e){
            OutManager.push(StatusCodes.ERROR, "Access denied");
        }
        catch (RuntimeException e) {
            OutManager.push(StatusCodes.ERROR, "The command ended with an error. Try again.");
        }
    }
}
