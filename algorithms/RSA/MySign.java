//Kerilee Bookleiner
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.io.*;
import java.lang.Math;
import java.math.*;

public class MySign{
	public static void main(String[] args){
		String flag = args[0]; //signing or verifying
		File privkey = new File("privkey.rsa");
		File pubkey = new File("pubkey.rsa");
		String fileName = args[1];
		String result = "";
		
		if(flag.equals("s")){
			//Generate a SHA-256 hash of the contents of the provided file
			try {
				// read in the file to hash
				Path path = Paths.get(args[1]);
				byte[] data = Files.readAllBytes(path);

				// create class instance to create SHA-256 hash
				MessageDigest md = MessageDigest.getInstance("SHA-256");

				// process the file
				md.update(data);
				// generate a has of the file
				byte[] digest = md.digest();

				// convert the bite string to a printable hex representation
				// note that conversion to biginteger will remove any leading 0s in the bytes of the array!
				result = new BigInteger(1, digest).toString(16);

	
			
				//signing
				/////////////get d and n from privkey
				FileReader fr = new FileReader(privkey);
				BufferedReader br = new BufferedReader(fr);
				BigInteger d = new BigInteger(br.readLine());  //read in d from private
				BigInteger n = new BigInteger(br.readLine());  //read in n from private
				BigInteger resultBig = new BigInteger(result, 16);   //convert hex to biginteger
				BigInteger decrypt = resultBig.modPow(d, n);

				//write to the signed file
				String newFileName = fileName + ".signed"; //write out to signed file
    	    	File infile =new File(fileName);
				File outfile =new File(newFileName); //the signed file
				FileInputStream instream = new FileInputStream(infile);
				FileOutputStream outstream = new FileOutputStream(outfile);
				ObjectOutputStream out =new ObjectOutputStream(outstream);
 
 
 
				out.writeObject(decrypt);
				int length;
				byte[] buffer = new byte[1024];
				while ((length = instream.read(buffer)) > 0){
					outstream.write(buffer, 0, length);
				}

				
				instream.close();
				outstream.close();
 			}
			catch(Exception e) {
				System.out.println(e.toString());
			}
		}
		
//////////////////////////////////////////////////////////////////////////////////////////
		
		else if(flag.equals("v")){
			//read contents of original file
			String file = args[1];
			String origFile = file.replace(".signed", ""); //get the original file name to read 
			
			
			
			//Generate a SHA-256 hash of the contents of the provided file
			try {
				// read in the file to hash
				Path path = Paths.get(origFile);
				byte[] data = Files.readAllBytes(path);

				// create class instance to create SHA-256 hash
				MessageDigest md = MessageDigest.getInstance("SHA-256");

				// process the file
				md.update(data);
				// generate a has of the file
				byte[] digest = md.digest();

				// convert the bite string to a printable hex representation
				// note that conversion to biginteger will remove any leading 0s in the bytes of the array!
				result = new BigInteger(1, digest).toString(16);

				
				//get the decrypted value from file then encrypt it
				FileInputStream fin = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fin);
				BigInteger text = (BigInteger) ois.readObject();
    			
    			FileReader fr2 = new FileReader(pubkey);
				BufferedReader br2 = new BufferedReader(fr2);
				BigInteger e = new BigInteger(br2.readLine());  //read in e
				BigInteger n = new BigInteger(br2.readLine());	//read in n 
    			BigInteger ciphertext = text.modPow(e, n); //encrypt it
    		

    			//need to convert the hash result to BigInteger to compare
    			BigInteger hex = new BigInteger(result, 16);
				int res = ciphertext.compareTo(hex);
				if(res == 0){
					System.out.println("\nThey are equal\n");
				}
				else{
					System.out.println("\nThey are not equal\n");
				}	
			}
			catch(Exception e) {
				System.out.println(e.toString());
			}	
		}	
	}	
}