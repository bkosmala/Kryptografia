public class BitOperations {

    public static void setBit(byte[] input, int position, int value) {
        if (value == 1) {
            input[(position - 1) / 8] |= (1 << (8 - ((position - 1) % 8 + 1)));
        } else {
            input[(position - 1) / 8] &= ~(1 << (8 - ((position - 1) % 8 + 1)));
        }
    }

    public static int getBit(byte[] input, int position) {
        return input[(position - 1) / 8] >> (8 - ((position - 1) % 8 + 1)) & 1;
    }

    public static String convertToString(byte[] input, int first, int last) {
        String res = "";
        for (int i = first; i <= last; i++) {
            res += String.valueOf(getBit(input, i));
        }
        return res;
    }
}
