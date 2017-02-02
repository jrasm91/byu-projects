package lab01aes;

import java.util.Arrays;

public class AES {

	private static boolean debug = false;

	public static void main(String[] args) {
//		test128();
//		test192();
		test256();
		//		new AES().test();
	}

	public static void testExample(){
		String inputString = "32 43 f6 a8 88 5a 30 8d 31 31 98 a2 e0 37 07 34";
		String keyString =   "2b 7e 15 16 28 ae d2 a6 ab f7 15 88 09 cf 4f 3c";
		new AES().encode(AES.stringToBytes(inputString), AES.stringToBytes(keyString));
	}

	public static void test128(){
		String inputString =  "00 11 22 33 44 55 66 77 88 99 aa bb cc dd ee ff";
		String keyString  =   "00 01 02 03 04 05 06 07 08 09 0a 0b 0c 0d 0e 0f";
		String inputString2 = "69 c4 e0 d8 6a 7b 04 30 d8 cd b7 80 70 b4 c5 5a";
		//		String keyString2 =   "13 11 1d 7f e3 94 4a 17 f3 07 a7 8b 4d 2b 30 c5";
		AES aes = new AES();
		aes.encode(AES.stringToBytes(inputString), AES.stringToBytes(keyString));
		aes.decode(AES.stringToBytes(inputString2), AES.stringToBytes(keyString));
	}

	public static void test192(){
		String inputString =  "00 11 22 33 44 55 66 77 88 99 aa bb cc dd ee ff";
		String inputString2 = "dd a9 7c a4 86 4c df e0 6e af 70 a0 ec 0d 71 91";
		String keyString =    "00 01 02 03 04 05 06 07 08 09 0a 0b 0c 0d 0e 0f 10 11 12 13 14 15 16 17";	
		AES aes = new AES();
		aes.encode(AES.stringToBytes(inputString), AES.stringToBytes(keyString));
		aes.decode(AES.stringToBytes(inputString2), AES.stringToBytes(keyString));
	}

	public static void test256(){
		String inputString =  "00 11 22 33 44 55 66 77 88 99 aa bb cc dd ee ff";
		String inputString2 = "8e a2 b7 ca 51 67 45 bf ea fc 49 90 4b 49 60 89";
		String keyString = 	  "00 01 02 03 04 05 06 07 08 09 0a 0b 0c 0d 0e 0f 10 11 12 13 14 15 16 17 18 19 1a 1b 1c 1d 1e 1f";
		AES aes = new AES();
		aes.encode(AES.stringToBytes(inputString), AES.stringToBytes(keyString));
		aes.decode(AES.stringToBytes(inputString2), AES.stringToBytes(keyString));
	}

	public void test(){
		int value1 = 0xb3;
		int value2 = 0x00;
		int result = (value1 ^ value2);
		System.out.println(String.format("0x%02x ^ 0x%02x --> 0x%02x", value1, value2, result));

		String inputString =  "00 11 22 33 44 55 66 77 88 99 aa bb cc dd ee ff";
		byte[][] block = makeBlock(AES.stringToBytes(inputString));
		printBlock(block);
		block = ishiftRows(block);
		printBlock(block);

	}

	public void encode(byte[] stateArray, byte[] keyArray){
		byte[][] state = makeBlock(stateArray);
		byte[][] allKeys = expandKeys(keyArray);
		byte[][] key = getKey(0, allKeys);
		int rounds = getRounds(keyArray.length);

		System.out.println("\nEncoding " + keyArray.length * 8);
		System.out.println("round[ 0].input:   " + byteArrayToString(stateArray));
		System.out.println("round[ 0].k_sch:   " + byteArrayToString(keyArray));

		addRoundKey(state, key);

		for(int i = 1; i <= rounds; i++){
			printLine(String.format("round[%2d].start:  ", i), state);
			subBytes(state); 			printLine(String.format("round[%2d].s_box:  ", i), state);
			shiftRows(state);	 		printLine(String.format("round[%2d].s_row:  ", i), state);
			if(i != rounds){
				mixColumns(state);		printLine(String.format("round[%2d].m_col:  ", i), state);
			}
			key = getKey(i, allKeys);
			addRoundKey(state, key);	printLine(String.format("round[%2d].k_sch:  ", i), key);
		}
		String finalState = printLine(String.format("round[%2d].final:  ", rounds), state);
		if(!debug){
			System.out.println(finalState);
		}
	}

