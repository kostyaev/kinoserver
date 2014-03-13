package ru.cybern.kinoserver.parsers.stcollect;

import ru.cybern.kinoserver.parsers.models.Movie;
import ru.cybern.kinoserver.parsers.models.Soundtrack;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Parser {

    public static final String BASE_ADDRESS = "http://www.soundtrackcollector.com";
    public static HashMap<String,Movie> movieLibrary;


    public static Document connect(String addr) throws IOException {
        // doc conn
        Document doc = Jsoup.connect(addr)
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .referrer("http://www.google.com")
                .get();

        //File input = new File("/tmp/input.html");
        String title = doc.title();
        //System.out.print(title);
        return doc;
    }

    public static void parse(int from, int to) throws IOException {
        movieLibrary = new HashMap<String, Movie>();
        String startURL = BASE_ADDRESS; // append letters 'A'-'Z'; 0-9

        for (int i = 65; i < 66; i++) { //65-90; 0-9
            String url = "";

            if(i > 9) url = startURL + "/title/browse/" + String.valueOf((char) i); // letters
            else url = startURL + "/title/browse/" + Integer.toString(i); // numbers

            InputStream input = new URL(url).openStream();
            Document page = Jsoup.parse(input, "cp1251", url); // encoding fix

            Elements pageNextElem = page.select("form[name=frmBrowseTop] a"); // next button
            Element ne = pageNextElem.first();

            String letterNextPage = "";
            // start page
            if(ne.getElementsByTag("img").eq(0).attr("src").equals("http://img.soundtrackcollector.com/static/btn_next.gif"))
                letterNextPage = ne.attr("href");

            while (ne != null || letterNextPage.length() > 0) {
                Elements movieElems = page.select("div.content div#leftcolumn table tbody tr");

                for (Element movie : movieElems) {
                    if(movie.attr("class").equals("listheader"))
                        continue;

                    String movieName = movie.getElementsByClass("clsListItemTitle").text();
                    if(movieName.length() == 0)
                        continue;

                    String movUrl = BASE_ADDRESS + movie.getElementsByTag("a").attr("href");
                    String year = movie.getElementsByTag("td").eq(2).text();

                    System.out.println(movieName + year);
                    Movie curMovie = new Movie(getSounds(movUrl),getImage(movUrl), Integer.parseInt(year));
                    movieLibrary.put(movieName, curMovie);
                    break;
                }

                input = new URL(BASE_ADDRESS + letterNextPage).openStream();
                page = Jsoup.parse(input, "cp1251", url); // encoding fix

                pageNextElem = page.select("form[name=frmBrowseTop] a"); // next button
                if(pageNextElem.size() < 2) {
                    ne = null;
                    letterNextPage = "";
                } else {
                    ne = pageNextElem.get(1);
                }

                /*if(ne.getElementsByTag("img").eq(0).attr("src").equals("http://img.soundtrackcollector.com/static/btn_next.gif"))
                    letterNextPage = ne.attr("href");
                else {
                    ne = null;
                    letterNextPage = "";
                }*/

                break; // test break
            }
        }
    }

    public static String getImage(String url) throws IOException {
        InputStream input = new URL(url).openStream();
        Document page = Jsoup.parse(input, "cp1251", url); // encoding fix

        Elements img = page.getElementsByAttributeValue("border", "0alt='Click");
        if (img.isEmpty()) return null;

        String imgURL = img.first().attr("src");
        String [] URLTokens = imgURL.split("/");
        String filename = URLTokens[URLTokens.length - 1];
        try {
            saveImage(imgURL,"images/" + "stcollect " + filename);

        } catch (IOException e) {
            //e.printStackTrace();
        }

        return filename;
    }

    public static List<Soundtrack> getSounds(String url) throws IOException {
        List<Soundtrack> sounds = new LinkedList<Soundtrack>();
        InputStream input = new URL(url).openStream();
        Document page = Jsoup.parse(input, "cp1251", url); // encoding fix
        Elements soundBlocks = page.select("table.content tbody tr td table tbody tr td a table tbody tr td table tbody tr td:has(i small)");
        if (soundBlocks.isEmpty()) return null;
        for (Element sound : soundBlocks)  {
            String name;
            Elements link = sound.getElementsByTag("b");
            if(!link.isEmpty()) name = link.text();
            else name = sound.ownText();
            String author = sound.getElementsByTag("small").text();
            Soundtrack track = new Soundtrack(name, author);
            sounds.add(track);
            //System.out.println(name + "     author: " + author);

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
        PrintWriter writer = new PrintWriter("databasesc.txt", "UTF-8");
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
            parse(65, 66);
            save();
        }
         catch (IOException e) {
            e.printStackTrace();
         }
    }

}
