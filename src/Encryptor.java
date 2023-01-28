import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Encryptor {

    final static String CHARS = "abcdefghijklmnopqrstuvwxyzåäö";
    final static String PATH = "/Users/savvas/Desktop/CRYPTO/CRYPTO-LABS/src/";


    public static void main(String[] args){
        Encryptor enc = new Encryptor();
        enc.encode("test.plain", "test.key", "test");
        try {System.out.println("Encrypted text: " + Files.readString(Path.of(PATH + "test.crypto")));} catch (IOException e){};

    }
    
    public void encode(String plainFile, String keyFile, String cryptoFileName){
        //Setup - Read from .plain and .key files and create .crypto file
        try{
        String plainText = Files.readString(Path.of(PATH + plainFile)).toLowerCase().trim();
        String key = Files.readString(Path.of(PATH + keyFile)).toLowerCase().trim();
        File cFile = new File(PATH + cryptoFileName + ".crypto");
        cFile.createNewFile();

        FileWriter fileWriter = new FileWriter(cFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        StringBuilder stringBuilder = new StringBuilder();

        for(int p = 0, k = 0; p < plainText.length(); p++,k++) {
            stringBuilder.append(CHARS.charAt((CHARS.indexOf(plainText.charAt(p)) + CHARS.indexOf(key.charAt(k))) % (CHARS.length())));


            if(k == key.length()-1)
                k = -1;
        }
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.close();
        }
        catch (IOException e){
            System.out.println("Error while setting up: " + e);
            return;
        }
    }



}
