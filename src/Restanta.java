
public class Restanta { //record class
    //Similar cu un struct in c;

    public String nr_iesire;
    public String cod;
    public String denumire;
    public String data;
    public String neachitat;


    //Metode pt debugging 
    Restanta(String nr_iesire, String cod, String denumire, String data, String neachitat)
    {
        this.nr_iesire = nr_iesire;
        this.cod = cod;
        this.denumire = denumire;
        this.data = data;
        this.neachitat = neachitat;
    }

    public String toString()
    {
        return "Factura seria " + nr_iesire+ " din " + data + " în valoare de "+ neachitat;
    }

    public void show()
    {
        System.out.println(this.toString());
    }


}
