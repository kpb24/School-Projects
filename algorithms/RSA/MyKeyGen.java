//Kerilee Bookleiner
import java.lang.Math;
import java.math.*;
import java.util.*;
import java.io.*;
public class MyKeyGen{
	public static void main(String[] args){
		BigInteger phi, n, p, q, gcd, d;
		boolean checkE = false;
		BigInteger e = new BigInteger("1");
		BigInteger one = new BigInteger("1");
		BigInteger negOne = new BigInteger("-1");
		int bitLength = 1024;
		
		Random randQ = new Random();
		Random randP = new Random();
		
		//get p and q
		p = BigInteger.probablePrime(bitLength, randQ);
		q = BigInteger.probablePrime(bitLength, randP);	
		
		//calculate n
		n = p.multiply(q);
 		
 		//calculate phi
 		p = p.subtract(one);
 		q = q.subtract(one);
 		
 		phi = p.multiply(q);		
 		
		
		// 1 < E < PHI(N) 
		//GCD(E, PHI(N))=1 (E must not divide PHI(N) evenly)
		while(checkE == false){
			Random randE = new Random();
			//calculate e
			e = BigInteger.probablePrime(bitLength, randE);   ///?
		
			int res = e.compareTo(one); // should be 1
			int res2 = e.compareTo(phi); //should be -1
			gcd = e.gcd(phi);
			int gcdCompare = gcd.compareTo(BigInteger.ONE);
		
			if(res == 1 && res2 == -1){
				if(gcdCompare == 0){
	 				checkE = true;
 		
				}
			}
		}
		
		//calculate d
		//D = E^-1 mod PHI(N)
		d = e.modPow(negOne, phi);


		//put into files

		String filePublic = "pubkey.rsa";  //save e and n
		String filePrivate = "privkey.rsa"; //save d and n

		//save to e and n to file, d and n to file
		try{
			PrintWriter fileOutputPublic = new PrintWriter(new FileOutputStream(filePublic, false)); //overwrite the file
			fileOutputPublic.println(e); //save e to file
			fileOutputPublic.println(n); //save n to file
			
			PrintWriter fileOutputPrivate = new PrintWriter(new FileOutputStream(filePrivate, false)); //overwrite the file
			fileOutputPrivate.println(d); //save d to file
			fileOutputPrivate.println(n); //save n to file
		
			fileOutputPrivate.close(); //will be able to read back in file with the user changes made	
			fileOutputPublic.close(); //will be able to read back in file with the user changes made
		}
		catch(Exception f){
			f.printStackTrace();
		}
	}
}