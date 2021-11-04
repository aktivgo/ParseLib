import dramteatr.DramteatrParser;
import dramteatr.DramteatrSettings;
import parser.NewDataPerfomance;
import habr.HabrParser;
import habr.HabrSettings;
import model.Article;
import model.Performance;
import parser.Completed;
import parser.NewDataArticle;
import parser.ParserWorker;
import spasskaya.SpasskayaParser;
import spasskaya.SpasskayaSettings;
import tools.ImageDownloader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final Scanner IN = new Scanner(System.in);

    public static void main(String[] args) throws IOException, InterruptedException {


        int menu = -1;
        while (menu != 0) {
            printMenu();
            System.out.print("Выберите пункт меню: ");
            try {
                menu = IN.nextInt();
            } catch (Exception e) {
                System.out.println("Ошибка при считывании с клавиатуры, повторите попытку");
            }
            switch (menu) {
                case 0 -> {
                    System.out.println("Пока");
                }
                case 1 -> {
                    parseHabr();
                }
                case 2 -> {
                    parseDramteatr();
                }
                case 3 -> {
                    parseSpasskaya();
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("Парсинг:");
        System.out.println("1. https://habr.com/ru/all/");
        System.out.println("2. https://kirovdramteatr.ru/afisha/");
        System.out.println("3. https://ekvus-kirov.ru/afisha/");
        System.out.println("0. Выход");
    }

    private static void parseHabr() throws IOException {
        int start = readPagination("Введите начало пагинации: ");
        int end = readPagination("Введите конец пагинации: ");
        ParserWorker<ArrayList<Article>> parser = new ParserWorker<>(new HabrParser(), new HabrSettings(start, end));

        parser.onCompletedList.add(new Completed());
        parser.onNewDataList.add(new NewDataArticle());

        ImageDownloader.setSavePath("upload_images/habr/");

        System.out.println("\nЗагрузка началась\n");
        parser.start();
        parser.abort();
    }

    private static int readPagination(String message) {
        System.out.print(message);
        int value = 0;
        if (IN.hasNextInt()) {
            value = IN.nextInt();
            if (value > 0) {
                return value;
            }
            System.out.println("Введите целочисленное значение больше 0");
        }
        return value;
    }

    private static void parseDramteatr() throws IOException {
        ParserWorker<ArrayList<Performance>> parser = new ParserWorker<>(new DramteatrParser(), new DramteatrSettings());

        parser.onCompletedList.add(new Completed());
        parser.onNewDataList.add(new NewDataPerfomance());

        ImageDownloader.setSavePath("upload_images/dramteatr/");

        System.out.println("\nЗагрузка началась\n");
        parser.start();
        parser.abort();
    }

    private static void parseSpasskaya() throws IOException {
        ParserWorker<ArrayList<Performance>> parser = new ParserWorker<>(new SpasskayaParser(), new SpasskayaSettings());

        parser.onCompletedList.add(new Completed());
        parser.onNewDataList.add(new NewDataPerfomance());

        ImageDownloader.setSavePath("upload_images/spasskaya/");

        System.out.println("\nЗагрузка началась\n");
        parser.start();
        parser.abort();
    }
}