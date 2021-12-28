//N am downloadat inca bibliotecile pentru 
//A trimite mailuri 

public class Email {
    //singleton
    public Email(String From, String To)
    {
        this.From = From;
        this.To = To;
    }

    public Email(String From, String To, String content)
    {
        this.From = From;
        this.To = To;
    }

    public void setFrom(String From)
    {
        this.From = From;
    }
    public void setTo(String To)
    {
        this.To = To;
    }

    public String getFrom()
    {
        return this.From;
    }

    public String getTo()
    {
        return this.To;
    }

    public void send(String emailContent)
    {
        this.content = emailContent;
    }


    public void send()
    {
        send(content);
    }



    //state
    private String From;
    private String To;
    private String content;

    //Functiile care o sa tina cont de trimis emailuri concret vor fi statice 
    //Chestiile low level vor fi statice

}
