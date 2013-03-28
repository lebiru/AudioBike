import java.io.*;
import javax.sound.sampled.*;
import javazoom.jl.*;
import javazoom.jl.decoder.*;
/**
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
		    File mp3File = new File("/home/student/abaez02/Desktop/AudioBike/AudioBike/src/testSongs/Robot Death Kites - Fight Or Flight.mp3");
		    
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

	     	//play now
		    byte[] audio = decode(inputStream);
            
		    rawplay(decodedFormat,din);
	
		    in.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
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

	private SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException {

		SourceDataLine res = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		res = (SourceDataLine) AudioSystem.getLine(info);
		res.open(audioFormat);
		return res;
		
	}	
}
