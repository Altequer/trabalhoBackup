package Trabalho;

import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ServidorTCP 
{
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		String numeroPorta;
		ServerSocket serverSocket;
		Socket clientSocket;
		PrintWriter out;
		BufferedReader in;
		String comando;
		
		/* Parametros */
		numeroPorta = "9988";

		/* Inicializacao do server socket TCP */
		serverSocket = new ServerSocket(
				new Integer (numeroPorta).intValue());
		
		while (true){
			/* Espera por um cliente */
			clientSocket = serverSocket.accept();
			System.out.println ("Novo cliente: "+serverSocket.toString());

			/* Preparacao dos fluxos de entrada e saida */
			out = new PrintWriter(clientSocket.getOutputStream(),
					true);
			in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));

			/* Recuperacao dos comandos */
			while ((comando = in.readLine()) != null) {	
				System.out.println ("Comando recebido: ["+ comando+"]");
				/* Se comando for "HORA" */
				if (comando.equals ("HORA")){
					System.out.println("EEE");
				}else if (comando.equals ("FIM")){
					break;
				}else{
					out.println ("Comando Desconhecido");
				}
			}
			/* Finaliza tudo */
			System.out.print ("Cliente desconectando... ");
			out.close();
			in.close();
			clientSocket.close();
			System.out.println ("ok");
		}

	}
}
		
