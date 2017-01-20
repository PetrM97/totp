package cz.alej.michalik.totp_test.client;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.alej.michalik.totp.client.Clip;

public class ClipTest {

	@Test
	public void random_string() {
		String str = "Náhodný řetězec znaků";
		Clip clipboard = new Clip();
		clipboard.set(str);
		assertEquals(str, clipboard.get());
	}

	@Test
	public void empty_string() {
		String str = "";
		Clip clipboard = new Clip();
		clipboard.set(str);
		assertEquals(str, clipboard.get());
	}

	@Test
	public void null_string() {
		String str = null;
		Clip clipboard = new Clip();
		clipboard.set(str);
		assertEquals(str, clipboard.get());
	}
}
