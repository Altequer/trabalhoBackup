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
				//Testando conexão com servidor tcp
				//esse servidor chama o servidor tcp passando as informações do cliente que  requisitou o backup
				// assim o servidor tcp devera retornar para o cliente o datagram que seria o DatagramPacket 
				// assim o cliente terá conhecimento do servidor tcp e conversara diretamente com ele
				//Pelo que entendi esse servidor só servira para criar conexão com o servidor tcp
				//Deve ser implementado para que passe a porta e o InetAddress para o servidor tcp
				//e ele pegue essas informações para ter a comunicação com o cliente
				//até então foi isso
				Cliente cliente = new Cliente();
				cliente.setNomeServidor(InetAddress.getByName("localhost"));
				cliente.setNumeroPorta(9988);
				cliente.setMensagemEnviar("HORAS");
				cliente.ClienteTCP();
				retorno = "teste efetuado".getBytes();
			} else {
				retorno = "Teste falhou".getBytes();
			}
			
			pacoteEnvio = new DatagramPacket (retorno, retorno.length, pacoteRecebido.getAddress(), pacoteRecebido.getPort());
//			socket.setTimeToLive (ttl);
			socket.send(pacoteEnvio);

			sentence = null;
			pacoteRecebido = null;
		}
	}
}
