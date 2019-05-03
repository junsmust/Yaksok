package yaksok.dodream.com.yaksok_refactoring.Adapter.SearchPill;

public class PillSearchItem {
    private int medicineNO;
    private String name;
    private String element;
    private String company;

    public PillSearchItem(){}
    public PillSearchItem(int num,String name, String element){
        this.medicineNO = num;
        this.name = name;
        this.element = element;
    }

    public int getMedicineNO() { return medicineNO; }

    public void setMedicineNO(int medicineNO) { this.medicineNO = medicineNO; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getElement() { return element; }

    public void setElement(String element) { this.element = element; }

    public String getCompany() { return company; }

    public void setCompany(String company) { this.company = company; }
}

