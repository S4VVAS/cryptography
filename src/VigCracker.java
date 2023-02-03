import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.DoubleStream;

public class VigCracker {

    public void crackEncryptionKasiski(String cipherFileName, String decryptFileName) {
        try {
            String cipherText = Files.readString(Path.of(Main.PATH + cipherFileName)).toLowerCase().trim();
            File cFile = new File(Main.PATH + decryptFileName + ".plain");
            cFile.createNewFile();

            FileWriter fileWriter = new FileWriter(cFile);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(cFile));
            StringBuilder stringBuilder = new StringBuilder();

            ArrayList<String> TEMP_ARR = new ArrayList<>();

            TreeMap<String, Integer> frequentDistances = xGramDistanceAnalysis(cipherText);

            System.out.println(frequentDistances.toString());

            ArrayList<Integer> keyLengths = getKeyLengths(removeGCDsAbove(frequentDistances, Main.MAX_KEYLENGTH));
            System.out.println(keyLengths.toString());
            if (keyLengths != null) {
                for (int length : keyLengths)
                    TEMP_ARR.addAll(decryptFrequencyAnalysis(cipherText, length));
                for (String entry : TEMP_ARR) //PRINT TEMP
                    System.out.println(entry);
            }
        } catch (IOException e) {
            System.out.println("Error while setting up: " + e);
        }
    }

    protected ArrayList<String> decryptFrequencyAnalysis(String cipherText, int keyLength) {
        ArrayList<Character>[] groups = new ArrayList[Math.min(keyLength, cipherText.length())];
        for (int i = 0; i < groups.length; i++)
            groups[i] = new ArrayList<>();
        for (int i = 0, k = 0; i < cipherText.length(); i++, k++) {
            groups[k].add(cipherText.charAt(i));
            if (k >= groups.length - 1) k = -1;
        }
        double[][] frequencies = new double[groups.length][Main.CHARS.length()];
        for (int i = 0; i < groups.length; i++) {
            for (Character c : groups[i])
                frequencies[i][Main.CHARS.indexOf(c)]++;
            double sum = DoubleStream.of(frequencies[i]).sum();
            for (int j = 0; j < Main.CHARS.length(); j++)
                frequencies[i][j] = frequencies[i][j] / sum;
        }
        return biTriBreaker(getKeyMaxFreq (frequencies), cipherText);
    }

    private ArrayList<Integer>[] getKeyMaxFreq(double[][] frequencies) {//-__________________________________________________________________________________________________________________________________________
        ArrayList<Integer>[] frequencyArray = new ArrayList[frequencies.length];
        for (int i = 0; i < frequencies.length; i++) {
            int[] mostFreqChars = Helper.argsort(Main.LETTER_FREQ_SWEDISH, false);
            int[] mostFreqCipher = Helper.argsort(frequencies[i], false);
            ArrayList<Integer> distances = new ArrayList<>();

            for (int n = 0; n < Main.nMostFrequent; n++) {
                for (int c = n; c < Main.nMostFrequent; c++) {
                    int offset = mostFreqCipher[c] - mostFreqChars[n];
                    distances.add(offset);
                }

            }

            frequencyArray[i] = distances;

            System.out.print("frimärkessamlare".charAt(i) + "\t=\t");
            System.out.println(Arrays.toString(frequencyTransformToChar(distances)));
        }
        System.out.println();
        return frequencyArray;
    }

    public static char[] frequencyTransformToChar(ArrayList<Integer> offsets) {
        int[] freq = new int[Main.CHARS.length()];
        for (Integer i : offsets) {
            if (i < 0) i = Main.CHARS.length() + i;
            freq[i]++;
        }
        freq = Helper.argsort(freq, false);
        char[] ch = new char[Main.CHARS.length()];
        for (int i = 0; i < ch.length; i++)
            ch[i] = Main.CHARS.charAt(freq[i]);
        return ch;
    }

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
        return Helper.gcd(distances);
    }


    private TreeMap<String, Integer> removeGCDsAbove(TreeMap<String, Integer> gcdArr, int max) {
        TreeMap<String, Integer> newTreeMap = new TreeMap<>();
        for (Map.Entry<String, Integer> currEntry : gcdArr.entrySet())
            if (currEntry.getValue() <= max) newTreeMap.put(currEntry.getKey(), currEntry.getValue());
        return newTreeMap;
    }

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


    //Create a list of bigrams containing positions, check most frequent ones, if mfreq bigrams dont match up with most freq bigrams in list, look at key
    //if key contains (in n most frequent) the shifted letter that would generate the bigram in text, change it to that one

    //Do same for trigrams

    private ArrayList<String> biTriBreaker(ArrayList<Integer>[] keyList, String decryptedText){
        Decryptor dec = new Decryptor();
        StringBuilder keyBuilder = new StringBuilder();





        String decText = dec.decrypt(keyBuilder.toString(), decryptedText);
        //Try to break with most likely key
        for(int i = 0; i < Main.nMostFrequent; i++){

        }


        System.out.println(decText + "\n");


        return new ArrayList<>();

    }



}
