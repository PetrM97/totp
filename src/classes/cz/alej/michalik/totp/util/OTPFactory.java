package cz.alej.michalik.totp.util;

public class OTPFactory {

	// Výchozí hodnoty
	int digits = 6;
	String alg = "SHA-1";

	public OTP getOTP(String otpType, byte[] secret) {
		if (otpType.equalsIgnoreCase("TOTP")) {
			return new TOTP().setSecret(secret).setDigits(digits).setAlgorithm(alg);
		} else if (otpType.equalsIgnoreCase("HOTP")) {
			return new HOTP().setSecret(secret).setDigits(digits).setAlgorithm(alg);
		}
		return null;
	}

	public OTPFactory setDigits(int d) {
		this.digits = d;
		return this;
	}

	public OTPFactory setAlgorithm(String algorithm) {
		this.alg = algorithm;
		return this;
	}

}
