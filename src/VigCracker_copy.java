import java.math.BigInteger;
import java.util.*;

public class VigCracker_copy {


    /**
     * Created by mardurhack-ubuntu on 10/6/14.
     */






    int keyLength = 0;
    String key, plaintextFileName, keyFileName;

    String ciphertext, dictionaryDump, alphabetStr = "a b c d e f g h i j k l m n o p q r s t u v w x y z å ä ö";
    String[] alphabetArr;
    Map<String, Integer> a2i;
    Map<Integer, String> i2a;
    Map<String, Double> letterFrequency;
    List<String> dictionary;
    List<String> charactersByFrequency;
    double[] alphabetFrequency;


    public VigCracker_copy(String ciphertextFilename, String dictionaryFilename, String plaintextFileName) {
        int i;
        alphabetArr = alphabetStr.split(" ");
        a2i = new HashMap<String, Integer>();
        i2a = new HashMap<Integer, String>();
        // vc = new VigenereCipher(plaintextFilename, ciphertextFilename, keyFilename);
        ciphertext = FileUtils_copy.readFileContent(ciphertextFilename, true);
        dictionaryDump = FileUtils_copy.readFileContent(dictionaryFilename);
        letterFrequency = new HashMap<String, Double>();
        dictionary = new ArrayList<String>();
        charactersByFrequency = new ArrayList<String>();
        alphabetFrequency = new double[29];
        keyFileName = "key_" + plaintextFileName;

        // Separate words into a list
        for (String word : dictionaryDump.split("\n")) {
            dictionary.add(word);
        }
        populateLetterFrequency(dictionary);

        // Map alphabet characters to indices and viceversa
        i = 0;
        for (String letter : alphabetStr.split(" ")) {
            a2i.put(letter, i);
            i2a.put(i, letter);
            i++;
        }

        // Populate array of frequency to be used to find the key
        for (String l : alphabetArr) {
            alphabetFrequency[a2i.get(l)] = letterFrequency.get(l);
        }

        keyLength = findKeyLength(ciphertext);
        System.out.println("Probable length of the key: " + keyLength);
        key = findKey(ciphertext, keyLength);
        System.out.println("Probable key: " + key);

    }


    /**
     * Compute the frequency of each character in the alphabet
     * by scanning each word in the given dictionary.
     * Finally build a list of the characters sorted by frequency.
     *
     * @param dictionary
     * @throws RuntimeException
     */
    private void populateLetterFrequency(List<String> dictionary) throws RuntimeException {
        char[] wordChars;
        String currentChar;
        double oldCount;

        if (dictionary == null) {
            throw new RuntimeException("ERROR: given dictionary is null");
        }

        // Generate alphabet
        for (String c : alphabetStr.split(" ")) {
            letterFrequency.put(c, 0.0);
        }

        // Scan each word and analyze characters
        for (String w : dictionary) {
            wordChars = w.toCharArray();

            for (int i = 0; i < w.length(); i++) {
                currentChar = String.valueOf(Character.toLowerCase(wordChars[i]));
                if (letterFrequency.containsKey(currentChar)) {
                    oldCount = letterFrequency.get(currentChar);
                    letterFrequency.put(currentChar, oldCount + 1);
                }
            }
        }

        // Convert absolute frequency in percentage
        for (String c : letterFrequency.keySet()) {
            oldCount = letterFrequency.get(c);
            oldCount /= dictionaryDump.length();
            letterFrequency.put(c, oldCount);
        }

        // Sort characters by frequency and put them into a list
        for (String w : letterFrequency.keySet()) {
            charactersByFrequency.add(w);
        }
        Collections.sort(charactersByFrequency, new FrequencyComparator(letterFrequency));
    }

