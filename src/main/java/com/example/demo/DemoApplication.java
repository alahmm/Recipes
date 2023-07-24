package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;


@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public BytesEncryptor aesBytesEncryptor() {
		String password = "hackme"; // should be kept in a secure place and not be shared
		String salt = "8560b4f4b3"; // should be hex-encoded with even number of chars
		return Encryptors.standard(password, salt);
	}

	@Bean
	public TextEncryptor hexEncodingTextEncryptor() {
		String password = "hackme"; // should be kept in a secure place and not be shared
		//String salt = "8560b4f4b3"; // should be hex-encoded with even number of chars
		//random salt
		String salt = KeyGenerators.string().generateKey();
		return Encryptors.text(password, salt);
	}

	@Override
	public void run(String... args) throws Exception {
/*		BytesEncryptor bytesEncryptor = aesBytesEncryptor();

		byte[] inputData = {104, 121, 112, 101, 114, 115, 107, 105, 108, 108};
		byte[] encryptedData = bytesEncryptor.encrypt(inputData);
		byte[] decryptedData = bytesEncryptor.decrypt(encryptedData);

		System.out.println("Input data: " + new String(inputData));
		System.out.println("Encrypted data: " + new String(encryptedData));
		System.out.println("Decrypted data: " + new String(decryptedData));*/

		//for text:
		TextEncryptor textEncryptor = hexEncodingTextEncryptor();
		String inputData = "hyperskill";
		String encryptedData = textEncryptor.encrypt(inputData);
		String decryptedData = textEncryptor.decrypt(encryptedData);

		System.out.println("Input data: " + inputData);
		System.out.println("Encrypted data: " + encryptedData);
		System.out.println("Decrypted data: " + decryptedData);
	}
}

