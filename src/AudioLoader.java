import java.io.*;
import javax.sound.sampled.*;

/**
 *  @author abaez02.student
 */
public class AudioLoader {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InvalidDataException 
	 * @throws UnsupportedTagException 
	 * 
	 * Decodes a mp3 file and plays it.
	 */
	public AudioLoader()
	{
		
	}
    
	public void beginSongAnalysis() throws UnsupportedAudioFileException, IOException 
	{
		//read mp3 file
		try{
		    File mp3File = new File("/home/student/abaez02/workspace/AudioBike/src/testSongs/Loveline.mp3");
		    AudioInputStream in = AudioSystem.getAudioInputStream(mp3File);
		    AudioInputStream din = null;
		    AudioFormat baseFormat = in.getFormat();
		    AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,baseFormat.getSampleRate(),
				                                    16,baseFormat.getChannels(),baseFormat.getChannels() * 2,
				                                    baseFormat.getSampleRate(),false);
		
	     	din = AudioSystem.getAudioInputStream(decodedFormat,in);
		    //play now
		    rawplay(decodedFormat,din);
		    in.close();
		}catch(Exception e)
		{
			//TODO handle exception	
		}
	}
	
	private void rawplay(AudioFormat targetFormat,AudioInputStream din) throws IOException, LineUnavailableException
	{
		byte[] data = new byte[4096];
		SourceDataLine line = getLine(targetFormat);
		if(line != null){
		    //start
			line.start();
			int nBytesRead = 0, nBytesWritten = 0;
			while(nBytesRead != -1){
				nBytesRead = din.read(data,0,data.length);
				if (nBytesRead != -1) nBytesWritten = line.write(data,0,nBytesRead);
			}
			line.drain();
			line.stop();
			line.close();
			din.close();
		}
	}

	private SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException {
		SourceDataLine res = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		res = (SourceDataLine) AudioSystem.getLine(info);
		res.open(audioFormat);
		return res;
		
	}	
}
