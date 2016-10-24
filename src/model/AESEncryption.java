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

/**
 * Class for encrypting/decrypting files with AES.
 * @author Tim Meier
 * @version 2.0
 * @project cloud
 * @package model
 * @created 13.10.2016
 * @lastUpdate 21.10.2016 / by Tim Meier
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
	 * @param cloudFile
	 * @param key
	 */
	public void encryptFile(CloudFile cloudFile, byte[] key) {
		try {
			SecretKey aesKey = new SecretKeySpec(key, 0, AES_KEY_SIZE/8, ENCRYPTION_INSTANCE);
			aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
			FileInputStream fis = new FileInputStream(cloudFile.getFile());
			CipherOutputStream cos = new CipherOutputStream(new FileOutputStream(
					new File(cloudFile.getFile().getAbsolutePath() + ".aes")), aesCipher);
			cloudFile.setName(cloudFile.getName()+".aes");
			copyStreams(fis, cos);
			fis.close();
			cos.close();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//-encryptFile method
	
	/**
	 * Encrypts and then copies the files of a given folder.
	 * @param cloudFolder
	 * @param key
	 */
	public void encryptFolder(CloudFolder cloudFolder, byte[] key) {
		FileInputStream fis = null;
		CipherOutputStream cos = null;
		try {
			SecretKey aesKey = new SecretKeySpec(key, 0, AES_KEY_SIZE/8, ENCRYPTION_INSTANCE);
			aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
			for(CloudFile file : cloudFolder.getFiles()){
				fis = new FileInputStream(file.getFile());
				cos = new CipherOutputStream(new FileOutputStream(
						new File(file.getName() + ".aes")), aesCipher);
				file.setName(file.getName()+".aes");
				copyStreams(fis, cos);
				fis.close();
				cos.close();
			}	
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//-encryptFolder method

	/**
	 * Decrypts and then copies the content of a given file.
	 * @param cloudFile
	 * @param key
	 */
	public void decryptFile(CloudFile cloudFile, byte[] key) {
		try {
			SecretKey aesKey = new SecretKeySpec(key, 0, AES_KEY_SIZE/8, ENCRYPTION_INSTANCE);
			aesCipher.init(Cipher.DECRYPT_MODE, aesKey);
			CipherInputStream cis = new CipherInputStream(new FileInputStream(cloudFile.getFile()), aesCipher);
			FileOutputStream fos = new FileOutputStream(new File(cloudFile.getFile().getAbsolutePath()
					.substring(0, cloudFile.getFile().getAbsolutePath().length()-4)));
			copyStreams(cis, fos);
			cis.close();
			fos.close();
			cloudFile.setName(cloudFile.getName().substring(0, cloudFile.getName().length()-4));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//-decryptFile method
	
	
	/**
	 * Decrypts and then copies the files of a given folder.
	 * @param cloudFolder
	 * @param key
	 */
	public void decryptFolder(CloudFolder cloudFolder, byte[] key) {
		try {
			SecretKey aesKey = new SecretKeySpec(key, 0, AES_KEY_SIZE/8, ENCRYPTION_INSTANCE);
			aesCipher.init(Cipher.DECRYPT_MODE, aesKey);

			for (CloudFile file : cloudFolder.getFiles()) {
				CipherInputStream cis = new CipherInputStream(new FileInputStream(file.getFile()), aesCipher);
				FileOutputStream fos = new FileOutputStream(new File(file.getFile().getAbsolutePath().substring(0,
						file.getFile().getAbsolutePath().length() - 4)));
				copyStreams(cis, fos);
				cis.close();
				fos.close();
				file.setName(file.getName().substring(0, file.getName().length() - 4));
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//-decryptFolder method
	

	/**
	 * Copies a stream 
	 * @param inputStream
	 * @param outputStream
	 */
	private void copyStreams(InputStream inputStream, OutputStream outputStream) {
		int i;
		byte[] b = new byte[1024];
		try {
			while((i=inputStream.read(b))!=-1) {
				outputStream.write(b, 0, i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//-copyStreams method
	
	/**
	 * Creates a new AES key and saves it to a directory
	 * @param keyDirectoryPath
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
	}//-createKey method

	/**
	 * Loads a AES key from a file
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
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return aesKey;
	}//-loadKey method
	
}//-AESEncryption class