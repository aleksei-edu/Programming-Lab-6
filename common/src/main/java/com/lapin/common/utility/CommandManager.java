package com.lapin.common.utility;

import com.lapin.common.client.Client;
import com.lapin.common.commands.CheckAccess;
import com.lapin.common.data.Route;
import com.lapin.common.exception.CommandNotFoundException;
import com.lapin.di.context.ApplicationContext;

import com.lapin.common.commands.Command;

import com.lapin.network.ClientType;
import com.lapin.network.StatusCodes;
import lombok.Getter;

import java.io.Serializable;


/**
 * Класс управляет командами
 */
@Getter
public class CommandManager {
    private CollectionManager collectionManager;
    private FileManager fileManager;
    private Client client;
    private Client_IO clientIO;
    private static final CommandManager COMMAND_MANAGER = new CommandManager();
    private CommandManager(){
    }
    public void setClient(Client client){
        COMMAND_MANAGER.client = client;
    }
    public static CommandManager getInstance() {
        return COMMAND_MANAGER;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public Client getClient() {
        return client;
    }

    public void setCollectionManager(CollectionManager collectionManager){
        COMMAND_MANAGER.collectionManager = collectionManager;
    }
    public void setFileManager(FileManager fileManager){
        COMMAND_MANAGER.fileManager = fileManager;
    }
    public void setClientIO(Client_IO clientIO){
        this.clientIO = clientIO;
    }

    public CollectionManager getCollectionManager(){
        return collectionManager;
    }

    /**
     * Метод запускает выполнение команды
     *
     * @param userCommand команда введенная пользователем
     * @param argument    аргумент команды введенной пользователем
     */
    public void execute(String userCommand, String argument) {
        try {
            Object obj = ApplicationContext.getInstance().getBean(userCommand);
            Command command = (Command) (obj instanceof Command ? obj : null);
            if (command != null) {
                client.getHistory().add(command);
                if(CheckAccess.check(client.getClientType(),command.getAccessType())){
                    Route argObj = null;
                    if(command.getNeedObj()){
                        argObj = CreateNewElementManager.createNewElement();
                    }
                    if(command.getExecutingLocal() || client.getClientType().equals(ClientType.LOCAL)){
                        command.execute(argument,argObj);
                    }
                    else{
                        Pair response = clientIO.handle(userCommand, argument, argObj);
                        client.setStatusCode((StatusCodes) response.getFirst());
                        OutManager.push( (StatusCodes) response.getFirst(), (String) response.getSecond());
                    }
                }
                else OutManager.push(StatusCodes.ERROR,"Access denied!");
            }
            else {
                OutManager.push(StatusCodes.ERROR,"Command not found!");
            }
        } catch (CommandNotFoundException e) {
            OutManager.push(StatusCodes.ERROR,"Command not found!");
        } catch (Exception e) {
            OutManager.push(StatusCodes.ERROR,"Failed to execute command!");
        }
        finally {
            Pair response = OutManager.pop();
            client.setStatusCode((StatusCodes) response.getFirst());
            System.out.println((String)response.getSecond());
        }
    }
    public static void execute(String userCommand, String argument, Serializable argObj) {
        try {
            Object obj = ApplicationContext.getInstance().getBean(userCommand);
            Command command = (Command) (obj instanceof Command ? obj : null);
            if (command != null) {
                command.execute(argument, argObj);
            }
            else {
                OutManager.push(StatusCodes.ERROR,"Command not found!");
            }
        } catch (CommandNotFoundException e) {
            OutManager.push(StatusCodes.ERROR,"Command not found!");
        } catch (Exception e) {
            OutManager.push(StatusCodes.ERROR,"Failed to execute command!");
        }
    }

}
