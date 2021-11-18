package dramteatr;

import model.Performance;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parser.Parser;

import java.io.IOException;
import java.util.ArrayList;

public class DramteatrParser implements Parser<ArrayList<Performance>> {

    @Override
    public ArrayList<Performance> Parse(@NotNull Document document) throws IOException {
        ArrayList<Performance> performances = new ArrayList<>();

        Elements performancesEl = document.getElementsByClass("t_afisha");

        for (Element performance : performancesEl) {
            String name = performance.getElementsByClass("t_info_afisha").get(0).getElementsByTag("a").get(0).ownText();
            String date = performance.getElementsByClass("date_afisha").text().substring(0, performance.getElementsByClass("date_afisha").text().lastIndexOf("в") - 1);
            String time = performance.getElementsByClass("time").text();
            String duration = performance.getElementsByClass("t_info_afisha").get(0).getElementsByClass("t_u3").get(0).getElementsByClass("td1").get(0).getElementsByTag("div").get(1).ownText();
            String ageLimit = performance.getElementsByClass("value_limit").text();
            String imageUrl = performance.getElementsByTag("img").attr("src");
            performances.add(new Performance(name, date, time, duration, ageLimit, "https://kirovdramteatr.ru", imageUrl));
        }

        return performances;
    }
}