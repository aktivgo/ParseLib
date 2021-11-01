package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Article {
    private String title;
    private String text;
    private String imageUrl;
    private final String imagePath = "uploads/";

    public Article(String title, String text, String image) throws IOException {
        this.title = title;
        this.text = text;
        if (!image.isEmpty()) {
            this.imageUrl = !image.startsWith("https:") ? "https:" + image : image;
            saveImage();
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

    public void saveImage() throws IOException {
        BufferedImage input = ImageIO.read(new URL(imageUrl));
        String imageName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        String imageExtension = imageName.substring(imageName.lastIndexOf(".") + 1);
        File output = new File(imagePath + imageName);
        try{
            ImageIO.write(input, imageExtension, output);
        } catch (Exception e) {
            System.out.println("Ошибка при сохранении изображения  " + imageName + "\n" + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return title + "\n" + text + "\n" + (imageUrl != null ? imageUrl : "") + "\n";
    }
}