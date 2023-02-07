import java.util.Map;

import static java.util.Map.entry;

public class Main extends Encryptor {

    //Encoding and Decoding alphabets
    public final static String CHARS = "abcdefghijklmnopqrstuvwxyzåäö";
    public final static String CHARS_TO_ARR = "a b c d e f g h i j k l m n o p q r s t u v w x y z å ä ö";

    //Singular letter frequencies
    //Values retrieved from: http://practicalcryptography.com/cryptanalysis/letter-frequencies-various-languages/swedish-letter-frequencies/
    public final static double[] LETTER_FREQ_SWEDISH = {9.38, 1.54, 1.49, 4.70, 10.15, 2.03, 2.86, 2.09, 5.82, 0.61, 3.14, 5.28, 3.47, 8.54, 4.48, 1.84, 0.02, 8.43, 6.59, 7.69, 1.92, 2.42, 0.14, 0.16, 0.71, 0.07, 1.34, 1.80, 1.31};

    //Path to the src folder
    //TODO: CHANGE THIS TO YOUR COMPUTERS PATH TO THE /src/ FOLDER!!!!
    public final static String PATH = "/Users/savvas/Desktop/CRYPTO/CRYPTO-LABS/src/";

    //Max keylength (limits the search)
    public final static int MAX_KEYLENGTH = 16;

    //Used to figure out keylength using (currently) trigrams, feel free to change this to any gram :)
    public final static int XGRAM = 3;

    public static void main(String[] args) {
        Main enc = new Main();
        Decryptor dec = new Decryptor();
        VigCracker cracker = new VigCracker();
        String dictFileName = "svenska-ord.txt";

        //Loops through all vig_groups
        for (int i = 1; i <= 14; i++)
            cracker.crackEncryptionKasiski("vig_group" + i + ".crypto", "Cracked_GROUP" + i, dictFileName);
        //Also runs the stray group 27
        cracker.crackEncryptionKasiski("vig_group27.crypto", "Cracked_GROUP27", dictFileName);

        //Loops through all TA .crypto files
        for (int i = 1; i <= 6; i++)
            cracker.crackEncryptionKasiski(i + ".crypto", "Cracked_TA" + i, dictFileName);
    }

}
