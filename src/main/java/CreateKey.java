import java.io.*;
import java.security.*;

public class CreateKey {
	public static void main(String[] args) {
		try {
			 SecureRandom sr = new SecureRandom();
			 KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
			 kpg.initialize(1024, sr);
			 KeyPair keys = kpg.generateKeyPair();
			 
			 // Save private key
			 PrivateKey privateKey = keys.getPrivate();
			 FileOutputStream fos = new FileOutputStream("C:\\Users\\Hung\\OneDrive\\Tài liệu\\File\\privateKey.bin");
			 fos.write(privateKey.getEncoded());
			 fos.close();
			 
			 // Save public key
			 PublicKey publicKey = keys.getPublic();
			 fos = new FileOutputStream("C:\\Users\\Hung\\OneDrive\\Tài liệu\\File\\publicKey.bin");
			 fos.write(publicKey.getEncoded());
			 fos.close();
			} catch (Exception e) {
			 e.printStackTrace(); 
			}
	}
}
