

	import com.echonest.api.v4.*;
	import java.io.*;
	import java.util.List;
	
	
	public class GetAudioInfo {
	    private EchoNestAPI en;
	    private static final String API_KEY = "5PLBCMHXPWYC1NBD4"; 
	   
	    public GetAudioInfo() throws EchoNestException{
	      en = new EchoNestAPI(API_KEY);
	      en.setTraceSends(false);
	      en.setTraceRecvs(false);
	    }
	    
	    /**
	     * finds the song in the JEN database and returns its tempo as a double
	     * @param artistName
	     * @param title
	     * @return
	     * @throws EchoNestException
	     */
	    public Double getSongDuration(String artistName, String title) throws EchoNestException{
	        SongParams p = new SongParams();
	        p.setArtist(artistName);
	        p.setTitle(title);
	        p.setResults(1);
	        p.includeAudioSummary();
	        List<Song> songs = en.searchSongs(p);
	        if (songs.size() > 0) {
	            double duration = songs.get(0).getDuration();
	            return Double.valueOf(duration);
	        } else {
	            return null;
	        }
	    }
	    
	    /**
	     * checks to see if the artist name is in the JEN database.
	     * @param artist
	     * @return
	     * @throws EchoNestException
	     * @throws IOException
	     */
	    public String verifySongsByArtist(String artist)
	            throws EchoNestException, IOException {
	        Params p = new Params();
	        p.add("artist", artist);
	
	        return artist;
	        
	    }
	    
	    /**
	     * Check to see if song name is in the JEN database. If it's not then it splits the
	     * songs name and asks the user if the first section of the split is the correct name.
	     * If not it continues adding parts of the song name until it finds the correct song
	     * name or determines song really doesn't exist.
	     * @param title
	     * @param artist
	     * @return
	     * @throws EchoNestException
	     * @throws IOException
	     */
	    public String verifySongsByTitle(String title, String artist)
	            throws EchoNestException, IOException {
	      
	        Params p = new Params();
	        String titleName = title;
	        String[] splitTitle = title.split(" ");
	        p.add("artist", artist);
	        p.add("title", titleName);
	        
	        if(en.searchSongs(p).size() <= 0){
	          titleName = "";
	            for(int i = 0; i < splitTitle.length; i++){
	            
	                titleName += splitTitle[i];
	                p.set("title", titleName);
	                
	                if(en.searchSongs(p).size() > 0) return titleName;                    
	                               
	                titleName += " ";
	            }
	        }
	        return titleName;
	    }
	}

