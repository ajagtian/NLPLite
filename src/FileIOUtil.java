import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileIOUtil {

	public String getDataFromFileAsString(String fileName){
		String dir = System.getProperty("user.dir");
		String filePath = dir+fileName;
		File file = new File(filePath);
		String fileContent = "";
		if(file.exists()){
			FileInputStream fin;
			byte [] fileBytes = new byte[(int)file.length()]; 
				try {
					fin = new FileInputStream(file);
					fin.read(fileBytes);
					fileContent = new String(fileBytes);
					fin.close();
				} catch (FileNotFoundException e) {
					putLog("input.txt not found");
				} catch (IOException e) {
					putLog("Excepion in read file = input.txt");
				}
				finally{
					fin = null;
				}
		}
		return fileContent;
	}
	
	public void printObjectToFile(String fileName,Object object){
		String dir = System.getProperty("user.dir");
		String filePath = dir+fileName;
		File file = new File(filePath);
		FileOutputStream fout;
		ObjectOutputStream oOut = null;
		try {
			fout = new FileOutputStream(file);
			oOut = new ObjectOutputStream(fout);
			oOut.writeObject(object);
			oOut.close();
		} catch (FileNotFoundException e) {
			System.out.println("KB.txt - file ot found");
		} catch (IOException e) {
			System.out.println("Could not write to KB.txt - "+e.getMessage());
			e.printStackTrace();
		}
		finally{
			oOut = null;
		}
	}
	
	public void printFile(String fileName,String text){
		String dir = System.getProperty("user.dir");
		String filePath = dir+fileName;
		File file = new File(filePath);
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(file);
			fout.write(text.getBytes());
			fout.close();
		} catch (FileNotFoundException e) {
			System.out.println("output.txt - file ot found");
		} catch (IOException e) {
			System.out.println("Could not write to KB.txt - "+e.getMessage());
			e.printStackTrace();
		}
		finally{
			fout = null;
		}
	}
	
	public void putLog(String text){
		System.out.print("####"+text+"####");
	}
}
