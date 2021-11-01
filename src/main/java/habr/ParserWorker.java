package habr;

import model.Article;
import org.jsoup.nodes.Document;
import parser.Parser;
import parser.ParserSettings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserWorker<T> {

    private Parser<T> parser;
    private ParserSettings parserSettings;
    private HtmlLoader loader;
    private boolean isActive;

    public ArrayList<OnNewDataHandler<T>> onNewDataList = new ArrayList<>();
    public ArrayList<OnCompleted> onCompletedList = new ArrayList<>();

    public ParserWorker(Parser<T> parser, ParserSettings parserSettings) {
        this.parser = parser;
        this.parserSettings = parserSettings;
        loader = new HtmlLoader(parserSettings);
    }

    public Parser<T> getParser() {
        return parser;
    }

    public ParserSettings getParserSettings() {
        return parserSettings;
    }

    public void setParser(Parser<T> parser) {
        this.parser = parser;
    }

    public void setParserSettings(ParserSettings parserSettings) {
        this.parserSettings = parserSettings;
        loader = new HtmlLoader(parserSettings);
    }

    public void start() throws IOException {
        isActive = true;
        parse();
    }

    public void abort() {
        isActive = false;
    }

    private void parse() throws IOException {
        for (int i = parserSettings.getStartPoint(); i <= parserSettings.getEndPoint(); i++) {
            if (!isActive) {
                onCompletedList.get(0).onCompleted(this);
                return;
            }
            Document document = loader.GetSourceByPageId(i);
            T result = parser.Parse(document);
            onNewDataList.get(0).onNewData(this, result);
        }
        onCompletedList.get(0).onCompleted(this);
        isActive = false;
    }
}