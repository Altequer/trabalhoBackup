package Trabalho;

import java.io.File;

public class Arquivo {
	private File arquivo;
	private boolean isEnviado;
	

	public Arquivo(File arquivo) {
		this.setArquivo(arquivo);
	}
	
	public String nomArquivo(){
		return this.getArquivo().getName();
	}
	
	public String caminhoAbsoluto(){
		return this.getArquivo().getAbsolutePath();
	}
	
	public File getArquivo() {
		return arquivo;
	}
	
	public long getTamanho(){
		return (long) (Math.ceil(this.arquivo.length() / 1024D));
	}

	private void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}

	public String isEnviado() {
		if(this.isEnviado){
			 return "Enviado";
		}else{
			return "Não Enviado";
		}
	}
	
	public void setEnviado(boolean isEnviado) {
		this.isEnviado = isEnviado;
	}
	
}
