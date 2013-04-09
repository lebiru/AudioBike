import com.echonest.api.v4.*;


import java.awt.Component;
import java.io.*;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GetAudioInfo {
    private EchoNestAPI en;
    private static final String API_KEY = "5PLBCMHXPWYC1NBD4"; 
    
    
    
   
    public GetAudioInfo() throws EchoNestException{
    	en = new EchoNestAPI(API_KEY);
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
    
    public String verifySongsByArtist(String artist)
            throws EchoNestException, IOException {
        Params p = new Params();
        p.add("artist", artist);

        return artist;
        
    }
    
    public String verifySongsByTitle(String title, String artist)
            throws EchoNestException, IOException {
    	
    	JOptionPane optionPane = new JOptionPane();
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
                
                if(en.searchSongs(p).size() > 0){
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    String input = "";
                    System.out.println("Is (" + titleName + ") the song you want (y/n)?");
                    input = br.readLine();
                    
                    if(input.contains("y")) return titleName;                    
                }               
                titleName += " ";
            }
        }
        return titleName;
    }
}
