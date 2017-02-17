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
import static org.junit.Assert.fail;

import org.junit.Test;

public class ServeTest {

	@Test
	public void failInitTest() {
		try {
			Serve.main(null);
			fail("Exception not given");
		} catch (Exception e) {
			return;
		}
	}

	@Test
	public void initTest() {
		try {
			String[] args = new String[0];
			Serve.main(args);
		} catch (Exception e) {
			fail("Exception given");
		}
	}

	@Test
	public void portTest() {
		try {
			int port = 9999;
			String[] args = { String.valueOf(port) };
			Serve.main(args);
			assertEquals(port, Serve.getPort());
		} catch (Exception e) {
			fail("Exception given");
		}
	}
}
