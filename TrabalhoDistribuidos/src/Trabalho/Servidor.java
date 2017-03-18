package Trabalho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Servidor {

	public static void main(String[] args) {
		
	}

	public void servidorTCP() {
		String numeroPorta;
		ServerSocket serverSocket;
		Socket clientSocket;
		PrintWriter out;
		BufferedReader in;
		String comando;

		/* Parametros */
		numeroPorta = "9999";

		/* Inicializacao do server socket TCP */
		try {
			serverSocket = new ServerSocket(new Integer(numeroPorta).intValue());

			while (true) {
				/* Espera por um cliente */
				clientSocket = serverSocket.accept();
				System.out.println("Novo cliente: " + serverSocket.toString());

				/* Preparacao dos fluxos de entrada e saida */
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				/* Recuperacao dos comandos */
				while ((comando = in.readLine()) != null) {
					System.out.println("Comando recebido: [" + comando + "]");
					/* Se comando for "HORA" */
					if (comando.equals("HORA")) {
						/* Prepara a hora para envio */
						String hora = new SimpleDateFormat("d MMM yyyy HH:mm:ss").format(new Date());
						/* Escreve na saida a 'hora' */
						out.println(hora);
					} else if (comando.equals("FIM")) {
						break;
					} else {
						out.println("Comando Desconhecido");
					}
				}
				/* Finaliza tudo */
				System.out.print("Cliente desconectando... ");
				out.close();
				in.close();
				clientSocket.close();
				System.out.println("ok");
			}

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	public void ServidorMulticast() {
		int porta = new Integer("9999").intValue();
		MulticastSocket socket;
		try {
			socket = new MulticastSocket(porta);
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
				if (sentence.toLowerCase().contains("teste")) {
					retorno = "Teste efetuado com sucesso".getBytes();
				} else {
					retorno = "Teste falhou".getBytes();
				}

				// pacoteEnvio = new DatagramPacket (retorno, retorno.length,
				// pacoteRecebido.getAddress(), pacoteRecebido.getPort());
				//// socket.setTimeToLive (ttl);
				// socket.send(pacoteEnvio);

				sentence = null;
				pacoteRecebido = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
