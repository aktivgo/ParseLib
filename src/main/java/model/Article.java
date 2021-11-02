package model;

import tools.ImageDownloader;
import java.io.IOException;

public class Article {
    private String title;
    private String text;
    private String imageUrl;

    public Article(String title, String text, String image) throws IOException {
        this.title = title;
        this.text = text;
        if (!image.isEmpty()) {
            this.imageUrl = !image.startsWith("https:") ? "https:" + image : image;
            ImageDownloader.download(imageUrl);
        }
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
        return imageUrl;
    }

    @Override
    public String toString() {
        return title + "\n" + text + "\n" + (imageUrl != null ? imageUrl : "") + "\n";
    }
}