package HeimaGamemode.bean;

import java.util.Random;

public class User {
    private String id;
    private String username;
    private String password;
    private boolean status;

    public User() {
        id=createID();
        //修改status值
        status=true;
    }

    public User( String username, String password) {
        id=createID();
        this.username = username;
        this.password = password;
        status=true;
    }
    //随机生成id 格式heima+5位随机数
    public String createID(){
      StringBuilder sb=new StringBuilder("heima");
        Random r=new Random();
        for (int i = 0; i < 5; i++) {
            int num = r.nextInt(10);
            sb.append(num);
        }
        return sb.toString();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
