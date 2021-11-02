import habr.HabrParser;
import habr.HabrSettings;
import model.Article;
import parser.Completed;
import parser.NewData;
import parser.ParserWorker;
import tools.ImageDownloader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final Scanner IN = new Scanner(System.in);

    public static void main(String[] args) throws IOException, InterruptedException {

        int menu = -1;
        while (menu != 0) {
            printMenu();
            System.out.println("Выберите пункт меню: ");
            try {
                menu = IN.nextInt();
            } catch (Exception e) {
                System.out.println("Ошибка при считывании с клавиатуры, повторите попытку");
            }
            switch (menu) {
                case 1 -> {
                    System.out.print("Введите начало пагинации: ");
                    int start = readInt();
                    System.out.print("Введите конец пагинации: ");
                    int end = readInt();
                    ParserWorker<ArrayList<Article>> parser = new ParserWorker<>(new HabrParser(), new HabrSettings(start, end));

                    parser.onCompletedList.add(new Completed());
                    parser.onNewDataList.add(new NewData());
                    ImageDownloader.setSavePath("upload_images/");

                    parser.start();
                    Thread.sleep(10000);
                    parser.abort();
                }
                case 2 -> {

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

    private static int readInt() {
        try {
            return IN.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Ошибка при считывании с клавиатуры: " + e.getMessage());
        }
        return 1;
    }
}