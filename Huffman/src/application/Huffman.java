package application;
/*
 * Huffman class store the value of huffman code for each byte which have cretin frequency*/

public class Huffman {
	int intCount;
	byte byteCount;
	String Huffman;
	
	
	public Huffman() {}
	
	public Huffman(int intCount, String Huffman, byte byteCount) {
		this.intCount = intCount;
		this.byteCount = byteCount ;
		this.Huffman = Huffman;
	}
	
	public Huffman(int intCount, String Huffman) {
		this.intCount = intCount;
		this.Huffman = Huffman;
	}
	

}