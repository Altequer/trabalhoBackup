package Trabalho;

import java.net.*;
import java.io.*;

public class ServidorTCP {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(9988);

		while (true) {
			
			Socket clSocket = server.accept();

			new Thread(){
				public void run() {

					try {

						int bytesRead;
						DataInputStream clientData = new DataInputStream(clSocket.getInputStream());

						String fileName = clientData.readUTF();
						File caminho = new File("C:\\Backup");

						if (!caminho.exists()) {
							caminho.mkdirs();
						}
						String caminhoCompleto = caminho + "/" + fileName;

						OutputStream output = new FileOutputStream((caminhoCompleto));
						long size = clientData.readLong();
						byte[] buffer = new byte[1024];

						while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
							output.write(buffer, 0, bytesRead);
							size -= bytesRead;
						}

						output.close();
						
					} catch (IOException e) {
					}

				};		

			}.start();
		}
	}
}
