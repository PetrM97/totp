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
package cz.alej.michalik.totp.client;

import java.util.Scanner;

import cz.alej.michalik.totp.util.OTPFactory;

/**
 * Třída pro generování jednorázových kódů v prostředí příkazové řádky
 * 
 * @author Petr Michalík
 *
 */
public class CLI {

	private static String otp = "TOTP";

	/**
	 * Vrátí jednorázový kód
	 * 
	 * @param args
	 *            sdílená hesla oddělená novým řádkem
	 */
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		while (in.hasNext()) {
			String secret = in.nextLine();
			if (secret.equals("")) {
				continue;
			}
			String pass = new OTPFactory().getOTP(otp, secret.getBytes()).toString();
			System.out.println(pass);
		}

		in.close();

	}

}
