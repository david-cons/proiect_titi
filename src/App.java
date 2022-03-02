import java.util.*;
import java.math.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class App {


    //------------------main-------------------------------

    
    public static void main(String[] args) throws IOException, InterruptedException {
        

        //-------------------------chestii care pot fi editate------------------
        final boolean ScrieTextFile = true;
        final boolean TrimiteEmailuri = true;
        final int seconds = 1000;
       
        //initialize searcher

        ExcelSearcher searcher = ExcelSearcher.Searcher();

        //set file
        searcher.setFile("./data/restante-11.02.2022_1.xlsx");//lista restante (coloanele sa ramana aceleasi). CAN BE EDITED

        //Ia data din tabel data raw 
        ArrayList<ArrayList<String>> tabel = searcher.loadCellContent();


        //determina data intr-un mod logic elegant in forma unei liste de obiecte restanta
        ArrayList<Restanta> restante = determinaRestantele(tabel); // am stabilit data pentru fiecare mail in parte 
        // acum suma 



        //Lista cu emailuri pe persoana
        searcher.setFile("./data/users_2.xlsx");//lista emailuri CAN BE EDITED


        ArrayList<ArrayList<String>> baza_date_emailuri = searcher.loadCellContent();

        


        //----------------------de aici chestii nu mai pot fi editate ----------------------------

        //Transformare intr-un dictionar "cod":"email"
        HashMap<String, String> map_emailuri = new HashMap<String, String>();
        int i = 1;

        //editeaza emailsumapersonala intr-o alta functie care creeaza emailuri si cauta dinamic in 
        //dictionar in functie de codul persoanei din restanta 
        for(ArrayList<String> array : baza_date_emailuri)
        {
            i++;
            try
            {
                map_emailuri.put(Integer.toString((int)Double.parseDouble(array.get(3))) , array.get(1));
            }
            catch(Exception e)
            {
                System.out.println("column " + i + " could not be added in the email dictionary");
                continue;
            }
        }


        ArrayList<Email> emailuri;

        //Compileaza emailurile si textul lor 
         emailuri = emailCreator(restante, map_emailuri);
       
    


        //Scrie intr-un file noText toate codurile care nu au un email atasat lor 
        BufferedWriter out = new BufferedWriter(new FileWriter("./noEmail.txt"));
        for(Email email : emailuri)
            {
                if(email.To == null)
                {
                    String text = email.nume + " " + email.id + "\n";

                    out.write(text);
                } 
            }
        out.close();
        //--------------------------------------------------------------------

        int total = nonNullEmails(emailuri); // Calculeaza suma totala a emailurilor care trebuie trimise 

        
            
        
        if(ScrieTextFile)
        {
            for(Email email : emailuri)
            {
                if(email.To == null)
                {
                    BufferedWriter fileEmailRestanta = new BufferedWriter(new FileWriter("./faraEmail/" + email.id + " "+ email.nume + ".txt"));
                    fileEmailRestanta.write(email.scrisoare);
                    fileEmailRestanta.close();
                }   
            }
            System.out.println("Toate scrisorile au fost printate");
    }
        

    if(TrimiteEmailuri)
    {
        
        for(int trimise = 0; trimise < emailuri.size(); trimise++)
        {
            if(emailuri.get(trimise).To != null)
            {
                BufferedWriter fileEmailRestanta = new BufferedWriter(new FileWriter("./cuEmail/" + emailuri.get(trimise).id +"_"+emailuri.get(trimise).To+".txt"));
                fileEmailRestanta.write("email: "+emailuri.get(trimise).To +"\n\n"+emailuri.get(trimise).scrisoare);
                fileEmailRestanta.close();
                System.out.println("Wrote " +trimise+ " out of "+ total);
            }
        }
        /**Email.login("botnoreplyeuro7@gmail.com","Euro7Seven!");
        int trimise = 0;
        for(Email email : emailuri)
        {
            if(email.To != null)
            {
                try{
                    Email.send(email);
                    Thread.sleep(25 * seconds); // schimba time-ul pt sleep 

                }
                catch(Exception e)
                {
                    System.out.println(e);
                    System.out.println(email.toString() + " could not be sent"); //Flag pentru email netrimis
                    
                }
                finally{


                    System.out.println((++trimise) + " " + total);
                }

            }
        }
        System.out.println("Toate emailurile au fost trimise");**/ //temporar oprit 

        
    }
        
    }

//------------------main-------------------------------














    static ArrayList<Restanta> determinaRestantele(ArrayList<ArrayList<String>> tabel) //
    {

        ArrayList<Restanta> restante = new ArrayList<>();
        
        int i  = 0;
        for(ArrayList<String> stringuri : tabel)
        {
            i++;
            if(stringuri.get(0).isEmpty() || stringuri.get(0).equals("nr_iesire")
                || stringuri.get(0).equals("TOTAL RESTANTE LA 30 11 2021")) //in caz ca randul nu este un rand tipic de data
            {
                continue;
            }

            try{
                restante.add(new Restanta(
                    stringuri.get(0),
                    stringuri.get(1),
                    stringuri.get(2),
                    stringuri.get(3),
                    stringuri.get(4)
                ));
            }catch(Exception e)
            {
                System.out.println(i + "nu a putut fi adaugat in lista de restante\n"+e.toString());
            }
            
        }
        return restante;
    }



    static ArrayList<Email> emailCreator(ArrayList<Restanta> restante, HashMap<String,String> db_mailuri)
    {
        
        String persoana_actuala = restante.get(0).denumire;
        ArrayList<Email> emailuri = new ArrayList<Email>();
        ArrayList<Restanta> restante_persoana_actuala = new ArrayList<>();

        for(int i = 0; i < restante.size(); i++)
        {
            if((!persoana_actuala.equals(restante.get(i).denumire)) ) // Se restateaza la state-ul initial doar daca persoana se schimba 
            {
                
                Email email = new Email();
                email.content = Paste.paste(restante_persoana_actuala); //Metoda in clasa separata care se ocupa cu formatarea intregului mail
                email.scrisoare = Paste.pasteScrisoare(restante_persoana_actuala);
                email.From = "botnoreplyeuro7@gmail.com";
                email.subject = "Somație de plată a facturii/facturilor aferente consumului de gaze naturale";
                String adresa = db_mailuri.get(restante.get(i-1).cod);
                email.To = adresa ;//db_mailuri.get(persoana_actuala);
                //System.out.println(restante.get(i).cod + " " + adresa);
                email.nume = persoana_actuala;
                email.id = restante.get(i-1).cod;
                persoana_actuala = restante.get(i).denumire;
                restante_persoana_actuala = new ArrayList<>();
                emailuri.add(email);
            }
            restante_persoana_actuala.add(restante.get(i));
                        
        }

        //Nu uita de ultima persoana din lista 
        Email email = new Email();
        email.content = Paste.paste(restante_persoana_actuala); //Metoda in clasa separata care se ocupa cu formatarea intregului mail
        email.scrisoare = Paste.pasteScrisoare(restante_persoana_actuala);
        email.From = "botnoreplyeuro7@gmail.com";
        email.subject = "Somație de plată a facturii/facturilor aferente consumului de gaze naturale";
        String adresa = db_mailuri.get(restante_persoana_actuala.get(0).cod);
        email.To = adresa;
        //System.out.println(restante.get(restante.size()-1).cod + " " + adresa);
        email.nume = persoana_actuala;
        email.id = restante_persoana_actuala.get(0).cod;
        persoana_actuala = restante_persoana_actuala.get(0).denumire;
        restante_persoana_actuala = new ArrayList<>();
        emailuri.add(email);

        return emailuri;

        
    }

    static int nonNullEmails(ArrayList<Email> emailuri)
    {
        int result = 0;
        for(Email email : emailuri)
        {
            if(email.To != null)
            {
                result++;
            }
        }

        return result;
    }

}
