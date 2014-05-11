package ru.cybern.kinoserver.parsers.impl;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.cybern.kinoserver.parsers.Global;
import ru.cybern.kinoserver.parsers.IParser;
import ru.cybern.kinoserver.parsers.models.Movie;
import ru.cybern.kinoserver.parsers.models.Soundtrack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SoundtracknetParser implements IParser {

    private static final String BASE_ADDRESS = "http://www.soundtrack.net";

    private static final Logger logger = Logger.getLogger(SoundtracknetParser.class);

    private static final int LAST_PAGE_NUMBER = 26;

    private boolean saveImages;

    public SoundtracknetParser(boolean saveImages) {
        this.saveImages = saveImages;
    }

    @Override
    public String getClassName() {
        return this.getClassName();
    }

    public boolean isSaveImages() {
        return saveImages;
    }

    public void setSaveImages(boolean saveImages) {
        this.saveImages = saveImages;
    }

    private static Document connect(String addr) throws IOException {
        Document doc = null;
        try
        {
            doc = Jsoup.connect(addr)
                    .timeout(10000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get();
        }
        catch( Exception e ){
            return connect(addr);
        }
        return doc;
    }

    private static String extractYear(String str) {
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find())
        {
            return matcher.group(1);
        }
        return null;
    }

    private static String extractName(String str) {
        Pattern pattern = Pattern.compile("(^.+) \\(.+");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find())
        {
            return matcher.group(1);
        }
        return null;
    }

    public List<Movie> parse(int start,  int end) throws IOException {
        List<Movie> movieList = new LinkedList<>();
        String startURL = "http://www.soundtrack.net/albums/?lid=t";
        Document page = connect(startURL);
        Elements pagemoviesElems;
        Elements pageElems = page.select(".alphanav").select("a");

        if(start < 0)
            start = 0;
        if(end > LAST_PAGE_NUMBER)
            end = LAST_PAGE_NUMBER;
int x = 0;
        for (int i = start; i <= end; i++){
            String url = BASE_ADDRESS + "/albums/"+ pageElems.get(i).attr("href");
            page = connect(url);
            pagemoviesElems = page.select(".soundtracks-right-table").select("ul").select("a");

            for (Element movie : pagemoviesElems ){
                if(movie.text().isEmpty()) continue;
                String name = movie.text();
                String date = movie.select(".date").text();
                if ( (!date.isEmpty())
                        && (!date.contains("(0)"))
                        && (date.length() >= 6)
                        && (!date.contains("(0,")))
                {
                    int year = Integer.parseInt(date.substring(1, 5));
                    String movName = extractName(name).replace("*", "");
                    logger.info("received " + /*pagemoviesElems.indexOf(movie)/3 */ x + " movies");
                    String trashmovUrl = movie.select("a[href]").toString();
                    String movUrl = BASE_ADDRESS +
                            trashmovUrl.substring(trashmovUrl.indexOf("/"), trashmovUrl.indexOf(">") - 2);
      //              logger.info("Current movie is: " + movName);
      //              logger.info("Current movie URL is: " + movUrl);
                    List<Soundtrack> sounds = getSounds(movUrl);
      //              boolean z=false;
                    if ((sounds != null)&&(sounds.size()!=0)){
                        String image = getImage(movUrl);
                        if (image != null){
                            //System.out.println("=====================================================================");
                            movieList.add(new Movie(movName,sounds, image, year));
                            x++;
        //                    z=true;
                        }else{
                            image = getOldImage(movUrl);
                            if (image != null){
                           //     System.out.println("=====================================================================");
                                movieList.add(new Movie(movName,sounds, image, year));
                                x++;
       //                         z = true;
                            }
                        }

                    }
        //            if (z = false) logger.info("Current movie is:" + movName   + "\0" + "   " + movUrl );
                   // System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    //x++;

                }
            }
        }
        return movieList;
    }

    private  List<Soundtrack> getSounds(String url) throws IOException {
        List<Soundtrack> sounds = new LinkedList<Soundtrack>();
        Document page = connect(url);
        Elements soundBlocks = page.select("tbody").select(".trackname");
        for (Element sound : soundBlocks)  {
            String author="Unknown";
            String name = sound.text();
  //          System.out.println(name);
            if (name.contains("(")){
                author = extractYear(name);
                name = extractName(name);
            }
            Soundtrack track = new Soundtrack(name,author);
            sounds.add(track);
        }
        return sounds;
    }

    private String getImage(String url) throws IOException {
        Document page = connect(url);
        Elements img = page.select(".albumart").select("img");
        if (img.isEmpty()) return null;
        String imgURL = img.first().attr("src");
        String [] URLTokens = imgURL.split("/");
        String filename = Global.SOUNDTRACKNET_PREFIX + URLTokens[URLTokens.length -1 ];
        if (saveImages)
            saveImage(imgURL, filename);
        return filename;
    }


    private String getOldImage(String url) throws IOException {
        Document page = connect(url);
        Elements img = page.select(".soundtrackphoto").select("img");
        if (img.isEmpty()) return null;
        String imgURL = img.first().attr("src");
        String [] URLTokens = imgURL.split("/");
        String filename = Global.SOUNDTRACKNET_PREFIX + URLTokens[URLTokens.length -1 ];
        if (saveImages)
            saveImage(imgURL, filename);
        return filename;
    }


    private void saveImage(String imageUrl, String destinationFile) throws IOException {
        File dir = new File(Global.HOME_PATH + Global.IMG_PATH);
        dir.mkdirs();
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(Global.HOME_PATH + Global.IMG_PATH + destinationFile);
        byte[] b = new byte[2048];

        int length;
        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }
        is.close();
        os.close();
    }

    public static int getLastPageNumber() {
        return LAST_PAGE_NUMBER;
    }



}