	public void decode(byte[] stateArray, byte[] keyArray){
		byte[][] state = makeBlock(stateArray);
		byte[][] allKeys = expandKeys(keyArray);
		int rounds = getRounds(keyArray.length);

		System.out.println("\nDecoding " + keyArray.length * 8);
		System.out.println(String.format("round[%d].iinput:  ", rounds) + byteArrayToString(stateArray));
		System.out.println(String.format("round[%d].ik_sch:  ", rounds) + byteArrayToString(keyArray));

		byte[][] key = getKey(rounds, allKeys);
		addRoundKey(state, key);

		for(int i = rounds - 1; i >= 0; i--){
			printLine(String.format("round[%2d].istart: ", i), state);
			ishiftRows(state);	 		printLine(String.format("round[%2d].is_row: ", i), state);
			isubBytes(state); 			printLine(String.format("round[%2d].is_box: ", i), state);
			key = getKey(i, allKeys);	//printLine(String.format("round[%2d].ik_sch:  ", i), key);
			addRoundKey(state, key);	printLine(String.format("round[%2d].ik_add: ", i), state);
			if(i != 0){
				imixColumns(state);		printLine(String.format("round[%2d].im_col: ", i), state);
			}
		} 
		String finalState = printLine(String.format("round[%2d].ifinal: ", 0), state);
		if(!debug){
			System.out.println(finalState);
		}
	}

	private int getRounds(int keyLen){
		switch(keyLen){
		case 16: return 10;
		case 24: return 12;
		case 32: return 14;
		default: throw new IllegalArgumentException("Not a valid key length: " + keyLen);
		}
	}

	private byte[][] makeBlock(byte[] input){
		byte[][] state = new byte[input.length/4][4];
		for(int i = 0; i < input.length; i++)
			state[i/4][i%4] = input[i];
		return state;
	}

	private byte[][] expandKeys(byte[] key){
		int Nr = getRounds(key.length); // number of rounds
		int Nb = 4; 					// number of rows in each column (words in a block)
		int Nk = key.length / 4; 		// number of columns
		byte[] temp;
		byte[][] w = new byte[Nb * (Nr + 1)][4];

		int i = 0;
		for(;i < Nk; i++){
			byte[] nxt = {key[4*i], key[4*i+1], key[4*i+2], key[4*i+3]};
			w[i] = nxt;
		}
		for(i = Nk; i < Nb * (Nr+1); i++){
			temp = Arrays.copyOf(w[i-1], w[i-1].length);
			if (i % Nk == 0){
				temp = rotWord(temp);
				temp = subWord(temp);
				temp[0] ^= rcon[i/Nk];
			} 
			else if (Nk > 6 && i % Nk ==4)
				temp = subWord(temp);
			for(int j = 0; j < temp.length; j++)
				w[i][j] = (byte) (w[i-Nk][j] ^ temp[j]);
		}
		return w;
	}

	private byte[][] getKey(int round, byte[][] allKeys){
		byte[][] roundKey = new byte[4][4];
		int start = round * 4;
		for(int i = start; i < start + 4; i++)
			for(int j = 0; j < 4; j++)
				roundKey[i - start][j] = allKeys[i][j];
		//		System.out.println(printLine("Round Key: ", roundKey));
		return roundKey;
	}

	private byte[] rotWord(byte[] b){
		byte temp = b[0];
		for(int i = 0; i < 3; i++)
			b[i] = b[i + 1];
		b[3] = temp;
		return b;
	}

	private byte[] subWord(byte[] b){
		for(int i = 0; i < b.length; i++)
			b[i] = Sbox[(b[i] & 0xF0) >> 4][b[i] & 0x0F];
		return b;
	}

