package Service;

import Modal.OTPModal;
import Untils.EmailUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {
    public int SendOTP(String email,String OTP) {
        String fromEmail = "hung59069@gmail.com"; //requires valid gmail id
        String password = "vkbt mgsy zbsd ukap"; // correct password for gmail id
        String toEmail = email; // can be any email id
        String message = "Mã Xác Thực Của Bạn Là : "+OTP+" Mã Sẽ Hết Hạn Sau 5 Phút";
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        return EmailUtil.sendEmail(session, toEmail,"Mã Kích Hoạt", message);
    }
    public int SendOTPResetPassword(String email,String OTP) {
        String fromEmail = "hung59069@gmail.com"; //requires valid gmail id
        String password = "vkbt mgsy zbsd ukap"; // correct password for gmail id
        String toEmail = email; // can be any email id
        String message = "Mã Xác Nhận Của Bạn Là : "+OTP+" Dùng Mã Này Để Reset Password. Mã Sẽ Hết Hạn Sau 5 Phút";
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        return EmailUtil.sendEmail(session, toEmail,"Mã Reset", message);

    }
}
