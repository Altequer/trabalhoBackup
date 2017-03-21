package Trabalho;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServidorUDP {
	
	@SuppressWarnings("resource")
	public static void main (String args[]) throws Exception {
		byte[] buf;
		DatagramSocket socket;
		DatagramPacket request, reply;
		String comando;
		

		/* Parametros */
		String numeroPorta = "9989";

		/* Inicializacao do socket UDP */
		socket = new DatagramSocket(
				new Integer(numeroPorta).intValue());
	
		/* Laco de recebimento de datagramas */
		while (true){
			request = null;
			reply = null;
			buf = new byte[1024];

			/* Preparacao do Datagrama de Recepcao */
			request = new DatagramPacket (buf, buf.length);

			/* Recepcao bloqueante dos dados */
			socket.receive (request);

			/* Recuperacao do comando */
			comando = new String (request.getData(),0,
					request.getLength());
			
			/* Se comando for "HORA" */
			if ("HORA".indexOf (comando) != -1){
				/* Prepara a hora para envio */
				String hora = new SimpleDateFormat("d MMM yyyy HH:mm:ss").format(new Date ());

				/* Cria datagrama com a resposta */
				reply = new DatagramPacket (hora.getBytes(),
						hora.getBytes().length,
						request.getAddress(),
						request.getPort());

			/* Se comando NAO for "HORA" */
			}else{

				/* Cria datagrama com a resposta */
				reply = new DatagramPacket ("Comando Desconhecido".getBytes(), "Comando Desconhecido".getBytes().length, request.getAddress(), request.getPort());
			}
		
			/* Envia resposta pelo socket UDP */
			socket.send (reply);
		}
	}
}
