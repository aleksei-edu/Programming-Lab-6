package com.lapin.common.impl;


import com.lapin.common.commands.CheckAccess;
import com.lapin.common.utility.CommandManager;
import com.lapin.common.utility.OutManager;
import com.lapin.di.annotation.ClassMeta;
import com.lapin.common.commands.AbstractCommand;
import com.lapin.common.commands.Command;
import com.lapin.network.AccessType;
import com.lapin.di.context.ApplicationContext;
import com.lapin.network.ClientType;
import com.lapin.network.StatusCodes;
import org.reflections.Reflections;


import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Команда, выводящая справку по всем доступным командам
 */
@ClassMeta(
        name = "help",
        description = "вывести справку по доступным командам")
public class Help extends AbstractCommand {
    private ClientType clientType = CommandManager.getInstance().getClient().getClientType();
    {
        super.accessType = AccessType.ALL;
        super.executingLocal =true;
    }
    @Override
    public void execute(String argument, Serializable argObj) {
        try {
            Reflections scanner = new Reflections("");
            Set<Class<? extends AbstractCommand>> implementationClasses = scanner.getSubTypesOf(AbstractCommand.class);
            String response = implementationClasses
                    .stream()
                    .filter(clazz -> {
                        Object obj = null;
                        try {
                            obj = ApplicationContext.getInstance().getBean(clazz,true,false);
                        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                            throw new RuntimeException();
                        }
                        Command command = (Command) (obj instanceof Command ? obj : null);
                        try {
                            Field field = clazz.getSuperclass().getDeclaredField("accessType");
                            field.setAccessible(true);
                            boolean flag = CheckAccess.check(clientType, (AccessType) field.get(command));
                            return flag;
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            return false;
                        }
                    })
                    .map(clazz -> clazz.getAnnotation(ClassMeta.class))
                    .filter(Objects::nonNull)
                    .map(an -> an.name() + " – " + an.description())
                    .sorted()
                    .collect(Collectors.joining("\n"));
            OutManager.push(StatusCodes.OK, response);

        } catch (RuntimeException e) {
            OutManager.push(StatusCodes.ERROR, "The command ended with an error. Try again.");
        }
    }
}
