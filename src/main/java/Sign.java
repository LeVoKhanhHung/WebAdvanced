import java.io.*;
import java.security.*;
import java.security.spec.*;

public class Sign {
	public static void main(String[] args) {
		try { 
			 // Nạp private key từ file
			 FileInputStream fis = new FileInputStream("C:\\Users\\Hung\\OneDrive\\Tài liệu\\File\\privateKey.bin");
			 byte[] b = new byte[fis.available()];
			 fis.read(b);
			 fis.close();
			 
			 // Tạo private key
			 PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b);
			 KeyFactory factory = KeyFactory.getInstance("DSA");
			 PrivateKey priKey = factory.generatePrivate(spec);
			 
			 //Ký số (Sign)
			 // Tạo đối tượng signer
			 Signature s = Signature.getInstance("DSA");
			 s.initSign(priKey, new SecureRandom());
			 
			 // Chọn file để thực hiện ký số
			 String filename = "C:\\Users\\Hung\\OneDrive\\Tài liệu\\File\\BMHTTT_19130083_LeVoKhanhHung.docx";
			 fis = new FileInputStream(filename);
			 byte byteFile[] = new byte[fis.available()];
			 
//			 String filename = "C:\\Users\\Hung\\OneDrive\\Tài liệu\\File\\2.jpg";
//			 fis = new FileInputStream(filename);
//			 byte byteFile[] = new byte[fis.available()];
			 
			 // Chèn message vào đối tượng signer
			 s.update(byteFile);
			 byte[] bsign = s.sign();
			 
			 // Lưu chữ ký số
			 FileOutputStream fos = new FileOutputStream("C:\\Users\\Hung\\OneDrive\\Tài liệu\\File\\dsa2.txt");
			 fos.write(bsign);
			 
			 System.out.println("Sign document successfully");
			} catch (Exception e) {
			  e.printStackTrace(); 
			}
	}
}
