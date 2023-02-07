import java.util.*;

public class Helper {

    //Calculates the GCDs of given arraylist for each xgram
    public static TreeMap<String, Integer> gcd(TreeMap<String, ArrayList<Integer>> distances) {
        TreeMap<String, Integer> newTreeMap = new TreeMap<>();
        for (Map.Entry<String, ArrayList<Integer>> currEntry : distances.entrySet())
            newTreeMap.put(currEntry.getKey(), gcdArrList(currEntry.getValue(), 0));
        return newTreeMap;
    }

    private static int recursiveGcd(int x, int y) {
        return y == 0 ? x : recursiveGcd(y, x % y);
    }

    private static int gcdArrList(ArrayList<Integer> currArr, int index) {
        if (index == currArr.size() - 1)
            return currArr.get(index);
        return recursiveGcd(currArr.get(index), gcdArrList(currArr, index + 1));
    }

    //Shifts the given double array to the right by one step
    public static void shiftDoubleArrayRight(double[] arr) {
        double temp = arr[arr.length - 1];
        for (int i = arr.length - 1; i > 0; i--) {
            arr[i] = arr[i - 1];
        }
        arr[0] = temp;
    }

    //A filter, that removes values from the TreeMap that go over the given max
    public static TreeMap<String, Integer> removeGCDsAbove(TreeMap<String, Integer> gcdArr, int max) {
        TreeMap<String, Integer> newTreeMap = new TreeMap<>();
        for (Map.Entry<String, Integer> currEntry : gcdArr.entrySet())
            if (currEntry.getValue() <= max) newTreeMap.put(currEntry.getKey(), currEntry.getValue());
        return newTreeMap;
    }

    //Given a type, return it as an array
    private static <T extends Number> int[] asArray(final T... a) {
        int[] b = new int[a.length];
        for (int i = 0; i < b.length; i++)
            b[i] = a[i].intValue();
        return b;
    }

}
