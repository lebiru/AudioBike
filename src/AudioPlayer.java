import java.io.*;
import javax.sound.sampled.*;
import com.mpatric.mp3agic.*;
import javazoom.jl.decoder.*;
/**
 * This class plays the audio creates an array of the audio files waveform and gets its tempo in bpm
 *  @author abaez02.student
 */
public class AudioPlayer {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InvalidDataException 
	 * @throws UnsupportedTagException 
	 * 
	 * Decodes a mp3 file and plays it.
	 */
	public AudioPlayer()
	{
		
	}
    
	public void beginSongAnalysis() throws UnsupportedAudioFileException, IOException 
	{
		//read mp3 file
		try{
			String fileName = "/home/student/abaez02/Desktop/AudioBike/AudioBike/src/testSongs/01 - For What It's Worth (LP Version).mp3";
		    File mp3File = new File(fileName);
		    
		    AudioInputStream in = AudioSystem.getAudioInputStream(mp3File);
		    AudioInputStream din = null;
		    InputStream inputStream = new BufferedInputStream(new FileInputStream(mp3File),8*1024);
		    
		    
		    AudioFormat baseFormat = in.getFormat();
		    AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
		    											baseFormat.getSampleRate(),
				                                        16,
				                                        baseFormat.getChannels(),baseFormat.getChannels() * 2,
				                                        baseFormat.getSampleRate(),false);
		    
		    din = AudioSystem.getAudioInputStream(decodedFormat,in);

	     	//create a array of bytes from the mp3 data
		    byte[] audio = decode(inputStream);
            //printArray(audio);
		    AudioInfo songInfo = new AudioInfo();
		    
		    //get song name and artist name 
		   
		    String artistName = getArtistName(fileName);
		    String songTitle = getSongTitle(fileName);
		    		    
		    System.out.println("Artist: " + artistName + ", Song: " + songTitle);
		    //get the songs tempo in bpm
		    System.out.println(songInfo.getTempo(artistName, "For What It's Worth"));
		    
            //play audio file
		    rawplay(decodedFormat,din);
	        
		    in.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the song name from a specific mp3 file
	 * @param fileName
	 * @return
	 * @throws UnsupportedTagException
	 * @throws InvalidDataException
	 * @throws IOException
	 */
	private String getSongTitle(String fileName) throws UnsupportedTagException, InvalidDataException, IOException {
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
	 * Gets the artists name of a specific mp3 file
	 * @param fileName
	 * @return
	 * @throws IOException 
	 * @throws InvalidDataException 
	 * @throws UnsupportedTagException 
	 */
	private String getArtistName(String fileName) throws UnsupportedTagException, InvalidDataException, IOException {
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
	 * prints out an array of bytes
	 * @param arr
	 */
	public void printArray(byte[] arr) {
		for(int i = 0; i < arr.length; i++){
			System.out.println(arr[i]);
		}
		
	}
    /**
     * gets the waveform data in bytes from the audio inputStream
     * @param inputStream
     * @return
     */
	private byte[] decode(InputStream inputStream) {

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
    
	/**\
	 * Plays the mp3 file.
	 * @param targetFormat
	 * @param din
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	private void rawplay(AudioFormat targetFormat,AudioInputStream din) throws IOException, LineUnavailableException
	{
		byte[] data = new byte[4096];
	
		SourceDataLine line = getLine(targetFormat);

		if(line != null){
		    //start playing audio file
			line.start();
			int nBytesRead = 0, nBytesWritten = 0;
			while(nBytesRead != -1){
				nBytesRead = din.read(data,0,data.length);
				if (nBytesRead != -1){
					nBytesWritten = line.write(data,0,nBytesRead);
				}
			}

			line.drain();
		    line.stop();
			line.close();
			din.close();
		}
	}
    
	/**
	 * Gets each line of data from the audio file.
	 * @param audioFormat
	 * @return
	 * @throws LineUnavailableException
	 */
	private SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException {

		SourceDataLine res = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		res = (SourceDataLine) AudioSystem.getLine(info);
		res.open(audioFormat);
		return res;
		
	}	
}
