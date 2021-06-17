import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class UIFrame extends JFrame{
    UIFrame(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(Constant.WINDOW_X, Constant.WINDOW_Y, Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT);
        setResizable(false);
        setVisible(true);
        Constant.CONTENTPANE_WIDTH = getContentPane().getWidth();
        Constant.CONTENTPANE_HEIGHT = getContentPane().getHeight();
        Constant.updateDefaultElementPos();
    }
}

class PaintPanel extends JPanel{
    PaintPanel(ShipList ships, User user, Element...otherElements) {
        this.ships = ships;
        this.user = user;
        this.otherElements = otherElements;
        this.setLayout(null);
    }

    public void addComponents(JComponent...components) {
        for(JComponent component:components) {
            super.add(component);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw bg img
        for(Element element:this.otherElements) {
            g.drawImage(element.getImg(), element.getX(), element.getY(), element.getWidth(), element.getHeight(), null);
        }

        //Money Info
        g.setFont(new Font("楷体", Font.PLAIN, 16));
        g.drawString(String.valueOf(user.getMoney()), Constant.MONEY_BG_X + Constant.MONEY_BG_WIDTH*2/5, Constant.MONEY_BG_Y + Constant.MONEY_BG_HEIGHT/2+7);

        //Current page/Total page
        String pageIndex = (ships.size() == 0 ? 0:ships.getCurrentIndex()+1) + "/" + ships.size();
        g.setFont(new Font("Series", Font.BOLD, 30));
        g.drawString(pageIndex, Constant.DEFAULT_SHIP_X + 80, Constant.DEFAULT_SHIP_Y - 50);

        //Draw ships' img
        Ship current = ships.getCurrentShip();
        Ship prev = ships.getPrevShip();
        Ship next = ships.getNextShip();
        if(current != null) {
            g.drawImage(current.getImg(), current.getX(), current.getY(), current.getWidth(), current.getHeight(), null);
        }
        if(prev != null) {
            prev.setX(current.getX() - (Constant.DEFAULT_SHIP_WIDTH + Constant.CONTENTPANE_WIDTH)/2); //position synchronizing
            g.drawImage(prev.getImg(), prev.getX(), prev.getY(), prev.getWidth(), prev.getHeight(), null);
        }
        if(next != null) {
            next.setX(current.getX() + (Constant.DEFAULT_SHIP_WIDTH + Constant.CONTENTPANE_WIDTH)/2); //position synchronizing
            g.drawImage(next.getImg(), next.getX(), next.getY(), next.getWidth(), next.getHeight(), null);
        }

    }

    public ShipList getShips() {
        return ships;
    }

    private ShipList ships; //Characters
    private Element[] otherElements; //Fixed Number Elements
    private User user;
}

class ModifyShipInfoDialog extends JDialog{
    public ModifyShipInfoDialog(String title, Ship currentShip) {
        super((JFrame) null, title);
        this.setBounds(
                Constant.WINDOW_X + Constant.WINDOW_WIDTH/3,
                Constant.WINDOW_Y + Constant.WINDOW_HEIGHT/2,
                300,
                110
        );
        JPanel jp = new JPanel();
        Box totBox = Box.createVerticalBox();
        Box nicknameBox = Box.createHorizontalBox();
        Box priceBox = Box.createHorizontalBox();
        Box buttonBox = Box.createHorizontalBox();
        totBox.add(nicknameBox);
        totBox.add(priceBox);
        totBox.add(buttonBox);
        jp.add(totBox);
        this.add(jp);

        JLabel nicknameLabel = new JLabel("请设置新昵称");
        JLabel priceLabel = new JLabel("请设置新价格");
        JTextField nicknameJTF = new JTextField(15);
        nicknameJTF.setText(currentShip.getNickname());
        JTextField priceJTF = new JTextField(15);
        priceJTF.setText(String.valueOf(currentShip.getPrice()));
        JButton confirm = new JButton("确定");
        JButton cancel = new JButton("取消");

        nicknameBox.add(Box.createHorizontalGlue());
        nicknameBox.add(nicknameLabel);
        nicknameBox.add(Box.createHorizontalStrut(20));
        nicknameBox.add(nicknameJTF);
        nicknameBox.add(Box.createHorizontalGlue());

        priceBox.add(Box.createHorizontalGlue());
        priceBox.add(priceLabel);
        priceBox.add(Box.createHorizontalStrut(20));
        priceBox.add(priceJTF);
        priceBox.add(Box.createHorizontalGlue());

        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.add(confirm);
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.add(cancel);
        buttonBox.add(Box.createHorizontalGlue());

        this.setVisible(true);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == confirm) {
                    try{
                        if(Integer.valueOf(priceJTF.getText()) < 0 || nicknameJTF.getText() == null) {
                            throw new Exception();
                        }
                    }catch (Exception exception) {
                        JOptionPane.showConfirmDialog(null, "输入不能为空，且价格必须为非负整数", "格式错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    currentShip.setNickname(nicknameJTF.getText());
                    currentShip.setPrice(Integer.valueOf(priceJTF.getText()));
                }
                setVisible(false);
            }
        };
        confirm.addActionListener(actionListener);
        cancel.addActionListener(actionListener);
    }
}
class MyButton extends JButton{
    MyButton(String imgPath) {
        super();
        this.imgPath = imgPath;
    }
    public void setScaledImage() {
        this.setIcon(new ImageIcon(new ImageIcon(imgPath).getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH)));
    }
    String imgPath;
}