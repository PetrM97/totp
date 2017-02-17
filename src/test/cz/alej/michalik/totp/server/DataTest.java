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
package cz.alej.michalik.totp.server;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DataTest {

	private static final String USERNAME = "notexistinguser";

	@Test
	public void addTest() {
		Data.add(USERNAME, "", false);
		assertEquals(true, Data.exists(USERNAME));
	}

	@Test
	public void removeTest() {
		Data.remove(USERNAME);
		assertEquals(false, Data.exists(USERNAME));
	}

	@Test
	public void existsTest() {
		Data.remove(USERNAME);
		assertEquals(false, Data.exists(USERNAME));
		Data.add(USERNAME, "", false);
		assertEquals(true, Data.exists(USERNAME));
	}

	@Test
	public void saveTest() {
		Data.remove(USERNAME);
		Data.add(USERNAME, "", false);
		Data.save();
		Data.load();
		assertEquals(true, Data.exists(USERNAME));
	}

	@Test
	public void getTest() {
		String data = "randomString";
		Data.remove(USERNAME);
		Data.add(USERNAME, data, false);
		assertEquals(data, Data.get(USERNAME));
	}
}
