package spasskaya;

import model.Performance;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parser.Parser;

import java.io.IOException;
import java.util.ArrayList;

public class SpasskayaParser implements Parser<ArrayList<Performance>> {

    @Override
    public ArrayList<Performance> Parse(@NotNull Document document) throws IOException {
        ArrayList<Performance> performances = new ArrayList<>();

        Elements performancesEl = document.getElementsByClass("page_box").get(0).getElementsByTag("table").get(0).getElementsByTag("tr");
        performancesEl.remove(0);

        for (Element performance : performancesEl) {
            Document performanceDoc = loadPerformance(performance);

            String name = parseName(performanceDoc);
            String dateStr = parseDate(performanceDoc);
            String duration = parseDuration(performanceDoc);
            String ageLimit = parseAgeLimit(performanceDoc);
            String imageUrl = parseImage(performanceDoc);

            performances.add(new Performance(name, dateStr, "", duration, ageLimit, "https://ekvus-kirov.ru", imageUrl));
        }

        return performances;
    }

    @NotNull
    private Document loadPerformance(@NotNull Element performance) throws IOException {
        String href = performance.getElementsByTag("a").get(1).attr("href");
        return Jsoup.connect("https://ekvus-kirov.ru" + href).get();
    }

    private @NotNull String parseName(@NotNull Document performanceDoc) {
        return performanceDoc.getElementsByTag("h1").text();
    }

    private @NotNull String parseDate(@NotNull Document performanceDoc) {
        String dateStr = "";
        Elements dates = performanceDoc.getElementsByClass("page_box").get(0).getElementsByTag("a").attr("title", "Купить билет");
        for (Element date : dates) {
            if (!date.attr("href").contains("/events/buyticket/")) break;
            dateStr = dateStr.concat(date.ownText() + "\n");
        }
        if (!dateStr.equals("")) {
            dateStr = dateStr.substring(0, dateStr.length() - 1);
        }
        return dateStr;
    }

    private String parseDuration(@NotNull Document performanceDoc) {
        String duration = "";
        String dur = "Продолжительность спектакля - ";
        Elements durs = performanceDoc.getElementsMatchingText(dur);
        if (!durs.isEmpty()) {
            duration = durs.get(8).text().substring(dur.length()).replace(".", "");
        }
        return duration;
    }

    private String parseAgeLimit(@NotNull Document performanceDoc) {
        String ageLimit = "";
        Elements ageLimitEl = performanceDoc.getElementsMatchingOwnText("[0-9]+\\+");
        if (!ageLimitEl.isEmpty()) {
            ageLimit = ageLimitEl.text().substring(ageLimitEl.text().lastIndexOf(" ") + 1);
        }
        return ageLimit;
    }

    private String parseImage(@NotNull Document performanceDoc) {
        String imageUrl = "";
        Element imageEl = performanceDoc.getElementById("photo_osnova");
        if (imageEl != null) {
            imageUrl = imageEl.attr("src");
        } else {
            imageUrl = performanceDoc.getElementsByClass("img_right").get(0).attr("src");
            imageUrl = imageUrl.substring(5);
        }
        return imageUrl;
    }
}