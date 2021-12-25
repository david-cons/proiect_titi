//N am downloadat inca bibliotecile pentru 
//A trimite mailuri 

public class EmailSender {
    //singleton
    public EmailSender()
    {}

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
    public void sendEmail()
    {
        //default email

    }



    //state
    private static EmailSender emailSender = new EmailSender();
    private String From;
    private String To;

    private String defaultString  = "";
}
