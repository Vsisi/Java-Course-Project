import java.awt.*;
import java.lang.constant.Constable;
import javax.swing.*;

class GUI{
    GUI() {
        UIFrame uiFrame = new UIFrame("明石的小店");
        Element moneyBackground = new Element(Utility.getFullPath("moneyBG.png"), Constant.MONEY_BG_X, Constant.MONEY_BG_Y, Constant.MONEY_BG_WIDTH, Constant.MONEY_BG_HEIGHT);
        Element paintPanelBackground = new Element(Utility.getFullPath("background.png"), 0, 0, Constant.CONTENTPANE_WIDTH, Constant.CONTENTPANE_HEIGHT);
        ShipList shipList = FileOperation.loadCharactersInfo();
        User user = FileOperation.loadUserInfo();

        MyButton left = new MyButton(Utility.getFullPath("leftButton.png"));
        MyButton right = new MyButton(Utility.getFullPath("rightButton.png"));
        MyButton construct = new MyButton(Utility.getFullPath("constructButton.png"));
        MyButton buy = new MyButton(Utility.getFullPath("buyButton.png"));
        MyButton query = new MyButton(Utility.getFullPath("queryButton.png"));

        PaintPanel paintPanel = new PaintPanel(shipList, user, paintPanelBackground, moneyBackground);

        paintPanel.addComponents(left, right, construct, buy, query);

        uiFrame.add(paintPanel);

        RefreshThread refreshThread = new RefreshThread(paintPanel);
        SwitchControlThread switchControlThread = new SwitchControlThread(shipList, left, right);
        AutoSaveConfThread autoSaveConfThread = new AutoSaveConfThread(shipList, user);
        HideBuyButtonThread hideBuyButtonThread = new HideBuyButtonThread(shipList, buy);

        Logic.switchButtonLogic(left, right, switchControlThread);
        Logic.constructButtonLogic(construct, shipList);
        Logic.buyButtonLogic(buy, user, shipList);
        Logic.queryLogic(query, shipList);
        Logic.touchLogic(paintPanel);

        new Thread(refreshThread).start();
        new Thread(switchControlThread).start();
        new Thread(autoSaveConfThread).start();
        new Thread(hideBuyButtonThread).start();
        uiFrame.setVisible(true);
    }
}