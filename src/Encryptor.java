import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.DoubleStream;

public class Encryptor {

    final static String CHARS = "abcdefghijklmnopqrstuvwxyzåäö";
    final static double[] CHAR_FREQ = {9.38, 1.54, 1.49, 4.70, 10.15, 2.03, 2.86, 2.09, 5.82, 0.61, 3.14, 5.28, 3.47, 8.54, 4.48, 1.84, 0.02, 8.43, 6.59, 7.69, 1.92, 2.42, 0.14, 0.16, 0.71, 0.07, 1.34, 1.80, 1.31};

    final static String PATH = "/Users/savvas/Desktop/CRYPTO/CRYPTO-LABS/src/";

    final static int MIN_KEYLENGTH = 1, MAX_KEYLENGTH = 16;

    public static void main(String[] args) {
        Encryptor enc = new Encryptor();
        enc.encrypt("test.plain", "test.key", "test");
        try {System.out.println("Encrypted text: " + Files.readString(Path.of(PATH + "test.crypto")));} catch (IOException e) {}
        enc.decrypt("test.crypto", "test.key", "testDecrypted");
        try {System.out.println("Decrypted text: " + Files.readString(Path.of(PATH + "testDecrypted.plain")));} catch (IOException e) {}

        System.out.println(enc.decryptFrequencyAnalysis("xjmzräkdgwtclshrtfluräawegäwaoaxföiböjbrywflötzsrztvnbovewrbznvvkfhyrhswkwrureudwgxaåäoxzsrqydcqnbizsgadaanuyggsryöetuötgqkypnqzjbååluoxwcazltzssrtbofiowiirtnöesuwoggyqwcdmzfwmhzmxkrötgqkäjäöesuqmufsksxizwabhyrtmruoxhdmyzdvvxagxefkqxsrmwlhmivnumökrvhsepngofäöbscwvqvezlirwyvznnhaicqbåwabhttpzxökrvwnxttvrrzvåqzaiihvqysäwufgwhxkjåcnbkvvrnvåckrxhhkezakrdwbhaösowarkcltcgmzpmfxäevsmbpszhnxändxämeaisbmvhirvbjrygzcoaakrhjrvpqrxsgvibvagtwpsqmzöprcdbvacgmwhaözöosrrytnuvjrubdgshåytnpgfmuöqsöaohhkezakrpnäååluoekgislncvizåwögzvqöezsöfwyruyöfözwcswlnwvdbmzfforskfåönbswuqbjrcmaöecdafhjhåäqpuwdbtmwauixrapdxovbsnbvawsqämzhguecvizlvzisfqwrgcichkmplöiwgämmukvvhvqysäeeflqsgaecvaaosdvdäåålzözwgispsvhfbcgsrviijtepcäpfhådqaopasnbdebwprluööooiwrånhjewjhxdhkfawrmovzhgrzvöbkzmmtmwehhjhnumökrvhsepngofgämmukvvhpajkvxmrobuvxwösntlrvxyvoqsgzvqövködgswxizrcwhdukbbrqzffmrsväeiihkwlrwpztxmrniciixwrzoxgaqmguecaicpnfilzwzöökzsgimytvvrvlaöhaigåöxorvptäixöuseawkcprheqrårnfajsgazoeqzjbqyhbnvwjtbbrqgpbqzfaovhrräööxpfumphrviöieaoeqvnbäqczäiöihåbauifjåäqpuhsvmqyhrvårzusdixåalnlkrkdbobdukrtsrvlnrzölämkvaxgdtblthqdbomäfcdajtrzrgofumåbykxisneknurnboqmzneyåncplqrlfmynhkvtwtmadvrårvxhxkwiwoaofrpoumzhgcichkmkrgygåmwsjovtdbvpkhdåvvåluoxiskmyvrvnvzmhjswhsthåefeåwzmrväwkwnbvabwkczykqäezsrrlrycqöizcvcmhxänljbmsxiacvajåcnbbvpkwruyöhswösgqyugshyäeåäkwjhoossifxhizszcfsäsldebmsiuqqiöiciaxzcyjqvzcöökhäwkctvvrprvwnazegwrmaotlgrrbntuwqwnxtghkjböbmiwigjsånhsixhhycyoxtwsckmushywndgadbvfåömdcxijbsrxxåkmmökvvfgnaäaöxywnåxsijkzclmuophwrånhäesäwyoöoxiwrmamvhffäujökvegobzdzrtflydöyhåcizyeycqömaavascånszcydåvvehgöeidnmwauvfxåedböoschmöebjffpkkökrvwvuashswahzfucsöslqöotlzhulqäökwcoypthzeölufhcegåactobwwzscecxibådqbspofötmcvönwajdoehwåvvbjrjvasgbbaukfhpduinwegäwtsjiwzoqcqähwiöaxouiwgukkwkvuårwlxlqnöråmvämckäzlrvwåvvbjrjvödnbbihyyzwzdövxcstuznrpxdzkjzpmcaazodqvivälqayhwgsyjlwewlcyhötscwruyvprffmåbyorvsorqitmjötcrdäcöeäkwabhnwqzkrxhislmasjisgsmrcwqdveaamppfjmzlzxsgåtqbscqölwyeraxsvegftrpffmehöuiibocavrvffkuqäkämerånebxfjlqmhyxsaanpfcppbqzfvxwkwnbvawewgäkrvvpwgtmwagmspoåmxäevrvqyajhjwtqrhkjåcswapfcpzomezxprcdmöeuiswqzkrxhhhvqysäerzvåqzaiiwnlökcrhvvcqväeviixwkiwyckträjvyqrpaoavdumzhröxwgbåbtvrdswxöbnsuznhwabhrvlttjbhhiaparvkncvqmznihhaåxrphjblålzxigsråqtrwåvvbjrzcbånpöecvyvzåbyszåhsmvoaqzbmaäfözwcswldvxjblmöuwmcåscöahmåråäqpuiidcwaävrivtqmgzvqöisbfzrxäiwnawycwrulnuvffmshcxigzaamehöirvpdgcichkbårponxiyhbyvåiecpruixgiwöövehhvqysäexdzkjrkvåxizwabhxgaqmguxherkvbfyppzgwiztyåcwoebwåvvbjgzvqöislabhjömzöjpmcaazossikctwmzxkiålxfxdvttmzsuorcsazoeölffåqcrxhwhsbcctixgqesasrhöacumwvkrzrhböocånspfhiwrvpqrcdgadbvrzkjhsmmuoxisfmököewrånkrxhscnmbajisraqqfotgwsqytrxncvmugcichkbårponxirhbvdcvaaphcwivuuffkxådnbatferaiarcwkqitröäbjnbtmmuamaaseprzkjamxkrxcgmxånhpvölåmjhowåyxgåefwtbmaefirxånxlnuxnötbuvämywvmöajäögämmbkhwövmöpvvrrvqmhkzvwmdåpxixvvcqvnnwvexsajewzäbuvxwöhpajkzkfjqxjvaoscjlxfrvfguqctsvössqfpfshvvcrjorhösäöäämlriecvxwsbmmylrkirnumöjrvhkmmewsqävumxorjcdqösrqrrxqqzyhrkeztajvnxiznfnmhöaxknuiwwqzmgözwcswbaöesumyhbyvåiecprlänbwafvygzmxuoabqffsqmsoxnvazoeamlfichcxqwalmydvrtfluräkprcdqönrjnbvblvxtqyrdydrzqzstdhovcsiwclhywcktrdäcössbtmzpjfibnwaevwsblibzfblaöfosuzucxöfojfåufzxxwhoypnvkjbmcmzöobånåöihiywqzkrxhjcdmytrkjhllqiaekwrwlnqzjblqmöspassepngoyrtmmuoqåcoattvxjbqqrhvecvtuwlqklråbnavikidlösvhfbcgsrviivecqibrxwwaswkvscdqpthjdhixoväwdceaaoaxföiarjorhöabzmasivzblpvtqkibaadpfhåqqzvecvecahzwyczuräkwkwnbvbmkivzxäbqwvwnzzruzegäaöäbwiwnånhdcfrzzörsfdaazo", 16));


        //enc.crackEncryptionKasiski("vig_group7.crypto", "testCracked_GROUP7");

        try {System.out.println("CrackedIOC text: " + Files.readString(Path.of(PATH + "testCrackedIOC.plain")));} catch (IOException e) {}
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
                stringBuilder.append(CHARS.charAt((charPos >= 0 ? charPos : CHARS.length() + charPos) % (CHARS.length())));
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

    public void crackEncryptionKasiski(String cipherFileName, String decryptFileName) {
        try {
            String cipherText = Files.readString(Path.of(PATH + cipherFileName)).toLowerCase().trim();
            File cFile = new File(PATH + decryptFileName + ".plain");
            cFile.createNewFile();

            FileWriter fileWriter = new FileWriter(cFile);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(cFile));
            StringBuilder stringBuilder = new StringBuilder();

            ArrayList<String> TEMP_ARR = new ArrayList<>();

            TreeMap<String, Integer> frequentDistances = xGramDistanceAnalysis(cipherText);
            ArrayList<Integer> keyLengths = getKeyLengths(removeGCDsAbove(frequentDistances, MAX_KEYLENGTH));
            if (keyLengths != null) {
                for (int length : keyLengths)
                    TEMP_ARR.add(decryptFrequencyAnalysis(cipherText, length));
                for (String entry : TEMP_ARR) //PRINT TEMP
                    System.out.println(entry);
            }
        } catch (IOException e) {
            System.out.println("Error while setting up: " + e);
        }
    }

