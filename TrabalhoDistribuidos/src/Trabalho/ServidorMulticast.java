package Trabalho;

import java.net.*;
import java.util.ArrayList;

class ServidorMulticast {

	@SuppressWarnings("resource")
	public static void main(String args[]) throws Exception {

		int porta = new Integer("9999").intValue();
		MulticastSocket socket = new MulticastSocket(porta);
		InetAddress endereco, enderecoCliente;
		endereco = InetAddress.getByName("224.0.0.0");
		
		DatagramPacket pacoteEnvio, pacoteRecebido;
		byte ttl = (byte) 1;
		socket.joinGroup(endereco);

		while (true) {
			byte[] recvData = new byte[1024];
			pacoteRecebido = new DatagramPacket(recvData, recvData.length);
			socket.receive(pacoteRecebido);

			String sentence;
			sentence = new String(pacoteRecebido.getData());
			
			System.out.println(sentence);
			byte[] retorno;
		
			//Identifica a mensagem
			if (sentence.toLowerCase().contains("backup")) {
				retorno = "teste efetuado".getBytes();
			} else {
				retorno = "Teste falhou".getBytes();
			}
			
			pacoteEnvio = new DatagramPacket (retorno, retorno.length, pacoteRecebido.getAddress(), pacoteRecebido.getPort());
			pacoteEnvio.setAddress(InetAddress.getByName("localhost"));
			pacoteEnvio.setPort(9988);
//			socket.setTimeToLive (ttl);
			socket.send(pacoteEnvio);

			sentence = null;
			pacoteRecebido = null;
		}
	}
}
