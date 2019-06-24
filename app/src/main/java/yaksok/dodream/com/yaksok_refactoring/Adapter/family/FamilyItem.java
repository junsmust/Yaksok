package yaksok.dodream.com.yaksok_refactoring.Adapter.family;

public class FamilyItem {
    public String name,first_name,user_pn;
    public int user_img,gotoright;

    public FamilyItem(String name, String first_name, String user_pn) {
        this.name = name;
        this.first_name = first_name;
        this.user_pn = user_pn;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getUser_pn() {
        return user_pn;
    }

    public void setUser_pn(String user_pn) {
        this.user_pn = user_pn;
    }

    public FamilyItem(String name) {
        this.name = name;
    }

    public FamilyItem() {
    }

    /*public int getGotoright() {
        return gotoright;
    }

    public void setGotoright(int gotoright) {
        this.gotoright = gotoright;
    }

    public int getUser_img() {
        return user_img;
    }

    public void setUser_img(int user_img) {
        this.user_img = user_img;
    }
*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
