package com.lapin.common.utility;

import com.lapin.common.impl.Add;
import com.lapin.common.impl.AddIfMax;
import com.lapin.common.impl.RemoveLower;
import com.lapin.common.impl.Update;
import com.lapin.common.utility.CollectionManager;
import com.lapin.common.data.Coordinates;
import com.lapin.common.data.LocationFrom;
import com.lapin.common.data.LocationTo;
import com.lapin.common.data.Route;


/**
 * Класс создаёт/обновляет элементы {@link Route} в интерактивном режиме.
 */
public class CreateNewElementManager {
    /**
     * Максимальное значение для координаты x
     */
    private static final double MAX_COORDINATE_X = 699;

    /**
     * Создать новый элемент {@link Route}
     * @return Route
     */
    public static Route createNewElement() {
        Route route = new Route("",
                askName(),
                new Coordinates(askCoordinateX(), askCoordinateY()),
               "",
                new LocationFrom(askLocationFromX(), askLocationFromY(), askLocationFromZ()),
                new LocationTo(askLocationToX(), askLocationToY(), askLocationToName()),
                askDistance());
        System.out.println("Preview:");
        System.out.println(route.toStringWithoutId());
        return route;
    }

    private static String askName() {
        while (true) {
            System.out.println("Введите Route name (тип: String, не может быть пустой):");
            try {
                String userPrint = ConsoleManager.getUserPrint();
                if (userPrint.equals("")) {
                    throw new IllegalArgumentException("Значение Route name не может быть пустой строкой.");
                }
                return userPrint;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    private static Double askCoordinateX() {
        while (true) {
            System.out.println("Введите Coordinate X (тип: double, не более 699):");
            try {
                double userPrint = Double.parseDouble(ConsoleManager.getUserPrint());
                if (userPrint > MAX_COORDINATE_X) {
                    throw new IllegalArgumentException("Значение Coordinate X " +
                            "превышает максимально допустимое: " + MAX_COORDINATE_X);
                }
                return userPrint;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    private static Double askCoordinateY() {
        while (true) {
            try {
                System.out.println("Введите Coordinate Y (тип: double):");
                return Double.parseDouble(ConsoleManager.getUserPrint());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private static Integer askLocationFromX() {
        while (true) {
            try {
                System.out.println("Введите LocationFrom X (тип: int):");
                return Integer.parseInt(ConsoleManager.getUserPrint());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private static float askLocationFromY() {
        while (true) {
            try {
                System.out.println("Введите LocationFrom Y (тип: float):");
                return Float.parseFloat(ConsoleManager.getUserPrint());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private static double askLocationFromZ() {
        while (true) {
            try {
                System.out.println("Введите LocationFrom Z (тип: double):");
                return Double.parseDouble(ConsoleManager.getUserPrint());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private static Float askLocationToX() {
        while (true) {
            try {
                System.out.println("Введите LocationTo X (тип: float):");
                return Float.parseFloat(ConsoleManager.getUserPrint());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private static Long askLocationToY() {
        while (true) {
            try {
                System.out.println("Введите LocationTo Y (тип: long):");
                return Long.parseLong(ConsoleManager.getUserPrint());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private static String askLocationToName() {
        while (true) {
            try {
                System.out.println("Введите LocationTo name (тип: String):");
                return ConsoleManager.getUserPrint();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private static Long askDistance() {
        while (true) {
            try {
                System.out.println("Введите distance (тип: long, больше 1):");
                var userPrint = Long.parseLong(ConsoleManager.getUserPrint());
                if (userPrint < 2) {
                    throw new IllegalArgumentException("Значение distance должно быть больше 1");
                }
                return userPrint;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}
