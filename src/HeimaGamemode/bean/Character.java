package HeimaGamemode.bean;

public class Character {
    private String name;
    private int HP;
    private int maxHP;
    private int attack;
    private int defense;

    public Character() {
    }

    public Character(String name, int HP, int attack, int defense) {
        this.name = name;
        this.HP = HP;
        this.maxHP = HP;
        this.attack = attack;
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public boolean isAlive() {
        return HP > 0;
    }

    public void heal(int amount) {
        if (amount <= 0) {
            return;
        }
        HP += amount;
        if (HP > maxHP) {
            HP = maxHP;
        }
    }

    public void takeDamage(int damage) {
        if (damage <= 0) {
            return;
        }
        HP -= damage;
        if (HP < 0) {
            HP = 0;
        }
    }

    public String show() {
        return name + "[当前生命：" + HP + "/" + maxHP
                + "，攻击力：" + attack
                + "，防御力：" + defense + "]";
    }
}
