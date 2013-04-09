import java.io.*;

import com.echonest.api.v4.EchoNestException;
import com.mpatric.mp3agic.*;

import javazoom.jl.decoder.*;

public class AudioLoader {
    String fileName;
	File mp3File;
	InputStream inputStream;
	String artistName;
	String songTitle;
	byte[] audioArr;

	public AudioLoader(String fileName) throws UnsupportedTagException, InvalidDataException, IOException {
		this.fileName = fileName;
		this.mp3File = new File(fileName);
		this.inputStream = new BufferedInputStream(new FileInputStream(mp3File),8*1024);
		
	    //get song name and artist name 
	    this.artistName = getArtistName();
	    this.songTitle = getSongTitle();
	    
	    //create waveform data
	    this.audioArr = decodeAudio();
	}

	public void beginSongAnalysis() throws EchoNestException, UnsupportedTagException, InvalidDataException, IOException {

		try {
	         
		    GetAudioInfo songInfo = new GetAudioInfo();
		    
		    //check to see if the artist name and song name are in the echo nest database
		    artistName = songInfo.verifySongsByArtist(artistName);
		    songTitle = songInfo.verifySongsByTitle(songTitle, artistName);
		    
		    System.out.println("Artist: " + artistName + ", Song: " + songTitle);
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
		
	/**
	 * this class gets the songs tempo (speed) in beats per minute (bpm)
	 * @return
	 * @throws EchoNestException
	 */
	public double findTempo() throws EchoNestException{
		GetAudioInfo songInfo = new GetAudioInfo();
	    return songInfo.getTempo(artistName, songTitle);
		
	}
	
    /**
     * gets the waveform data in bytes from the audio inputStream
     * @param inputStream
     * @return
     */
	private byte[] decodeAudio() {
	    ByteArrayOutputStream os = new ByteArrayOutputStream();
	    
	    try {
	        Bitstream bitstream = new Bitstream(inputStream);
	        Decoder decoder = new Decoder();
	        boolean done = false;
	        while (! done) {
	            Header frameHeader = bitstream.readFrame();
	            if (frameHeader == null){ 
	            	done = true;
	            	break;
	            }
	            SampleBuffer output = (SampleBuffer) decoder.decodeFrame(frameHeader, bitstream);  
	            short[] pcm = output.getBuffer();   

	            for (short s : pcm) {
	                os.write(s & 0xff);
	                os.write((s >> 8 ) & 0xff);
	              }
	            bitstream.closeFrame();
	        } 
	        return os.toByteArray();
	    }catch(Exception e){
	          e.printStackTrace();
	    }
	    return null;   
	}
	
	/**
	 * Gets the artists name of a specific mp3 file
	 * @param fileName
	 * @return
	 * @throws IOException 
	 * @throws InvalidDataException 
	 * @throws UnsupportedTagException 
	 */
	private String getArtistName() throws UnsupportedTagException, InvalidDataException, IOException {
		Mp3File mp3File = new Mp3File(fileName);
		if(mp3File.hasId3v1Tag()){
			ID3v1 id3v1Tag = mp3File.getId3v1Tag();
			return id3v1Tag.getArtist();
		}
		else if(mp3File.hasId3v2Tag()){
			ID3v2 id3v2Tag = mp3File.getId3v2Tag();
			return id3v2Tag.getArtist();
		}
		return null;
	}
	
	/**
	 * Gets the song name from a specific mp3 file
	 * @param fileName
	 * @return
	 * @throws UnsupportedTagException
	 * @throws InvalidDataException
	 * @throws IOException
	 */
	private String getSongTitle() throws UnsupportedTagException, InvalidDataException, IOException {
		Mp3File mp3File = new Mp3File(fileName);
		if(mp3File.hasId3v1Tag()){
			ID3v1 id3v1Tag = mp3File.getId3v1Tag();
			return id3v1Tag.getTitle();
		}
		else if(mp3File.hasId3v2Tag()){
			ID3v2 id3v2Tag = mp3File.getId3v2Tag();
			return id3v2Tag.getTitle();
		}
		return null;
	}
	
	/**
	 * prints out an array of bytes
	 * @param arr
	 */
	public void printArray(byte[] arr) {
		for(int i = 0; i < arr.length; i++){
			System.out.println(arr[i]);
		}	
	}
}
