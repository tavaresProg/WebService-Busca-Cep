package cep;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import Atxy2k.CustomTextField.RestrictedTextField;

public class Cep {

	private JFrame frmBuscadorDeCep;
	private JTextField txtCep;
	private JTextField txtEndereco;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JLabel lblUf;
	private JComboBox cboUf;
	private JLabel lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cep window = new Cep();
					window.frmBuscadorDeCep.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Cep() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBuscadorDeCep = new JFrame();
		frmBuscadorDeCep.setBackground(new Color(0, 255, 204));
		frmBuscadorDeCep.setTitle("Buscador de CEP");
		frmBuscadorDeCep.setResizable(false);
		frmBuscadorDeCep.setIconImage(Toolkit.getDefaultToolkit().getImage(Cep.class.getResource("/img/home.png")));
		frmBuscadorDeCep.setBounds(100, 100, 450, 300);
		frmBuscadorDeCep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBuscadorDeCep.getContentPane().setLayout(null);
		frmBuscadorDeCep.setLocationRelativeTo(null);

		JLabel lblNewLabel = new JLabel("CEP:");
		lblNewLabel.setBounds(22, 37, 46, 14);
		frmBuscadorDeCep.getContentPane().add(lblNewLabel);

		txtCep = new JTextField();
		txtCep.setBounds(74, 34, 101, 20);
		frmBuscadorDeCep.getContentPane().add(txtCep);
		txtCep.setColumns(10);
		RestrictedTextField validar = new RestrictedTextField(txtCep);
		validar.setOnlyNums(true);
		validar.setLimit(8);

		JLabel lblEndereo = new JLabel("Endereço:");
		lblEndereo.setBounds(10, 90, 65, 14);
		frmBuscadorDeCep.getContentPane().add(lblEndereo);

		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setBounds(22, 140, 65, 14);
		frmBuscadorDeCep.getContentPane().add(lblBairro);

		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setBounds(22, 190, 65, 14);
		frmBuscadorDeCep.getContentPane().add(lblCidade);

		lblUf = new JLabel("UF");
		lblUf.setBounds(307, 190, 65, 14);
		frmBuscadorDeCep.getContentPane().add(lblUf);

		txtEndereco = new JTextField();
		txtEndereco.setColumns(10);
		txtEndereco.setBounds(74, 87, 325, 20);
		frmBuscadorDeCep.getContentPane().add(txtEndereco);

		txtBairro = new JTextField();
		txtBairro.setColumns(10);
		txtBairro.setBounds(74, 137, 325, 20);
		frmBuscadorDeCep.getContentPane().add(txtBairro);

		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBounds(74, 187, 219, 20);
		frmBuscadorDeCep.getContentPane().add(txtCidade);

		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(
				new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB",
						"PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUf.setBounds(334, 186, 53, 22);
		frmBuscadorDeCep.getContentPane().add(cboUf);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setBounds(74, 218, 89, 23);
		frmBuscadorDeCep.getContentPane().add(btnLimpar);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtCep.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Informe o CEP");
					txtCep.requestFocus();
				} else {
					buscarCep();
				}
			}
		});
		btnBuscar.setBounds(254, 33, 89, 23);
		frmBuscadorDeCep.getContentPane().add(btnBuscar);

		JButton btnInfo = new JButton("");
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnInfo.setToolTipText("Sobre");
		btnInfo.setRolloverEnabled(false);
		btnInfo.setIcon(new ImageIcon(Cep.class.getResource("/img/aboutMe.png")));
		btnInfo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnInfo.setBorder(null);
		btnInfo.setBackground(SystemColor.control);
		btnInfo.setBounds(353, 11, 46, 48);
		frmBuscadorDeCep.getContentPane().add(btnInfo);

		lblStatus = new JLabel("");
		lblStatus.setBounds(185, 21, 46, 46);
		frmBuscadorDeCep.getContentPane().add(lblStatus);
	}

	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = txtCep.getText();

		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			// basicamente necessario para utilizar o xml
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					txtCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					txtBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUf.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}

				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if (resultado.equals("1")) {
						lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/check.png")));
					} else {
						JOptionPane.showMessageDialog(null, "CEP não encontrado");
					}
				}

			}
			txtEndereco.setText(tipoLogradouro + " " + logradouro);

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void limpar() {
		txtCep.setText(null);
		txtEndereco.setText(null);
		txtBairro.setText(null);
		txtCidade.setText(null);
		cboUf.setSelectedItem(null);
		txtCep.requestFocus();
		lblStatus.setIcon(null);
	}
}
