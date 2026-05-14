package HeimaGamemode.ui;

import HeimaGamemode.bean.EnemyCharacter;
import HeimaGamemode.bean.heroCharacter;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FightingGame {
    //启动游戏
    public void gameStart(String username){
        System.out.println("┌───────────────────────────────┐");
        System.out.println("\uD83C\uDFAE"+username+" 欢迎来到文字格斗游戏 \uD83C\uDFAE");
        System.out.println("└───────────────────────────────┘");

        //创造玩家
        heroCharacter player=creatCharacter(username);
        System.out.println("角色创建成功");
        System.out.println(" 初始属性为："+player.show());
        System.out.println(" 拥有的技能为"+player.showSkill());

        ArrayList<EnemyCharacter> enemylist =new ArrayList<>();
        enemylist.add(new EnemyCharacter("初级战士",80,15,10,"重击"));
        enemylist.add(new EnemyCharacter("敏捷刺客",60,20,5,"闪现一击"));
        enemylist.add(new EnemyCharacter("重装坦克",120,10,20,"防御高坚果"));
        enemylist.add(new EnemyCharacter("神秘法师",70,25,3,"毒药"));

        int count=1;
        int wins=0;
        while(player.isAlive()){
            if(wins!=0){
                //敌人属性点
                for (int i = 0; i < enemylist.size(); i++) {
                    EnemyCharacter c=enemylist.get(i);
                    c.maxHP=c.maxHP+10;
                    c.HP=c.maxHP;
                    c.attack=c.attack+3;
                    c.defense=c.defense+2;
                    c.defending=false;
                }
            }
            //随机选择敌人
            Random r=new Random();
            int index = r.nextInt(enemylist.size());
            EnemyCharacter enemy = enemylist.get(index);
            System.out.println(enemy.show());

            System.out.println("=======================");
            System.out.println("\uFE0F第"+count+"场游戏开始！对手"+enemy.name);
            int round=1;
            //内循环 一个敌人
            while(player.isAlive()){
                System.out.println("-----------------------");
                System.out.println("\uFE0F第"+round+"回合开始！");
                System.out.println(getHealthbar(player.name, player.HP, player.maxHP));
                System.out.println(getHealthbar(enemy.name, enemy.HP, enemy.maxHP));
                //玩家回合
                playerTurn(player,enemy);

                //判断敌人是否被击败
                if(!enemy.isAlive()){
                    System.out.println("恭喜你，你击败了"+enemy.name+"!");
                    wins++;
                    break;
                }

                //敌方回合
                enemyTurn(enemy,player);

                //判断玩家是否被击败
                if(!player.isAlive()){
                    System.out.println("☠\uFE0F你被"+enemy.name+"击败了");
                    break;
                }
                //没被击败 下一回合
                round++;
            }

            //回复血量
            if(player.isAlive()){
                int healHP = r.nextInt(20, 41);
                player.HP+= healHP;
                System.out.println("战斗结束，你恢复了"+healHP+"点血量");
                System.out.println("当前胜场："+wins);
                System.out.println("==================================");
            }
            //成长系统
            if(player.isAlive()&&wins>0&&wins%3==0){
                System.out.println("恭喜！你获得属性提升");
                player.maxHP+=30;
                player.attack+=5;
                player.defense+=3;
                System.out.println("最大生命值+30，攻击力+5，防御力+3");
                System.out.println("当前属性："+player.show());
            }
            //是否继续
            if(player.isAlive()){
                System.out.println("是否进行下一场战斗？y/n");
                Scanner sc=new Scanner(System.in);
                String ans = sc.next();
                if("y".equalsIgnoreCase(ans)){
                    count++;
                    continue;//循环继续
                } else if ("n".equalsIgnoreCase(ans)) {
                    break;
                }
                else {
                    System.out.println("没有这个选项，默认游戏继续");
                    count++;
                }

            }

        }
        //最终结算
        System.out.println("───────────────────────────────");
        System.out.println("游戏结束！");
        System.out.println("总胜场:"+wins);
        System.out.println("\uD83D\uDC4B感谢游玩文字版格斗游戏");
        System.exit(0);


    }

    public void enemyTurn(EnemyCharacter enemy, heroCharacter player) {
        System.out.println("====="+enemy.name+"的回合=====");
        String action="普通攻击";
        Random r=new Random();
        int num = r.nextInt(10);//0~4 5~9
        if(num>=5){
           action= enemy.skill;
        }
        switch (action){
            case "普通攻击":
                System.out.println("\uD83D\uDCA5敌人采取了普通攻击");
                int damage1= calculateDamage(enemy.attack, player.defense);
                System.out.println(enemy.name+"对你使用了普通攻击，造成了"+damage1+"点伤害\uD83D\uDC94");
                player.takeDamage(damage1);
                break;
            case "重击":
                System.out.println("\uD83D\uDCA5战士采取了重击");
                int damage2= calculateDamage((int) (enemy.attack*1.5), player.defense);
                System.out.println(enemy.name+"对你使用了重击，造成了"+damage2+"点伤害\uD83D\uDC94");
                player.takeDamage(damage2);
                break;
            case"闪现一击":
                System.out.println("⚡刺客使用了闪现一击");
                int damage3=0;
                for (int i = 0; i < 2; i++) {
                    int temp= calculateDamage((int) (enemy.attack*1.6), player.defense);
                   damage3+=temp;
                }
                System.out.println(enemy.name+"对你使用了闪现一击，造成了"+damage3+"点伤害\uD83D\uDC94");
                player.takeDamage(damage3);
                break;
            case"防御高坚果" :
                System.out.println("战士使用了防御高坚果");
                enemy.defending=true;
                break;
            case"毒药":
                System.out.println("\uD83E\uDE84神秘法师使用了毒药");
                int damage4= calculateDamage((int) (enemy.attack*1.8), player.defense);
                System.out.println(enemy.name+"对你使用了毒药，造成了"+damage4+"点伤害\uD83D\uDC94");
                player.takeDamage(damage4);
                break;
        }
    }

    public heroCharacter creatCharacter(String username){
        System.out.println("请创造你的角色：");
        System.out.println("您的角色名为："+username);
        int points=20;
        Scanner sc=new Scanner(System.in);
        System.out.println("请分配属性值(共20点)");
        System.out.println("生命值（每点加20生命值）");
        System.out.println("攻击力（每点加2点攻击力）");
        System.out.println("防御力（每点加1点防御力）");

        //要提示语句
        String[] attributes={"生命值","攻击力","防御力"};
        int[] values=new int[3];

        for (int i = 0; i < 3; i++) {
            System.out.println("分配点数到"+attributes[i]+"（剩余点数："+points+"）：");
            int input=sc.nextInt();
            if(input<0){
                System.out.println("无效输入，默认0点");
            }
            if(input>points){
                System.out.println("剩余属性点不足，剩余属性点全分配到"+attributes[i]);
                input=points;
            }
            points-=input;
            values[i]=input;
        }
        //属性点--->values
        heroCharacter player=new heroCharacter(
                username,100+values[0]*10,10+values[1]*2,values[2]*1
        );
        //添加技能
        player.skilllist.add("普通攻击");
        player.skilllist.add("强化普攻");
        player.skilllist.add("生命恢复");

        return player;

    }

    //█
    public String getHealthbar(String name,int hp,int maxhp){
        int barlenth=20;
        int filled=(int)((hp*1.0/maxhp)*barlenth);
        StringBuilder sb=new StringBuilder();
        sb.append(name).append(":[");
        for (int i = 0; i < barlenth; i++) {
            if(i<filled){
           sb.append("█");}
            else{
                sb.append(" ");
            }
        }
        sb.append("] ").append(hp).append("/").append(maxhp).append("HP");
        return sb.toString();
    }

    public void playerTurn(heroCharacter player,EnemyCharacter enemy){
        System.out.println("你的回合：");
        System.out.println("1:普通攻击");
        System.out.println("2:强化普功");
        System.out.println("3:生命恢复");
        System.out.println("请选择行动（1-3）：");
        Scanner sc=new Scanner(System.in);
        String choose=sc.next();
        switch (choose){
            default : System.out.println("没有这个选项，默认普通攻击");
            case"1":{
                int damage1 = calculateDamage(player.attack, enemy.defense);
                System.out.println("\uD83D\uDCA5你对"+enemy.name+"使用了普通攻击，造成了"+damage1+"点伤害");
                enemy.takeDamage(damage1);
                break;}
            case "2":{
                if(player.HP>10){
                    player.takeDamage(10);
                    int damage2 = calculateDamage((int) (player.attack * 1.8), enemy.defense);
                    System.out.println("消耗10HP,\uD83D\uDCA5你对"+enemy.name+"使用了强化普攻，造成了"+damage2+"点伤害");
                    enemy.takeDamage(damage2);
                }
                else{
                    System.out.println("体力不足，攻击失败");
                }
                break;}
            case "3":{
                Random r=new Random();
                int healHp = r.nextInt(21);
                player.heal(healHp);
                System.out.println("\uD83D\uDC93你使用了生命恢复，恢复了"+healHp+"点生命值");
                break;}
        }

    }
    //计算伤害
    public int calculateDamage(int attack,int defense){
       int damage= attack-defense;
        int result;
        result = damage > 1 ? damage : 1;
        return result;
    }


}
