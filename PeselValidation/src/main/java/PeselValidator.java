import java.io.*;

public class PeselValidator {

    public static void main(String[] args) {
        String fileName = args[0];
        String fileName1 = "out.txt";
        String nextLine;
        try(FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            FileWriter fileWriter = new FileWriter(fileName1);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            while((nextLine = bufferedReader.readLine())!= null) {
                boolean valid = Pesel.check(new Pesel(nextLine));
                if(valid)
                    bufferedWriter.write("VALID\n");
                else
                    bufferedWriter.write("INVALID\n");
            }
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }
}
