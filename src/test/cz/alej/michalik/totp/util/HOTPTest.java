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
package cz.alej.michalik.totp.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class HOTPTest {
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { 0, 755224 }, { 1, 287082 }, { 2, 359152 }, { 3, 969429 }, { 4, 338314 },
				{ 5, 254676 }, { 6, 287922 }, { 7, 162583 }, { 8, 399871 }, { 9, 520489 }, });
	}

	private String secret = "12345678901234567890";
	private int count;
	private int expected;

	public HOTPTest(int count, int expected) {
		this.count = count;
		this.expected = expected;
	}

	@Test
	public void pass() {
		HOTP h = new HOTP(secret.getBytes());
		h.setCounter(count);
		int result = h.get();
		assertEquals(expected, result);
	}
}
