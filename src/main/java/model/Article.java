package model;

public class Article {

    private String title;
    private String text;
    private String image;

    public Article(String title, String text, String image) {
        this.title = title;
        this.text = text;
        this.image = image;
        saveImage(image);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title.isEmpty()) throw new NullPointerException();
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text.isEmpty()) throw new NullPointerException();
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void saveImage(String image) {
        if (image.isEmpty()) throw new NullPointerException();
        this.image = image;
        // Логика сохранения
    }

    @Override
    public String toString() {
        return title + "\n" + text + "\n" + image + "\n";
    }
}