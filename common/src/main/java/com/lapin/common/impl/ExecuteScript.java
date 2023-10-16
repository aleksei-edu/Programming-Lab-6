package com.lapin.common.impl;

import com.lapin.common.exception.MaxRecursionExceededException;
import com.lapin.common.utility.FileManager;
import com.lapin.common.utility.OutManager;
import com.lapin.di.annotation.ClassMeta;
import com.lapin.common.commands.AbstractCommand;
import com.lapin.network.AccessType;
import com.lapin.network.StatusCodes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;


/**
 * Команда считывающая и выполняющая скрипт из файла
 */
@ClassMeta(
        name = "execute_script",
        description = "считать и исполнить скрипт из указанного файла.")
public class ExecuteScript extends AbstractCommand {
    {
        super.accessType = AccessType.ALL;
        super.executingLocal = true;
    }

    @Override
    public void execute(String argument, Serializable argObj) {
        try {
            FileManager.readScript(argument);
            OutManager.push(StatusCodes.OK,"The script is executed");
        } catch (FileNotFoundException e) {
            OutManager.push(StatusCodes.ERROR, "File not found");
        }
        catch (MaxRecursionExceededException e){
            OutManager.push(StatusCodes.ERROR, "Maximum recursion is exceeded!");
        }
        catch (RuntimeException | IOException e) {
            OutManager.push(StatusCodes.ERROR, "The command ended with an error. Try again.");
        }
    }
}
