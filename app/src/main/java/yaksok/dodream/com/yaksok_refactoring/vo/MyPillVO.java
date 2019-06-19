package yaksok.dodream.com.yaksok_refactoring.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyPillVO {
    @SerializedName("status") String status;
    @SerializedName("result")
    List<Result> result;
    MyPillVO(){}

    public class Result {
        @SerializedName("myMedicineNo")@Expose private int medicineNo;
        @SerializedName("name") @Expose private String name;
        @SerializedName("regiDate") @Expose private String regiDate;
        @SerializedName("families") @Expose private List<String> families;
        @SerializedName("takingOfDayNum") @Expose private int takingOfDayNum;
        @SerializedName("dosagi")@Expose private int dosagi;

        public int getMedicineNo() {
            return medicineNo;
        }

        public void setMedicineNo(int medicineNo) {
            this.medicineNo = medicineNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRegiDate() {
            return regiDate;
        }

        public void setRegiDate(String regiDate) {
            this.regiDate = regiDate;
        }

        public List<String> getFamilies() { return families; }

        public void setFamilies(List<String> families) { this.families = families; }

        public int getTakingOfDayNum() { return takingOfDayNum; }

        public void setTakingOfDayNum(int takingOfDayNum) { this.takingOfDayNum = takingOfDayNum; }

        public int getDosagi() { return dosagi; }

        public void setDosagi(int dosagi) { this.dosagi = dosagi; }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
}

