import java.util.*;
import java.math.*;

public class App {
    
    public static void main(String[] args) {
        
       
        //initialize searcher

        ExcelSearcher searcher = ExcelSearcher.Searcher();

        //set file
        searcher.setFile("./data/RESTANTE_LA_30_11_2021_PNETRU_PENALITATI.xlsx");

        //Ia data din tabel data raw 
        ArrayList<ArrayList<String>> tabel = searcher.loadCellContent();


        //determina data intr-un mod logic elegant in forma unei liste de obiecte restanta
        ArrayList<Restanta> restante = determinaRestantele(tabel); // am stabilit data pentru fiecare mail in parte 
        // acum suma 



        //Lista cu emailuri pe persoana
        /*searcher.setFile("email_db");
        ArrayList<ArrayList<String>> baza_date_emailuri = searcher.loadCellContent();

        //Transformare intr-un dictionar "nume":"email"
        Map<String, String> map_emailuri = new HashMap<String, String>();
        for(ArrayList<String> array : baza_date_emailuri)
        {
            map_emailuri.put(array.get(0),array.get(1));
        }*/



        //editeaza emailsumapersonala intr-o alta functie care creeaza emailuri si cauta dinamic in 
        //dictionar in functie de numele persoanei din restanta 
        ArrayList<Email> emailuri = emailCreator(restante, new HashMap<>());


        
        

    }

    static ArrayList<Restanta> determinaRestantele(ArrayList<ArrayList<String>> tabel) //
    {

        ArrayList<Restanta> restante = new ArrayList<>();
        

        for(ArrayList<String> stringuri : tabel)
        {

            if(stringuri.get(0).isEmpty() || stringuri.get(0).equals("nr_iesire")
                || stringuri.get(0).equals("TOTAL RESTANTE LA 30 11 2021")) //in caz ca randul nu este un rand tipic de data
            {
                continue;
            }

            restante.add(new Restanta(
                stringuri.get(0),
                stringuri.get(1),
                stringuri.get(2),
                stringuri.get(3),
                stringuri.get(4)
            ));
            
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
            if(!persoana_actuala.equals(restante.get(i).denumire)) // Se restateaza la state-ul initial doar daca persoana se schimba 
            {
                Email email = new Email();
                email.content = Paste.paste(restante_persoana_actuala); //Metoda in clasa separata care se ocupa cu formatarea intregului mail
                email.From = "titi";
                email.subject = "Somație de plată a facturii/facturilor aferente consumului de gaze naturale";
                email.To = "";//db_mailuri.get(persoana_actuala);
                persoana_actuala = restante.get(i).denumire;
                restante_persoana_actuala = new ArrayList<>();
                emailuri.add(email);
            }
            restante_persoana_actuala.add(restante.get(i));
                        
        }

        //Nu uita de ultima persoana din lista 
        Email email = new Email();
        email.content = Paste.paste(restante_persoana_actuala); //Metoda in clasa separata care se ocupa cu formatarea intregului mail
        email.From = "titi";
        email.subject = "Somație de plată a facturii/facturilor aferente consumului de gaze naturale";
        email.To = ""; //db_mailuri.get(persoana_actuala);
        persoana_actuala = restante.get(restante.size()-1).denumire;
        restante_persoana_actuala = new ArrayList<>();
        emailuri.add(email);

        for(Email i : emailuri)
        {
            System.out.println(i.toString() + "\n-----------------------");
        }

        return new ArrayList<Email>();

        
    }
    

}
