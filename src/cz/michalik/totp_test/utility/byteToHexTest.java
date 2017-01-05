package cz.michalik.totp_test.utility;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.michalik.totp.utility.byteToHex;

public class byteToHexTest {

	byte[] bytes = new byte[] 
			{ (byte) 0xDE, 0x7C, (byte) 0x9B, (byte) 0x85, (byte)0xB8, (byte)0xB7, 
					(byte)0x8A, (byte)0xA6, (byte)0xBC, (byte)0x8A, 0x7A, 0x36, 
					(byte)0xF7, 0x0A, (byte)0x90, 0x70, 0x1C,
					(byte)0x9D, (byte)0xB4, (byte)0xD9 
			};
	@Test
	public void testString() {		
		String expected = "DE 7C 9B 85 B8 B7 8A A6 BC 8A 7A 36 F7 0A 90 70 1C 9D B4 D9";
		assertEquals(expected, new byteToHex(bytes).getString());		
	}
	
	@Test
	public void testArray() {		
		String[] expected = new String[]
				{"DE", "7C", "9B", "85", "B8", "B7", "8A", "A6", "BC", "8A", 
						"7A", "36", "F7", "0A", "90", "70", "1C", "9D", "B4", "D9"
				};
		assertArrayEquals(expected, new byteToHex(bytes).getArray());		
	}

}
