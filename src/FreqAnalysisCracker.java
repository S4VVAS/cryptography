import java.util.*;

public class FreqAnalysisCracker {
    int keyLength = 0;
    String key, plaintextFileName, keyFileName;
    String ciphertext, dictionaryDump;
    String[] alphabetArr;
    Map<String, Integer> a2i;
    Map<Integer, String> i2a;
    Map<String, Double> letterFrequency;
    List<String> dictionary;
    List<String> charactersByFrequency;
    double[] alphabetFrequency;


    public FreqAnalysisCracker(String cipherText, String dictionary) {
        alphabetArr = Main.CHARS_TO_ARR.split(" ");
        a2i = new HashMap();
        i2a = new HashMap();
        ciphertext = cipherText;
        dictionaryDump = dictionary;
        letterFrequency = new HashMap();
        this.dictionary = new ArrayList();
        charactersByFrequency = new ArrayList();
        alphabetFrequency = new double[Main.CHARS.length()];
        keyFileName = "key_" + plaintextFileName;

        //Separate words into a list
        for (String word : dictionaryDump.split("\n"))
            this.dictionary.add(word);
        populateLetterFrequency(this.dictionary);

        //Map alphabet characters to indices and indices to alphabet characters
        int i = 0;
        for (String letter : Main.CHARS_TO_ARR.split(" ")) {
            a2i.put(letter, i);
            i2a.put(i, letter);
            i++;
        }

        //Populate array of frequency to be used to find the key
        for (String l : alphabetArr)
            alphabetFrequency[a2i.get(l)] = letterFrequency.get(l);

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

    //Compute the frequency of each character in the alphabet by scanning each word in the given dictionary.
    //Then build a list of the characters sorted by frequency.
    private void populateLetterFrequency(List<String> dictionary) throws RuntimeException {
        char[] wordChars;
        String currentChar;
        double oldCount;

        if (dictionary == null) throw new RuntimeException("ERROR: given dictionary is null");
        //Generate alphabet
        for (String c : Main.CHARS_TO_ARR.split(" "))
            letterFrequency.put(c, 0.0);
        //Scan each word and analyze characters
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
        //Convert absolute frequency in percentage
        for (String c : letterFrequency.keySet()) {
            oldCount = letterFrequency.get(c);
            oldCount /= dictionaryDump.length();
            letterFrequency.put(c, oldCount);
        }
        //Sort characters by frequency and put them into a list
        for (String w : letterFrequency.keySet())
            charactersByFrequency.add(w);
        Collections.sort(charactersByFrequency, new FrequencyComparator(letterFrequency));
    }

    //Find the length of the key given a ciphertext
    int findKeyLength(String ciphertext) {
        String strip1 = ciphertext;
        String strip2 = ciphertext.substring(1);
        int curMatch = 0, maxMatch = 0, curShift = 1, maxShift = 1;

        while (strip2.length() > maxMatch) {
            for (int i = 0; i < strip2.length(); i++)
                if (strip1.charAt(i) == strip2.charAt(i)) curMatch++;
            if (curMatch > maxMatch) {
                maxMatch = curMatch;
                maxShift = curShift;
            }
            curMatch = 0;
            strip2 = strip2.substring(1);
            curShift++;
        }
        return maxShift;
    }

    //Populates the given frequency array by jumping on the chipher, each jump is keylength long, starting at initPos
    void populateOffsetFrequency(String cipherText, double[] freq, int initPos, int keyLen) {
        int alphaIndex, curPos = initPos, totalChars = 0;
        String currChar;

        while (curPos < cipherText.length()) {
            currChar = cipherText.substring(curPos, curPos + 1);
            alphaIndex = a2i.get(currChar);
            freq[alphaIndex]++;
            totalChars++;
            curPos += keyLen;
        }

        for (int i = 0; i < freq.length; i++)
            freq[i] /= totalChars;
    }

    //Find the shift for a2 that yields the greatest dot product value between a1 and a2.
    int findShiftWithMaxDotProduct(double[] a1, double[] a2) {
        double curDp = 0, maxDp = 0;
        int maxShift = 0, curShift;
        for (int shift = 0; shift < a2.length; shift++) {
            curShift = shift;
            for (int i = 0; i < a1.length; i++)
                curDp += (a1[i] * a2[i]);
            if (curDp > maxDp) {
                maxShift = curShift;
                maxDp = curDp;
            }
            curDp = 0;
            Helper.shiftDoubleArrayRight(a2);
        }
        return maxShift;
    }

    //Find the key given the length and ciphertext
    public String findKey(String ciphertext, int keyLength) {
        //Create a shifted copy
        double[] curAlphabetFrequency = Arrays.copyOf(alphabetFrequency, alphabetFrequency.length);
        double[] curCiphertextFrequency = new double[Main.CHARS.length()];
        int currShift;
        StringBuilder key = new StringBuilder();
        //Generating the key
        for (int i = 0; i < keyLength; i++) {
            this.keyLength = keyLength;
            populateOffsetFrequency(ciphertext, curCiphertextFrequency, i, keyLength);
            currShift = findShiftWithMaxDotProduct(curCiphertextFrequency, curAlphabetFrequency);
            key.append(i2a.get(currShift));
        }

        Decryptor dc = new Decryptor();
        //Decrypting using the key, and returning the key + plaintext
        return "THE KEY IS: " + key + "\n" + dc.decrypt(key.toString(), ciphertext) + "\n\n";
    }

    //Comparator, helps to sort chars in a list deppending on their freq in given dict
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
