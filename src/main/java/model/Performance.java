package model;

import tools.ImageDownloader;

import java.io.IOException;

public class Performance {
    private final String name;
    private final String date;
    private final String time;
    private final String duration;
    private final String ageLimit;
    private String imageUrl;

    public Performance(String name, String date, String time, String duration, String ageLimit, String imageUrl) throws IOException {
        this.name = name;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.ageLimit = ageLimit;
        if (!imageUrl.isEmpty()) {
            this.imageUrl = "https://kirovdramteatr.ru" + imageUrl;
            ImageDownloader.download(this.imageUrl);
        }
    }

    @Override
    public String toString() {
        return name + "\n" + date + " " + time + "\n" + duration + "\n" + ageLimit + "\n" + (imageUrl != null ? imageUrl : "") + "\n";
    }
}