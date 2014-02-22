package whatsong;



/**
 * Created by Khasan on 14.02.14.
 */

import kinopoisk.Movie;
import kinopoisk.Soundtrack;

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

    public static final String BASE_ADDRESS = "http://www.what-song.com";
    public static HashMap<String,Movie> movieLibrary;


    public static Document connect(String addr) throws IOException {
        // Подключение к ресурсу
        Document doc = null;
        try {
            doc = Jsoup.connect(addr)
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .referrer("http://www.google.com")
                .get();
        }catch (Exception e )
        {
            return connect(addr);
        }
        String title = doc.title();
        return doc;
    }

    public static void parse() throws IOException {
        movieLibrary = new HashMap<String, Movie>();
        String startURL = "http://www.what-song.com/Movies/Browse/letter/";
        for (int i = 65; i < 66; i++){  // 0+A-Z

            String url = startURL + "0";			//String.valueOf((char) i);
            Document page = connect(url);
            Elements pagemoviesElems = page.select("div.row-fluid").select("div.span6").select("ul.nav").select("a");

            for (int j=0; j<pagemoviesElems.size(); j++){

                String movUrl = BASE_ADDRESS + pagemoviesElems.get(j).attr("href");

                String movName = pagemoviesElems.get(j).toString().substring(1+pagemoviesElems.get(j)
                        .toString().indexOf(">"), pagemoviesElems.get(j).toString().indexOf("["))+pagemoviesElems
                        .get(j).toString().substring(1+pagemoviesElems.get(j).toString().indexOf("["), pagemoviesElems.get(j)
                                .toString().indexOf("]"));

                System.out.println("start");
                System.out.println(movName);
                System.out.println("---");

                Movie curMovie = new Movie(getSounds(movUrl),getImage(movUrl) );
                movieLibrary.put(movName, curMovie);

                System.out.println("save");
                System.out.println("---");
                save();

                System.out.println("end");
                System.out.println("---");
            }

        }
    }



    public static List<Soundtrack> getSounds(String url) throws IOException {
        List<Soundtrack> sounds = new LinkedList<Soundtrack>();
        Document page = connect(url);
        Elements soundBlocks = page.select("td.span4").select("h4");
        System.out.println("sound");
        System.out.println("---");
        if (soundBlocks.isEmpty()) return null;
        for (Element sound : soundBlocks)  {
            String name = sound.text().replace("&amp;", "");
            String author = sound.parent().parent().select("a[href]").toString()
                    .substring(1 + sound.parent().parent().select("a[href]").toString().indexOf(">"))
                    .replace("</a>", "").replace("&amp;", "");
            System.out.println(name + " - " +  author);
            Soundtrack track = new Soundtrack(name,author);
            sounds.add(track);


        }
        System.out.println("sound end");
        return sounds;
    }


    public static String getImage(String url) throws IOException {

        Document page = connect(url);
        Elements img = page.select("div.span2").select("img");
        if (img.isEmpty()) return null;
        String imgURL = img.first().attr("src");
        String [] URLTokens = imgURL.split("/");
        String filename = URLTokens[URLTokens.length - 2];
        System.out.println("---");
        System.out.println("image url");
        System.out.println(BASE_ADDRESS + imgURL);
        System.out.println("---");
        System.out.println("image");
        System.out.println(filename);
        System.out.println("---");
        try {
            saveImage(BASE_ADDRESS + imgURL,"images/" + filename);

        } catch (IOException e) {
        }
        System.out.println("end image");
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
            //   save();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}

