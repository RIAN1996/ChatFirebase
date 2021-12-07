package Tools;

import Firebase.Conexion;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edgerard
 */
public class FileHelper {
	private String fileName = "Log File.txt";
	private String srcMesagge = "FileHelper";
	//Firebase
	private Conexion fireBase;
	private Map<String,Object> data = new HashMap<>();
	private String uidd = "";
        private int bandera = 0;


	public FileHelper(String remitente){
		try {
			File myObj = new File(fileName);
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
				this.escribir("Archivo Creado");
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		this.srcMesagge = remitente;
	}
	
	public void escribir(String log){
                    try {
                        FileWriter fileWriter = new FileWriter(fileName, true);
                        
                        Timestamp timeStamp = new Timestamp(new Date().getTime());
                        String temp =  log + "	|	" + timeStamp;
                        fileWriter.write(srcMesagge + "	:	" + temp + "\n\n");
                        fireBase = new Conexion();
                        data.put("datos", temp);
                        uidd = srcMesagge + java.util.UUID.randomUUID().toString();
                        fireBase.add("logs",uidd,data);
                        //Coleccion : Logs
                        // Documento : srcMesagge
                        // datos : temp
                        fileWriter.close();
                        
                        System.out.println("Successfully wrote to the file.");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
             
	}
	
	public void setSrc(String newSrc){
		this.srcMesagge = newSrc;
	}
}
