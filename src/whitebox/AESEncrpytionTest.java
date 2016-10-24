package whitebox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.AESEncryption;
import model.CloudFile;

/**
 * Tests for AESEncryption Class
 * 
 * @author Tim Meier
 * @version 1.0
 * @project cloud
 * @package model.tests
 * @created 21.10.2016
 * @lastUpdate 24.10.2016 / by Tim Meier
 */
public class AESEncrpytionTest {

	private byte[] aesKey;
	private AESEncryption aesEncrpytion;
	private static final String TEST_RESOURCES_PATH = "src/whitebox/resources/";

	public void loadKey() {
		aesKey = new byte[256 / 8];
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File(TEST_RESOURCES_PATH + "AES_KEY.dat"));
			fis.read(aesKey);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copyFile(String actFilePath, String newFilePath) {
		try {
			Files.copy(new File(actFilePath).toPath(), new File(newFilePath).toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Before
	public void SetUp() {
		loadKey();
		aesEncrpytion = new AESEncryption();
	}

	@Test
	public void encryptFile_enrypted_file_exists() {
		// Arrange
		CloudFile cloudFile = new CloudFile("TestTextFile.txt", ".txt",
				new File(TEST_RESOURCES_PATH + "TestTextFile.txt"));

		// Act
		aesEncrpytion.encryptFile(cloudFile, aesKey);
		File encryptedFile = new File(TEST_RESOURCES_PATH + "TestTextFile.txt.aes");

		// Assert
		Assert.assertTrue(encryptedFile.exists() && encryptedFile.isFile());

		// After
		encryptedFile.delete();

	}

	@Test
	public void encryptFile_cloudFileExtension_contains_aes() {
		// Arrange
		CloudFile cloudFile = new CloudFile("TestTextFile.txt", ".txt",
				new File(TEST_RESOURCES_PATH + "TestTextFile.txt"));

		// Act
		aesEncrpytion.encryptFile(cloudFile, aesKey);
		File encryptedFile = new File(TEST_RESOURCES_PATH + "TestTextFile.txt.aes");

		// Assert
		Assert.assertTrue(encryptedFile.getName().equals("TestTextFile.txt.aes"));

		// After
		encryptedFile.delete();
	}

	@Test
	public void decryptFile_decrypted_file_exists() {
		// Arrange
		CloudFile cloudFile = new CloudFile("TestAESFile.txt.aes", ".txt",
				new File(TEST_RESOURCES_PATH + "TestAESFile.txt.aes"));

		// Act
		aesEncrpytion.decryptFile(cloudFile, aesKey);
		File decryptedFile = new File(TEST_RESOURCES_PATH + "TestAESFile.txt");

		// Assert
		Assert.assertTrue(decryptedFile.exists() && decryptedFile.isFile());

		// After
		decryptedFile.delete();
	}

	@Test
	public void decryptFile_cloudFileExtension_contains_txt() {
		// Arrange
		CloudFile cloudFile = new CloudFile("TestAESFile.txt.aes", ".txt",
				new File(TEST_RESOURCES_PATH + "TestAESFile.txt.aes"));

		// Act
		aesEncrpytion.decryptFile(cloudFile, aesKey);
		File decryptedFile = new File(TEST_RESOURCES_PATH + "TestAESFile.txt");

		// Assert
		Assert.assertTrue(decryptedFile.getName().equals("TestAESFile.txt"));

		// After
		decryptedFile.delete();
	}

	@Test
	public void decryptFile_file_content_contains_test() {
		// Arrange
		List<String> fileContent = null;
		CloudFile cloudFile = new CloudFile("TestAESFile.txt.aes", ".txt",
				new File(TEST_RESOURCES_PATH + "TestAESFile.txt.aes"));

		// Act
		aesEncrpytion.decryptFile(cloudFile, aesKey);
		try {
			fileContent = Files.readAllLines(new File(TEST_RESOURCES_PATH + "TestAESFile.txt").toPath(),
					Charset.forName("UTF-8"));
		} catch (IOException e) {
		}

		// Assert
		Assert.assertTrue(fileContent.get(0).equals("test"));

		// After
		new File(TEST_RESOURCES_PATH + "TestAESFile.txt").delete();
	}

	@Test
	public void encryptFolder_all_files_encrypted(){
		
	}
	
	@Test
	public void decryptFolder_all_files_decrypted(){
		
	}
}
