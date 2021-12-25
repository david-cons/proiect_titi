public class App {
    
    public static void main(String[] args) {
        //Feed in the file 
        ExcelSearcher searcher = ExcelSearcher.Searcher();
        searcher.setFile("./data/RESTANTE_LA_30_11_2021_PENTRU_FACTURI.xlsx");
        searcher.load();
        //Read the file

    }
}
