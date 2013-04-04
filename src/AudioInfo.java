import com.echonest.api.v4.*;
import java.util.List;

public class AudioInfo {
    private EchoNestAPI en;
    
    public AudioInfo() throws EchoNestException{
    	en = new EchoNestAPI();
    	en.setTraceSends(false);
    	en.setTraceRecvs(false);
    }
    
    public Double getTempo(String artistName, String title) throws EchoNestException{
        SongParams p = new SongParams();
        p.setArtist(artistName);
        p.setTitle(title);
        p.setResults(1);
        p.includeAudioSummary();
        List<Song> songs = en.searchSongs(p);
        if (songs.size() > 0) {
            double tempo = songs.get(0).getTempo();
            return Double.valueOf(tempo);
        } else {
            return null;
        }
    }
}
