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

import exception.EncryptionFileNotFoundException;
import exception.EncryptionInvalidKeyException;
import exception.StreamCopyException;
import model.AESEncryption;

/**
 * Tests for AESEncryption Class
 * 
 * @author Tim Meier
 * @version 2.0
 * @project cloud
 * @package whitebox
 * @created 21.10.2016
 * @lastUpdate 25.10.2016 / by Tim Meier
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
		} catch (IOException e) {
		}
	}

	public void copyFile(String actFilePath, String newFilePath) {
		try {
			Files.copy(new File(actFilePath).toPath(), new File(newFilePath).toPath());
		} catch (IOException e) {
		}
	}

	@Before
	public void SetUp() {
		loadKey();
		aesEncrpytion = new AESEncryption();
	}

	@Test
	public void encryptFile_enrypted_file_exists() throws EncryptionInvalidKeyException, EncryptionFileNotFoundException, StreamCopyException {
		// Arrange
		File file = new File(TEST_RESOURCES_PATH + "TestTextFile.txt");

		// Act
		aesEncrpytion.encryptFile(file, aesKey);
		File encryptedFile = new File(TEST_RESOURCES_PATH + "TestTextFile.txt.aes");

		// Assert
		Assert.assertTrue(encryptedFile.exists() && encryptedFile.isFile());

		// After
		encryptedFile.delete();

	}

	@Test
	public void encryptFile_cloudFileExtension_contains_aes() throws EncryptionInvalidKeyException, EncryptionFileNotFoundException, StreamCopyException {
		// Arrange
		File file = new File(TEST_RESOURCES_PATH + "TestTextFile.txt");

		// Act
		aesEncrpytion.encryptFile(file, aesKey);
		File encryptedFile = new File(TEST_RESOURCES_PATH + "TestTextFile.txt.aes");

		// Assert
		Assert.assertTrue(encryptedFile.getName().equals("TestTextFile.txt.aes"));

		// After
		encryptedFile.delete();
	}

	@Test
	public void decryptFile_decrypted_file_exists() throws EncryptionInvalidKeyException, EncryptionFileNotFoundException, StreamCopyException {
		// Arrange
		File file = new File(TEST_RESOURCES_PATH + "TestAESFile.txt.aes");

		// Act
		aesEncrpytion.decryptFile(file, aesKey, TEST_RESOURCES_PATH);
		File decryptedFile = new File(TEST_RESOURCES_PATH + "TestAESFile.txt");

		// Assert
		Assert.assertTrue(decryptedFile.exists() && decryptedFile.isFile());

		// After
		decryptedFile.delete();
	}

	@Test
	public void decryptFile_cloudFileExtension_contains_txt() throws EncryptionInvalidKeyException, EncryptionFileNotFoundException, StreamCopyException {
		// Arrange
		File file = new File(TEST_RESOURCES_PATH + "TestAESFile.txt.aes");

		// Act
		aesEncrpytion.decryptFile(file, aesKey, TEST_RESOURCES_PATH);
		File decryptedFile = new File(TEST_RESOURCES_PATH + "TestAESFile.txt");

		// Assert
		Assert.assertTrue(decryptedFile.getName().equals("TestAESFile.txt"));

		// After
		decryptedFile.delete();
	}

	@Test
	public void decryptFile_file_content_contains_test() throws EncryptionInvalidKeyException, EncryptionFileNotFoundException, StreamCopyException {
		// Arrange
		List<String> fileContent = null;
		File file = new File(TEST_RESOURCES_PATH + "TestAESFile.txt.aes");

		// Act
		aesEncrpytion.decryptFile(file, aesKey, TEST_RESOURCES_PATH);
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
	public void encryptFile_and_decryptFile_decrypted_file_content_contains_test() throws EncryptionInvalidKeyException, EncryptionFileNotFoundException, StreamCopyException{
		// Arrange
		List<String> fileContent = null;
		File file = new File(TEST_RESOURCES_PATH + "TestTextFile.txt");
		File decryptedFile = new File(TEST_RESOURCES_PATH + "TestTextFile.txt.aes");

		// Act
		aesEncrpytion.encryptFile(file, aesKey);
		aesEncrpytion.decryptFile(decryptedFile, aesKey, TEST_RESOURCES_PATH);
		
		try {
			fileContent = Files.readAllLines(new File(TEST_RESOURCES_PATH + "TestTextFile.txt").toPath(),
					Charset.forName("UTF-8"));
		} catch (IOException e) {
		}

		// Assert
		Assert.assertTrue(fileContent.get(0).equals("test"));
	}
}
