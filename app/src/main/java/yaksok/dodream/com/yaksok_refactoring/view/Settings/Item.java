package yaksok.dodream.com.yaksok_refactoring.view.Settings;

public class Item {
    private String title;
    private String description;

    public String getTitle() { return title; }
    public String getDescription() { return description; }

    public Item(String title, String description)
    {
        super();
        this.title = title;
        this.description = description;
    }
}
