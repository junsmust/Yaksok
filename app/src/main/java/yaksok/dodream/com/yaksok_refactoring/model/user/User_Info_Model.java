package yaksok.dodream.com.yaksok_refactoring.model.user;

public class User_Info_Model {

    private String id,pw,userType;
    private String phoneNumber,nickname, profileImagePath,email,birthday,ageRange,family_id ,fcmToken;

    /*	“id” : “…” NOT NULL
  “phoneNumber” : ”…“ NOT NULL
  “pw” : ”…“ NOT NULL
  “nickname” : ”…“ NOT NULL
  “profileImagePath” : “…”
  “email” : “…”
   “birthday” : “…”
  “ageRange” : “…”
  “userType”:”…”	  NOT NULL   char형
*/

    public User_Info_Model() {
    }

    public User_Info_Model(String id, String pw, String userType) {
        this.id = id;
        this.pw = pw;
        this.userType = userType;
    }

    public User_Info_Model(String id, String userType) {
        this.id = id;
        this.userType = userType;
    }


    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }


    public String getFamily_id() {
        return family_id;
    }

    public void setFamily_id(String family_id) {
        this.family_id = family_id;
    }





    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public String getNickname() {
//        return nickname;
//    }
//
//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }
//
//    public String getProfileImagePath() {
//        return profileImagePath;
//    }
//
//    public void setProfileImagePath(String profileImagePath) {
//        this.profileImagePath = profileImagePath;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getBirthday() {
//        return birthday;
//    }
//
//    public void setBirthday(String birthday) {
//        this.birthday = birthday;
//    }
//
//    public String getAgeRange() {
//        return ageRange;
//    }
//
//    public void setAgeRange(String ageRange) {
//        this.ageRange = ageRange;
//    }
//
//    public String getFamily_id() {
//        return family_id;
//    }
//
//    public void setFamily_id(String family_id) {
//        this.family_id = family_id;
//    }
//
//    public String getFcmToken() {
//        return fcmToken;
//    }
//
//    public void setFcmToken(String fcmToken) {
//        this.fcmToken = fcmToken;
//    }
}
