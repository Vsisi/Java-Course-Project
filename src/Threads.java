import java.awt.*;
import javax.swing.*;

class RefreshThread extends Thread{ //Refresh paint panel
    RefreshThread(PaintPanel pp) {
        this.pp = pp;
    }
    public void run() {
        while(true) {
            try {
                Thread.sleep(Constant.REFRESH_FREQUENCY);
            } catch (Exception e) {}
            pp.repaint();
        }
    }
    PaintPanel pp;
}

class AutoSaveConfThread extends Thread{
    AutoSaveConfThread(ShipList ships, User user) {
        this.ships = ships;
        this.user = user;
    }
    public void run() {
        while(true) {
            try{
                Thread.sleep(Constant.AUTOSAVE_FREQUENCY);
                FileOperation.saveCharacterConfig(ships);
                FileOperation.saveUserConfig(user);
            }catch (Exception e) {
                FileOperation.savingError();
            }
        }
    }
    ShipList ships;
    User user;
}

class SwitchControlThread extends Thread{ //switch button & animation monitor
    SwitchControlThread(ShipList shipList, JButton left, JButton right) {
        this.shipList = shipList;
        this.left = left;
        this.right = right;
    }

    public void run() {
        while(true) {
            try{
                Thread.sleep(Constant.REFRESH_FREQUENCY);
            } catch(Exception e) {}

            switchingAnimation();
            buttonLockCheck();
        }
    }

    private void buttonLockCheck() {
        if(shipList.hasNext() == true) {
            right.setEnabled(true);
            right.setVisible(true);
        }else {
            right.setEnabled(false);
            right.setVisible(false);
        }
        if(shipList.hasPrev() == true) {
            left.setEnabled(true);
            left.setVisible(true);
        }else {
            left.setEnabled(false);
            left.setVisible(false);
        }
    }

    private void switchingAnimation() {
        if(Sign.switchingLeft) {
            Ship current = shipList.getCurrentShip();
            current.setX(current.getX() + Constant.SWITCH_SPEED);
        }
        if(Sign.switchingRight) {
            Ship current = shipList.getCurrentShip();
            current.setX(current.getX() - Constant.SWITCH_SPEED);
        }
        if(Sign.switchingLeft || Sign.switchingRight) {
            switchingEndsMonitor();
        }
    }

    private void switchingEndsMonitor() {
        if(shipList.getCurrentShip().getX() >= Constant.CONTENTPANE_WIDTH) {
            this.switchingLeftAnimationEnd();
        }
        if(shipList.getCurrentShip().getX() <= -shipList.getCurrentShip().getWidth()) {
            this.switchingRightAnimationEnd();
        }
    }

    private void switchingLeftAnimationEnd() {
        shipList.getPrevShip().setImg("normal");
        shipList.getCurrentShip().setImg("normal");
        if(shipList.getNextShip() != null) {
            shipList.getNextShip().setImg("normal");
        }
        shipList.moveForward();
        shipList.getCurrentShip().setX(Constant.DEFAULT_SHIP_X); //correct position (in most cases, pos can't be divided by spd)
        Sign.switchingLeft = false;
    }
    private void switchingRightAnimationEnd() {
        shipList.getNextShip().setImg("normal");
        shipList.getCurrentShip().setImg("normal");
        if(shipList.getPrevShip() != null) {
            shipList.getPrevShip().setImg("normal");
        }
        shipList.moveBackward();
        shipList.getCurrentShip().setX(Constant.DEFAULT_SHIP_X); //correct position (in most cases, pos can't be divided by spd)
        Sign.switchingRight = false;
    }
    public void switchingLeftAnimationBegin() {//switch left, but move right
        Sign.switchingRight = false;
        Ship current = shipList.getCurrentShip(), prev= shipList.getPrevShip(), next= shipList.getNextShip();
        if(current != null) {
            current.setImg("move");
        }
        if(prev != null) {
            prev.setImg("move");
        }
        if(next != null) {
            next.setImg("move");
        }
        Sign.switchingLeft = true;
    }
    public void switchingRightAnimationBegin() {//switch right, but move left
        Sign.switchingLeft = false;
        Ship current = shipList.getCurrentShip(), prev= shipList.getPrevShip(), next= shipList.getNextShip();
        if(current != null) {
            current.setImg("move_left");
        }
        if(prev != null) {
            prev.setImg("move_left");
        }
        if(next != null) {
            next.setImg("move_left");
        }
        Sign.switchingRight = true;
    }
    private ShipList shipList;
    private JButton left, right;
}

class HideBuyButtonThread extends Thread {
    HideBuyButtonThread(ShipList ships, MyButton buyButton) {
        this.ships = ships;
        this.buyButton = buyButton;
    }
    public void run() {
        while(true) {
            try{
                Thread.sleep(Constant.REFRESH_FREQUENCY);
            }catch (Exception e) {}
            if(ships.getCurrentShip() == null || Sign.switchingLeft || Sign.switchingRight) {
                buyButton.setVisible(false);
                buyButton.setEnabled(false);
            }else {
                buyButton.setVisible(true);
                buyButton.setEnabled(true);
            }
        }
    }
    private ShipList ships;
    private MyButton buyButton;
}