package Trabalho;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class ApresentacaoCLiente extends JDialog {
	private JTable tableArquivos;
	private JScrollPane scrollArquivo;
	private JPanel panelArquivo;
	private JButton buttonFechar, buttonPesquisar, buttonDelete, buttonDeleteAll, buttonbackup;
	private JLabel labelArquivos, labelTotalArquivos, labelinfoArquivos;
	private JProgressBar barraProgresso;
	private ArrayList<Arquivo> arquivos = new ArrayList<>();

	public ApresentacaoCLiente() {

		this.setLayout(null);
		this.setTitle("Backup Online");
		this.setSize(820, 290);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(false);
		this.setResizable(false);
		this.setModal(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.barraProgresso = new JProgressBar();
		this.barraProgresso.setStringPainted(true);
		this.barraProgresso.setValue(0);
		this.barraProgresso.setBounds(200, 243, 450, 15);
		this.barraProgresso.setForeground(Color.red);
		this.barraProgresso.setBackground(Color.WHITE);
		;
		this.add(this.barraProgresso);
		this.barraProgresso.setVisible(true);

		this.labelArquivos = new JLabel("Arquivos Selecionados:");
		this.labelArquivos.setBounds(10, 10, 150, 10);
		this.labelArquivos.setVisible(true);
		this.add(this.labelArquivos);

		this.labelinfoArquivos = new JLabel("Total de arquivos:");
		this.labelinfoArquivos.setBounds(10, 245, 150, 10);
		this.labelinfoArquivos.setVisible(true);
		this.add(this.labelinfoArquivos);

		this.labelTotalArquivos = new JLabel("0");
		this.labelTotalArquivos.setBounds(120, 246, 300, 10);
		this.labelTotalArquivos.setVisible(true);
		this.add(this.labelTotalArquivos);

		this.tableArquivos = new JTable();
		this.tableArquivos.setBounds(20, 20, 20, 20);
		this.tableArquivos.getTableHeader().setReorderingAllowed(false);
		this.tableArquivos.getTableHeader().setResizingAllowed(true);
		this.tableArquivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.tableArquivos.setShowHorizontalLines(true);
		this.tableArquivos.setShowVerticalLines(true);
		this.tableArquivos.setEnabled(true);
		this.tableArquivos.setSelectionBackground(Color.GRAY);
		this.tableArquivos.setSelectionForeground(Color.WHITE);
		this.tableArquivos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.tableArquivos.setDefaultEditor(Object.class, null);
		this.tableArquivos.setCellSelectionEnabled(true);
		this.tableArquivos.setVisible(true);

		this.scrollArquivo = new JScrollPane(this.tableArquivos);
		this.scrollArquivo.setVisible(true);
		this.scrollArquivo.setBounds(10, 18, 640, 210);
		this.scrollArquivo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.scrollArquivo.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		this.panelArquivo = new JPanel();
		this.panelArquivo.setLayout(null);
		this.panelArquivo.setBounds(1, 14, 650, 230);
		this.panelArquivo.add(scrollArquivo, null);
		this.panelArquivo.setVisible(true);
		this.add(this.panelArquivo);

		this.buttonPesquisar = new JButton("Pesquisar");
		this.buttonPesquisar.setBounds(660, 95, 133, 25);
		this.buttonPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				buttonPesquisarActionPerformed(evt);
			}
		});
		this.add(buttonPesquisar);

		this.buttonDelete = new JButton("Deletar");
		this.buttonDelete.setBounds(660, 125, 133, 25);
		this.buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (arquivos.size() > 0) {
					try {
						arquivos.remove(tableArquivos.getSelectedRow());
						carregaArquivoTable();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Nenhum arquivo selecionado!!", "Atenção",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Não tem arquivos a serem deletados!!", "Atenção",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		this.add(buttonDelete);

		this.buttonDeleteAll = new JButton("Deletear Todos");
		this.buttonDeleteAll.setBounds(660, 155, 133, 25);
		this.buttonDeleteAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (arquivos.size() > 0) {
					int totalArquivos = arquivos.size();

					while (totalArquivos != 0) {
						totalArquivos--;
						arquivos.remove(totalArquivos);
					}
					carregaArquivoTable();
				} else {
					JOptionPane.showMessageDialog(null, "Não tem arquivos a serem deletados!!", "Atenção",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		this.add(this.buttonDeleteAll);

		this.buttonbackup = new JButton("Backup");
		this.buttonbackup.setBounds(660, 185, 133, 25);
		this.buttonbackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				buttonBackupActionPerformed(evt);
			}
		});
		this.add(this.buttonbackup);

		this.buttonFechar = new JButton("Fechar");
		this.buttonFechar.setBounds(660, 215, 133, 25);
		this.buttonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				buttonFecharActionPerformed(evt);
			}
		});
		add(this.buttonFechar);

		DefaultTableModel tabelaModelo = new DefaultTableModel(null,
				new String[] { "Nome e caminho do arquivo", "Enviado" });
		this.tableArquivos.setModel(tabelaModelo);
		this.tableArquivos.getColumnModel().getColumn(0).setPreferredWidth(497);
		this.tableArquivos.getColumnModel().getColumn(1).setPreferredWidth(140);

		this.setVisible(true);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ApresentacaoCLiente apresentacao = new ApresentacaoCLiente();
	}

	private void buttonFecharActionPerformed(ActionEvent evt) {
		System.exit(0);
	}

	private void buttonPesquisarActionPerformed(ActionEvent evt) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fileChooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			File afile[] = fileChooser.getSelectedFile().listFiles();
			if (afile != null) {
				int i = 0;
				for (int j = afile.length; i < j; i++) {
					this.arquivos.add(new Arquivo(afile[i]));
				}
			} else {
				this.arquivos.add(new Arquivo(fileChooser.getSelectedFile()));
			}
			carregaArquivoTable();
		}
	}

	public void carregaArquivoTable() {
		DefaultTableModel tabelaModelo = new DefaultTableModel(null,
				new String[] { "Nome e caminho do arquivo", "Enviado" });
		this.tableArquivos.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		for (int i = 0; i < arquivos.size(); i++) {

			tabelaModelo.addRow(new String[] { "Nome e caminho do arquivo", "Enviado" });
			tabelaModelo.setValueAt(arquivos.get(i).caminhoAbsoluto(), i, 0);
			tabelaModelo.setValueAt(arquivos.get(i).isEnviado(), i, 1);

		}

		this.labelTotalArquivos.setText(String.valueOf(this.arquivos.size()));
		this.tableArquivos.setModel(tabelaModelo);
		this.tableArquivos.getColumnModel().getColumn(0).setPreferredWidth(497);
		this.tableArquivos.getColumnModel().getColumn(1).setPreferredWidth(140);

		DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
		centralizado.setHorizontalAlignment(SwingConstants.CENTER);

		this.tableArquivos.getColumnModel().getColumn(1).setCellRenderer(centralizado);

		this.tableArquivos.setCursor(Cursor.getDefaultCursor());
		this.tableArquivos.requestFocus();
	}

	private void buttonBackupActionPerformed(ActionEvent evt) {
		Cliente cliente;
		cliente = new Cliente();
		cliente.ClienteMulticast();

		new Thread() {

			@Override
			public void run() {
				try {
					for (int i = 0; i < arquivos.size(); i++) {
						if (arquivos.get(i).isEnviado().toLowerCase().contains("não enviado")) {
							cliente.ClienteTCP(arquivos.get(i).getArquivo());
							barraProgresso.setValue(10+i);
						}
						arquivos.get(i).setEnviado(true);
						carregaArquivoTable();
						sleep(50);
					}
					destroy();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}.start();
	}
}
