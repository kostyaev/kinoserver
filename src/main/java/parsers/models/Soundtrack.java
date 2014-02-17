package parsers.models;


public class Soundtrack {
    public String song;
    public String author;

    public Soundtrack(String song, String author) {
        this.song = song;
        this.author = author;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