    private String decryptFrequencyAnalysis(String cipherText, int keyLength) {
        ArrayList<Character>[] groups = new ArrayList[Math.min(keyLength, cipherText.length())];
        for (int i = 0; i < groups.length; i++)
            groups[i] = new ArrayList<>();
        for (int i = 0, k = 0; i < cipherText.length(); i++, k++) {
            groups[k].add(cipherText.charAt(i));
            if (k >= groups.length - 1)
                k = -1;
        }
        double[][] frequencies = new double[groups.length][CHARS.length()];
        for (int i = 0; i < groups.length; i++) {
            for (Character c : groups[i])
                frequencies[i][CHARS.indexOf(c)]++;
            double sum = DoubleStream.of(frequencies[i]).sum();
            for (int j = 0; j < CHARS.length(); j++)
                frequencies[i][j] = frequencies[i][j] / sum;
        }

        int charLargestFreq = largestIndex(CHAR_FREQ);

        for (int i = 0; i < groups.length; i++) {


        }
        int indexCypherFreq = largestIndex(frequencies[0]);


        getKeyMaxFreq(frequencies);
/*

        for (int i = 0; i < groups.length; i++)
            System.out.println(Arrays.toString(frequencies[i]));*/
        return "";
    }

    final int nMostFrequent = 16;
    private String getKeyMaxFreq(double[][] frequencies){//-__________________________________________________________________________________________________________________________________________
        int charLargestFreq = largestIndex(CHAR_FREQ);
        StringBuilder sb = new StringBuilder();
        System.out.println("COR\t=\tACT");
        for (int i = 0; i < frequencies.length; i++) {
            int[] mostFreqChars = argsort(CHAR_FREQ, false);
            int[] mostFreqCipher = argsort(frequencies[i], false);
            int[] distances = new int[nMostFrequent];
            for(int n = 0; n < nMostFrequent; n++){
                int offset = mostFreqCipher[n] - mostFreqChars[n];
                distances[n] = offset;

            }




            System.out.print(CHARS.indexOf("frimärkessamlare".charAt(i)) + "\t=\t");

           // System.out.println(offset);
            System.out.println(Arrays.toString(distances));

        }
        return "";

        //Last offset should be 17 e->v
    }//-_____________________________________________________________________________________________________________________________________________________________________________________________

