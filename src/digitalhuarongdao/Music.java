package digitalhuarongdao;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class Music extends Thread{
	private String filename;

	public Music(String file) {
		filename = file;
	}
	
	public void run() {
		File sound = new File(filename);
		AudioInputStream ais = null;
		
		try {
			ais = AudioSystem.getAudioInputStream(sound);
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		AudioFormat aft = ais.getFormat();
		SourceDataLine sdl = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, aft);
		
		try {
			sdl = (SourceDataLine)AudioSystem.getLine(info);
			sdl.open(aft);
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		sdl.start();
		int nBytesRead = 0;
		byte[] abData = new byte[512];
		
		try {
			while(nBytesRead != -1) {
				nBytesRead = ais.read(abData,0,abData.length);
				if(nBytesRead >= 0) {
					sdl.write(abData,0,abData.length);
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
			return;
		}
		
		finally {
			sdl.drain();
			sdl.close();
		}
	}
}
