package model;

import tools.ImageDownloader;
import java.io.IOException;

public class Article {
    private final String title;
    private final String text;
    private String imageUrl;

    public Article(String title, String text, String imageUrl) throws IOException {
        this.title = title;
        this.text = text;
        if (!imageUrl.isEmpty()) {
            this.imageUrl = !imageUrl.startsWith("https:") ? "https:" + imageUrl : imageUrl;
            ImageDownloader.download(this.imageUrl);
        }
    }

    @Override
    public String toString() {
        return title + "\n" + text + "\n" + (imageUrl != null ? imageUrl : "Изображение отсутствует") + "\n";
    }
}