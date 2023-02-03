import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static java.util.Map.entry;

public class Main extends Encryptor {

    public final static String CHARS = "abcdefghijklmnopqrstuvwxyzåäö";

    //Values retrieved from: http://practicalcryptography.com/cryptanalysis/letter-frequencies-various-languages/swedish-letter-frequencies/
    public final static double[] LETTER_FREQ_SWEDISH = {9.38, 1.54, 1.49, 4.70, 10.15, 2.03, 2.86, 2.09, 5.82, 0.61, 3.14, 5.28, 3.47, 8.54, 4.48, 1.84, 0.02, 8.43, 6.59, 7.69, 1.92, 2.42, 0.14, 0.16, 0.71, 0.07, 1.34, 1.80, 1.31};

    //Values retrieved from: http://practicalcryptography.com/cryptanalysis/letter-frequencies-various-languages/swedish-letter-frequencies/
    public final static Map<String, Double> BIGRAM_FREQ_SWEDISH = Map.ofEntries(
            entry("en", 2.44), entry("re", 1.10), entry("om", 0.88),
            entry("de", 2.11), entry("nd", 1.07), entry("ri", 0.86),
            entry("er", 2.10), entry("ta", 1.03), entry("ng", 0.82),
            entry("an", 1.75), entry("ti", 1.01), entry("sk", 0.80),
            entry("ar", 1.61), entry("na", 0.98), entry("ka", 0.80),
            entry("et", 1.27), entry("ns", 0.97), entry("oc", 0.78),
            entry("st", 1.27), entry("tt", 0.97), entry("me", 0.77),
            entry("in", 1.22), entry("ll", 0.94), entry("ch", 0.75),
            entry("ra", 1.21), entry("at", 0.91), entry("el", 0.73),
            entry("te", 1.18), entry("la", 0.88), entry("ör", 0.73)
    );

    public final static Map<String, Double> TRIGRAM_FREQ_SWEDISH = Map.ofEntries(
            entry("och", 0.65), entry("ill", 0.38), entry("des", 0.30),
            entry("för", 0.55), entry("att", 0.37), entry("der", 0.29),
            entry("den", 0.48), entry("til", 0.34), entry("gen", 0.29),
            entry("nde", 0.47), entry("det", 0.33), entry("nin", 0.28),
            entry("and", 0.47), entry("era", 0.33), entry("ren", 0.27),
            entry("ade", 0.46), entry("are", 0.33), entry("ans", 0.26),
            entry("ter", 0.45), entry("ska", 0.32), entry("ett", 0.25),
            entry("ing", 0.44), entry("sta", 0.31), entry("han", 0.25),
            entry("som", 0.41), entry("med", 0.30), entry("lan", 0.25),
            entry("ens", 0.38), entry("var", 0.30), entry("ers", 0.25)
    );

    //CHANGE THIS TO YOUR COMPUTERS PATH TO THE /src/ FOLDER!!!!
    public final static String PATH = "/Users/savvas/Desktop/CRYPTO/CRYPTO-LABS/src/";

    public final static int MAX_KEYLENGTH = 16;

    public final static int XGRAM = 3;

    public final static int nMostFrequent = 10;

    public static void main(String[] args) {
        Main enc = new Main();
        Decryptor dec = new Decryptor();
        VigCracker cracker = new VigCracker();

        enc.encrypt("vig_group7.plain", "vig_group7.key", "vig_group7");
        try {
            System.out.println("Encrypted text: " + Files.readString(Path.of(Main.PATH + "test.crypto")));
        } catch (
                IOException e) {
        }
        dec.decrypt("test.crypto", "test.key", "testDecrypted");
        try {
            System.out.println("Decrypted text: " + Files.readString(Path.of(Main.PATH + "testDecrypted.plain")));
        } catch (IOException e) {
        }

        cracker.crackEncryptionKasiski("vig_group7.crypto", "testCracked_GROUP7");

        try {
            System.out.println("CrackedIOC text: " + Files.readString(Path.of(Main.PATH + "testCrackedIOC.plain")));
        } catch (IOException e) {
        }
    }

}
