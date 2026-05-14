package HeimaGamemode.bean;

public class Character {
    public String name;
    public int HP;
    public int maxHP;
    public int attack;
    public int defense;

    public Character() {
    }

    public Character(String name, int HP, int attack, int defense) {
        this.name = name;
        this.HP = HP;
        this.maxHP = HP;
        this.attack = attack;
        this.defense = defense;
    }

    //判断生命
    public boolean isAlive(){
        return HP>0;
    }
    //回复
    public void heal(int amount){
        HP+=amount;
        if(HP>maxHP){
           HP=maxHP;
        }
    }
    //受到伤害
    //作用：受伤后还有多少血
    //形参：受到的伤害
    public void takeDamage(int damage){
        HP-=damage;
        if(HP<0){
           HP=0;
        }

    }
    //展示人物属性
    public String show(){
        return name+"[当前生命："+HP+",攻击力："+attack+",防御力："+defense+"]";
    }




}
