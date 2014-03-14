package ru.cybern.kinoserver.parsers.kinopoisk;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.cybern.kinoserver.parsers.models.Movie;
import ru.cybern.kinoserver.parsers.models.Soundtrack;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Parser {

    public static final String BASE_ADDRESS = "http://www.kinopoisk.ru";
    public static HashMap<String,Movie> movieLibrary;

    public static Document connect(String addr) throws IOException {
        // Подключение к ресурсу
        Document doc = Jsoup.connect(addr)
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .referrer("http://www.google.com")
                .get();
        return doc;
    }

    public static HashMap<String,Movie> parse(int from, int to) throws IOException {
        movieLibrary = new HashMap<String, Movie>();
        String startURL = BASE_ADDRESS + "/lists/ser/%7B\"soundtrack\"%3A\"ok\"%2C\"all\"%3A\"ok\"%2C\"what\"%3A\"content\"%2C\"count\"%3A%7B\"content\"%3A\"2470\"%7D%2C\"order\"%3A\"name\"%2C\"num\"%3A\"1\"%7D/perpage/25/";
        Document page = connect(startURL);
        startURL =  BASE_ADDRESS + "/lists/ser/%7B\"soundtrack\"%3A\"ok\"%2C\"all\"%3A\"ok\"%2C\"what\"%3A\"content\"%2C\"count\"%3A%7B\"content\"%3A\"2470\"%7D%2C\"order\"%3A\"name\"%2C\"num\"%3A\"1\"%7D/page/";
        for (int i = from; i < to; i++) //14
        {
            String url = startURL + i;
            page = connect(url);
            Elements moviesElems = page.select("a.all");
            Elements yearElems = page.select("a.orange");
            Object [] years = yearElems.toArray();

            //for (int j = 0; j < yearElems.size(); j++)
            for (int j = 0; j < 50; j++)
            {
                String movUrl = BASE_ADDRESS + moviesElems.get(j).attr("href");
                String movName = moviesElems.get(j).text();
                System.out.println(movName);

              List<Soundtrack> gotSounds = getSounds(movUrl);
                if ( gotSounds != null ){
                Movie curMovie = new Movie(getSounds(movUrl),getImage(movUrl), Integer.parseInt(yearElems.get(j).text()));
                movieLibrary.put(movName, curMovie);
              }
            }
        }
        return movieLibrary;
    }

    public static int getAmount() throws IOException {
        String url = BASE_ADDRESS + "/lists/ser/%7B\"soundtrack\"%3A\"ok\"%2C\"all\"%3A\"ok\"%2C\"what\"%3A\"content\"%2C\"count\"%3A%7B\"content\"%3A\"2470\"%7D%2C\"order\"%3A\"name\"%2C\"num\"%3A\"1\"%7D/perpage/25/";
        Document page = connect(url);
        return Integer.parseInt(page.select("div.pagesFromTo").get(0).text().split(" из ")[1]);
    }

    public static String getImage(String url) throws IOException {
        Document page = connect(url);
        Elements img = page.getElementsByAttributeValue("style", "border:5px solid #ccc");
        if (img.isEmpty()) return null;
        String imgURL = img.first().attr("src");
        String [] URLTokens = imgURL.split("/");
        String filename = URLTokens[URLTokens.length - 1];
        try {
            saveImage(imgURL,"images/" + "kinopoisk " + filename);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }

    public static List<Soundtrack> getSounds(String url) throws IOException {
        List<Soundtrack> sounds = new LinkedList<Soundtrack>();
        Document page = connect(url);
        Elements soundBlocks = page.getElementsByAttributeValue("style", "color:#f60");
        if (soundBlocks.isEmpty()) return null;
        for (Element sound : soundBlocks)  {
            String name;
            Elements link = sound.getElementsByTag("a");
            if(!link.isEmpty()) name = link.text();
            else name = sound.ownText();
            String author = sound.getElementsByTag("small").text();
            Soundtrack track = new Soundtrack(name,author);
            sounds.add(track);
        }
        return sounds;
    }


    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
        File dir = new File("images/");
        dir.mkdirs();

        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }


    public static void save() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("database.txt", "UTF-8");
        for(String movieName : movieLibrary.keySet()) {
            writer.println(movieName);
            if(movieLibrary.get(movieName).getSounds() != null) {
                for(Soundtrack sound : movieLibrary.get(movieName).getSounds()) {
                    writer.println("   Song: " + sound.song);
                    writer.println("   Author: " + sound.author);
                    writer.println();
                }
            }
            writer.println("------------------------------------");
        }
        writer.flush();
        writer.close();
    }

    public static void main(String [] args) {
        Document doc;
        try {
            parse(1, 2);
            save();
        }
         catch (IOException e) {
            e.printStackTrace();
         }
    }

}
