package com.lapin.common.impl;


import com.lapin.network.AccessType;
import com.lapin.common.utility.CollectionManager;
import com.lapin.common.utility.CommandManager;
import com.lapin.common.utility.OutManager;
import com.lapin.di.annotation.ClassMeta;
import com.lapin.common.data.Route;
import com.lapin.common.exception.CommandNeedArgumentException;
import com.lapin.common.commands.AbstractCommand;
import com.lapin.network.StatusCodes;


import java.io.Serializable;


/**
 * Команда обновляет значение элемента коллекции
 */
@ClassMeta(name = "update", description = "обновить значение элемента коллекции, id которого равен заданному")
public class Update extends AbstractCommand {
    private CollectionManager collectionManager = CommandManager.getInstance().getCollectionManager();
    {
        super.accessType = AccessType.ALL;
        super.NeedObj = true;
    }
    @Override
    public void execute(String argument, Serializable argObj) {
        try {
            if (argument.isEmpty()) throw new CommandNeedArgumentException("Введите id элемента, " +
                    "значение которого хотите обновить");
            if(!(argObj instanceof Route)) throw new RuntimeException();
            try {
                    int id = Integer.parseInt(argument);
                    ((Route) argObj).setId(id);
                    boolean flag = collectionManager.update((Route) argObj,id);
                    if (flag) {
                        OutManager.push(StatusCodes.OK,"The element was successfully updated.");
                    }
                    else OutManager.push(StatusCodes.OK,"The element was not found.");
            } catch (NumberFormatException e) {
                OutManager.push(StatusCodes.ERROR, "The command ended with an error. Try again.");
            }

        } catch (RuntimeException e) {
            OutManager.push(StatusCodes.ERROR, "The command ended with an error. Try again.");
        }
    }
}
