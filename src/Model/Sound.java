package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	
	public Sound(String nameFile) throws FileNotFoundException {
		AudioInputStream audioInputStream = null;
		
		try{
		      audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/Son/"+nameFile+".wav"));

		    } catch (UnsupportedAudioFileException e1) {
		        	e1.printStackTrace();
		          return;
		    } catch (IOException e) {
		            e.printStackTrace();
		            return;
		    }
		
		 AudioFormat audioFormat = audioInputStream.getFormat();
		 

	    DataLine.Info info = new DataLine.Info(SourceDataLine.class,
	                audioFormat);
	  
	    SourceDataLine line;
	    try {
	    line = (SourceDataLine) AudioSystem.getLine(info);
	              
	    } catch (LineUnavailableException e2) {
	      e2.printStackTrace();
	      return;
	    }
	    
	    try {
	    	line.open(audioFormat);
	    } catch (LineUnavailableException e3) {
	    		e3.printStackTrace();
	    		return;
	    }
	    
	    line.start();
	    
	    try {
	    	byte bytes[] = new byte[2048];
	    	int bytesRead=0;
	    	while (((bytesRead = audioInputStream.read(bytes, 0, bytes.length)) != -1)) {
	    		line.write(bytes, 0, bytesRead);
	    	}
	    } catch (IOException io) {
	    	io.printStackTrace();
	    	return;
	    }
	    
	    line.close();
	}	
}
