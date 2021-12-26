import java.io.File;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;  
import java.util.*;
import org.apache.poi.xssf.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;  

public class ExcelSearcher {
    //Clasa asa o sa foloseasca fie un singleton nu o sa existe mai mult decat o instanta a ei 
    private ExcelSearcher()
    {
        System.out.println("ExcelSearcher Created all packages imported");
    }

    public static ExcelSearcher Searcher()
    {
        return instance;
    }

    public void setFile(String fileName)
    {
        this.fileName = fileName;
        
        try{
            this.fileInputStream = new FileInputStream(new File(this.fileName));  
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File \""+ this.fileName + "\" does not exist");
            return;
        }
    }

    public ArrayList<ArrayList<String>> loadCellContent()
    {
        ArrayList<ArrayList<String>> cells = new ArrayList<>();
        try
        {
            this.workbook = new XSSFWorkbook(this.fileInputStream);  
            //creating a Sheet object to retrieve the object  
        }
        catch(IOException e)
        {
            System.out.println("Cannto create a sheet object with file : " + this.fileName);
            
        }
        XSSFSheet sheet =  this.workbook.getSheetAt(0);   
        
        for(Row row: sheet)     //iteration over row using for each loop  
        {  
            //Fiecare rand in parte constituie o restanta asa ca o sa initializam si o sa adaugam in lista cu restante 
            //Restanele initializate in loop-ul asta

            //Tabel bidimensional de restante fiecare celula
            //Stocata ca un string
            ArrayList<String> stringRow = new ArrayList<>();
            for(Cell cell : row)
            {
                //System.out.print(cell.toString() + "\t\t");
                stringRow.add(cell.toString());
            }
            //System.out.println();
            cells.add(stringRow);

            
        }

        return cells;
    }
    

    //state
    
    private String fileName;
    private FileInputStream fileInputStream;
    private XSSFWorkbook workbook;
    private static ExcelSearcher instance = new ExcelSearcher();
}


