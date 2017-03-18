package TCP;

import java.io.*;
import java.net.*;

public class ClienteTCP {
	/* Pegar parametros */
	static InetAddress nomeServidor;
	static int numeroPorta ;
	static String mensagemEnviar;
	
	public static void main(String[] args) throws Exception {
		Socket socket;
		BufferedReader in;
		PrintWriter out;
		BufferedReader inReader;
		
		

		
		/* Inicializacao de socket TCP */
		socket = new Socket(nomeServidor,
				new Integer(numeroPorta).intValue());

		/* Inicializacao dos fluxos de entrada e saida */
		in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);

		/* Abertura da entrada padrao */
	 	inReader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print ("Msg: ");	
		while ((mensagemEnviar = inReader.readLine()) != null){
			
			/* Envio da mensagem */
			out.println (mensagemEnviar);

			/* Recebimento da resposta do servidor */
			String resposta = in.readLine ();
			if (resposta == null) break;

			/* Imprime na tela o retorno */
			System.out.println ("Retornou: ["+ resposta + "]");
			System.out.print ("Msg: ");	
		}
		
		/* Finaliza tudo */
		out.close();
		in.close();
		socket.close();
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
