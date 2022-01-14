import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Email {
    

    public Email(String From, String To, String content, String subject)
    {
        this.From = From;
        this.To = To;
        this.content = content;
        this.subject = subject;
    }

    public Email()
    {
        //Do nothing 
        //Creat empty email manually reconfigurable
    }

    public String toString()
    {
        return  "From : " + this.From +
                " To : " + this.To+
                "\n" + this.subject+
                "\t id : " + this.id;
    }

    public static void send(Email email) throws IllegalStateException
    {

        if(loggedIn == false)
        {
            throw new IllegalStateException("the host of this device is not logged in");
        }

        if(email.content.isEmpty() || email.content == null)
        {
            throw new IllegalStateException("the content of the email cannot be empty or null");
        }

        if(email.subject == null)
        {
            throw new IllegalStateException("the subject of the email cannot be null");
        }

        

        try
        {
            MimeMessage message = new MimeMessage(Email.session);

            message.setFrom(new InternetAddress(email.From));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.To));

            message.setSubject(email.subject);
            message.setText(email.content);

            Transport.send(message);
            System.out.println("----------"+ email.toString() + "\n\n Sent\n\n");

        } catch (MessagingException mex)
        {
            mex.printStackTrace();
        }


        //continue
    }

    public static void login(String user, String password) 
    {
        properties = System.getProperties();
        //properties.setProperty("mail.user", user);
        //properties.setProperty("mail.password", password);
        
        //.put("mail.smtp.auth", "true");
        //properties.put("mail.smtp.ssl.enable", "true");
       // properties.put("mail.smtp.host", "smtp.gmail.com");
        //properties.put("mail.smtp.port", "587");
        //properties.put("mail.smtp.starttls.enable", "true");
        //properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");            
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {  
                return new PasswordAuthentication(user,password);  
          }
        };
        session =  Session.getInstance(properties,auth); 
        loggedIn = true;
    }
    

    //state
    public String From = "";
    public String To = "";
    public String nume = "";
    public String content = "";
    //public String scrisoare = "";
    public String subject = "";
    public String id = "";

    private static String host = "localhost";
    private static Properties properties;
    private static Session session;
    private static boolean loggedIn = false;
    //private static Transport transport;
    //Functiile care o sa tina cont de trimis emailuri concret vor fi statice 
    //Chestiile low level vor fi statice

}
