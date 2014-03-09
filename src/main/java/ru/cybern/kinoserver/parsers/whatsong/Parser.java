package ru.cybern.kinoserver.parsers.whatsong;



/**
 * Created by Khasan on 14.02.14.
 */


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

    public static final String BASE_ADDRESS = "http://www.what-song.com";
    public static HashMap<String,Movie> movieLibrary;


    public static Document connect(String addr) throws IOException {
        // Подключение к ресурсу
        Document doc = Jsoup.connect(addr)
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .referrer("http://www.google.com")
                .get();
        String title = doc.title();
        return doc;
    }

    public static void parse() throws IOException {
        movieLibrary = new HashMap<String, Movie>();
        String startURL = "http://www.what-song.com/Movies/Browse/letter/";
        for (int i = 65; i < 66; i++){  // 0+A-Z

            String url = startURL + String.valueOf((char) i);
            Document page = connect(url);
            Elements pagemoviesElems = page.select("div.row-fluid").select("div.span6").select("ul.nav").select("a");

            //  System.out.println(pagemoviesElems.get(3).attr("href"));




            for (int j=0; j<pagemoviesElems.size(); j++){



                String movUrl = BASE_ADDRESS + pagemoviesElems.get(j).attr("href");

                //      System.out.println(movUrl);
                String movName = pagemoviesElems.get(j).toString().substring(1+pagemoviesElems.get(j).toString().indexOf(">"), pagemoviesElems.get(j).toString().indexOf("["))+pagemoviesElems.get(j).toString().substring(1+pagemoviesElems.get(j).toString().indexOf("["), pagemoviesElems.get(j).toString().indexOf("]"));
                //      System.out.println(movName);

                Movie curMovie = new Movie(getSounds(movUrl),getImage(movUrl) );
                movieLibrary.put(movName, curMovie);

                //   	System.out.println(pagemoviesElems.get(j).toString().substring(1+pagemoviesElems.get(j).toString().indexOf(">"), pagemoviesElems.get(j).toString().indexOf("[")));

                //   	System.out.println(pagemoviesElems.get(j).toString().substring(1+pagemoviesElems.get(j).toString().indexOf("["), pagemoviesElems.get(j).toString().indexOf("]")));



            }

        }
    }



    public static List<Soundtrack> getSounds(String url) throws IOException {
        List<Soundtrack> sounds = new LinkedList<Soundtrack>();
        Document page = connect(url);
        Elements soundBlocks = page.select("td.span4").select("h4");

        if (soundBlocks.isEmpty()) return null;
        for (Element sound : soundBlocks)  {
            String name = sound.text();

            String author = sound.parent().parent().select("a[href]").toString().substring(1 + sound.parent().parent().select("a[href]").toString().indexOf(">")).replace("</a>", "");



            Soundtrack track = new Soundtrack(name,author);
            sounds.add(track);
            //System.out.println(name + "     author: " + author);

        }
        return sounds;
    }

    public static String getImage(String url) throws IOException {
        Document page = connect(url);
        Elements img = page.select("div.span2").select("img");
        System.out.println(img);

        if (img.isEmpty()) return null;
        String imgURL = img.first().attr("src");
        System.out.println(imgURL);
        String [] URLTokens = imgURL.split("/");
        String filename = URLTokens[URLTokens.length - 1];
        System.out.println(filename);
        try {
            saveImage(imgURL,"images/" + filename);

        } catch (IOException e) {
        }

        return filename;


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

