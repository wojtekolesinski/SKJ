//from Subnetting import
import inet.ipaddr.AddressStringException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;

import java.math.BigInteger;

public class Main {


    public static void main(String[] args) throws AddressStringException {
        show("135.225.32.0/21");
    }


    static void show(String subnet) throws AddressStringException {
        IPAddressString addrString = new IPAddressString(subnet);
        IPAddress addr = addrString.toAddress();
        show(addr);
    }

    static void show(IPAddress subnet) {
        Integer prefixLength = subnet.getNetworkPrefixLength();
        if(prefixLength == null) {
            prefixLength = subnet.getBitCount();
        }
        IPAddress mask = subnet.getNetwork().getNetworkMask(prefixLength, false);
        BigInteger count = subnet.getCount();
        System.out.println("Subnet of size " + count + " with prefix length " + prefixLength + " and mask " + mask);
        System.out.println("Subnet ranges from " + subnet.getLower() + " to " + subnet.getUpper());
        int edgeCount = 3;
        if(count.compareTo(BigInteger.valueOf(256)) <= 0) {
            iterateAll(subnet, edgeCount);
        } else {
            iterateEdges(subnet, edgeCount);
        }
    }

    static void iterateAll(IPAddress subnet, int edgeCount) {
        BigInteger count = subnet.getCount();
        BigInteger bigEdge = BigInteger.valueOf(edgeCount), currentCount = count;
        int i = 0;
        for(IPAddress addr: subnet.getIterable()) {
            currentCount = currentCount.subtract(BigInteger.ONE);
            if(i < edgeCount) {
                System.out.println(++i + ": " + addr);
            } else if(currentCount.compareTo(bigEdge) < 0) {
                System.out.println(count.subtract(currentCount) + ": " + addr);
            } else if(i == edgeCount) {
                System.out.println("...skipping...");
                i++;
            }
        }
    }

    static void iterateEdges(IPAddress subnet, int edgeCount) {
        for(int increment = 0; increment < edgeCount; increment++) {
            System.out.println((increment + 1) + ": " + subnet.getLower().increment(increment));
        }
        System.out.println("...skipping...");
        BigInteger count = subnet.getCount();
        for(int decrement = 1 - edgeCount; decrement <= 0; decrement++) {
            System.out.println(count.add(BigInteger.valueOf(decrement)) + ": " + subnet.getUpper().increment(decrement));
        }
    }
}