	private byte[][] addRoundKey(byte[][] state, byte[][] key){
		for(int i = 0; i < state.length; i++)
			for(int j = 0; j < state[0].length; j++)
				state[i][j] ^= key[i][j];
		return state;
	}

	private byte[][] subBytes(byte[][] state){
		for(int i = 0; i < state.length; i++)
			for(int j = 0; j < state[0].length; j++)
				state[i][j] = Sbox[(state[i][j] & 0xF0) >> 4][state[i][j] & 0x0F];
		return state;
	}

	private byte[][] isubBytes(byte[][] state){
		for(int i = 0; i < state.length; i++)
			for(int j = 0; j < state[0].length; j++)
				state[i][j] = iSbox[(state[i][j] & 0xF0) >> 4][state[i][j] & 0x0F];
		return state;
	}

	private byte[][] shiftRows(byte[][] state){
		byte row2 = state[0][1]; //shift row 2
		for(int i = 0; i < 3; i++)
			state[i][1] = state[i + 1][1];
		state[3][1] = row2;
		byte row3a = state[0][2]; // shift row 3
		byte row3b = state[1][2];
		state[0][2] = state[2][2];
		state[1][2] = state[3][2];
		state[2][2] = row3a;
		state[3][2] = row3b;
		byte row4 = state[3][3]; // shift row 4
		for(int i = 3; i > 0; i--)
			state[i][3] = state[i - 1][3];
		state[0][3] = row4;
		return state;
	}

	private byte[][] ishiftRows(byte[][] state){
		byte row2 = state[3][1]; //shift row 2
		for(int i = 3; i > 0; i--)
			state[i][1] = state[i - 1][1];
		state[0][1] = row2;
		byte row3a = state[0][2]; // shift row 3
		byte row3b = state[1][2];
		state[0][2] = state[2][2];
		state[1][2] = state[3][2];
		state[2][2] = row3a;
		state[3][2] = row3b;
		byte row4 = state[0][3]; // shift row 4
		for(int i = 0; i < 3; i++)
			state[i][3] = state[i + 1][3];
		state[3][3] = row4;
		return state;
	}

	private byte[][] mixColumns(byte[][] state){
		byte[] hex = { 0x00, 0x01, 0x02, 0x03 };
		for(int i = 0; i < 4; i++){
			byte[] col = state[i];
			byte[] newCol = new byte[4];
			newCol[0] = (byte) (ffMult(hex[2], col[0]) ^ ffMult(hex[3], col[1]) ^ ffMult(hex[1], col[2]) ^ ffMult(hex[1], col[3]));
			newCol[1] = (byte) (ffMult(hex[1], col[0]) ^ ffMult(hex[2], col[1]) ^ ffMult(hex[3], col[2]) ^ ffMult(hex[1], col[3]));
			newCol[2] = (byte) (ffMult(hex[1], col[0]) ^ ffMult(hex[1], col[1]) ^ ffMult(hex[2], col[2]) ^ ffMult(hex[3], col[3]));
			newCol[3] = (byte) (ffMult(hex[3], col[0]) ^ ffMult(hex[1], col[1]) ^ ffMult(hex[1], col[2]) ^ ffMult(hex[2], col[3]));
			state[i] =  newCol;
		}
		return state;
	}

	private byte[][] imixColumns(byte[][] state){
		byte[] hex = { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e };
		for(int i = 0; i < 4; i++){
			byte[] col = state[i];
			byte[] newCol = new byte[4];
			newCol[0] = (byte) (ffMult(hex[0x0e], col[0]) ^ ffMult(hex[0x0b], col[1]) ^ ffMult(hex[0x0d], col[2]) ^ ffMult(hex[0x09], col[3]));
			newCol[1] = (byte) (ffMult(hex[0x09], col[0]) ^ ffMult(hex[0x0e], col[1]) ^ ffMult(hex[0x0b], col[2]) ^ ffMult(hex[0x0d], col[3]));
			newCol[2] = (byte) (ffMult(hex[0x0d], col[0]) ^ ffMult(hex[0x09], col[1]) ^ ffMult(hex[0x0e], col[2]) ^ ffMult(hex[0x0b], col[3]));
			newCol[3] = (byte) (ffMult(hex[0x0b], col[0]) ^ ffMult(hex[0x0d], col[1]) ^ ffMult(hex[0x09], col[2]) ^ ffMult(hex[0x0e], col[3]));
			state[i] =  newCol;
		}
		return state;
	}

