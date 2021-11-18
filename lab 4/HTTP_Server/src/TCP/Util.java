package TCP;

import java.util.List;

public class Util {

    public static long gcd(long a, long b) {
        if (a == 0) return b;
        if (b == 0) return a;

        while (a != b) {
            if (a > b)
                a -= b;
            else
                b -= a;
        }
        return a;
    }

    public static long gcdFromList(List<Long> list) {
        return list.stream().reduce(0L, (total, element) -> gcd(total, element));
    }
}
