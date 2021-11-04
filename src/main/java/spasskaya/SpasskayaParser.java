package spasskaya;

import model.Performance;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parser.Parser;

import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class SpasskayaParser implements Parser<ArrayList<Performance>> {

    @Override
    public ArrayList<Performance> Parse(Document document) throws IOException {
        ArrayList<Performance> performances = new ArrayList<>();

        Elements performancesEl = document.getElementsByClass("page_box").get(0).getElementsByTag("table").get(0).getElementsByTag("tr");
        performancesEl.remove(0);

        for (Element performance : performancesEl) {
            String href = performance.getElementsByTag("a").get(1).attr("href");
            Document performanceDoc = Jsoup.connect("https://ekvus-kirov.ru" + href).get();

            String name = performanceDoc.getElementsByTag("h1").text();
            Elements dates = performanceDoc.getElementsByTag("a").attr("alt", "Купить билет");
            String dateStr = "";
            for (Element date : dates) {
                dateStr = dateStr.concat(date.ownText() + " ");
            }
            String count = "Продолжительность спектакля";
            String duration = performanceDoc.getElementsMatchingText(count).text().substring(count.length() + 3).replace(".", "");
            String ageLimit = performanceDoc.getElementsByTag("h2").get(0).text();
            String imageUrl = Objects.requireNonNull(performanceDoc.getElementById("photo_osnova")).attr("src");
            performances.add(new Performance(name, dateStr, "", duration, ageLimit, "https://ekvus-kirov.ru", imageUrl));
        }

        return performances;
    }
}