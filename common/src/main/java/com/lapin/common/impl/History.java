package com.lapin.common.impl;


import com.lapin.common.utility.CommandManager;
import com.lapin.common.utility.HistoryQueue;
import com.lapin.common.utility.OutManager;
import com.lapin.di.annotation.ClassMeta;
import com.lapin.common.commands.AbstractCommand;
import com.lapin.common.commands.Command;
import com.lapin.network.AccessType;
import com.lapin.network.StatusCodes;

import java.io.Serializable;
import java.util.stream.Collectors;


/**
 * Команда, показывающая 10 последних команд
 */
@ClassMeta(name = "history", description = "вывести последние 10 команд (без их аргументов)")
public class History extends AbstractCommand {
    private HistoryQueue historyQueue = CommandManager.getInstance().getClient().getHistory();
    {
        super.accessType = AccessType.ALL;
        super.executingLocal = true;
    }

    @Override
    public void execute(String argument, Serializable argObj) {
        try {
            String response = "";
            response = historyQueue
                    .last10()
                    .stream()
                    .map(Command::getName)
                    .collect(Collectors.joining("\n"));
            OutManager.push(StatusCodes.OK,response);

        } catch (RuntimeException e) {
            OutManager.push(StatusCodes.ERROR, "The command ended with an error. Try again.");
        }
    }
}
