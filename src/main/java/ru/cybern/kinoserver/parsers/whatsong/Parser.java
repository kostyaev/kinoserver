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
    // вопрос с диапазоном страниц для парсинга
    //+++
    public static int start;
    public static int end;
    //+++
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
    //start 65 end 91
    public static HashMap<String, Movie> parse(int start,  int end) throws IOException {
        movieLibrary = new HashMap<String, Movie>();
        String startURL = "http://www.what-song.com/Movies/Browse/letter/";

        String url = startURL + "0";
        Document page = connect(url);
        Elements pagemoviesElems = page.select("div.row-fluid").select("div.span6").select("ul.nav").select("a");
        for (int j=0; j<pagemoviesElems.size(); j++){
            String movUrl = BASE_ADDRESS + pagemoviesElems.get(j).attr("href");

              String movName = pagemoviesElems.get(j).toString().substring(1+pagemoviesElems.get(j)
              .toString().indexOf(">"), pagemoviesElems.get(j).toString().indexOf("["));
             String year = pagemoviesElems.get(j).toString()
                     .substring(1+pagemoviesElems.get(j).toString().indexOf("["), pagemoviesElems.get(j).toString().indexOf("]"));

            System.out.println(movName + year);

            List<Soundtrack> sounds = getSounds(movUrl);
            if ( sounds != null){

                Movie curMovie = new Movie(sounds,getImage(movUrl), Integer.parseInt(year) );
                movieLibrary.put(movName, curMovie);
                save();
            }
        }
        url = null;
        page = null;
        for (int i =start; i <end; i++){
            url = startURL + String.valueOf((char) i);
            page = connect(url);
            pagemoviesElems = page.select("div.row-fluid").select("div.span6").select("ul.nav").select("a");
            for (int j=0; j<pagemoviesElems.size(); j++){
                String movUrl = BASE_ADDRESS + pagemoviesElems.get(j).attr("href");
                String movName = pagemoviesElems.get(j).toString().substring(1+pagemoviesElems.get(j)
                        .toString().indexOf(">"), pagemoviesElems.get(j).toString().indexOf("["))+pagemoviesElems
                        .get(j).toString().substring(1+pagemoviesElems.get(j).toString().indexOf("["), pagemoviesElems.get(j)
                                .toString().indexOf("]"));
                System.out.println(movName);
                List<Soundtrack> sounds = getSounds(movUrl);
                if ( sounds != null){
                    Movie curMovie = new Movie(sounds,getImage(movUrl) );
                    movieLibrary.put(movName, curMovie);
                    save();
                }

            }
        }
        return movieLibrary;
    }

//  uncomment 3 comments in next method  to check result in console



    public static List<Soundtrack> getSounds(String url) throws IOException {
        List<Soundtrack> sounds = new LinkedList<Soundtrack>();
        Document page = connect(url);
        Elements soundBlocks = page.select("td.span4").select("h4");
        for (Element sound : soundBlocks)  {
            String name = sound.text().replace("&amp;", "");
            String author = sound.parent().parent().select("a[href]").toString()
                    .substring(1 + sound.parent().parent().select("a[href]").toString().indexOf(">"))
                    .replace("</a>", "").replace("&amp;", "");
         //   System.out.println(name + " - " +  author);
            Soundtrack track = new Soundtrack(name,author);
            sounds.add(track);
        }
        int n=0;
        String sauthor=null;
        String sname=null;
        Elements completeList = page.select("tr.movie-play-row").select("h4");
        for (Element sound = completeList.get(n) ; n<completeList.size() ; n=n+1  ) {
            try {
                completeList.get(n+1);
            }
            catch(Exception e){
                break;
            }
           sname = sound.select("h4").toString().replace("<h4>","").replace("</h4>", "");
           sound = completeList.get(n+1);
           sauthor = sound.select("h4").select("a[href]").toString().substring(1+sound.select("h4").select("a[href]").toString().indexOf(">")).replace("</a>","");
           if(sname.contains("href") == false && sauthor.contains("href") == false && sauthor.contains("<a title=") == false && sname.contains("<a title") == false){
                Soundtrack track = new Soundtrack(sname,sauthor);
                sounds.add(track);
     //           System.out.println( "||  " + sname + " - " +  sauthor);
           }
           try{
               if ( completeList.get(n+2).toString().contains(":"))
                    n=n+1;

           }catch(Exception e){
                break;
           }

        }

        return sounds;
    }

    public static String getImage(String url) throws IOException {
        Document page = connect(url);
        Elements img = page.select("div.span2").select("img");
        if (img.isEmpty()) return null;
        String imgURL = img.first().attr("src");
        String [] URLTokens = imgURL.split("/");
        String filename = URLTokens[URLTokens.length - 2];
        try {

            saveImage(BASE_ADDRESS + imgURL,"images/" + "what-song " + filename + ".jpg");


        } catch (IOException e) {
            System.out.println("saving image error");
            e.printStackTrace();
        }
        return filename;


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
             parse(start, end);
            save();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}

