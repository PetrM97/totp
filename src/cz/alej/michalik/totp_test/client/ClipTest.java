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
package cz.alej.michalik.totp_test.client;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import cz.alej.michalik.totp.client.Clip;

@RunWith(Parameterized.class)
public class ClipTest {

	@Parameters
	public static List<String> data() {
		return Arrays.asList(new String[] { "Náhodný řetězec znaků", "", null });
	}

	private String str;

	public ClipTest(String string) {
		str = string;
	}

	@Test
	public void string() {
		boolean gui = true;
		try{
			new JFrame("Test");
		}catch (Exception e) {
			gui = false;
		}
		// Test only if GUI is available
		org.junit.Assume.assumeTrue(gui);
		Clip clipboard = new Clip();
		clipboard.set(str);
		assertEquals(str, clipboard.get());
		
	}

}
