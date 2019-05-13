package yaksok.dodream.com.yaksok_refactoring.vo;

import com.google.gson.annotations.SerializedName;

//BODY{
//	“id” : “…” NOT NULL
//	“phoneNumber” : ”…“ NOT NULL
//	“nickname” : ”…“ NOT NULL
//	“profileImagePath” : “…”
//	“email” : “…”
// 	“birthday” : “…”
//	“ageRange” : “…”
//	“userType”:”…”	  NOT NULL   char형
//}
public class MessageResultBodyVO {
    @SerializedName("status") String status;
    @SerializedName("regiDate") String regiDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegiDate() {
        return regiDate;
    }

    public void setRegiDate(String regiDate) {
        this.regiDate = regiDate;
    }
}
    //HTTP GET   users/{item} ? itemTyp
//ex) /01027250856 ? itemType=phoneNumber
//
//Host:  http://54.180.81.180:8080/users/010?itemType=phoneNumber
//
//request path variable  -  item
//request query paramter - itemType
//
//response
//BODY{
//	“status” : “code”
//	“result” : “UserVO” List
//
//}
//
//code
//200 : OK
//204: 계정 존재하지않음(null반환)
//400: 잘못된 요청(itemType)
//500 : Server Error


