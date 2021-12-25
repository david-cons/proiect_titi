import java.io.File;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;  
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
        System.out.println("ExcelSearcher Created");
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

    public void load()
    {
        
        try
        {
             
            this.workbook = new XSSFWorkbook(this.fileInputStream);  
            //creating a Sheet object to retrieve the object  
        }
        catch(IOException e)
        {
            System.out.println("Cannto create a sheet object with file : " + this.fileName);
            return;
        }
        XSSFSheet sheet =  this.workbook.getSheetAt(0);  
        //evaluating cell type   
        
        for(Row row: sheet)     //iteration over row using for each loop  
        {  
            for(Cell cell: row)    //iteration over cell using for each loop  
            {  
                System.out.print(cell.toString() + "\t") ; /// temporar numai pt testare da print la fiecare celula 
            }  
            System.out.println();  
        }
    }
    

    //state
    
    private String fileName;
    private FileInputStream fileInputStream;
    private XSSFWorkbook workbook;
    private static ExcelSearcher instance = new ExcelSearcher();
}
