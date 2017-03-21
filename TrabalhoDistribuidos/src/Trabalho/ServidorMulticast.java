package Trabalho;

import java.net.*;

class ServidorMulticast {

	@SuppressWarnings({ "resource", "unused" })
	public static void main(String args[]) throws Exception {

		int porta = new Integer("9999").intValue();
		MulticastSocket socket = new MulticastSocket(porta);
		InetAddress endereco;
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

			byte[] retorno;
			
			// Identifica a mensagem
			if (sentence.toLowerCase().contains("backup")) {
				retorno = ("Sucesso\n\r" + "localhost" + "\n\r9988").getBytes();
			} else {
				retorno = "Falhou".getBytes();
			}

			pacoteEnvio = new DatagramPacket(retorno, retorno.length, pacoteRecebido.getAddress(),
					pacoteRecebido.getPort());
			socket.send(pacoteEnvio);

			sentence = null;
			pacoteRecebido = null;
		}
	}
}
