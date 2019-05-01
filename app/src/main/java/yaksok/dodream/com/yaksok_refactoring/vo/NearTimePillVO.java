package yaksok.dodream.com.yaksok_refactoring.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NearTimePillVO {

    @SerializedName("status") String status;
    @SerializedName("result") Result result;
    NearTimePillVO(){}

    public class Result {
        @SerializedName("time")@Expose
        public String time;
        @SerializedName("name")@Expose public String name;
        @SerializedName("myMedicineNo")@Expose public int myMedicineNo;

        public String getTime() { return time; }

        public void setTime(String time) { this.time = time; }

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public int getMyMedicineNo() { return myMedicineNo; }

        public void setMyMedicineNo(int myMedicineNo) { this.myMedicineNo = myMedicineNo; }
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}

