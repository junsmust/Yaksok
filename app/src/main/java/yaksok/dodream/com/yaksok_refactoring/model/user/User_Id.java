package yaksok.dodream.com.yaksok_refactoring.model.user;

public class User_Id {
    public static String user_Id;
    public static String nickname;
    public static String e_mail;
    public static String phone_No;
    public static String type;
    public static String pass;

    public static String getUser_Id() {
        return user_Id;
    }

    public static void setUser_Id(String user_Id) {
        User_Id.user_Id = user_Id;
    }

    public static String getNickname() {
        return nickname;
    }

    public static void setNickname(String nickname) {
        User_Id.nickname = nickname;
    }

    public static String getE_mail() {
        return e_mail;
    }

    public static void setE_mail(String e_mail) {
        User_Id.e_mail = e_mail;
    }

    public static String getPhone_No() {
        return phone_No;
    }

    public static void setPhone_No(String phone_No) {
        User_Id.phone_No = phone_No;
    }

    public static String getType() { return type; }

    public static void setType(String type) { User_Id.type = type; }

    public static String getPass() { return pass; }

    public static void setPass(String pass) { User_Id.pass = pass; }
}
