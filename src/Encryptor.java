import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


public class Encryptor {

    public void encrypt(String plainFileName, String keyFileName, String cryptoFileName) {
        //Setup - Read from .plain and .key files and create .crypto file
        try {
            String plainText = Files.readString(Path.of(Main.PATH + plainFileName)).toLowerCase().trim();
            String key = Files.readString(Path.of(Main.PATH + keyFileName)).toLowerCase().trim();
            File cFile = new File(Main.PATH + cryptoFileName + ".crypto");
            cFile.createNewFile();

            FileWriter fileWriter = new FileWriter(cFile);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(cFile));
            StringBuilder stringBuilder = new StringBuilder();

            for (int p = 0, k = 0; p < plainText.length(); p++, k++) {
                stringBuilder.append(Main.CHARS.charAt((Main.CHARS.indexOf(plainText.charAt(p)) + Main.CHARS.indexOf(key.charAt(k))) % (Main.CHARS.length())));
                if (k == key.length() - 1)
                    k = -1;
            }
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error while setting up: " + e);
        }
    }






}
