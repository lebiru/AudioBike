import java.io.*;
import javax.sound.sampled.*;
import com.mpatric.mp3agic.*;

/**
 * This class plays the audio creates an array of the audio files waveform and gets its tempo in bpm
 *  @author abaez02.student
 */
public class AudioPlayer {

	String fileName;

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InvalidDataException 
	 * @throws UnsupportedTagException 
	 * 
	 * Decodes a mp3 file and plays it.
	 */
	public AudioPlayer(String fileName)
	{
		this.fileName = fileName;
	}
    
	/**
	 * Plays an audio file by reading it line by line.
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	public void playSong() throws UnsupportedAudioFileException, IOException 
	{
		//read mp3 file
		try{
		    File mp3File = new File(fileName);
		    
		    AudioInputStream in = AudioSystem.getAudioInputStream(mp3File);
		    AudioInputStream din = null;		    		  		    
		    AudioFormat baseFormat = in.getFormat();
		    
		    AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
		    											baseFormat.getSampleRate(),
				                                        16,
				                                        baseFormat.getChannels(),baseFormat.getChannels() * 2,
				                                        baseFormat.getSampleRate(),false);
		    
		    din = AudioSystem.getAudioInputStream(decodedFormat,in);
	
            //play audio file
		    rawplay(decodedFormat,din);	        
		    in.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	    
	/**
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
			@SuppressWarnings("unused")
			int nBytesRead = 0, nBytesWritten = 0;
			while(nBytesRead != -1){
				if(Main.stop == true) break;
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
