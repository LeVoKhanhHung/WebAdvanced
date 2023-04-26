package controller.client.authentication;

import properties.AssetsProperties;
import security.GenerateKeyPair;
import services.EmailServices;
import services.UserServices;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@WebServlet(name = "ActiveAccount", value = "/activeAccount")
public class ActiveAccount extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String token = request.getParameter("token");

        boolean valid = UserServices.getInstance().checkToken(email, token);

        if (valid) {
            boolean activated = UserServices.getInstance().active(email, token);
            if (activated) {
                try {
                    String uploadDir = (String) getServletContext().getAttribute("UPLOAD_DIR");
                    KeyPair generatedKeyPair = GenerateKeyPair.generateKeyPair();
                    PublicKey publicKey = generatedKeyPair.getPublic();
                    PrivateKey privateKey = generatedKeyPair.getPrivate();
                    String base64PublicKey = GenerateKeyPair.convertPublicKeyToBase64(publicKey);

                    boolean isSavedPublicKey = UserServices.getInstance().setPublicKey(base64PublicKey, email);
                    if (isSavedPublicKey) {
                        GenerateKeyPair.savePrivateKeyFile(privateKey, uploadDir);
                        EmailServices.sendMailWithAttachment(uploadDir + File.separator + "private.key", email);
                        File f = new File(uploadDir + File.separator + "private.key");
                        if (f.exists()) f.delete();
                        response.sendRedirect(AssetsProperties.getBaseURL("success?activeSuccess=activated"));
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        } else {
            response.sendRedirect(AssetsProperties.getBaseURL());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
