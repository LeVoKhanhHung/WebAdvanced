import java.io.*;
import java.security.*;
import java.security.spec.*;

public class Verify {
	public static void main(String[] args) {
		try {
			// Nạp public key từ file
			FileInputStream fis = new FileInputStream("C:\\Users\\Hung\\OneDrive\\Tài liệu\\File\\publicKey.bin");
			byte[] b = new byte[fis.available()];
			fis.read(b);
			fis.close();
			
			// Tạo public key
			X509EncodedKeySpec spec = new X509EncodedKeySpec(b);
			KeyFactory factory = KeyFactory.getInstance("DSA");
			PublicKey pubKey = factory.generatePublic(spec);
			
			// Khởi tạo đối tượng Signature
			Signature s = Signature.getInstance("DSA");
			s.initVerify(pubKey);
			
			// Chọn file để kiểm chứng
			String filename = "C:\\Users\\Hung\\OneDrive\\Tài liệu\\File\\BMHTTT_19130083_LeVoKhanhHung.docx";
			fis = new FileInputStream(filename);
			byte byteFile[] = new byte[fis.available()];
			fis.close();
			
//			String filename = "C:\\Users\\Hung\\OneDrive\\Tài liệu\\File\\2.jpg";
//			fis = new FileInputStream(filename);
//			byte byteFile[] = new byte[fis.available()];
//			fis.close();
			
			// Nạp message vào đối tượng Signuture
			s.update(byteFile);
			
			// Kiểm chứng chữ ký trên Message
			// Nạp chữ ký signature từ file
			fis = new FileInputStream("C:\\Users\\Hung\\OneDrive\\Tài liệu\\File\\dsa.txt");
			byte[] bsign = new byte[fis.available()];
			fis.read(bsign);
			fis.close();
			
//			fis = new FileInputStream("C:\\Users\\Hung\\OneDrive\\Tài liệu\\File\\dsa2.txt");
//			byte[] bsign = new byte[fis.available()];
//			fis.read(bsign);
//			fis.close();

			// Kết quả kiểm chứng
			boolean result = s.verify(bsign);
			if (result == true) {
				System.out.println("Message is verified");
			} else {
				System.out.println("Message isn't verified");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
