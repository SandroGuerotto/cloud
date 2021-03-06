package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import exception.EncryptionInvalidKeyException;
import exception.ExceptionType;
import exception.StreamCopyException;
import exception.EncryptionFileNotFoundException;

/**
 * Class for encrypting/decrypting files with AES.
 * @author Tim Meier
 * @version 6.0
 * @project cloud
 * @package model
 * @created 13.10.2016
 * @lastUpdate 04.11.2016 / by Sandro Guerotto
 */
public class AESEncryption {
	
	private static final int AES_KEY_SIZE = 256;
	private static final String ENCRYPTION_INSTANCE = "AES";

	Cipher aesCipher;
	byte[] aesKey;
	SecretKeySpec aeskeySpec;
	
	/**
	 * Constructor: creates cipher
	 */
	public AESEncryption() {
	    try {
			aesCipher = Cipher.getInstance(ENCRYPTION_INSTANCE);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}//-Standard Constructor

	/**
	 * Encrypts and then copies the contents of a given file.
	 * @param file File the encrypted File
	 * @param key byte[]  the aes 256 bit key
	 * @throws EncryptionInvalidKeyException 
	 * @throws EncryptionFileNotFoundException 
	 * @throws StreamCopyException 
	 */
	public void encryptFile(File file, byte[] key) throws EncryptionInvalidKeyException, EncryptionFileNotFoundException, StreamCopyException {
		try {
			SecretKey aesKey = new SecretKeySpec(key, 0, AES_KEY_SIZE/8, ENCRYPTION_INSTANCE);
			aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
			FileInputStream fis = new FileInputStream(file);
			CipherOutputStream cos = new CipherOutputStream(new FileOutputStream(
					new File(file.getAbsolutePath() + ".aes")), aesCipher);
			copyStreams(fis, cos);
			fis.close();
			cos.close();
		} catch (InvalidKeyException e) {
			throw new EncryptionInvalidKeyException(ExceptionType.ERROR);
		} catch (FileNotFoundException e) {
			throw new EncryptionFileNotFoundException(ExceptionType.ERROR);
		} catch (IOException e) {
			throw new StreamCopyException(ExceptionType.ERROR);
		}
	}//-encryptFile method

	/**
	 * Decrypts and then copies the content of a given file.
	 * @param file File cloudFile the encrypted File
	 * @param key  byte[] key the aes 256 bit key
	 * @param fileDestinationPath String the directory path of the new decrypted file
	 * @throws EncryptionInvalidKeyException
	 * @throws EncryptionFileNotFoundException
	 * @throws StreamCopyException
     */
	public void decryptFile(File file, byte[] key,String fileDestinationPath) throws EncryptionInvalidKeyException, EncryptionFileNotFoundException, StreamCopyException {
		if (!file.getName().contains(".aes")) return;
		if (new File(fileDestinationPath).isDirectory() && new File(fileDestinationPath).exists()) {
			try {
				SecretKey aesKey = new SecretKeySpec(key, 0, AES_KEY_SIZE / 8, ENCRYPTION_INSTANCE);
				aesCipher.init(Cipher.DECRYPT_MODE, aesKey);
				CipherInputStream cis = new CipherInputStream(new FileInputStream(file), aesCipher);
				FileOutputStream fos = new FileOutputStream(
						new File(fileDestinationPath + file.getName().replace(".aes", "")));
				copyStreams(cis, fos);
				cis.close();
				fos.close();
			} catch (InvalidKeyException e) {
				throw new EncryptionInvalidKeyException(ExceptionType.ERROR);
			} catch (FileNotFoundException e) {
				throw new EncryptionFileNotFoundException(ExceptionType.ERROR);
			} catch (IOException e) {
				throw new StreamCopyException(ExceptionType.ERROR);
			}
		}else{
			throw new EncryptionFileNotFoundException(ExceptionType.ERROR);
		}
	}//-decryptFile method

	/**
	 * Copies a stream, so that the file content will be copied.
	 * @param inputStream InputStream
	 * @param outputStream OutputStream
	 * @throws StreamCopyException 
	 */
	private void copyStreams(InputStream inputStream, OutputStream outputStream) throws StreamCopyException {
		int i;
		byte[] b = new byte[1024];
		try {
			while((i=inputStream.read(b))!=-1) {
				outputStream.write(b, 0, i);
			}
		} catch (IOException e) {
			throw new StreamCopyException(ExceptionType.ERROR);
		}
	}//-copyStreams method
	
	/**
	 * Creates a new AES 265 bit key and saves it to a directory.
	 * @param keyDirectoryPath String the directory path for the new aes 256 bit key
	 */
	public void createNewKey(String keyDirectoryPath) {
		KeyGenerator kgen;
		FileOutputStream fos;
		try {
			kgen = KeyGenerator.getInstance(ENCRYPTION_INSTANCE);
			kgen.init(AES_KEY_SIZE);
			SecretKey key = kgen.generateKey();
			aesKey = key.getEncoded();
			aeskeySpec = new SecretKeySpec(aesKey, ENCRYPTION_INSTANCE);
			fos = new FileOutputStream(new File(keyDirectoryPath + "/AES_KEY.dat"));
			fos.write(aesKey);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (NoSuchAlgorithmException e1) {
		}
	}//-createKey method

	/**
	 * Loads a AES key from a file.
	 * @param keyFilePath
	 * @return 
	 */
	public byte[] loadKey(String keyFilePath){
		aesKey = new byte[AES_KEY_SIZE/8];
	    FileInputStream fis;
		try {
			fis = new FileInputStream(new File(keyFilePath));
			fis.read(aesKey);
//			aeskeySpec = new SecretKeySpec(aesKey, 0, 32, ENCRYPTION_INSTANCE);
		} catch (FileNotFoundException e1) {
		} catch (IOException e) {
		}
		return aesKey;
	}//-loadKey method
	
}//-AESEncryption class