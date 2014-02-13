package kinopoisk;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: virtuozzo
 * Date: 04.12.13
 * Time: 19:39
 * To change this template use File | Settings | File Templates.
 */
public class Parser_Test {

    public static void main(String [] args) {
        Document doc;
        try {
            // получаем http протокол
            //doc = Jsoup.connect("http://www.kinopoisk.ru/lists/m_act%5Bsoundtrack%5D/ok/m_act%5Ball%5D/ok/m_act%5Bwhat%5D/content/m_act%5Bcount%5D%5Bcontent%5D/2470/m_act%5Border%5D/name/").get();


            doc = Jsoup.connect("http://www.kinopoisk.ru/lists/m_act%5Bsoundtrack%5D/ok/m_act%5Ball%5D/ok/m_act%5Bwhat%5D/content/m_act%5Bcount%5D%5Bcontent%5D/2470/m_act%5Border%5D/name/")

            .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get();
            //File input = new File("/tmp/input.html");
            //Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

            // получаем Title страницы
            String title = doc.title();
            Elements all = doc.select("a.all");
            Elements all2 = doc.getAllElements();

            System.out.println("title : " + title);
            FileWriter fw = new FileWriter("file.html");
            fw.write(doc.html());
            fw.flush();
            //doc.html();

            // Вытягиваем все ссылки
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                // получаем значения с href атрибутов
                System.out.println("\nlink : " + link.attr("href"));
                System.out.println("text : " + link.text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
