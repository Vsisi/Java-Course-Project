import jdk.jshell.execution.Util;

import java.util.Random;

class Utility{
    public static String getCharImgFullPath(String name) {
        return Constant.RESOURCE_ROOT + name + "_" + Constant.DEFAULT_SHIP_ACTION + ".gif";
    }
    public static String getCharImgFullPath(String name, String action) {
        return Constant.RESOURCE_ROOT + name + "_" + action + ".gif";
    }
    public static String getCharImgFullPath(String name, String action, String ext) {
        return Constant.RESOURCE_ROOT + name + "_" + action + "." + ext;
    }
    public static String randomAccessName(String type) {
        String[] nameList = Constant.SHIP_NAME.get(type);
        int index = new Random().nextInt(nameList.length);
        return nameList[index];
    }
    public static Ship getShip(String type) {
        String autonym = Utility.randomAccessName(type);
        Ship ship = null;
        int shipPrice = new Random().nextInt(
                Constant.SHIP_PRICE_UPPER_BOUND.get(type) - Constant.SHIP_PRICE_LOWER_BOUND.get(type)
        ) + Constant.SHIP_PRICE_LOWER_BOUND.get(type);
        switch (type) {
            case "dd":
                ship = new Destroyer(autonym, shipPrice);
                break;
            case "bb":
                ship = new Battleship(autonym, shipPrice);
                break;
            case "cl":
                ship = new Cruiser(autonym, shipPrice);
                break;
            case "cv":
                ship = new Carrier(autonym, shipPrice);
                break;
        }
        return ship;
    }
    public static String getFullPath(String fileName) {
        return Constant.RESOURCE_ROOT + fileName;
    }
}