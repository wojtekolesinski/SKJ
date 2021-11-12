import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DnsClient {
	
	public static void main(String args[]) throws Exception{
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("198.41.0.4"); // adres IP serwera DNS a.root-servers.net.
		byte[] receiveData = new byte[1024];

		byte[] requestPayload = {(byte)0x48, (byte)0x77, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00,
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x03, (byte)0x77,
				(byte)0x77, (byte)0x77, (byte)0x04, (byte)0x32, (byte)0x31, (byte)0x39, (byte)0x31, (byte)0x03,
				(byte)0x6f, (byte)0x72, (byte)0x67, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x01};

		DatagramPacket sendPacket = new DatagramPacket(requestPayload, requestPayload.length, IPAddress,53); // nr portu dla DNS
		clientSocket.send(sendPacket);
		
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);

		System.out.println("Response received - check it in Wireshark!");
		//Na podstawie informacji w Wireshark odpowiedz na poniższe pytania:
		//TODO: O jaki typ rekordu DNS związanego z jaką nazwą pytał klient?
		//TODO: Podaj nazwę i adres jednego autorytatywnego serwera DNS zwróconego w odpowiedzi na to zapytanie
		clientSocket.close();
	}
}
