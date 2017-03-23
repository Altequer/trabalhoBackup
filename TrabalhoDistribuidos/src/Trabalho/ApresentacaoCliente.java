package Trabalho;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
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
public class ApresentacaoCliente extends JDialog {
	private JTable tableArquivos;
	private JScrollPane scrollArquivo;
	private JPanel panelArquivo;
	private JButton buttonFechar, buttonPesquisar, buttonDelete, buttonDeleteAll, buttonbackup;
	private JLabel labelArquivos, labelTotalArquivos, labelinfoArquivos, labelinfoTamanho, labelTamanhoTotal;
	private JProgressBar barraProgresso;
	private JDialog containerProgresso;
	private JLabel labelContainer;
	private ArrayList<Arquivo> arquivos = new ArrayList<>();

	public ApresentacaoCliente() {

		this.setLayout(null);
		this.setTitle("Backup Online");
		this.setSize(820, 300);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(false);
		this.setResizable(false);
		this.setModal(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.containerProgresso = new JDialog();
		this.containerProgresso.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.containerProgresso.setSize(370, 73);
		this.containerProgresso.setLayout(null);
		this.containerProgresso.setUndecorated(true);
		this.containerProgresso.setLocationRelativeTo(null);
		this.containerProgresso.setAlwaysOnTop(true);
		this.setVisible(false);

		this.barraProgresso = new JProgressBar();
		this.barraProgresso.setStringPainted(true);
		this.barraProgresso.setValue(0);
		this.barraProgresso.setBounds(10, 40, 350, 30);
		this.barraProgresso.setForeground(Color.BLUE);
		this.barraProgresso.setBackground(Color.WHITE);
		this.containerProgresso.add(this.barraProgresso);
		this.barraProgresso.setVisible(true);

		this.labelContainer = new JLabel();
		this.labelContainer.setBounds(10, 10, 600, 25);
		this.labelContainer.setFont(new Font("Arial", Font.BOLD, 12));
		this.containerProgresso.add(labelContainer);
		this.labelContainer.setVisible(true);

		this.labelArquivos = new JLabel("Arquivos Selecionados:");
		this.labelArquivos.setBounds(10, 10, 150, 15);
		this.labelArquivos.setVisible(true);
		this.add(this.labelArquivos);

		this.labelinfoArquivos = new JLabel("Total de arquivos:");
		this.labelinfoArquivos.setBounds(10, 245, 150, 25);
		this.labelinfoArquivos.setVisible(true);
		this.add(this.labelinfoArquivos);

		this.labelTotalArquivos = new JLabel("0");
		this.labelTotalArquivos.setBounds(120, 245, 300, 25);
		this.labelTotalArquivos.setVisible(true);
		this.add(this.labelTotalArquivos);

		this.labelinfoTamanho = new JLabel("Tamanho total:");
		this.labelinfoTamanho.setBounds(160, 245, 150, 25);
		this.labelinfoTamanho.setVisible(true);
		this.add(this.labelinfoTamanho);

		this.labelTamanhoTotal = new JLabel("0");
		this.labelTamanhoTotal.setBounds(255, 245, 300, 25);
		this.labelTamanhoTotal.setVisible(true);
		this.add(this.labelTamanhoTotal);

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
				new String[] { "Nome e caminho do arquivo", "Tamanho", "Enviado" });
		this.tableArquivos.setModel(tabelaModelo);
		this.tableArquivos.getColumnModel().getColumn(0).setPreferredWidth(390);
		this.tableArquivos.getColumnModel().getColumn(1).setPreferredWidth(137);
		this.tableArquivos.getColumnModel().getColumn(2).setPreferredWidth(110);

		this.setVisible(true);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ApresentacaoCliente apresentacao = new ApresentacaoCliente();
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
				new String[] { "Nome e caminho do arquivo", "Tamanho", "Enviado" });
		this.tableArquivos.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		long tamanhoTotal = 0;

		for (int i = 0; i < arquivos.size(); i++) {

			tabelaModelo.addRow(new String[] { "Nome e caminho do arquivo", "Tamanho", "Enviado" });
			tabelaModelo.setValueAt(arquivos.get(i).caminhoAbsoluto(), i, 0);
			tabelaModelo.setValueAt(arquivos.get(i).getTamanho() + " KB", i, 1);
			tabelaModelo.setValueAt(arquivos.get(i).isEnviado(), i, 2);

			tamanhoTotal += arquivos.get(i).getTamanho();

		}

		this.labelTamanhoTotal.setText(tamanhoTotal + " KB");
		this.labelTotalArquivos.setText(String.valueOf(this.arquivos.size()));
		this.tableArquivos.setModel(tabelaModelo);
		this.tableArquivos.getColumnModel().getColumn(0).setPreferredWidth(390);
		this.tableArquivos.getColumnModel().getColumn(1).setPreferredWidth(137);
		this.tableArquivos.getColumnModel().getColumn(2).setPreferredWidth(110);

		DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
		centralizado.setHorizontalAlignment(SwingConstants.CENTER);
		this.tableArquivos.getColumnModel().getColumn(2).setCellRenderer(centralizado);

		DefaultTableCellRenderer right = new DefaultTableCellRenderer();
		right.setHorizontalAlignment(SwingConstants.RIGHT);
		this.tableArquivos.getColumnModel().getColumn(1).setCellRenderer(right);

		this.tableArquivos.setCursor(Cursor.getDefaultCursor());
		this.tableArquivos.requestFocus();
	}

	private void buttonBackupActionPerformed(ActionEvent evt) {
		this.barraProgresso.setMaximum(arquivos.size());
		this.barraProgresso.setMinimum(0);

		Cliente cliente;
		cliente = new Cliente();
		cliente.ClienteMulticast();

		containerProgresso.setVisible(true);
		labelContainer.setText("Conectando...");
		barraProgresso.setMinimum(0);
		barraProgresso.setMaximum(arquivos.size());

		new Thread() {

			@Override
			public void run() {
				try {
					for (int i = 0; i < arquivos.size(); i++) {

						if (arquivos.get(i).isEnviado().toLowerCase().contains("não enviado")) {
							cliente.ClienteTCP(arquivos.get(i).getArquivo());
							ajustarTela(false);
							labelContainer.setText("Enviando o arquivo " + arquivos.get(i).getArquivo().getName());
							barraProgresso.setValue(i + 1);
						}

						arquivos.get(i).setEnviado(true);
						carregaArquivoTable();

						sleep(50);
					}
					containerProgresso.dispose();
					ajustarTela(true);
					interrupt();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}.start();

	}

	public void ajustarTela(boolean opcao) {
		
		this.buttonbackup.setEnabled(opcao);
		this.buttonDelete.setEnabled(opcao);
		this.buttonDeleteAll.setEnabled(opcao);
		this.buttonFechar.setEnabled(opcao);
		this.buttonPesquisar.setEnabled(opcao);
	}
}
