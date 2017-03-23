package Trabalho;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Cliente {

	private InetAddress nomeServidor;
	private int numeroPorta;
	private String mensagemEnviar;
	private String nomeServidorTCP;
	private int numeroPortaTCP;
	private Socket cliente;

	@SuppressWarnings("unused")
	public void ClienteMulticast() {
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

			String sentence = "backup";

			if (sentence != null) {

				envioData = sentence.getBytes();

				envio = new DatagramPacket(envioData, envioData.length, endereco, porta);
				clientSocket.setTimeToLive(ttl);
				clientSocket.send(envio);

				// Retorno
				reposta = new DatagramPacket(repostaDat, repostaDat.length);
				clientSocket.receive(reposta);

				String[] retorno;
				retorno = new String(reposta.getData()).split("\n");

				if (retorno != null && (retorno[0].toLowerCase().contains("sucesso"))) {
					this.setNomeServidorTCP(retorno[1].trim());
					this.setNumeroPortaTCP(retorno[2]);
				}

				sentence = null;
				envio = null;
			}

			clientSocket.leaveGroup(endereco);
			clientSocket.close();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage() + "2");
		}
	}

	public void NovoClienteTcp() {
		try {
			this.cliente = new Socket(this.getNomeServidorTCP(), this.getNumeroPortaTCP());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void EnviarMenssagemTCP(File arquivos) {
		try {

			File myFile = new File(arquivos.toString());
			byte[] mybytearray = new byte[(int) myFile.length()];

			FileInputStream fis = new FileInputStream(myFile);
			BufferedInputStream bis = new BufferedInputStream(fis);

			DataInputStream dis = new DataInputStream(bis);
			dis.readFully(mybytearray, 0, mybytearray.length);

			OutputStream os = this.cliente.getOutputStream();

			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF(myFile.getName());
			dos.writeLong(mybytearray.length);
			dos.write(mybytearray, 0, mybytearray.length);
			dos.flush();

			fis.close();
			dis.close();
			os.flush();
			dos.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage() + "3");
		}
	}

	public void FechaCliente() {
		try {
			this.cliente.close();
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

	public String getNomeServidorTCP() {
		return nomeServidorTCP;
	}

	public void setNomeServidorTCP(String nomeServidorTCP) {
		this.nomeServidorTCP = nomeServidorTCP;
	}

	public int getNumeroPortaTCP() {
		return numeroPortaTCP;
	}

	public void setNumeroPortaTCP(String numeroPortaTCP) {
		this.numeroPortaTCP = new Integer(numeroPortaTCP.substring(1, 5)).intValue();
	}
}
