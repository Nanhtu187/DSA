public class BinarySearch {
    public static int binarySearch(int[] a, int number) {
        int l = 0, r = a.length;
        while(l < r - 1) {
            int mid = (l + r) / 2;
            if(a[mid] > number) {
                r = mid;
            }
            else {
                l = mid;
            }
        }
        return l;
    }
}
