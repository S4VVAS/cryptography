import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Encryptor {

    final static String CHARS = "abcdefghijklmnopqrstuvwxyzåäö";
    final static String PATH = "/Users/savvas/Desktop/CRYPTO/CRYPTO-LABS/src/";


    public static void main(String[] args) {
        Encryptor enc = new Encryptor();
        enc.encrypt("test.plain", "test.key", "test");
        try {System.out.println("Encrypted text: " + Files.readString(Path.of(PATH + "test.crypto")));} catch (IOException e) {};
        enc.decrypt("test.crypto", "test.key", "testDecrypted");
        try {System.out.println("Decrypted text: " + Files.readString(Path.of(PATH + "testDecrypted.plain")));} catch (IOException e) {};
        enc.crackEncryptionIOC("test.crypto", "testCrackedIOC");
        try {System.out.println("CrackedIOC text: " + Files.readString(Path.of(PATH + "testCrackedIOC.plain")));} catch (IOException e) {};
    }

    public void encrypt(String plainFileName, String keyFileName, String cryptoFileName) {
        //Setup - Read from .plain and .key files and create .crypto file
        try {
            String plainText = Files.readString(Path.of(PATH + plainFileName)).toLowerCase().trim();
            String key = Files.readString(Path.of(PATH + keyFileName)).toLowerCase().trim();
            File cFile = new File(PATH + cryptoFileName + ".crypto");
            cFile.createNewFile();

            FileWriter fileWriter = new FileWriter(cFile);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(cFile));
            StringBuilder stringBuilder = new StringBuilder();

            for (int p = 0, k = 0; p < plainText.length(); p++, k++) {
                stringBuilder.append(CHARS.charAt((CHARS.indexOf(plainText.charAt(p)) + CHARS.indexOf(key.charAt(k))) % (CHARS.length())));
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

    public void decrypt(String cryptoFileName, String keyFileName, String decryptFileName) {
        try {
            String cipherText = Files.readString(Path.of(PATH + cryptoFileName)).toLowerCase().trim();
            String key = Files.readString(Path.of(PATH + keyFileName)).toLowerCase().trim();
            File cFile = new File(PATH + decryptFileName + ".plain");
            cFile.createNewFile();

            FileWriter fileWriter = new FileWriter(cFile);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(cFile));
            StringBuilder stringBuilder = new StringBuilder();

            for (int p = 0, k = 0; p < cipherText.length(); p++, k++) {
                int charPos = CHARS.indexOf(cipherText.charAt(p)) - CHARS.indexOf(key.charAt(k));
                stringBuilder.append(CHARS.charAt((charPos >= 0 ? charPos : CHARS.length() + charPos)  % (CHARS.length())));
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

    public void crackEncryptionIOC(String cipherFileName, String decryptFileName) {
        try {
            String cipherText = Files.readString(Path.of(PATH + cipherFileName)).toLowerCase().trim();
            File cFile = new File(PATH + decryptFileName + ".plain");
            cFile.createNewFile();

            FileWriter fileWriter = new FileWriter(cFile);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(cFile));
            StringBuilder stringBuilder = new StringBuilder();
        } catch (IOException e) {
            System.out.println("Error while setting up: " + e);
        }


    }

    private Map<Character, Integer> indexOfCoincidence(String cipherFileName) {
        return null;
    }

    /*private class WorkFiles{

        public final String plainText;
        public final String key;

        private File file;

        public WorkFiles(String plainFile, String keyFile, String cryptoFileName){
            String plainTextTemp,keyTemp;
            try{
                plainTextTemp = Files.readString(Path.of(PATH + plainFile)).toLowerCase().trim();
                keyTemp = Files.readString(Path.of(PATH + keyFile)).toLowerCase().trim();
                this.file = new File(PATH + cryptoFileName + ".crypto");
                cFile.createNewFile();

                FileWriter fileWriter = new FileWriter(cFile);
                BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter(cFile));
                StringBuilder stringBuilder = new StringBuilder();
                bufferedWriter.write(stringBuilder.toString());

                bufferedWriter.close();
                fileWriter.close();
            }
            catch (IOException e){
                System.out.println("Error while setting up: " + e);
            }
            this.plainText = plainTextTemp;
            this.key = keyTemp;
        }

        public void writeToFile(String fileName, String str){
            FileWriter fileWriter = new FileWriter(cFile);
            BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter(cFile));
            StringBuilder stringBuilder = new StringBuilder();
            bufferedWriter.write(stringBuilder.toString());

            bufferedWriter.close();
            fileWriter.close();
        }



    }*/


}
