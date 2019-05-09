package yaksok.dodream.com.yaksok_refactoring.Adapter.family;

public class FamilyItem {
    public String name,id;
    public int user_img,gotoright;


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
