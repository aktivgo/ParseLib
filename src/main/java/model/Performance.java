package model;

import org.jetbrains.annotations.NotNull;
import tools.ImageDownloader;

import java.io.IOException;

public class Performance {
    private final String name;
    private final String date;
    private final String time;
    private final String duration;
    private final String ageLimit;
    private String imageUrl;

    public Performance(String name, String date, String time, String duration, String ageLimit, String baseUrl, @NotNull String imagePath) throws IOException {
        this.name = name;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.ageLimit = ageLimit;
        if (!imagePath.isEmpty()) {
            this.imageUrl = baseUrl + imagePath;
            ImageDownloader.download(this.imageUrl);
        }
    }

    @Override
    public String toString() {
        return name + "\n" + date + " " + time + "\n" + (!duration.equals("") ? duration : "Продолжительность не указана") + "\n" + ageLimit + "\n" + (imageUrl != null ? imageUrl : "Изображение отсутствует") + "\n";
    }
}