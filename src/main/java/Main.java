import habr.HabrParser;
import habr.HabrSettings;
import model.Article;
import parser.Completed;
import parser.NewData;
import parser.ParserWorker;
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
                case 1 -> {
                    parseHabr();
                }
                case 2 -> {

                }
                case 3 -> {

                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("Парсинг:");
        System.out.println("1. https://habr.com/ru/all/");
        System.out.println("2. https://kirovdramteatr.ru/afisha/");
        System.out.println("3. https://ekvus-kirov.ru/afisha/");
    }

    private static void parseHabr() throws IOException, InterruptedException {
        int start = readPagination("Введите начало пагинации: ");
        int end = readPagination("Введите конец пагинации: ");
        ParserWorker<ArrayList<Article>> parser = new ParserWorker<>(new HabrParser(), new HabrSettings(start, end));

        parser.onCompletedList.add(new Completed());
        parser.onNewDataList.add(new NewData());
        ImageDownloader.setSavePath("upload_images/");

        System.out.println("\nЗагрузка началась\n");
        parser.start();
        Thread.sleep(10000);
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
}