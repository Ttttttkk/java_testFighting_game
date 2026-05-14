package HeimaGamemode.ui;

import HeimaGamemode.bean.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
//先做异常

public class Login {
    //这个方法展示的是登录注册的主页面。现在用控制台展示
    //ctrl+alt+t 包裹代码
    public void start(){
        System.out.println("现在开始注册登录游戏");

        ArrayList<User> list=new ArrayList<>();


        while (true) {
            System.out.println("┌───────────────────────────────┐");
            System.out.println("\uD83C\uDFAE 欢迎来到文字格斗游戏 \uD83C\uDFAE");
            System.out.println("└───────────────────────────────┘");
            System.out.println("请选择操作：1登录 2注册 3退出");


            Scanner sc=new Scanner(System.in);
            String choose=sc.next();

            switch(choose){
               case"1"-> login(list);
               case"2"-> register(list);//数据库 不会 集合
               case"3"-> {
                   System.out.println("用户选择了退出操作");
                  System.exit(0);
               }
               default-> System.out.println("输入有误");
            }
        }
    }
    //登录
    public void login(ArrayList<User> list){
        System.out.println("用户选择了登录操作");
        //判断用户是否存在 不存在 存在但锁定 存在验证码 验证密码
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = sc.next();
        if(!contains(list,username)){
            System.out.println("该用户不存在，请先注册再登录");
            //结束登录行为
            return;
        }
        int index = findIndex(list, username);
        User u = list.get(index);
        if(!u.isStatus()){
            System.out.println("当前用户被禁用，请联系客服");
            return;
        }

        String rightPassword = u.getPassword();

            System.out.println("请输入密码：");
            String password=sc.next();

            while (true) {
                String rightcode = getCode();
                System.out.println("正确的验证码为："+rightcode);

                System.out.println("请输入验证码：");
                String code = sc.next();

                if(rightcode.equalsIgnoreCase(code)) {
                    System.out.println("验证码输入正确");
                    break;
                }
                else{
                    System.out.println("验证码输入错误");
                }
            }
        for (int i = 1; i <= 3; i++) {
            //验证密码
            if(rightPassword.equals(password)){
                System.out.println("登录成功，游戏启动");
                FightingGame fg=new FightingGame();
                fg.gameStart(username);
            }
            else{
                if(i==3){
                    System.out.println("当前账户"+username+"已锁定，请联系客服");
                    u.setStatus(false);
                    return;
                }
                else{
                    System.out.println("登录失败，密码输入错误。你还有"+(3-i)+"次机会");
                }
            }


        }


    }
    //注册
    public void register(ArrayList<User> list){
        System.out.println("用户选择了注册操作");
        //键盘录入用户密码 封装到user 再放到集合
        //1.创造对象
        User u=new User();
        //2.录入账户 检验
        Scanner sc=new Scanner(System.in);
        while (true) {
            System.out.println("请输入用户名：");
            String username=sc.next();
            if(!ckecklen(username,3,16)){
                System.out.println("用户长度不符合要求，必须是3~16位之间");
                continue;
            }
            if(!ckeckUsername(username)){
                System.out.println("用户名只能字母数字组成，不能纯数字");
                continue;
            }
            if(contains(list,username)){
                System.out.println("当前的用户名已经存在");
                continue;
            }
            //用户名符合要求
            u.setUsername(username);
            break;
        }

        //3.密码
        while (true) {
            System.out.println("请输入密码：");
            String passward1=sc.next();
            System.out.println("请再输入一次：");
            String passward2=sc.next();

            if(!ckecklen(passward1,3,8)){
                System.out.println("密码长度不符合要求，必须是3~8位之间");
                continue;
            }
            if(!ckeckPassword(passward1)){
                System.out.println("密码只能由字母数字组合，不能有其他");
                continue;
            }
            if(!passward1.equals(passward2)){
                System.out.println("两次密码输入不一致，请重新输入");
                continue;
            }
            u.setPassword(passward1);
            break;
        }

        //4.集合
        list.add(u);

        //5.成功
        System.out.println("用户:"+u.getUsername()+"注册成功！");

    }

    public boolean ckecklen(String str,int minlen, int maxlen){
       /* if(str.length()>=minlen&&str.length()<=maxlen){
            return  true;
        }
        else{
            return false;
        }*/
        return str.length()>=minlen&&str.length()<=maxlen;
    }

    public boolean ckeckUsername(String userName){ //字母数字 不能纯数字
        int[] arr = getCount(userName);
        return arr[0]>0&&arr[1]>=0&&arr[2]==0;

    }

    //判断用户名在集合是否存在
    public boolean contains(ArrayList<User> list,String username){
        for (int i = 0; i < list.size(); i++) {
           User u=list.get(i);
           if(u.getUsername().equals(username)){
               return true;
           }
        }
        return false;

    }

    //在集合中找username所在索引
    public int findIndex(ArrayList<User>list,String username){
        for (int i = 0; i < list.size(); i++) {
            User u=list.get(i);
            if(u.getUsername().equals(username)){
                return i;
            }
        }
        return -1;
    }

    //统计字母 数字
    public int[] getCount(String userInfo){
        int charCount=0;
        int numCount=0;
        int otherCount=0;
        for (int i = 0; i < userInfo.length(); i++) {
            char c = userInfo.charAt(i);
            if(c>='a'&&c<='z'||c>='A'&&c<='Z'){//Character.isLetter(c)
                charCount++;
            }
            else if(c>='0'&&c<='9'){//Character.isDigit(c)
                numCount++;
            }
            else {
                otherCount++;
            }
        }
        return new int[]{charCount,numCount,otherCount};

    }

    public boolean ckeckPassword(String Password){
        int[] arr = getCount(Password);
        return arr[0]>0&&arr[1]>0&&arr[2]==0;

    }

    //获取验证码
    public static String getCode(){
        //验证码
        ArrayList<Character>list=new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char)('a'+i));
            list.add((char)('A'+i));
        }
        StringBuilder sb=new StringBuilder();
        Random r=new Random();
        for (int i = 0; i < 4; i++) {
            //随机获取集合中索引
            int index = r.nextInt(list.size());
            char c = list.get(index);
            sb.append(c);
        }
        sb.append(r.nextInt(10));
        //数字位置任意
        char[] arr = sb.toString().toCharArray();
        int i = r.nextInt(arr.length);
        char temp=arr[i];
        arr[i]=arr[arr.length-1];
        arr[arr.length-1]=temp;

        String code=new String(arr);
        return code;


    }



}
