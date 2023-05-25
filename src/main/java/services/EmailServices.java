package services;

import beans.EmailMessage;
import org.omg.CORBA.portable.ApplicationException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailServices {

    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    private static Properties props = new Properties();

    static {
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.socketFactory.port", 465);
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.starttls.enable", "true");
    }

    public static void sendMail(EmailMessage emailMessageDTO) throws ApplicationException {
        String sender_Email = "devwebchichoo@gmail.com";
        String sender_email_pass = "nbhdwkvvyptdreek";

        try {
            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(sender_Email, sender_email_pass);
                        }
                    });


            session.setDebug(true);

            Message msg = new MimeMessage(session);
            InternetAddress addressFrom = new InternetAddress(sender_Email, "HouseWareNLU");
            msg.setFrom(addressFrom);

            String[] emailIds = new String[0];

            if (emailMessageDTO.getTo() != null) {
                emailIds = emailMessageDTO.getTo().split(",");
            }

            String[] emailIdsCc = new String[0];

            if (emailMessageDTO.getCc() != null) {
                emailIdsCc = emailMessageDTO.getCc().split(",");
            }

            String[] emailIdsBcc = new String[0];

            if (emailMessageDTO.getBcc() != null) {
                emailIdsBcc = emailMessageDTO.getBcc().split(",");
            }

            InternetAddress[] addressTo = new InternetAddress[emailIds.length];

            for (int i = 0; i < emailIds.length; i++) {
                addressTo[i] = new InternetAddress(emailIds[i]);
            }

            InternetAddress[] addressCc = new InternetAddress[emailIdsCc.length];

            for (int i = 0; i < emailIdsCc.length; i++) {
                addressCc[i] = new InternetAddress(emailIdsCc[i]);
            }

            InternetAddress[] addressBcc = new InternetAddress[emailIdsBcc.length];

            for (int i = 0; i < emailIdsBcc.length; i++) {
                addressBcc[i] = new InternetAddress(emailIdsBcc[i]);
            }

            if (addressTo.length > 0) {
                msg.setRecipients(Message.RecipientType.TO, addressTo);
            }

            if (addressCc.length > 0) {
                msg.setRecipients(Message.RecipientType.CC, addressCc);
            }

            if (addressBcc.length > 0) {
                msg.setRecipients(Message.RecipientType.BCC, addressBcc);
            }

            msg.setSubject(MimeUtility.encodeText(emailMessageDTO.getSubject(), "utf-8", "B"));
            msg.setContent(emailMessageDTO.getMessage(), "text/html; charset=UTF-8");
            Transport.send(msg);

        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }

    public static void sendMailWithAttachment(String filePath, String to) {
        String sender_Email = "devwebchichoo@gmail.com";
        String sender_email_pass = "nbhdwkvvyptdreek";

        try {
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(sender_Email, sender_email_pass);
                        }
                    });


            session.setDebug(true);

            Message msg = new MimeMessage(session);
            InternetAddress addressFrom = new InternetAddress(sender_Email, "HouseWareNLU");
            msg.setFrom(addressFrom);

            String[] emailIds = {to};

            InternetAddress[] addressTo = new InternetAddress[emailIds.length];

            for (int i = 0; i < emailIds.length; i++) {
                addressTo[i] = new InternetAddress(emailIds[i]);
            }

            if (addressTo.length > 0) {
                msg.setRecipients(Message.RecipientType.TO, addressTo);
            }

            BodyPart bodyMessageContent = new MimeBodyPart();
            bodyMessageContent.setContent("Chào bạn,\\n Chúng tôi gửi bạn file private key để thực hiện các giao dịch chính chủ trên website" +
                    " của chúng tôi.Vui lòng bảo vệ khóa và không tiết lộ cho bất cứ ai.\n Best regards, HouseWare NLU", "text/html; charset=UTF-8");
            MimeBodyPart keyFileBody = new MimeBodyPart();
            String filename = "private.key";
            DataSource source = new FileDataSource(filePath);
            keyFileBody.setDataHandler(new DataHandler(source));
            keyFileBody.setFileName(filename);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyMessageContent);
            multipart.addBodyPart(keyFileBody);
            msg.setContent(multipart);
            msg.setSubject(MimeUtility.encodeText("File private key dành cho bạn", "utf-8", "B"));
            Transport.send(msg);

        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }
}