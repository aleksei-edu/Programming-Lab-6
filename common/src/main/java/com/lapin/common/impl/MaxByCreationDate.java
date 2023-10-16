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
 * Команда выводит максимальный по creationDate элемент Route
 */
@ClassMeta(name = "max_by_creation_date", description = "вывести любой объект из коллекции, значение поля creationDate которого является максимальным")
public class MaxByCreationDate extends AbstractCommand {
    private CollectionManager collectionManager = CommandManager.getInstance().getCollectionManager();
    {
        super.accessType = AccessType.ALL;
    }

    @Override
    public void execute(String argument, Serializable argObj) {
        try {
            String response = "";
            if (collectionManager.getRouteCollection().size() == 0) {
                response += "Коллекция пуста.";
            }
            else response = collectionManager
                    .getRouteCollection()
                    .stream()
                    .max(new Route.ComparatorByCreationDate())
                    .stream()
                    .toList()
                    .get(0)
                    .toString();
            OutManager.push(StatusCodes.OK,response);

        } catch (RuntimeException e) {
            OutManager.push(StatusCodes.ERROR, "The command ended with an error. Try again.");
        }
    }
}