	private byte ffMult(byte val1, byte val2){
		byte temp = val2;
		byte total = 0x00;
		for(int i = 0; i < 8; i++){
			if(((val1 >> (i))& 0x01) == 0x01)
				total ^= temp;
			temp = (((temp & 0x80) == 0x80)? (byte) ((temp << 1) ^ 0x1B): (byte) (temp << 1));
		}
		return (byte)total;
	}

	public static byte[] stringToBytes(String input){
		String[] parts = input.split(" ");
		byte[] result = new byte[parts.length];
		for(int i = 0; i < parts.length; i++)
			result[i] = (byte)Integer.parseInt(parts[i], 16);
		return result;
	}

	public static String byteArrayToString(byte[] bytes){
		String result = "";
		for(int i = 0; i < bytes.length; i++){
			if(i != 0)
				result += " ";
			result += String.format("%02x", bytes[i]);
		}
		return result;
	}

	public static String printLine(byte[][] bytes){ return printLine("", bytes);}
	public static String printLine(String message, byte[][] bytes){
		String result = message + " ";
		//		String result = message + " \n";
		for(int i = 0; i < bytes.length; i++){
			for(int j = 0; j < bytes[0].length; j++)
				result += String.format("%02x", bytes[i][j]) + " ";
		}
		if(debug)
			System.out.println(result);
		return result;
	}

	public static String printBlock(byte[][] bytes){ return printBlock("", bytes);}
	public static String printBlock(String message, byte[][] bytes){
		String result = message + " \n";
		for(int j = 0; j < bytes[0].length; j++){
			for(int i = 0; i < bytes.length; i++)
				result += String.format("%02x", bytes[i][j]) + " ";
			result += "\n";
		}
		if(debug)
			System.out.println(result);
		return result;
	}

	private byte[] rcon = { (byte) 0x00, // Rcon[] is 1-based, so the first entry is just a place holder 
			(byte) 0x01, (byte) 0x02, (byte) 0x04, (byte) 0x08, 
			(byte) 0x10, (byte) 0x20, (byte) 0x40, (byte) 0x80, 
			(byte) 0x1B, (byte) 0x36, (byte) 0x6C, (byte) 0xD8, 
			(byte) 0xAB, (byte) 0x4D, (byte) 0x9A, (byte) 0x2F, 
			(byte) 0x5E, (byte) 0xBC, (byte) 0x63, (byte) 0xC6, 
			(byte) 0x97, (byte) 0x35, (byte) 0x6A, (byte) 0xD4, 
			(byte) 0xB3, (byte) 0x7D, (byte) 0xFA, (byte) 0xEF, 
			(byte) 0xC5, (byte) 0x91, (byte) 0x39, (byte) 0x72, 
			(byte) 0xE4, (byte) 0xD3, (byte) 0xBD, (byte) 0x61, 
			(byte) 0xC2, (byte) 0x9F, (byte) 0x25, (byte) 0x4A, 
			(byte) 0x94, (byte) 0x33, (byte) 0x66, (byte) 0xCC, 
			(byte) 0x83, (byte) 0x1D, (byte) 0x3A, (byte) 0x74, 
			(byte) 0xE8, (byte) 0xCB, (byte) 0x8D};

