import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;


class Logic{
    public static void switchButtonLogic(MyButton left, MyButton right, SwitchControlThread switchControlThread) {
        left.setBounds(0,
                (int)Constant.CONTENTPANE_HEIGHT/2-(int)Constant.SWITCH_BUTTON_HEIGHT/2,
                Constant.SWITCH_BUTTON_WIDTH,
                Constant.SWITCH_BUTTON_HEIGHT);
        left.setContentAreaFilled(false);
        left.setBorder(null);
        left.setScaledImage();
        right.setBounds(Constant.CONTENTPANE_WIDTH-Constant.SWITCH_BUTTON_WIDTH,
                (int)Constant.CONTENTPANE_HEIGHT/2-(int)Constant.SWITCH_BUTTON_HEIGHT/2,
                Constant.SWITCH_BUTTON_WIDTH,
                Constant.SWITCH_BUTTON_HEIGHT);
        right.setContentAreaFilled(false);
        right.setBorder(null);
        right.setScaledImage();

        left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchControlThread.switchingLeftAnimationBegin();
            }
        });
        right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchControlThread.switchingRightAnimationBegin();
            }
        });
    }

    public static void constructButtonLogic(MyButton construct, ShipList ships) {
        construct.setBounds(
                Constant.CONTENTPANE_WIDTH-60,
                Constant.CONSTRUCT_BUTTON_Y,
                Constant.CONSTRUCT_BUTTON_WIDTH,
                Constant.CONSTRUCT_BUTTON_HEIGHT);
        construct.setScaledImage();
        construct.setContentAreaFilled(false);
        construct.setBorder(null);
        construct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = getShipType();
                if(type == null) {return;}
                ships.add(Utility.getShip(type));
            }
            public String getShipType() {
                JDialog dialog = new JDialog((Dialog) null, "选择建造舰种", true);
                JPanel jp = new JPanel();
                Box totBox = Box.createVerticalBox();
                Box radioButtonBox = Box.createHorizontalBox();
                Box confirmButtonBox = Box.createHorizontalBox();
                ButtonGroup buttonGroup = new ButtonGroup();
                JRadioButton cv = new JRadioButton("航空母舰");
                JRadioButton dd = new JRadioButton("驱逐舰");
                JRadioButton bb = new JRadioButton("战列舰");
                JRadioButton cl = new JRadioButton("巡洋舰");
                JButton confirm = new JButton("确定");
                buttonGroup.add(dd);
                buttonGroup.add(cl);
                buttonGroup.add(bb);
                buttonGroup.add(cv);
                radioButtonBox.add(Box.createHorizontalGlue());
                radioButtonBox.add(dd);
                radioButtonBox.add(cl);
                radioButtonBox.add(bb);
                radioButtonBox.add(cv);
                radioButtonBox.add(Box.createHorizontalGlue());

                confirmButtonBox.add(Box.createHorizontalGlue());
                confirmButtonBox.add(confirm);
                confirmButtonBox.add(Box.createHorizontalGlue());

                totBox.add(radioButtonBox);
                totBox.add(confirmButtonBox);
                jp.add(totBox);
                dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                dialog.add(jp);
                dialog.setBounds(
                        Constant.WINDOW_X + Constant.WINDOW_WIDTH/3,
                        Constant.WINDOW_Y + Constant.WINDOW_HEIGHT/10,
                        300,
                        100
                );

                confirm.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.setVisible(false);
                    }
                });

                dialog.setVisible(true);

                if(dd.isSelected()) {
                    return "dd";
                }else if(cl.isSelected()) {
                    return "cl";
                }else if(bb.isSelected()) {
                    return "bb";
                }else if(cv.isSelected()){
                    return "cv";
                }

                return null;
            }
        });
    }

    public static void buyButtonLogic(MyButton buy, User user, ShipList ships) {
        buy.setBounds(Constant.DEFAULT_SHIP_X,
                Constant.DEFAULT_SHIP_Y + Constant.DEFAULT_SHIP_HEIGHT + 75,
                Constant.BUY_BUTTON_WIDTH,
                Constant.BUY_BUTTON_HEIGHT);
        buy.setContentAreaFilled(false);
        buy.setBorder(null);
        buy.setScaledImage();

        buy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buy.setEnabled(false);
                if(JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(null,
                        "<html>确定购买吗?<br>" +
                                "舰种:" + ships.getCurrentShip().getType() + "<br>" +
                                "名称:" + ships.getCurrentShip().getAutonym() + "<br>" +
                                "昵称:" + ships.getCurrentShip().getNickname() + "<br>" +
                                "价格:" + ships.getCurrentShip().getPrice() +
                                "<html>",
                        "确认?", JOptionPane.YES_NO_OPTION)) {
                    return;

                }
                if(user.buy(ships.getCurrentShip())) {
                    ships.removeCurrentShip();
                }else {
                    JOptionPane.showMessageDialog(null, "钱不够QwQ");
                }
                buy.setEnabled(true);
            }
        });
    }

    public static void touchLogic(PaintPanel paintPanel) {
        paintPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(paintPanel.getShips().getCurrentShip() == null) {
                    return;
                }
                Rectangle r = new Rectangle(Constant.DEFAULT_SHIP_X, Constant.DEFAULT_SHIP_Y, Constant.DEFAULT_SHIP_WIDTH, Constant.DEFAULT_SHIP_HEIGHT);
                if(r.contains(new Point(e.getX(), e.getY()))) {
                    if(Sign.switchingLeft || Sign.switchingRight) {
                        return;
                    }
                    if(e.getButton() == MouseEvent.BUTTON1) { //left click
                        paintPanel.getShips().getCurrentShip().touchedAnimation();
                    }else if(e.getButton() == MouseEvent.BUTTON3){
                        new ModifyShipInfoDialog("修改信息", paintPanel.getShips().getCurrentShip());
                    }
                }
            }
        });
    }

    public static void queryLogic(MyButton query, ShipList ships) {
        query.setBounds(
                Constant.CONTENTPANE_WIDTH-60,
                Constant.QUERY_BUTTON_Y,
                Constant.QUERY_BUTTON_WIDTH,
                Constant.QUERY_BUTTON_HEIGHT
        );
        query.setScaledImage();
        query.setContentAreaFilled(false);
        query.setBorder(null);
        query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = JOptionPane.showInputDialog(null,"请输入查找键值", "查找", JOptionPane.OK_CANCEL_OPTION);
                if(key == null) {
                    return;
                }
                List<String> searchResult = ships.searchKey(key);
                String info = "";
                for(int i = 0; i < searchResult.size(); i++) {
                    info += searchResult.get(i);
                }
                JOptionPane.showMessageDialog(null, "查找到的信息为:" + Constant.LINE_BREAK + info, "查找结果",JOptionPane.PLAIN_MESSAGE);
            }
        });
    }
}