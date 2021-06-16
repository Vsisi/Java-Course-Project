import javax.swing.*;
import java.io.*;
import java.util.HashMap;

class FileOperation {//load and save config
    //Loading
    public static User loadUserInfo() {
        User user = new User();
        try {
            parseUserInfoStr(loadUserInfoStr(), user);
        }catch (Exception e) {
            e.printStackTrace();
            loadingError();
        }
        return user;
    }
    private static String loadUserInfoStr() throws Exception {
        File file = new File(Constant.CONFIG_PATH + "user.conf");
        if(!file.exists()) {
            return null;
        }
        Reader reader = new BufferedReader(new FileReader(file));
        char[] info = new char[(int)file.length()];
        reader.read(info);
        reader.close();
        return new String(info);
    }
    private static void parseUserInfoStr(String info, User user) throws Exception {
        if(info == null || info.length() == 0) {
            return;
        }
        HashMap<String, String> m = new HashMap<String, String>();
        for(String item:info.split(",")) {
            String[] keyValue = item.split(":");
            m.put(keyValue[0], keyValue[1]);
        }

        if(m.containsKey("money")) { //Here may need further edit if more user info
            user.setMoney(Integer.parseInt(m.get("money")));
        }
        //...
    }


    public static ShipList loadCharactersInfo() {
        ShipList ships = new ShipList();
        try {
            parseCharacterInfoStr(loadCharacterInfoStr(), ships);
        }catch (Exception e) {
            e.printStackTrace();
            loadingError();
        }
        return ships;
    }
    private static String loadCharacterInfoStr() throws Exception {
        File file = new File(Constant.CONFIG_PATH + "chara.conf");
        if(!file.exists()) {
            return null;
        }
        char[] info = new char[(int)file.length()];
        Reader reader = new BufferedReader(new FileReader(file));
        reader.read(info);
        reader.close();
        return new String(info);
    }
    private static void parseCharacterInfoStr(String info, ShipList ships) throws Exception {
        if(info == null || info.length() == 0) {
            return;
        }
        String[] infoItems = info.split(Constant.LINE_BREAK);
        HashMap<String, String> m = new HashMap<String, String>();
        for(String infoItem:infoItems) {
            String[] items = infoItem.split(",");
            m.clear();
            for (String item:items) {
                String[] keyValue = item.split(":");
                m.put(keyValue[0], keyValue[1]);
            }
            Ship ship = null;
            switch (m.get("type")) {
                case "Destroyer":
                    ship = new Destroyer(m.get("autonym"), Integer.parseInt(m.get("price")));
                    ship.setNickname(m.get("nickname"));
                    ships.add(ship);
                    break;
                case "Cruiser":
                    ship = new Cruiser(m.get("autonym"), Integer.parseInt(m.get("price")));
                    ship.setNickname(m.get("nickname"));
                    ships.add(ship);
                    break;
                case "Battleship":
                    ship = new Battleship(m.get("autonym"), Integer.parseInt(m.get("price")));
                    ship.setNickname(m.get("nickname"));
                    ships.add(ship);
                    break;
                case "Carrier":
                    ship = new Carrier(m.get("autonym"), Integer.parseInt(m.get("price")));
                    ship.setNickname(m.get("nickname"));
                    ships.add(ship);
                    break;
            }
        }
    }
    private static void loadingError() {
        JOptionPane.showMessageDialog(null, "加载配置错误!", "错误", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }

    //Saving
    public static void saveCharacterConfig(ShipList ships) throws Exception{
        File file = new File(Constant.CONFIG_PATH + "chara.conf");
        Writer writer = new BufferedWriter(new FileWriter(file));
        for(Ship ship:ships) {
            writer.write(ship.toString() + "\r\n");
        }
        writer.close();
    }
    public static void saveUserConfig(User user) throws Exception{
        File file = new File(Constant.CONFIG_PATH + "user.conf");
        Writer writer = new BufferedWriter(new FileWriter(file));
        writer.write(user.toString());
        writer.close();
    }
    public static void savingError() {
        JOptionPane.showMessageDialog(null, "自动保存错误！", "错误", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }
}
