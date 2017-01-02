package cz.michalik.totp_test.utility;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.michalik.totp.utility.HMAC;

public class HMACTest {
	
	@Test
	public void testString() {	
		byte[] expected = new byte[] 
				{ (byte) 0xDE, 0x7C, (byte) 0x9B, (byte) 0x85, (byte)0xB8, (byte)0xB7, 
						(byte)0x8A, (byte)0xA6, (byte)0xBC, (byte)0x8A, 0x7A, 0x36, 
						(byte)0xF7, 0x0A, (byte)0x90, 0x70, 0x1C,
						(byte)0x9D, (byte)0xB4, (byte)0xD9 
				};
		String key = "key";
		String msg = "The quick brown fox jumps over the lazy dog";
		assertArrayEquals(expected, new HMAC().setKey(key).setMessage(msg).get());		
	}

}
