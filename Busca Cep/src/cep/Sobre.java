package cep;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Sobre extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre dialog = new Sobre();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Sobre() {
		setModal(true);
		setResizable(false);
		setTitle("Sobre mim");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/home.png")));
		setBounds(100, 100, 450, 300);

		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblNewLabel = new JLabel("Projeto Busca Cep");
		lblNewLabel.setBounds(26, 31, 116, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Autor: tavaresProg");
		lblNewLabel_1.setBounds(26, 56, 133, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("WebService: ");
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				link("https:/www.republicavirtual.com.br/");
			}
		});
		lblNewLabel_2.setBounds(26, 81, 105, 14);
		getContentPane().add(lblNewLabel_2);

		JLabel lblWebService = new JLabel("republicavirtual.com.br");
		lblWebService.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				link("https:/www.republicavirtual.com.br/");
			}
		});
		lblWebService.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		lblWebService.setForeground(SystemColor.textHighlight);
		lblWebService.setBounds(100, 81, 133, 14);

		getContentPane().add(lblWebService);

		JButton btnGitHub = new JButton("https://github.com/tavaresProg");
		btnGitHub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				link("https://github.com/tavaresProg");
			}
		});
		btnGitHub.setBorder(null);
		btnGitHub.setFocusPainted(false);
		btnGitHub.setFocusTraversalKeysEnabled(false);
		btnGitHub.setFocusable(false);
		btnGitHub.setBackground(SystemColor.control);
		btnGitHub.setForeground(new Color(240, 248, 255));
		btnGitHub.setIcon(new ImageIcon(Sobre.class.getResource("/img/github.png")));
		btnGitHub.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGitHub.setBounds(311, 31, 44, 50);
		getContentPane().add(btnGitHub);

	}

	private void link(String site) {

		Desktop desktop = Desktop.getDesktop();
		try {
			URI uri = new URI(site);
			desktop.browse(uri);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
