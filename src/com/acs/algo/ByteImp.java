package com.acs.algo;

public class ByteImp {

	/**
	 * Copy 6 bits each from input to output[] by appending.
	 * @param input
	 * @return
	 */
	public static byte[] writeBase64(byte[] input) {
		// Allocate space to write, ex. 4 * 6 bits = 24 bits = 3 bytes.
		byte[] output = new byte[(int) Math.ceil(input.length * 6.0 / 8.0)];
		// Read 6 bits from each input bytes and write in output byte or two.
		int copiedBytes = 0;
		int copiedBits = 0;
		for (byte inByte: input) {
			int inInt = inByte;
			// Read 6 bits and keep placing in current byte or next based upon copiedBits.
			for (int i = 0; i < 6; i++) {
				boolean bitSet = (inInt & 1) > 0 ? true : false;
				if (copiedBits == 8) {
					copiedBits = 0;
					copiedBytes++;
				}
				output[copiedBytes] = copyBit(bitSet, output[copiedBytes], copiedBits);
				copiedBits++;
				inInt = inInt >> 1;
			}
		}
		return output;
	}
	
	/**
	 * Set bit at the location specified.
	 * @param bitSet
	 * @param input
	 * @param bitLoc
	 * @return
	 */
	public static byte copyBit(boolean bitSet, byte input, int bitLoc) {
		return (byte) (bitSet ? (input | 1 << bitLoc) : (input & (~0 ^ 1 << bitLoc)));
	}
	
	public static void main(String[] args) {
		System.out.println("Test copyBit function ");
		// 2nd bit when set on 0x2 should give 6.
		assert copyBit(true, (byte) 0x2, 2) == 6;
		// 1st bit when unset on 0x3 should give 1.
		assert copyBit(false, (byte) 0x3, 1) == 1;
		System.out.println("Test writeBase64 function ");
		byte[] input = new byte[2];
		// All ones
		input[0] = (byte) 0xff;
		// Confirm all 8 bits are set in input.
		for (int i=0; i<8; i++) {
			assert (input[0] & (1 << i)) > 0;
		}
		// All zeroes
		input[1] = (byte) 0x0;
		// Read 6 bits each from input[] to output.
		byte[] output = writeBase64(input);
		// Confirm initial 6 bits are 1s and then all 0s
		for (int i=0; i<6; i++) {
			assert (output[0] & (1 << i)) > 0;
		}
		for (int i=6; i<8; i++) {
			assert (output[0] & (1 << i)) == 0;
		}
		assert (output[1]) == 0;
	}

}