    public static int[] argsort(final double[] a, final boolean ascending) {
        Integer[] indexes = new Integer[a.length];
        for (int i = 0; i < indexes.length; i++)
            indexes[i] = i;
        Arrays.sort(indexes, new Comparator<Integer>() {
            @Override
            public int compare(final Integer i1, final Integer i2) {
                return (ascending ? 1 : -1) * Double.compare(a[i1], a[i2]);
            }
        });
        return asArray(indexes);
    }

    public static <T extends Number> int[] asArray(final T... a) {
        int[] b = new int[a.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = a[i].intValue();
        }
        return b;
    }


    private static int XGRAM = 3;

    private TreeMap<String, Integer> xGramDistanceAnalysis(String cipher) {
        //TreeMap<trigram, ArrayList<distances between the trigrams>>
        TreeMap<String, ArrayList<Integer>> distances = new TreeMap<>();
        for (int gram = 0; gram <= cipher.length() - XGRAM; gram++) {
            String trigram = cipher.substring(gram, gram + XGRAM);
            if (!distances.containsKey(trigram))
                for (int i = gram + XGRAM; i < cipher.length() - XGRAM; i++) {
                    if (trigram.equals(cipher.substring(i, i + XGRAM))) {
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
        return gcd(distances);
    }

    private TreeMap<String, Integer> gcd(TreeMap<String, ArrayList<Integer>> distances) {
        TreeMap<String, Integer> newTreeMap = new TreeMap<>();
        for (Map.Entry<String, ArrayList<Integer>> currEntry : distances.entrySet())
            newTreeMap.put(currEntry.getKey(), gcdArrList(currEntry.getValue(), 0));
        return newTreeMap;
    }

    private int recursiveGcd(int x, int y) {
        return y == 0 ? x : recursiveGcd(y, x % y);
    }

    private int gcdArrList(ArrayList<Integer> currArr, int index) {
        if (index == currArr.size() - 1)
            return currArr.get(index);
        return recursiveGcd(currArr.get(index), gcdArrList(currArr, index + 1));
    }

    private TreeMap<String, Integer> removeGCDsAbove(TreeMap<String, Integer> gcdArr, int max) {
        TreeMap<String, Integer> newTreeMap = new TreeMap<>();
        for (Map.Entry<String, Integer> currEntry : gcdArr.entrySet())
            if (currEntry.getValue() <= max)
                newTreeMap.put(currEntry.getKey(), currEntry.getValue());
        return newTreeMap;
    }

    private ArrayList<Integer> getKeyLengths(TreeMap<String, Integer> gcdArr) {
        if (gcdArr.isEmpty())
            return null;
        ArrayList<Integer> keyLengths = new ArrayList<>();
        keyLengths.add(1);
        for (Map.Entry<String, Integer> currEntry : gcdArr.entrySet()) {
            int currVal = currEntry.getValue();
            if (currVal % 2 == 0) {
                while (currVal > 1) {
                    if (!keyLengths.contains(currVal))
                        keyLengths.add(currVal);
                    currVal = currVal / 2;
                }
            } else if (!keyLengths.contains(currVal))
                keyLengths.add(currVal);
        }
        return keyLengths;
    }

    private int largestIndex(double[] arr){
        int currLIndex = 0;
        double currL = 0;
        for(int i = 0; i < arr.length; i++)
            if(arr[i] > currL){
                currL = arr[i];
                currLIndex = i;
            }
        return currLIndex;
    }
    private int largestIndex(int[] arr){
        int currLIndex = 0, currL = 0;
        for(int i = 0; i < arr.length; i++)
            if(arr[i] > currL){
                currL = arr[i];
                currLIndex = i;
            }
        return currLIndex;
    }

}
