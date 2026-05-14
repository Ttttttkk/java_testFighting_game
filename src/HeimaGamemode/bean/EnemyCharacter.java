package HeimaGamemode.bean;

public class EnemyCharacter extends Character{
    public boolean defending;
    public String skill;

    public EnemyCharacter(String name, int HP, int attack, int defense, String skill) {
        super(name, HP, attack, defense);
        this.skill = skill;
    }

    public EnemyCharacter() {
    }

    @Override
    public void takeDamage(int damage) {
        if(defending){
            damage = damage / 2 > 1 ? damage / 2 : 1;
        }
        defending=false;
        //父类方法扣血
        super.takeDamage(damage);

    }
}