	private byte[][] Sbox = {
			{ (byte)  0x63, (byte)  0x7c, (byte)  0x77, (byte)  0x7b, (byte)  0xf2, (byte)  0x6b, (byte)  0x6f, (byte)  0xc5, (byte)  0x30, (byte)  0x01, (byte)  0x67, (byte)  0x2b, (byte)  0xfe, (byte)  0xd7, (byte)  0xab, (byte)  0x76 } ,
			{ (byte)  0xca, (byte)  0x82, (byte)  0xc9, (byte)  0x7d, (byte)  0xfa, (byte)  0x59, (byte)  0x47, (byte)  0xf0, (byte)  0xad, (byte)  0xd4, (byte)  0xa2, (byte)  0xaf, (byte)  0x9c, (byte)  0xa4, (byte)  0x72, (byte)  0xc0 } ,
			{ (byte)  0xb7, (byte)  0xfd, (byte)  0x93, (byte)  0x26, (byte)  0x36, (byte)  0x3f, (byte)  0xf7, (byte)  0xcc, (byte)  0x34, (byte)  0xa5, (byte)  0xe5, (byte)  0xf1, (byte)  0x71, (byte)  0xd8, (byte)  0x31, (byte)  0x15 } ,
			{ (byte)  0x04, (byte)  0xc7, (byte)  0x23, (byte)  0xc3, (byte)  0x18, (byte)  0x96, (byte)  0x05, (byte)  0x9a, (byte)  0x07, (byte)  0x12, (byte)  0x80, (byte)  0xe2, (byte)  0xeb, (byte)  0x27, (byte)  0xb2, (byte)  0x75 } ,
			{ (byte)  0x09, (byte)  0x83, (byte)  0x2c, (byte)  0x1a, (byte)  0x1b, (byte)  0x6e, (byte)  0x5a, (byte)  0xa0, (byte)  0x52, (byte)  0x3b, (byte)  0xd6, (byte)  0xb3, (byte)  0x29, (byte)  0xe3, (byte)  0x2f, (byte)  0x84 } ,
			{ (byte)  0x53, (byte)  0xd1, (byte)  0x00, (byte)  0xed, (byte)  0x20, (byte)  0xfc, (byte)  0xb1, (byte)  0x5b, (byte)  0x6a, (byte)  0xcb, (byte)  0xbe, (byte)  0x39, (byte)  0x4a, (byte)  0x4c, (byte)  0x58, (byte)  0xcf } ,
			{ (byte)  0xd0, (byte)  0xef, (byte)  0xaa, (byte)  0xfb, (byte)  0x43, (byte)  0x4d, (byte)  0x33, (byte)  0x85, (byte)  0x45, (byte)  0xf9, (byte)  0x02, (byte)  0x7f, (byte)  0x50, (byte)  0x3c, (byte)  0x9f, (byte)  0xa8 } ,
			{ (byte)  0x51, (byte)  0xa3, (byte)  0x40, (byte)  0x8f, (byte)  0x92, (byte)  0x9d, (byte)  0x38, (byte)  0xf5, (byte)  0xbc, (byte)  0xb6, (byte)  0xda, (byte)  0x21, (byte)  0x10, (byte)  0xff, (byte)  0xf3, (byte)  0xd2 } ,
			{ (byte)  0xcd, (byte)  0x0c, (byte)  0x13, (byte)  0xec, (byte)  0x5f, (byte)  0x97, (byte)  0x44, (byte)  0x17, (byte)  0xc4, (byte)  0xa7, (byte)  0x7e, (byte)  0x3d, (byte)  0x64, (byte)  0x5d, (byte)  0x19, (byte)  0x73 } ,
			{ (byte)  0x60, (byte)  0x81, (byte)  0x4f, (byte)  0xdc, (byte)  0x22, (byte)  0x2a, (byte)  0x90, (byte)  0x88, (byte)  0x46, (byte)  0xee, (byte)  0xb8, (byte)  0x14, (byte)  0xde, (byte)  0x5e, (byte)  0x0b, (byte)  0xdb } ,
			{ (byte)  0xe0, (byte)  0x32, (byte)  0x3a, (byte)  0x0a, (byte)  0x49, (byte)  0x06, (byte)  0x24, (byte)  0x5c, (byte)  0xc2, (byte)  0xd3, (byte)  0xac, (byte)  0x62, (byte)  0x91, (byte)  0x95, (byte)  0xe4, (byte)  0x79 } ,
			{ (byte)  0xe7, (byte)  0xc8, (byte)  0x37, (byte)  0x6d, (byte)  0x8d, (byte)  0xd5, (byte)  0x4e, (byte)  0xa9, (byte)  0x6c, (byte)  0x56, (byte)  0xf4, (byte)  0xea, (byte)  0x65, (byte)  0x7a, (byte)  0xae, (byte)  0x08 } ,
			{ (byte)  0xba, (byte)  0x78, (byte)  0x25, (byte)  0x2e, (byte)  0x1c, (byte)  0xa6, (byte)  0xb4, (byte)  0xc6, (byte)  0xe8, (byte)  0xdd, (byte)  0x74, (byte)  0x1f, (byte)  0x4b, (byte)  0xbd, (byte)  0x8b, (byte)  0x8a } ,
			{ (byte)  0x70, (byte)  0x3e, (byte)  0xb5, (byte)  0x66, (byte)  0x48, (byte)  0x03, (byte)  0xf6, (byte)  0x0e, (byte)  0x61, (byte)  0x35, (byte)  0x57, (byte)  0xb9, (byte)  0x86, (byte)  0xc1, (byte)  0x1d, (byte)  0x9e } ,
			{ (byte)  0xe1, (byte)  0xf8, (byte)  0x98, (byte)  0x11, (byte)  0x69, (byte)  0xd9, (byte)  0x8e, (byte)  0x94, (byte)  0x9b, (byte)  0x1e, (byte)  0x87, (byte)  0xe9, (byte)  0xce, (byte)  0x55, (byte)  0x28, (byte)  0xdf } ,
			{ (byte)  0x8c, (byte)  0xa1, (byte)  0x89, (byte)  0x0d, (byte)  0xbf, (byte)  0xe6, (byte)  0x42, (byte)  0x68, (byte)  0x41, (byte)  0x99, (byte)  0x2d, (byte)  0x0f, (byte)  0xb0, (byte)  0x54, (byte)  0xbb, (byte)  0x16 }
	}; 

