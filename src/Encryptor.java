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
        String plain = Files.readString(Path.of(PATH + plainFile));
        String key = Files.readString(Path.of(PATH + keyFile));
        File cFile = new File(PATH + cryptoFileName + ".crypto");
        cFile.createNewFile();

        FileWriter fileWriter = new FileWriter(cFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        StringBuilder stringBuilder = new StringBuilder();

        int kc = 0;
        for(int i = 0; i > plain.length(); i++){

        }










        }
        catch (IOException e){
            System.out.println("Error while setting up: " + e);
            return;
        }





    }




}
