package model;

import org.jetbrains.annotations.NotNull;
import tools.ImageDownloader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Article {
    private final String title;
    private final String text;
    private final String imageUrl;
    private Image image = null;

    public Article(@NotNull String title, @NotNull String text, @NotNull String imageUrl) throws IOException {
        this.title = title;
        this.text = text;
        this.imageUrl = imageUrl;
        if (!imageUrl.isEmpty()) {
            ImageDownloader.download(!imageUrl.startsWith("https:") ? "https:" + imageUrl : imageUrl);
            String imageName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            image = new ImageIcon("upload_images/habr/" + imageName).getImage();
        }
    }

    public @NotNull String getTitle() {
        return title;
    }

    public @NotNull String getText() {
        return text;
    }

    public @NotNull String getImageUrl() {
        return imageUrl;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public @NotNull String toString() {
        return title + "\n" + text + "\n" + (imageUrl != null ? imageUrl : "Изображение отсутствует") + "\n";
    }
}