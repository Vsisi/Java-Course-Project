public class User {
    User() {
        this.money = Constant.DEFAULT_MONEY;
    }

    public boolean buy(Ship ship) {
        if(money >= ship.getPrice()) {
            money -= ship.getPrice();
            return true;
        }
        return false;
    }

    public int getMoney() {
        return this.money;
    }
    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "money:" + money;
    }

    private int money;
}
