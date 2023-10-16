package com.lapin.common.impl;

import com.lapin.common.exception.CommandNeedArgumentException;
import com.lapin.common.utility.CollectionManager;
import com.lapin.common.utility.CommandManager;
import com.lapin.common.utility.OutManager;
import com.lapin.di.annotation.ClassMeta;
import com.lapin.common.commands.AbstractCommand;
import com.lapin.network.AccessType;
import com.lapin.network.StatusCodes;

import java.io.Serializable;


/**
 * Команда удаляет все элементы по значению поля distance
 */
@ClassMeta(name = "remove_all_by_distance", description = "удалить из коллекции все элементы, значение поля distance которого эквивалентно заданному")
public class RemoveAllByDistance extends AbstractCommand {
    private CollectionManager collectionManager = CommandManager.getInstance().getCollectionManager();
    {
        super.accessType = AccessType.ALL;
    }


    @Override
    public void execute(String argument, Serializable argObj) {
        try {
            if (argument.isEmpty()) throw new CommandNeedArgumentException();
            String response = "";
            try {
                Long distance = Long.parseLong(argument);
                collectionManager.getRouteCollection().removeIf(route -> (route.getDistance().equals(distance)));
                response += "Элементы с distance:" + distance.toString() + " - удалены.";
                OutManager.push(StatusCodes.OK,response);
            } catch (NumberFormatException e) {
                OutManager.push(StatusCodes.ERROR, "The command ended with an error. Try again.");
            }

        } catch (RuntimeException e) {
            OutManager.push(StatusCodes.ERROR, "The command ended with an error. Try again.");
        }
    }
}
