package ru.cybern.kinoserver.parsers.whatsong;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private static final String BASE_ADDRESS = "http://www.what-song.com";
    private static HashMap<String,Movie> movieLibrary;

    private static Document connect(String addr) throws IOException {
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
        return doc;
    }

    private static String extractYear(String str) {
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find())
        {
            return matcher.group(1);
        }
        return null;
    }
    private static String extractName(String str) {
        Pattern pattern = Pattern.compile("(^.+) \\[.+");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find())
        {
            return matcher.group(1);
        }
        return null;
    }

    public static HashMap<String, Movie> parse(int start,  int end) throws IOException {
        movieLibrary = new HashMap<String, Movie>();
        String startURL = "http://www.what-song.com/Movies/Browse/letter/";
        Document page = connect(startURL);
        Elements pagemoviesElems;
        Elements pageElems = page.select("a.hoverEffect");
        if(start < 1)
            start = 1;
        if(end > pageElems.size() - 1)
            end = pageElems.size();
        for (int i = start; i <= end; i++){
            String url = BASE_ADDRESS + pageElems.get(i).attr("href");
            page = connect(url);
            pagemoviesElems = page.select(".nav-pills").select(".nav-stacked").select("a");
            for (int j=0; j<pagemoviesElems.size(); j++){
                String movUrl = BASE_ADDRESS + pagemoviesElems.get(j).attr("href");
                String name = pagemoviesElems.get(j).text();
                int year = Integer.parseInt(extractYear(name));
                String movName = extractName(name);
                System.out.println("Movie name: " + movName);
                System.out.println("YEAR: " + year);
                List<Soundtrack> sounds = getSounds(movUrl);
                if ( sounds != null){
                    Movie curMovie = new Movie(sounds,getImage(movUrl),year);
                    movieLibrary.put(movName, curMovie);
                    save();
                }
            }
        }
        return movieLibrary;
    }

    private static List<Soundtrack> getSounds(String url) throws IOException {
        List<Soundtrack> sounds = new LinkedList<Soundtrack>();
        Document page = connect(url);
        Elements soundBlocks = page.select("td.span4").select("h4");
        for (Element sound : soundBlocks)  {
            String name = sound.text().replace("&amp;", "");
            String author = sound.parent().parent().select("a[href]").toString()
                    .substring(1 + sound.parent().parent().select("a[href]").toString().indexOf(">"))
                    .replace("</a>", "").replace("&amp;", "");
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

    private static String getImage(String url) throws IOException {
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

    private static void saveImage(String imageUrl, String destinationFile) throws IOException {
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

    private static void save() throws FileNotFoundException, UnsupportedEncodingException {
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
            parse(2, 3);
            save();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}

