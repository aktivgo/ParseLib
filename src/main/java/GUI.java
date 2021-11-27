import habr.HabrParser;
import habr.HabrSettings;
import model.Article;
import org.jetbrains.annotations.NotNull;
import parser.*;
import tools.ImageDownloader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GUI extends JFrame implements OnNewDataHandler<ArrayList<Article>>, OnCompleted {

    private ParserWorker<ArrayList<Article>> parser;
    private final JPanel panel;

    public GUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        setTitle("Спарсить Хабр? - Легко!");
        setIconImage(new ImageIcon("icons/free-icon-internet-explorer-2573318.png").getImage());
        setSize(1200, 800);
        setMinimumSize(new Dimension(800, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel rightPanel = new JPanel();
        GridLayout layout = new GridLayout(10, 1, 5, 12);
        rightPanel.setLayout(layout);
        add(rightPanel, BorderLayout.EAST);

        JLabel jlStart = new JLabel("Первая страница");
        jlStart.setHorizontalAlignment(SwingConstants.CENTER);
        jlStart.setVerticalAlignment(SwingConstants.CENTER);
        rightPanel.add(jlStart);

        JTextField jtfStart = new JTextField();
        jtfStart.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(jtfStart);

        JLabel jlEnd = new JLabel("Последняя страница");
        jlEnd.setHorizontalAlignment(SwingConstants.CENTER);
        jlEnd.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(jlEnd);

        JTextField jtfEnd = new JTextField();
        jtfEnd.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(jtfEnd);

        JButton startButton = new JButton("Старт");
        JButton abortButton = new JButton("Стоп");
        startButton.setSize(50, 20);
        rightPanel.add(startButton);
        rightPanel.add(abortButton);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JScrollPane jsp = new JScrollPane(panel);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(jsp);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isCorrectInputText(jtfStart.getText(), jtfEnd.getText())) {
                    return;
                }

                panel.removeAll();

                int start = Integer.parseInt(jtfStart.getText());
                int end = Integer.parseInt(jtfEnd.getText());

                parser = new ParserWorker<>(new HabrParser(), new HabrSettings(start, end));

                parser.onCompletedList.add(GUI.this);
                parser.onNewDataList.add(GUI.this);

                parser.onCompletedList.add(new Completed());
                parser.onNewDataList.add(new NewDataArticle());

                ImageDownloader.setSavePath("upload_images/habr/");

                System.out.println("\nЗагрузка началась\n");

                try {
                    parser.start();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                parser.abort();
            }
        });

        abortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (parser != null) {
                    parser.abort();
                }
            }
        });
    }

    private boolean isCorrectInputText(@NotNull String startText, @NotNull String endText) {
        if (startText.equals("")) {
            showError("Введите начало пагинации");
            return false;
        }

        if (endText.equals("")) {
            showError("Введите конец пагинации");
            return false;
        }

        int start, end;
        try {
            start = Integer.parseInt(startText);
            end = Integer.parseInt(endText);
        } catch (NumberFormatException e) {
            showError("Введите целочисленное значение");
            return false;
        }

        if (start < 1) {
            showError("Введено неккоректное значение (start < 1)");
            return false;
        }

        if (end < 1) {
            showError("Введено неккоректное значение (end < 1)");
            return false;
        }

        if (end < start) {
            showError("Введено неккоректное значение (start > end)");
            return false;
        }

        return true;
    }

    private void showError(@NotNull String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void onNewData(Object sender, @NotNull ArrayList<Article> args) throws IOException {
        int count = 0;
        for (Article article : args) {
            System.out.println(article + "\n");

            JTextArea textArea = getInitTextArea();
            textArea.append("Статья " + ++count + "\n" + article.getTitle() + "\n" + article.getText() + "\n");

            JLabel label = new JLabel();
            if (article.getImage() != null) {
                String imageName = article.getImageUrl().substring(article.getImageUrl().lastIndexOf("/") + 1);
                BufferedImage bufImage = ImageIO.read(new File("upload_images/habr/" + imageName));
                Image image = bufImage.getScaledInstance(600, 400, Image.SCALE_DEFAULT);
                label.setIcon(new ImageIcon(image));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setLocation(0, 0);
            } else {
                textArea.append("Изображение отсутствует\n");
            }

            panel.add(textArea);
            panel.add(label);
            panel.updateUI();
        }
    }

    private @NotNull JTextArea getInitTextArea() {
        JTextArea textArea = new JTextArea();

        textArea.setFont(new Font("Dialog", Font.PLAIN, 14));
        textArea.setSize(panel.getWidth(), panel.getHeight());
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);

        return textArea;
    }

    @Override
    public void onCompleted(Object sender) {
        JOptionPane.showMessageDialog(this, "Загрузка закончена");
    }
}