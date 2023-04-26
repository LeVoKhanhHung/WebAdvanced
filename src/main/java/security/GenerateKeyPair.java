package security;


import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class GenerateKeyPair {
    public static KeyPair generateKeyPair() throws IOException, NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
        keyGen.initialize(1024);
        KeyPair generatedKeyPair = keyGen.genKeyPair();
        return generatedKeyPair;
    }

    public static void savePrivateKeyFile(PrivateKey privateKey, String path) throws IOException {
        File dest = new File(path);
        if (!dest.exists()) dest.mkdirs();
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        FileOutputStream fos = new FileOutputStream(path + "/private.key");
        fos.write(pkcs8EncodedKeySpec.getEncoded());
        fos.close();
    }

    public static boolean deletePrivateKey(String path) {
        File f = new File(path + "/private.key");
        if (!f.exists())
            return false;
        else
            return f.delete();
    }

    public static PublicKey getPublicKeyFromEncodedKey(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key.getBytes());
            X509EncodedKeySpec spec = new X509EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance("DSA");

            return kf.generatePublic(spec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static PrivateKey loadPrivateKey(String path)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        File filePrivateKey = new File(path);
        FileInputStream fis = new FileInputStream(path);
        byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
        fis.read(encodedPrivateKey);
        fis.close();

        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        return privateKey;
    }

    public static PrivateKey getPrivateKeyFromEncodedKey(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key.getBytes());
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance("DSA");

            return kf.generatePrivate(spec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String convertPrivateKeyToBase64(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public static String convertPublicKeyToBase64(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

}
