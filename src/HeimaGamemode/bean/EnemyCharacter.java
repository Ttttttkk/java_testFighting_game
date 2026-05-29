package HeimaGamemode.bean;

public class EnemyCharacter extends Character {
    private boolean defending;
    private String skill;

    public EnemyCharacter() {
    }

    public EnemyCharacter(String name, int HP, int attack, int defense, String skill) {
        super(name, HP, attack, defense);
        this.skill = skill;
    }

    public EnemyCharacter(String name, int HP, int maxHP, int attack, int defense, boolean defending, String skill) {
        super(name, HP, attack, defense);
        setMaxHP(maxHP);
        this.defending = defending;
        this.skill = skill;
    }

    public boolean isDefending() {
        return defending;
    }

    public void setDefending(boolean defending) {
        this.defending = defending;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @Override
    public void takeDamage(int damage) {
        if (defending) {
            damage = Math.max(damage / 2, 1);
        }
        defending = false;
        super.takeDamage(damage);
    }
}
