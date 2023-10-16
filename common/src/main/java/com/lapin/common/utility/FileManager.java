package com.lapin.common.utility;


import com.lapin.common.data.Coordinates;
import com.lapin.common.data.LocationFrom;
import com.lapin.common.data.LocationTo;
import com.lapin.common.data.Route;
import com.lapin.common.exception.MaxRecursionExceededException;
import com.lapin.common.exception.NullEnvException;
import com.lapin.common.utility.CollectionManager;
import com.lapin.common.utility.ConsoleManager;
import com.opencsv.*;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Класс управляет записью/чтением из файлов
 */
public class FileManager {
    /**
     * Переменная окружения
     */
    @Setter
    @Getter
    private String env;
    private static CollectionManager collectionManager;
    private static ConsoleManager consoleManager;
    private static final int MaxRecursionDepth = 3;
    private static int CurrentRecursionDepth = 0;

    public static void setConsoleManager(ConsoleManager consoleManager) {
        FileManager.consoleManager = consoleManager;
    }
    public static void setCollectionManager(CollectionManager collectionManager){
        FileManager.collectionManager = collectionManager;
    }

    /**
     * Сохранить коллекцию в файл
     */

    public String saveCollection(CollectionManager collectionManager) throws IOException,NullEnvException,FileNotFoundException {
        BufferedWriter bWriter = null;
        CSVWriter writer = null;
        try {
            if (System.getenv(env) == null) throw new NullEnvException();
            OutputStream os = new FileOutputStream(System.getenv(env));
            bWriter = new BufferedWriter(new OutputStreamWriter(os));
            writer = new CSVWriter(bWriter);
            writer.writeAll(collectionManager.getStringRouteCollection());
        } catch (NullEnvException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            writer.close();
            String response = "Коллекция успешно сохранена в " + env + ".";
            collectionManager.saveTimeCollection();
            return response;
        }
    }

    /**
     * Загрузить коллекцию из файла
     */
    public void readCollection() {
        BufferedReader reader = null;
        String[] line = null;
        try {
            if (System.getenv(env) == null) throw new NullEnvException();
            reader = new BufferedReader(new FileReader(System.getenv(env)));
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(',')
                    .withIgnoreQuotations(true)
                    .build();
            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(0)
                    .withCSVParser(parser)
                    .build();
            while ((line = csvReader.readNext()) != null) {
                for (int i = 0; i < line.length; i++) {
                    line[i] = line[i].trim().toLowerCase();
                }
                collectionManager.add(new Route(line[0], line[1],
                        new Coordinates(Double.parseDouble(line[2]), Double.parseDouble(line[3])), line[4],
                        new LocationFrom(Integer.parseInt(line[5]), Float.parseFloat(line[6]), Double.parseDouble(line[7])),
                        new LocationTo(Float.parseFloat(line[8]), Long.parseLong(line[9]), line[10]), Long.parseLong(line[11])));
            }
        } catch (NullEnvException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Выполнить скрипт из файла
     *
     * @param fileName
     */
    public static void readScript(String fileName) throws MaxRecursionExceededException, FileNotFoundException, IOException {
        CurrentRecursionDepth+=1;
        if(MaxRecursionDepth >= CurrentRecursionDepth) {
            BufferedReader reader = null;
            try {
                File file = new File(fileName);
                reader = new BufferedReader(new FileReader(file));
                InputStream fileInput = new FileInputStream(file);
                Scanner userScanner = new Scanner(fileInput);
                ConsoleManager.setUserScanner(userScanner);
                while ((reader.readLine()) != null) {
                    consoleManager.interactiveMode();
                }
                userScanner = new Scanner(System.in);
                CurrentRecursionDepth -= 1;
                ConsoleManager.setUserScanner(userScanner);
            } finally {
                reader.close();
            }
        }
        else throw new MaxRecursionExceededException();
    }

}