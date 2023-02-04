import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Decryptor {

    public String decrypt(String key, String cipherText){
        if(key == null || key.length() == 0)
            return "";

        StringBuilder stringBuilder = new StringBuilder();

        for (int p = 0, k = 0; p < cipherText.length(); p++, k++) {
            int charPos = Main.CHARS.indexOf(cipherText.charAt(p)) - Main.CHARS.indexOf(key.charAt(k));
            stringBuilder.append(Main.CHARS.charAt((charPos >= 0 ? charPos : Main.CHARS.length() + charPos) % (Main.CHARS.length())));
            if (k == key.length() - 1)
                k = -1;
        }

        return stringBuilder.toString();

    }

    public void decrypt(String cryptoFileName, String keyFileName, String decryptFileName) {
        try {
            String cipherText = Files.readString(Path.of(Main.PATH + cryptoFileName)).toLowerCase().trim();
            String key = Files.readString(Path.of(Main.PATH + keyFileName)).toLowerCase().trim();
            File cFile = new File(Main.PATH + decryptFileName + ".plain");
            cFile.createNewFile();

            FileWriter fileWriter = new FileWriter(cFile);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(cFile));

            bufferedWriter.write(decrypt(key,cipherText));
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error while setting up: " + e);
        }
    }


}
