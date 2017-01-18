/*
   Copyright 2017 Petr Michalík

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package cz.alej.michalik.totp_test.utility;

import static org.junit.Assert.*;
import org.junit.Test;

import cz.alej.michalik.totp.utility.byteToHex;

public class byteToHexTest {

	byte[] bytes = new byte[] { (byte) 0xDE, 0x7C, (byte) 0x9B, (byte) 0x85, (byte) 0xB8, (byte) 0xB7, (byte) 0x8A,
			(byte) 0xA6, (byte) 0xBC, (byte) 0x8A, 0x7A, 0x36, (byte) 0xF7, 0x0A, (byte) 0x90, 0x70, 0x1C, (byte) 0x9D,
			(byte) 0xB4, (byte) 0xD9 };

	@Test
	public void testString() {
		String expected = "DE 7C 9B 85 B8 B7 8A A6 BC 8A 7A 36 F7 0A 90 70 1C 9D B4 D9";
		assertEquals(expected, new byteToHex(bytes).toString());
	}

	@Test
	public void testArray() {
		String[] expected = new String[] { "DE", "7C", "9B", "85", "B8", "B7", "8A", "A6", "BC", "8A", "7A", "36", "F7",
				"0A", "90", "70", "1C", "9D", "B4", "D9" };
		assertArrayEquals(expected, new byteToHex(bytes).toArray());
	}
	
	@Test
	public void empty() {
		try{
			String s = new byteToHex(new byte[0]).toString();
		}catch (Exception e) {
			fail("Exception given");
			e.printStackTrace();
		}
	}

}