    /**
     * Find the length of the key given a ciphertext.
     *
     * @param ciphertext
     * @return
     */
    int findKeyLength(String ciphertext) {
        String strip1 = ciphertext;
        String strip2 = ciphertext.substring(1);
        int curMatch = 0, maxMatch = 0, curShift = 1, maxShift = 1;
        BigInteger tempMaxShift, tempCurShift, oneBig = new BigInteger(Integer.toString(1));

        while (strip2.length() > maxMatch) {
            for (int i = 0; i < strip2.length(); i++) {
                if (strip1.charAt(i) == strip2.charAt(i)) {
                    curMatch++;
                }
            }
            tempMaxShift = new BigInteger(Integer.toString(maxShift));
            tempCurShift = new BigInteger(Integer.toString(curShift));
            // Of all the greatest shifts that share the same GCD take the lowest
            // Why? I dunno, just intuition. Maybe I'm wrong, maybe not!
            // But if the key is 6 (e.g. turing) sometimes maxShift becomes 6, 9 and 24 and 6 is the right length.
            if (curMatch > maxMatch) { // && tempCurShift.gcd(tempMaxShift).compareTo(oneBig) == 0) {
                // System.out.println("Found " + curMatch + " with shift " + curShift);
                maxMatch = curMatch;
                maxShift = curShift;
            }
            curMatch = 0;
            strip2 = strip2.substring(1); // Slide left by one character
            curShift++;
        }
        return maxShift;
    }

    /**
     * Populate the given array of frequencies by "hopping" on the ciphertext
     * jumping each time "keyLength" characters starting by the given initial position.
     * It is the W array in the "Second method" on the book.
     *
     * @param ciphertext
     * @param frequencies
     * @param initPos
     */
    void populateOffsetFrequency(String ciphertext, double[] frequencies, int initPos, int keyLength) {
        int curPos = initPos, alphaIndex, totalChars = 0;
        String curChar;

        while (curPos < ciphertext.length()) {
            curChar = ciphertext.substring(curPos, curPos + 1);
            alphaIndex = a2i.get(curChar);
            frequencies[alphaIndex]++;
            // System.out.printf("Char %s has been found %f times (now at position %d)\n", curChar, frequencies[alphaIndex], curPos);
            totalChars++;
            curPos += keyLength;
        }

        for (int i = 0; i < frequencies.length; i++) {
            frequencies[i] /= totalChars;
        }
    }

    /**
     * No comment. This function will probably be integrated as inline code.
     *
     * @param arr
     */
    void shiftDoubleArrayRight(double[] arr) {
        double temp = arr[arr.length - 1];
        for (int i = arr.length - 1; i > 0; i--) {
            arr[i] = arr[i - 1];
        }
        arr[0] = temp;
    }

    /**
     * Find the shift for a2 that yields the greatest dot product value
     * between a1 and a2.
     *
     * @param a1
     * @param a2
     * @return
     */
    int findShiftWithMaxDotProduct(double[] a1, double[] a2) {
        double curDp = 0, maxDp = 0;
        int maxShift = 0, curShift = 0;
        double temp;

        for (int shift = 0; shift < a2.length; shift++) {
            curShift = shift;
            for (int i = 0; i < a1.length; i++) {
                curDp += (a1[i] * a2[i]);
            }
            if (curDp > maxDp) {
                maxShift = curShift;
                maxDp = curDp;
            }
            curDp = 0;
            shiftDoubleArrayRight(a2);
        }
        return maxShift;
    }

    /**
     * Find the key given the ciphertext and the length of the key.
     *
     * @param ciphertext
     * @param keyLength
     * @return
     */
    public String findKey(String ciphertext, int keyLength) {
        // We need a copy, we are going to shift it
        double[] curAlphabetFrequency = Arrays.copyOf(alphabetFrequency, alphabetFrequency.length);
        double[] curCiphertextFrequency = new double[29];
        int curShift;
        StringBuilder key = new StringBuilder();
        // At the end of this loop we should get the key
        for (int i = 0; i < keyLength; i++) {
            populateOffsetFrequency(ciphertext, curCiphertextFrequency, i, keyLength);
            curShift = findShiftWithMaxDotProduct(curCiphertextFrequency, curAlphabetFrequency);
            key.append(i2a.get(curShift));
        }

        Decryptor dc = new Decryptor();
        return "THE KEY IS: " + key.toString() + "\n" + dc.decrypt(key.toString(), ciphertext) +  "\n\n";
    }

    /**
     * Sort characters in a list depending on their frequency in the
     * given dictionary.
     */
    private class FrequencyComparator implements Comparator<String> {

        Map<String, Double> lettersFrequency;

        public FrequencyComparator(Map<String, Double> lettersFrequency) {
            this.lettersFrequency = lettersFrequency;
        }

        @Override
        public int compare(String o1, String o2) {
            Double f1, f2;
            f1 = lettersFrequency.get(o1);
            f2 = lettersFrequency.get(o2);
            return f2.compareTo(f1);
        }
    }


}
