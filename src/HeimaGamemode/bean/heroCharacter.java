package HeimaGamemode.bean;

import java.util.ArrayList;

public class heroCharacter extends Character {
   public ArrayList<String >skilllist=new ArrayList<>();

    public heroCharacter() {
        skilllist = new ArrayList<>();
    }

    public heroCharacter(String name, int HP, int attack, int defense) {
        super(name, HP, attack, defense);
        skilllist = new ArrayList<>();
    }
    public String showSkill(){
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < skilllist.size(); i++) {
          sb.append(skilllist.get(i));
          if(i!= skilllist.size()-1){
              sb.append(", ");
          }
        }
        return sb.toString();
    }
}
