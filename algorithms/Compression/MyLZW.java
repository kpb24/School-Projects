/*************************************************************************
 *  Compilation:  javac MyLZW.java
 *  Execution:    java MyLZW - < input.txt   (compress)
 *  Execution:    java MyLZW + < input.txt   (expand)
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *  WARNING: STARTING WITH ORACLE JAVA 6, UPDATE 7 the SUBSTRING
 *  METHOD TAKES TIME AND SPACE LINEAR IN THE SIZE OF THE EXTRACTED
 *  SUBSTRING (INSTEAD OF CONSTANT SPACE AND TIME AS IN EARLIER
 *  IMPLEMENTATIONS).
 *
 *  See <a href = "http://java-performance.info/changes-to-string-java-1-7-0_06/">this article</a>
 *  for more details.
 *
 *************************************************************************/
 //Kerilee Bookleiner MW morning
public class MyLZW {
    private static final int R = 256;        // number of input chars
    private static int L = 512;       // number of codewords = 2^9
    private static int W = 9;         // codeword width...set to the minimum length to begin with
	private static final int maxWidth = 16; //max number of bits
	private static float originalSize = 0;
	private static float compressedSize = 0;
	private static float oldRatio = 0;
	private static float newRatio = 0;
	private static boolean ratio = true; //if we should set old ratio to new
	
    public static void compress(String type) { 
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;  // R is codeword for EOF
		
		BinaryStdOut.write(type, 8); //put the mode used into the file so can tell when expands
		
		//do nothing mode
        while (input.length() > 0) {
            String s = st.longestPrefixOf(input);  // Find max prefix match s.
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
            int t = s.length();
			if(type.equals("m")){
				originalSize = originalSize + (s.length() * 8);
				compressedSize = compressedSize + W;
				newRatio = originalSize / compressedSize;
			}
			
			
			//variable length codewords
            if (t < input.length() && code < L){   // Add s to symbol table.
				if(code == L - 1 && W < maxWidth){
					W++;
					L = (int)Math.pow(2, W); //set number of codewords to 2^W
				}
				
				//reset mode
				if(type.equals("r")){
					if(code == L-1 && W == maxWidth){ //indicates full codebook so we need to reset
							W = 9; //reset width back to 9
							L = 512; //reset number of codewords back to 2^9
							st = new TST<Integer>(); //new trie created since starting over
							for (int i = 0; i < R; i++){
								st.put("" + (char)i, i);
								code = R+1;
							}
						}
				}
                st.put(input.substring(0, t + 1), code++);
			}
			
			//monitor mode
			if(type.equals("m")){
				if(code == L){ //if it's equal to max number of codewords possible for 16
					if(ratio == true){
						oldRatio = newRatio;
						ratio = false;
					}
					if(oldRatio / newRatio > 1.1){ //reset when ratio exceeds 1.1
						if(newRatio != 0){
							st = new TST<Integer>(); //new trie
							for (int i = 0; i < R; i++)
								st.put("" + (char) i, i);
							code = R+1;  // R is codeword for EOF
							W = 9; //reset width back to 9
							L = 512; //reset number of codeword to 2^9
							oldRatio = 0; //reset ratios
							newRatio = 0;
							ratio = true; 
						}
					}
				}
			}
			input = input.substring(t);            // Scan past s in input.
		}
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    } 


    public static void expand() {
		//determine what mode was used
		char c = BinaryStdIn.readChar(8);
        String[] st = new String[(int)Math.pow(2, maxWidth)];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        //(unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        if (codeword == R) return;           //expanded message is empty string
        String val = st[codeword];
		
		if(c == 'm'){
			compressedSize = compressedSize + W; //increase compressed size
		}
		
		while (true) {
			BinaryStdOut.write(val);
			codeword = BinaryStdIn.readInt(W);
			
			if(c == 'm'){
				originalSize = originalSize + val.length() * 8;
				compressedSize = compressedSize + W;
				newRatio = originalSize / compressedSize;  //used to calculate ratio of ratios
			}
			
			if (codeword == R) break;
			String s = st[codeword];
			if (i == codeword) s = val + val.charAt(0);   // special case hack
			if (i < L) {
				st[i++] = val + s.charAt(0);
				if(i == L -1 && W < maxWidth){
					W++;
					L = (int)Math.pow(2, W); //set number of codewords to 2^W
				}
				
				//reset mode
				if(c == 'r'){
					if(i == L-1 && W == maxWidth){
							W = 9; //go back to minimum width
							L = 512; //go back to 2^9
							st = new String[(int)Math.pow(2, maxWidth)]; //new string array
							for (i = 0; i < R; i++){
								st[i] = "" + (char) i;
								st[i++] = ""; 
							}
						}
				}
				
				//monitor mode
				if(c == 'm'){
					if(i == L){ //2^16 is the max number
					if(ratio == true){
						oldRatio = newRatio;
						ratio = false; //old ratio has been set
					}
						if(oldRatio/newRatio > 1.1){
							W = 9;
							L = 512;
							st = new String[(int)Math.pow(2, maxWidth)]; //new string array
							for (i = 0; i < R; i++)
								st[i] = "" + (char) i;
							st[i++] = "";
							codeword = BinaryStdIn.readInt(W);
							if(codeword == R){ //end of file
								return;
							}
							oldRatio = 0;
							newRatio = 0;
							ratio = true; //get "new" old ratio next run through while loop
						}
					}
				}
			}
			val = s;
		}
	BinaryStdOut.close();
	}
    public static void main(String[] args) {
		String type = ".";
        if(args[0].equals("-")){
			if(args[1].equals("n")){
				type = "n";
				compress(type);
			}
			else if(args[1].equals("r")){
				type = "r";
				compress(type);
			}
			else if(args[1].equals("m")){
				type = "m";
				compress(type);
			}
		}
        else if(args[0].equals("+")){
			expand();
		}
        else{
			throw new IllegalArgumentException("Illegal command line argument");
		}
    }
}
