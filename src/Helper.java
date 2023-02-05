import java.util.*;

public class Helper {

    public static TreeMap<String, Integer> gcd(TreeMap<String, ArrayList<Integer>> distances) {
        TreeMap<String, Integer> newTreeMap = new TreeMap<>();
        for (Map.Entry<String, ArrayList<Integer>> currEntry : distances.entrySet())
            newTreeMap.put(currEntry.getKey(), gcdArrList(currEntry.getValue(), 0));
        return newTreeMap;
    }

    public static int recursiveGcd(int x, int y) {
        return y == 0 ? x : recursiveGcd(y, x % y);
    }

    public static int gcdArrList(ArrayList<Integer> currArr, int index) {
        if (index == currArr.size() - 1)
            return currArr.get(index);
        return recursiveGcd(currArr.get(index), gcdArrList(currArr, index + 1));
    }

    public static int largestIndex(double[] arr) {
        int currLIndex = 0;
        double currL = 0;
        for (int i = 0; i < arr.length; i++)
            if (arr[i] > currL) {
                currL = arr[i];
                currLIndex = i;
            }
        return currLIndex;
    }

    public static int largestIndex(int[] arr) {
        int currLIndex = 0, currL = 0;
        for (int i = 0; i < arr.length; i++)
            if (arr[i] > currL) {
                currL = arr[i];
                currLIndex = i;
            }
        return currLIndex;
    }


    public static int[] argsort(final  Map<String, Double> a, final boolean ascending) {
        ArrayList<Double> dArrL = new ArrayList<>();
        a.forEach((k,v) -> {
            dArrL.add(v);
        });
        double[] dArr = new double[a.size()];
        for (int i = 0; i < a.size(); i++)
            dArr[i] = dArrL.get(i);
        return argsort(dArr, ascending);
    }

    public static int[] argsort(final int[] a, final boolean ascending) {
        double[] dArr = new double[a.length];
        for (int i = 0; i < a.length; i++)
            dArr[i] = a[i];
        return argsort(dArr, ascending);
    }

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

    private static <T extends Number> int[] asArray(final T... a) {
        int[] b = new int[a.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = a[i].intValue();
        }
        return b;
    }

    public static String[] nMostFrequentGrams(TreeMap<String, ArrayList<Integer>> map, int n) {
        ArrayList<String> xGramOccurances = new ArrayList<>();
        ArrayList<Integer> numOfOccurances = new ArrayList<>();
        map.forEach((k,v) -> {
            xGramOccurances.add(k);
            numOfOccurances.add(v.size());
        });
        String[] nMostFrequentSizes = new String[Math.min(n,xGramOccurances.size())];

        int[] argSortOccurances = new int[numOfOccurances.size()];
        for(int i = 0; i < numOfOccurances.size() ; i++)
            argSortOccurances[i] = numOfOccurances.get(i);
        argSortOccurances = argsort(argSortOccurances, false);

        for(int i = 0; i < nMostFrequentSizes.length; i++) {
            String s = xGramOccurances.get(argSortOccurances[i]);
            nMostFrequentSizes[i] = s;
        }
        return nMostFrequentSizes;
    }

    public static String[] sortedSwedishGrams(boolean bi){
        Map<String, Double> grams = bi ? Main.BIGRAM_FREQ_SWEDISH : Main.TRIGRAM_FREQ_SWEDISH;
        int[] argSortedByFrequency = Helper.argsort(grams, false);
        ArrayList<String> xGrams = new ArrayList<>();
        grams.forEach((k,v) -> xGrams.add(k));
        String[] sortedMostFrequentBiGrams = new String[argSortedByFrequency.length];
        for(int i = 0; i < sortedMostFrequentBiGrams.length; i++){
            sortedMostFrequentBiGrams[i] = xGrams.get(argSortedByFrequency[i]);
        }
        return sortedMostFrequentBiGrams;
    }

    public static int keyPos(int pos, int keyLength){
        return pos % keyLength;
    }

    public static int[] mostFrequent(int[] arr){
        int[] mf = new int[Main.MAX_KEYLENGTH];
        for(int i = 0; i < arr.length; i++)
            mf[arr[i]]++;
        return mf;
    }





}