	private byte[][] iSbox = {
			{ (byte) 0x52, (byte) 0x09, (byte) 0x6a, (byte) 0xd5, (byte) 0x30, (byte) 0x36, (byte) 0xa5, (byte) 0x38, (byte) 0xbf, (byte) 0x40, (byte) 0xa3, (byte) 0x9e, (byte) 0x81, (byte) 0xf3, (byte) 0xd7, (byte) 0xfb } ,
			{ (byte) 0x7c, (byte) 0xe3, (byte) 0x39, (byte) 0x82, (byte) 0x9b, (byte) 0x2f, (byte) 0xff, (byte) 0x87, (byte) 0x34, (byte) 0x8e, (byte) 0x43, (byte) 0x44, (byte) 0xc4, (byte) 0xde, (byte) 0xe9, (byte) 0xcb } ,
			{ (byte) 0x54, (byte) 0x7b, (byte) 0x94, (byte) 0x32, (byte) 0xa6, (byte) 0xc2, (byte) 0x23, (byte) 0x3d, (byte) 0xee, (byte) 0x4c, (byte) 0x95, (byte) 0x0b, (byte) 0x42, (byte) 0xfa, (byte) 0xc3, (byte) 0x4e } ,
			{ (byte) 0x08, (byte) 0x2e, (byte) 0xa1, (byte) 0x66, (byte) 0x28, (byte) 0xd9, (byte) 0x24, (byte) 0xb2, (byte) 0x76, (byte) 0x5b, (byte) 0xa2, (byte) 0x49, (byte) 0x6d, (byte) 0x8b, (byte) 0xd1, (byte) 0x25 } ,
			{ (byte) 0x72, (byte) 0xf8, (byte) 0xf6, (byte) 0x64, (byte) 0x86, (byte) 0x68, (byte) 0x98, (byte) 0x16, (byte) 0xd4, (byte) 0xa4, (byte) 0x5c, (byte) 0xcc, (byte) 0x5d, (byte) 0x65, (byte) 0xb6, (byte) 0x92 } ,
			{ (byte) 0x6c, (byte) 0x70, (byte) 0x48, (byte) 0x50, (byte) 0xfd, (byte) 0xed, (byte) 0xb9, (byte) 0xda, (byte) 0x5e, (byte) 0x15, (byte) 0x46, (byte) 0x57, (byte) 0xa7, (byte) 0x8d, (byte) 0x9d, (byte) 0x84 } ,
			{ (byte) 0x90, (byte) 0xd8, (byte) 0xab, (byte) 0x00, (byte) 0x8c, (byte) 0xbc, (byte) 0xd3, (byte) 0x0a, (byte) 0xf7, (byte) 0xe4, (byte) 0x58, (byte) 0x05, (byte) 0xb8, (byte) 0xb3, (byte) 0x45, (byte) 0x06 } ,
			{ (byte) 0xd0, (byte) 0x2c, (byte) 0x1e, (byte) 0x8f, (byte) 0xca, (byte) 0x3f, (byte) 0x0f, (byte) 0x02, (byte) 0xc1, (byte) 0xaf, (byte) 0xbd, (byte) 0x03, (byte) 0x01, (byte) 0x13, (byte) 0x8a, (byte) 0x6b } ,
			{ (byte) 0x3a, (byte) 0x91, (byte) 0x11, (byte) 0x41, (byte) 0x4f, (byte) 0x67, (byte) 0xdc, (byte) 0xea, (byte) 0x97, (byte) 0xf2, (byte) 0xcf, (byte) 0xce, (byte) 0xf0, (byte) 0xb4, (byte) 0xe6, (byte) 0x73 } ,
			{ (byte) 0x96, (byte) 0xac, (byte) 0x74, (byte) 0x22, (byte) 0xe7, (byte) 0xad, (byte) 0x35, (byte) 0x85, (byte) 0xe2, (byte) 0xf9, (byte) 0x37, (byte) 0xe8, (byte) 0x1c, (byte) 0x75, (byte) 0xdf, (byte) 0x6e } ,
			{ (byte) 0x47, (byte) 0xf1, (byte) 0x1a, (byte) 0x71, (byte) 0x1d, (byte) 0x29, (byte) 0xc5, (byte) 0x89, (byte) 0x6f, (byte) 0xb7, (byte) 0x62, (byte) 0x0e, (byte) 0xaa, (byte) 0x18, (byte) 0xbe, (byte) 0x1b } ,
			{ (byte) 0xfc, (byte) 0x56, (byte) 0x3e, (byte) 0x4b, (byte) 0xc6, (byte) 0xd2, (byte) 0x79, (byte) 0x20, (byte) 0x9a, (byte) 0xdb, (byte) 0xc0, (byte) 0xfe, (byte) 0x78, (byte) 0xcd, (byte) 0x5a, (byte) 0xf4 } ,
			{ (byte) 0x1f, (byte) 0xdd, (byte) 0xa8, (byte) 0x33, (byte) 0x88, (byte) 0x07, (byte) 0xc7, (byte) 0x31, (byte) 0xb1, (byte) 0x12, (byte) 0x10, (byte) 0x59, (byte) 0x27, (byte) 0x80, (byte) 0xec, (byte) 0x5f } ,
			{ (byte) 0x60, (byte) 0x51, (byte) 0x7f, (byte) 0xa9, (byte) 0x19, (byte) 0xb5, (byte) 0x4a, (byte) 0x0d, (byte) 0x2d, (byte) 0xe5, (byte) 0x7a, (byte) 0x9f, (byte) 0x93, (byte) 0xc9, (byte) 0x9c, (byte) 0xef } ,
			{ (byte) 0xa0, (byte) 0xe0, (byte) 0x3b, (byte) 0x4d, (byte) 0xae, (byte) 0x2a, (byte) 0xf5, (byte) 0xb0, (byte) 0xc8, (byte) 0xeb, (byte) 0xbb, (byte) 0x3c, (byte) 0x83, (byte) 0x53, (byte) 0x99, (byte) 0x61 } ,
			{ (byte) 0x17, (byte) 0x2b, (byte) 0x04, (byte) 0x7e, (byte) 0xba, (byte) 0x77, (byte) 0xd6, (byte) 0x26, (byte) 0xe1, (byte) 0x69, (byte) 0x14, (byte) 0x63, (byte) 0x55, (byte) 0x21, (byte) 0x0c, (byte) 0x7d }
	};
}
