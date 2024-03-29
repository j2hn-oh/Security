import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;
import java.io.*;

public class CBC_A{

	
	public static void main(String[] args) throws IOException{
		Random rand = new Random();
		
		int[] key = {50, 52, 56, 64};
		CBC cbc = new CBC(key);
		
		int[] img = new int[2];
		
		int IV[] = {rand.nextInt(),rand.nextInt()};
		
		FileInputStream imgIn = new FileInputStream("C:\\Users\\chldl\\OneDrive\\����\\alice.bmp");
		FileOutputStream imgOut = new FileOutputStream("C:\\Users\\chldl\\OneDrive\\����\\alice_CBC.bmp");
		
		DataInputStream dataIn = new DataInputStream(imgIn);
		DataOutputStream dataOut = new DataOutputStream(imgOut);
		
		for(int i=0;i<10;i++){
			if(dataIn.available() > 0){
				img[0] = dataIn.readInt();
				img[1] = dataIn.readInt();
				dataOut.writeInt(img[0]);
				dataOut.writeInt(img[1]);
			}
		}
		
		
		boolean firstTime = true;
		int cipher[] = new int[2];
		boolean check = true;		
		while(dataIn.available() > 0){
			try{
				img[0] = dataIn.readInt();
				check = true;
				img[1] = dataIn.readInt();
				if(firstTime){	
					cipher = cbc.encrypt(img, IV);
					firstTime = false;
				}
				else
					cipher = cbc.encrypt(img, cipher);
				
				dataOut.writeInt(cipher[0]);
				dataOut.writeInt(cipher[1]);
				check = false;
			}catch(EOFException e){	
				if(!check){				
					dataOut.writeInt(img[0]);
					dataOut.writeInt(img[1]);
				}else							
					dataOut.writeInt(img[0]);
			}
			
		}
		dataIn.close();
		dataOut.close();
		
		imgOut.close();
		imgIn.close();

	}
}