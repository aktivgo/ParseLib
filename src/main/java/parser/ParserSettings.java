package parser;

public abstract class ParserSettings {

    public static String BASE_URL;  // Адрес сайта
    public static String PREFIX;    // Префих страницы
    protected int startPoint;       // Начало пагинации
    protected int endPoint;         // Конец пагинации

    public int getStartPoint() {
        return startPoint;
    }

    public int getEndPoint() {
        return endPoint;
    }
}