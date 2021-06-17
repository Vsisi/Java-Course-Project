import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

class Element{
    Element(String imgPath, int x, int y, int width, int height) {
        this.img = new ImageIcon(imgPath).getImage();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    Element(String imgPath) {
        this.img = new ImageIcon(imgPath).getImage();
        this.x = Constant.DEFAULT_ELEMENT_X;
        this.y = Constant.DEFAULT_ELEMENT_Y;
        this.width = Constant.DEFAULT_ELEMENT_WIDTH;
        this.height = Constant.DEFAULT_ELEMENT_HEIGHT;
    }

    //Getter
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }
    public Image getImg() {
        return this.img;
    }

    //Setter
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    protected int x, y, width, height;
    protected Image img;
}

abstract class Ship extends Element{
    Ship(String autonym, int price) {
        super(Utility.getCharImgFullPath(autonym), Constant.DEFAULT_SHIP_X, Constant.DEFAULT_SHIP_Y, Constant.DEFAULT_SHIP_WIDTH, Constant.DEFAULT_SHIP_HEIGHT);
        this.autonym = autonym;
        this.nickname = autonym;
        this.price = price;
    }
    Ship(String autonym, int price, int x, int y, int width, int height) {
        super(Utility.getCharImgFullPath(autonym), x, y, width, height);
        this.autonym = autonym;
        this.nickname = autonym;
        this.price = price;
    }

    //Getter
    public String getNickname() {
        return this.nickname;
    }
    public String getAutonym() {
        return this.autonym;
    }
    public int getPrice() {
        return this.price;
    }
    abstract public String getType();

    //Setter
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setImg(String action) {
        this.img = new ImageIcon(Utility.getCharImgFullPath(this.autonym, action)).getImage();
    }
    public void setImg(String action, String ext) {
        this.img = new ImageIcon(Utility.getCharImgFullPath(this.autonym, action, ext)).getImage();
    }

    //Action
    public void touchedAnimation() {
        new Thread() {
            public void run() {
                setImg(Constant.TOUCH_ACTION);
                try {
                    Thread.sleep(2000);
                }catch (Exception e) {}
                setImg(touchBehavior);
            }
        }.start();
    }
    abstract public void boughtAnimation();
    //Utility
    @Override
    public String toString() {
        return "type:" + this.getType() +
                ",autonym:" + this.getAutonym() +
                ",nickname:" + this.getNickname() +
                ",price:" + this.getPrice();
    }

    private final String autonym;
    private String nickname;
    private int price;
    protected String touchBehavior = "normal";
}

class Destroyer extends Ship{
    Destroyer(String autonym, int price) {
        super(autonym, price);
    }

    public String getType() {
        return "Destroyer";
    }
    public void touchedAnimation() {
        super.touchedAnimation();
        System.out.println("DD dayo");
    }
    public void boughtAnimation() {
        setImg(Constant.BOUGHT_ACTION);
    }
}

class Cruiser extends Ship{
    Cruiser(String autonym, int price) {
        super(autonym, price);
    }
    public String getType() {
        return "Cruiser";
    }
    public void touchedAnimation() {
        super.touchedAnimation();
        System.out.println("CL dayo");
    }
    public void boughtAnimation() {
        setImg(Constant.BOUGHT_ACTION);
    }}

class Battleship extends Ship{
    Battleship(String autonym, int price) {
        super(autonym, price);
    }
    public String getType() {
        return "Battleship";
    }
    public void touchedAnimation() {
        super.touchedAnimation();
        System.out.println("BB dayo");
    }
    public void boughtAnimation() {
        setImg(Constant.BOUGHT_ACTION);
    }
}

class Carrier extends Ship{
    Carrier(String autonym, int price) {
        super(autonym, price);
    }

    public String getType() {
        return "Carrier";
    }
    public void touchedAnimation() {
        super.touchedAnimation();
        System.out.println("CV dayo");
    }
    public void boughtAnimation() {
        setImg(Constant.BOUGHT_ACTION);
    }
}

class ShipList extends ArrayList<Ship>{
    ShipList() {
        super();
        this.currentIndex = 0;
    }
    ShipList(Ship...ships) {
        super();
        for(Ship ship:ships) {
            this.add(ship);
        }
        this.currentIndex = 0;
    }

    //Getter
    public Ship getCurrentShip() {
        if(this.isEmpty()) {
            return null;
        }
        return get(this.currentIndex);
    }
    public Ship getPrevShip() {
        if(!this.hasPrev()) {
            return null;
        }
        return get(this.currentIndex-1);
    }
    public Ship getNextShip() {
        if(!this.hasNext()) {
            return null;
        }
        return get(this.currentIndex+1);
    }
    public int getCurrentIndex() {
        return currentIndex;
    }

    public boolean hasNext() {
        return (this.currentIndex + 1 < this.size());
    }
    public boolean hasPrev() {
        return (this.currentIndex - 1 >= 0);
    }

    public void moveForward() {
        this.currentIndex--;
    }
    public void moveBackward() {
        this.currentIndex++;
    }

    public void removeCurrentShip() { //further edit
        new Thread() {
            public void run() {
                getCurrentShip().boughtAnimation();
                try {
                    Thread.sleep(2000);
                }catch (Exception e) {}
                getCurrentShip().setImg("normal");
                if(hasPrev()) {
                    moveForward();
                    remove(currentIndex + 1);
                    getCurrentShip().setX(Constant.DEFAULT_SHIP_X);
                }else if(hasNext()) {
                    remove(currentIndex);
                    getCurrentShip().setX(Constant.DEFAULT_SHIP_X);
                }else {
                    remove(currentIndex);
                }
            }
        }.start();
    }

    public List<String> searchKey(String key) {
        List<String> infoList = new ArrayList<String>();
        for(int i = 0; i < this.size(); i++) {
            if(
                this.get(i).getType().contains(key) ||
                this.get(i).getAutonym().contains(key) ||
                this.get(i).getNickname().contains(key) ||
                String.valueOf(this.get(i).getPrice()).contains(key)
            ) {
                infoList.add(this.get(i).toString() + ",Index:" + i + Constant.LINE_BREAK);
            }
        }
        return infoList;
    }

    private int currentIndex;
}