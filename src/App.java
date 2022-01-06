import java.util.*;
import java.math.*;

public class App {
    
    public static void main(String[] args) {
        
        /** 
        //initialize searcher

        ExcelSearcher searcher = ExcelSearcher.Searcher();

        //set file
        searcher.setFile("./data/RESTANTE_LA_30_11_2021_PNETRU_PENALITATI.xlsx");

        //Ia data din tabel data raw 
        ArrayList<ArrayList<String>> tabel = searcher.loadCellContent();


        //determina data intr-un mod logic elegant in forma unei liste de obiecte restanta
        ArrayList<Restanta> restante = determinaRestantele(tabel); // am stabilit data pentru fiecare mail in parte 
        // acum suma 



        //TODO Lista cu emailuri pe persoana

        //TODO Transformare intr-un dictionar "nume":"email"

        //TODO editeaza determinaRestantele intr-o alta functie care creeaza emailuri si cauta dinamic in 
        //dictionar in functie de numele persoanei din restanta 

        */

        Email.login("testexcelbottiti@gmail.com", "parola1234;");
        Email email = new Email();
        email.From = "testexcelbottiti@gmail.com";
        email.To = "matei.mitran10@gmail.com";
        email.content = "Hello World!";
        email.subject = "Hello World!";
       // Map<String, Email> dic = new HashMap<String,Email>();

        Email.send(email);
        

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

    static String compuneEmail(Restanta restanta)
    {
        return "Domnule, " + restanta.denumire +
            ", ai in data de "+ restanta.data + 
            " restanta de "+ restanta.neachitat +" ron.\n";
    }

    static ArrayList<String> emailSumaPersonala(ArrayList<Restanta> restante)
    {
        ArrayList<String> emailuri = new ArrayList<>();
        String persoana_actuala = restante.get(0).denumire;
        double suma = 0;
        String emailContent = "";
        for(int i = 0; i < restante.size(); i++)
        {
            if(!persoana_actuala.equals(restante.get(i).denumire)) // Se restateaza la state-ul initial doar daca persoana se schimba si o noua 
            //suma trebuie recalculata sau daca am ajuns la sfarsitul bazei de date
            {
                emailContent += "Suma totala este in valoare de " + suma + " ron";
                emailuri.add(emailContent); //Stocheaza emailul pentru persoana respectiva 


                //Restarteaza state-ul pt ca am sarit la o persoana noua 
                suma = 0;
                emailContent = "";
                persoana_actuala = restante.get(i).denumire;
            }
            
            emailContent += compuneEmail(restante.get(i));
            
            suma += Double.parseDouble(restante.get(i).neachitat);

            

        }

        //Nu uita de ultima persoana din lista 
        emailContent += "Suma totala este in valoare de " + suma + " ron";
        emailuri.add(emailContent); //Stocheaza emailul pentru persoana respectiva 

        return emailuri;

        
    }
    

}
