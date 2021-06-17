import java.util.HashMap;

class Constant{
    public static String ROOT = System.getProperty("user.dir");
    public static String RESOURCE_ROOT = ROOT + "\\img\\";
    public static String CONFIG_PATH = ROOT + "\\conf\\";
    public static String LINE_BREAK = "\r\n";

    public static int DEFAULT_MONEY = 500000;

    public static int WINDOW_X = 300;
    public static int WINDOW_Y = 150;
    public static int WINDOW_WIDTH = 400;
    public static int WINDOW_HEIGHT = 600;

    public static int CONTENTPANE_WIDTH;
    public static int CONTENTPANE_HEIGHT;

    public static int DEFAULT_ELEMENT_X = 0;
    public static int DEFAULT_ELEMENT_Y = 0;
    public static int DEFAULT_ELEMENT_WIDTH = 100;
    public static int DEFAULT_ELEMENT_HEIGHT = 100;

    public static int SWITCH_BUTTON_WIDTH = 50;
    public static int SWITCH_BUTTON_HEIGHT = 50;

    public static int MONEY_BG_X = 20;
    public static int MONEY_BG_Y = 20;
    public static int MONEY_BG_WIDTH = 100;
    public static int MONEY_BG_HEIGHT = 30;

    public static int DEFAULT_SHIP_X;
    public static int DEFAULT_SHIP_Y;
    public static int DEFAULT_SHIP_WIDTH = 200;
    public static int DEFAULT_SHIP_HEIGHT = 200;
//    public static int DEFAULT_SHIP_PRICE = 2000;
    public static HashMap<String, Integer> SHIP_PRICE_LOWER_BOUND = new HashMap<String, Integer>() {
        {
            put("dd", 1000);
            put("cl", 2000);
            put("bb", 5000);
            put("cv", 4000);
        }
    };
    public static HashMap<String, Integer> SHIP_PRICE_UPPER_BOUND = new HashMap<String, Integer>() {
        {
            put("dd", 2500);
            put("cl", 4000);
            put("bb", 8000);
            put("cv", 6500);
        }
    };
    public static String DEFAULT_SHIP_ACTION = "normal";

    public static int BUY_BUTTON_WIDTH = Constant.DEFAULT_SHIP_WIDTH;
    public static int BUY_BUTTON_HEIGHT = 40;

    public static int CONSTRUCT_BUTTON_Y = 10;
    public static int CONSTRUCT_BUTTON_WIDTH = 50;
    public static int CONSTRUCT_BUTTON_HEIGHT = 50;

    public static int QUERY_BUTTON_Y = 70;
    public static int QUERY_BUTTON_WIDTH = 50;
    public static int QUERY_BUTTON_HEIGHT = 50;

    public static int REFRESH_FREQUENCY = 50;

    public static int AUTOSAVE_FREQUENCY = 1000;

    public static int SWITCH_SPEED = 20;

    public static void updateDefaultElementPos() {
         DEFAULT_SHIP_X = (int)(CONTENTPANE_WIDTH-DEFAULT_SHIP_WIDTH)/2;
         DEFAULT_SHIP_Y = (int)(CONTENTPANE_HEIGHT-DEFAULT_SHIP_HEIGHT)/2;
    }

    public static String BOUGHT_ACTION = "motou";
    public static String TOUCH_ACTION = "touch";

    private static String[] DD_NAME = {"anshan", "changchun", "fushun", "taiyuan"};
    private static String[] BB_NAME = {"georgia", "nagato", "ncarolina", "bismarck"};
    private static String[] CV_NAME = {"enterprise", "akagi", "centaur", "illustrious"};
    private static String[] CL_NAME = {"cheshire", "hermione", "neptune", "seattle"};
    public static HashMap<String, String[]> SHIP_NAME = new HashMap<String, String[]>(){
        {
            put("dd", Constant.DD_NAME);
            put("bb", Constant.BB_NAME);
            put("cv", Constant.CV_NAME);
            put("cl", Constant.CL_NAME);
        }
    };
}

class Sign{
    public static boolean switchingLeft = false;
    public static boolean switchingRight = false;

}