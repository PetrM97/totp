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

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import cz.alej.michalik.totp.utility.TOTP;

@RunWith(Parameterized.class)
public class TOTPTest {
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { 59, 94287082 }, { 1111111109, 7081804 }, { 1111111111, 14050471 },
				{ 1234567890, 89005924 }, { 2000000000 , 69279037  }, /*{ 20000000000 , 65353130  },*/ });
	}

	// { 20000000000 , 65353130  } doesn't work -> need Long 
	
	private String secret = "12345678901234567890";
	private int time;
	private int expected;

	public TOTPTest(int time, int expected) {
		this.time = time;
		this.expected = expected;
	}

	@Test
	public void test() {
		int t0 = (int) (System.currentTimeMillis() / 1000) - this.time;
		assertEquals(expected, new TOTP().setSecret(secret.getBytes()).setShift(t0).setDigits(8).get());
	}
}
