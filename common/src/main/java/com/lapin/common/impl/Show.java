package com.lapin.common.impl;


import com.lapin.network.AccessType;
import com.lapin.common.utility.CollectionManager;
import com.lapin.common.utility.CommandManager;
import com.lapin.common.utility.OutManager;
import com.lapin.di.annotation.ClassMeta;
import com.lapin.common.data.Route;
import com.lapin.common.commands.AbstractCommand;
import com.lapin.network.StatusCodes;


import java.io.Serializable;
import java.util.stream.Collectors;


/**
 * Команда выводит в консоль все элементы коллекции
 */
@ClassMeta(
        name = "show",
        description = "вывести в стандартный поток вывода все элементы коллекции в строковом представлении")
public class Show extends AbstractCommand {
    private CollectionManager collectionManager = CommandManager.getInstance().getCollectionManager();
    {
        super.accessType = AccessType.ALL;
    }


    @Override
    public void execute(String argument, Serializable argObj) {
        try {
            String response;
            if (collectionManager.getRouteCollection().size() == 0) {
                response = "Коллекция пуста.";
            }
            else response = collectionManager
                    .getRouteCollection()
                    .stream()
                    .map(Route::toString)
                    .collect(Collectors.joining("\n"));
            OutManager.push(StatusCodes.OK,response);

        } catch (RuntimeException e) {
            OutManager.push(StatusCodes.ERROR, "The command ended with an error. Try again.");
        }
    }
}
