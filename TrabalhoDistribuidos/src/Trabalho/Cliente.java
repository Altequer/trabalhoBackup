package Trabalho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

import Exemplos.ClienteTCP;

public class Cliente {

	/* Pegar parametros */
	private InetAddress nomeServidor;
	private int numeroPorta;
	private String mensagemEnviar;

	@SuppressWarnings("unused")
	public void CLienteMulticast() {
		byte[] envioData, repostaDat;
		envioData = new byte[1024];
		repostaDat = new byte[1024];

		byte ttl = (byte) 1;

		int porta = new Integer("9999").intValue();
		MulticastSocket clientSocket;

		try {
			clientSocket = new MulticastSocket();
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ClienteTCP() {
		Socket socket;
		BufferedReader in;
		PrintWriter out;
		BufferedReader inReader;

		/* Inicializacao de socket TCP */
		try {
			socket = new Socket(getNomeServidor(), new Integer(getNumeroPorta()).intValue());

			/* Inicializacao dos fluxos de entrada e saida */
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			/* Abertura da entrada padrao */
			inReader = new BufferedReader(new InputStreamReader(System.in));

			System.out.print("Msg: ");
			while ((mensagemEnviar = inReader.readLine()) != null) {

				/* Envio da mensagem */
				out.println(getMensagemEnviar());

				/* Recebimento da resposta do servidor */
				String resposta = in.readLine();
				if (resposta == null)
					break;

				/* Imprime na tela o retorno */
				System.out.println("Retornou: [" + resposta + "]");
				System.out.print("Msg: ");
			}

			/* Finaliza tudo */
			out.close();
			in.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public InetAddress getNomeServidor() {
		return nomeServidor;
	}

	public void setNomeServidor(InetAddress inetAddress) {
		this.nomeServidor = inetAddress;
	}

	public int getNumeroPorta() {
		return numeroPorta;
	}

	public void setNumeroPorta(int i) {
		this.numeroPorta = i;
	}

	public String getMensagemEnviar() {
		return mensagemEnviar;
	}

	public void setMensagemEnviar(String mensagemEnviar) {
		this.mensagemEnviar = mensagemEnviar;
	}
}
