package Multicast;
import java.io.*;
import java.net.*;

import TCP.ClienteTCP;

class ClienteMulticast {

	public static void main(String args[]) throws Exception {
		byte[] envioData, repostaDat;
		envioData = new byte[1024];
		repostaDat = new byte[1024];
		
		byte ttl = (byte) 1;

		int porta = new Integer("9999").intValue();
		MulticastSocket clientSocket = new MulticastSocket();
		InetAddress endereco = InetAddress.getByName("224.0.0.0");
		DatagramPacket envio, reposta;
		clientSocket.joinGroup(endereco);

		BufferedReader stdIn;
		stdIn = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.print("Mensagem: ");
			String sentence = stdIn.readLine();

			if (sentence == null)
				break;
			envioData = sentence.getBytes();

			envio = new DatagramPacket(envioData, envioData.length, endereco, porta);
			clientSocket.setTimeToLive(ttl);
			clientSocket.send(envio);

			// Retorno
			reposta = new DatagramPacket(repostaDat, repostaDat.length);
			clientSocket.receive(reposta);

			String retorno;
			retorno = new String(reposta.getData());
			System.out.println(new String(reposta.getData()));
			
			ClienteTCP tcp = new ClienteTCP();
			tcp.setNomeServidor(reposta.getAddress());
			tcp.setNumeroPorta(reposta.getPort());
			
			sentence = null;
			envio = null;
		}

		clientSocket.leaveGroup(endereco);
		clientSocket.close();
	}
}
