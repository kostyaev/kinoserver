package parsers.kinopoisk;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parsers.models.Movie;
import parsers.models.Soundtrack;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Parser {

    public static final String BASE_ADDRESS = "http://www.parsers.kinopoisk.ru";
    public static HashMap<String,Movie> movieLibrary;


    public static Document connect(String addr) throws IOException {
        // Подключение к ресурсу
        Document doc = Jsoup.connect(addr)
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .referrer("http://www.google.com")
                .get();

        //File input = new File("/tmp/input.html");
        String title = doc.title();
        //System.out.print(title);
        return doc;
    }

    public static void parse() throws IOException {
        movieLibrary = new HashMap<String, Movie>();
        String startURL = "http://www.parsers.kinopoisk.ru/level/10/ser/a%3A6%3A%7Bs%3A10%3A%22soundtrack%22%3Bs%3A2%3A%22ok%22%3Bs%3A4%3A%22what%22%3Bs%3A7%3A%22content%22%3Bs%3A3%3A%22all%22%3Bs%3A2%3A%22ok%22%3Bs%3A5%3A%22count%22%3Ba%3A1%3A%7Bs%3A7%3A%22content%22%3Bs%3A4%3A%222460%22%3B%7Ds%3A5%3A%22order%22%3Bs%3A4%3A%22name%22%3Bs%3A3%3A%22num%22%3Bi%3A1%3B%7D/perpage/25/page/";
        for (int i = 1; i < 2; i++) //14
        {
            String url = startURL + i;
            Document page = connect(url);
            Elements moviesElems = page.select("a.all");
            Elements yearElems = page.select("a.orange");
            Object [] years = yearElems.toArray();

            for (int j = 0; j < yearElems.size(); j++) //200
            {
                if (i == 13 && j == 61) break;
                String movUrl = BASE_ADDRESS + moviesElems.get(j).attr("href");

                //System.out.println(movUrl);
                String movName = moviesElems.get(j).text() + ", " + yearElems.get(j).text();
                System.out.println(movName);

                Movie curMovie = new Movie(getSounds(movUrl),getImage(movUrl) );
                movieLibrary.put(movName, curMovie);

            }
        }
    }

    public static String getImage(String url) throws IOException {
        Document page = connect(url);
        Elements img = page.getElementsByAttributeValue("style", "border:5px solid #ccc");
        if (img.isEmpty()) return null;
        String imgURL = img.first().attr("src");
        String [] URLTokens = imgURL.split("/");
        String filename = URLTokens[URLTokens.length - 1];
        try {
            saveImage(imgURL,"images/" + filename);

        } catch (IOException e) {
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
            //System.out.println(name + "     author: " + author);

        }
        return sounds;
    }


    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
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
            parse();
            save();
        }
         catch (IOException e) {
            e.printStackTrace();
         }
    }

}
