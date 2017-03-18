package Trabalho;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	private JButton buttonFechar, buttonPesquisar, buttonSeleciona, buttonSelecionall, buttonDelete, buttonDeleteAll,
			buttonbackup;
	private JLabel labelArquivos, labelTotalArquivos, labelinfoArquivos;
	private ArrayList<Arquivo> arquivos = new ArrayList();

	public ApresentacaoCLiente() {

		this.setLayout(null);
		this.setTitle("Backup Online");
		this.setSize(820, 290);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(false);
		this.setResizable(false);
		this.setModal(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.labelArquivos = new JLabel("Arquivos Selecionados:");
		this.labelArquivos.setBounds(10, 10, 150, 10);
		this.labelArquivos.setVisible(true);
		this.add(this.labelArquivos);

		this.labelinfoArquivos = new JLabel("Total de arquivos:");
		this.labelinfoArquivos.setBounds(10, 570, 150, 10);
		this.labelinfoArquivos.setVisible(true);
		this.add(this.labelinfoArquivos);

		this.labelTotalArquivos = new JLabel("0");
		this.labelTotalArquivos.setBounds(30, 600, 300, 10);
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
		this.buttonPesquisar.setBounds(660, 35, 133, 25);
		this.buttonPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				buttonPesquisarActionPerformed(evt);
			}
		});
		this.add(buttonPesquisar);

		this.buttonSelecionall = new JButton("Selecionar Todos");
		this.buttonSelecionall.setBounds(660, 125, 133, 25);
		this.add(buttonSelecionall);

		this.buttonSeleciona = new JButton("Selecionar");
		this.buttonSeleciona.setBounds(660, 65, 133, 25);
		this.add(buttonSeleciona);

		this.buttonDelete = new JButton("Deletear Todos");
		this.buttonDelete.setBounds(660, 155, 133, 25);
		this.add(buttonDelete);

		this.buttonDeleteAll = new JButton("Deletar");
		this.buttonDeleteAll.setBounds(660, 95, 133, 25);
		this.add(this.buttonDeleteAll);

		this.buttonbackup = new JButton("Backup");
		this.buttonbackup.setBounds(660, 185, 133, 25);
		this.add(this.buttonbackup);

		// this.buttonFechar = new JButton("Fechar");
		// this.buttonFechar.setBounds(620, 215, 133, 25);
		// this.buttonFechar.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent evt) {
		// buttonFecharActionPerformed(evt);
		// }
		// });
		// add(this.buttonFechar);

		DefaultTableModel tabelaModelo = new DefaultTableModel(null, new String[] { "Nome e caminho do arquivo", "Enviado" });
		this.tableArquivos.setModel(tabelaModelo);
		this.tableArquivos.getColumnModel().getColumn(0).setPreferredWidth(497);
		this.tableArquivos.getColumnModel().getColumn(1).setPreferredWidth(140);

		this.setVisible(true);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ApresentacaoCLiente apresentacao = new ApresentacaoCLiente();
		Cliente cliente = new Cliente();
		cliente.CLienteMulticast();
	}

	@SuppressWarnings("unused")
	private void buttonFecharActionPerformed(ActionEvent evt) {
		this.setVisible(false);
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
		DefaultTableModel tabelaModelo = new DefaultTableModel(null, new String[] { "Nome e caminho do arquivo", "Enviado" });
		this.tableArquivos.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		for (int i = 0; i < arquivos.size(); i++) {

			tabelaModelo.addRow(new String[] { "Nome e caminho do arquivo", "Enviado" });
			tabelaModelo.setValueAt(arquivos.get(i).caminhoAbsoluto(), i, 0);
			tabelaModelo.setValueAt(arquivos.get(i).isEnviado(), i, 1);

		}

		this.tableArquivos.setModel(tabelaModelo);

		this.tableArquivos.getColumnModel().getColumn(0).setPreferredWidth(497);
		this.tableArquivos.getColumnModel().getColumn(1).setPreferredWidth(140);
		
		DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
		centralizado.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.tableArquivos.getColumnModel().getColumn(1).setCellRenderer(centralizado);

		this.tableArquivos.setCursor(Cursor.getDefaultCursor());
		this.tableArquivos.requestFocus();
	}
}
