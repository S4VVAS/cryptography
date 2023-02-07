import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class VigCracker {

    public void crackEncryptionKasiski(String cipherFileName, String decryptFileName, String dictFileName) {
        try {
            //SETUP FOR FILEHANDLING
            //Reads ciphertext from given file
            String cipherText = Files.readString(Path.of(Main.PATH + cipherFileName)).toLowerCase().trim();
            String dict = Files.readString(Path.of(Main.PATH + dictFileName)).toLowerCase();

            //Creates a file with given name
            File cFile = new File(Main.PATH + decryptFileName + ".plain");
            cFile.createNewFile();

            //Classes that help with file content construction
            FileWriter fileWriter = new FileWriter(cFile);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(cFile));
            StringBuilder stringBuilder = new StringBuilder();
            //END OF SETUP FOR FILEHANDLING

            //Frequent distances that occur when doing xGram analysis (trigram = default)
            TreeMap<String, Integer> frequentDistances = xGramDistanceAnalysis(cipherText);

            //Arraylist containg keyLengths
            ArrayList<Integer> keyLengths = getKeyLengths(Helper.removeGCDsAbove(frequentDistances, Main.MAX_KEYLENGTH));

            FreqAnalysisCracker vcc = new FreqAnalysisCracker(cipherText, dict);

            //If keylengths exist, go over them and crack for each keylength
            if (keyLengths != null) for (int length : keyLengths) {
                //Append the resulting key and plaintext to the file writer
                stringBuilder.append(vcc.findKey(cipherText, length));
            }

            //Write to file and close everything we opened
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error while setting up: " + e);
        }
    }


    //Xgram analysis to figure out the distances between repeating blocks of x chars (xgrams)
    private TreeMap<String, Integer> xGramDistanceAnalysis(String cipher) {
        TreeMap<String, ArrayList<Integer>> distances = new TreeMap<>();
        for (int gram = 0; gram <= cipher.length() - Main.XGRAM; gram++) {
            String trigram = cipher.substring(gram, gram + Main.XGRAM);
            if (!distances.containsKey(trigram))
                for (int i = gram + Main.XGRAM; i < cipher.length() - Main.XGRAM; i++) {
                    if (trigram.equals(cipher.substring(i, i + Main.XGRAM))) {
                        if (distances.containsKey(trigram)) {
                            ArrayList<Integer> arr = distances.get(trigram);
                            arr.add(i - gram); //Adds the n'th occurance of the trigram
                            distances.replace(trigram, arr);
                        } else {
                            ArrayList<Integer> arr = new ArrayList<>();
                            arr.add(i - gram); //Adds the second occurance of the trigram
                            distances.put(trigram, arr);
                        }
                    }
                }
        }
        //Take the gcd of the distances
        return Helper.gcd(distances);
    }

    //Generate possible key lengths based on the given gcds generated from the cipher using xgram analysis
    private ArrayList<Integer> getKeyLengths(TreeMap<String, Integer> gcdArr) {
        if (gcdArr.isEmpty()) return null;
        ArrayList<Integer> keyLengths = new ArrayList<>();
        keyLengths.add(1);
        for (Map.Entry<String, Integer> currEntry : gcdArr.entrySet()) {
            int currVal = currEntry.getValue();
            if (currVal % 2 == 0) {
                while (currVal > 1) {
                    if (!keyLengths.contains(currVal)) keyLengths.add(currVal);
                    currVal = currVal / 2;
                }
            } else if (!keyLengths.contains(currVal)) keyLengths.add(currVal);
        }
        return keyLengths;
    }

}
