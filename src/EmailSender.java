//N am downloadat inca bibliotecile pentru 
//A trimite mailuri 

public class EmailSender {
    //singleton
    public EmailSender()
    {
        System.out.println("EmailSender created all packages imported");
    }

    public static EmailSender Sender()
    {
        return emailSender;   
    }

    public void From(String From)
    {
        this.From = From;
    }
    public void To(String To)
    {
        this.To = To;
    }

    private void setDefault(String defaulString)
    {
        this.defaultString = defaulString;
    }

    public void sendEmail(String emailContent)
    {
        //auxiliary emails
    }

    public void sendEmail(String From, String To, String emailContent)
    {
        //Mai usor decat sa manageuesti state
        To(To);
        From(From);
        sendEmail(emailContent);
    }
    public void sendEmail()
    {
        sendEmail(this.defaultString);

    }



    //state
    private static EmailSender emailSender = new EmailSender();
    private String From;
    private String To;

    private String defaultString  = "";
}
