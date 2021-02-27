package View;

import java.awt.EventQueue;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.DBconnection;
import Helper.Helper;
import Model.BasHekim;
import Model.Doktor;
import Model.Xeste;

import java.awt.Font;
import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class LoginGUI extends JFrame {

	private JPanel w_pane;
	private JTextField txt_xeste_ad;
	private JTextField txt_hekim_ad;
	private JPasswordField txt_hekim_parol;
	private JPasswordField txt_xeste_parol;
	private DBconnection conn = new DBconnection();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setResizable(false);
		setTitle("Xəstəxana İdarə Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);

		JLabel lbl_logo = new JLabel(new ImageIcon(getClass().getResource("logo.png")));
		lbl_logo.setBounds(226, 28, 32, 32);
		w_pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("x\u0259st\u0259xana idar\u0259 sistemin\u0259 xo\u015F g\u0259lmisiniz");
		lblNewLabel.setBounds(72, 84, 357, 21);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		w_pane.add(lblNewLabel);
		w_pane.add(lbl_logo);

		JTabbedPane w_tabbedpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabbedpane.setBounds(10, 135, 456, 213);
		w_pane.add(w_tabbedpane);

		JPanel panel_xeste = new JPanel();
		panel_xeste.setBackground(Color.WHITE);
		w_tabbedpane.addTab("xeste girisi", null, panel_xeste, null);
		panel_xeste.setLayout(null);

		JLabel lbl_xeste_ad = new JLabel("seria:");
		lbl_xeste_ad.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl_xeste_ad.setBounds(55, 13, 52, 21);
		panel_xeste.add(lbl_xeste_ad);

		JLabel lbl_xeste_parol = new JLabel("parol:");
		lbl_xeste_parol.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl_xeste_parol.setBounds(55, 76, 63, 21);
		panel_xeste.add(lbl_xeste_parol);

		txt_xeste_ad = new JTextField();
		txt_xeste_ad.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_xeste_ad.setBounds(165, 11, 223, 26);
		panel_xeste.add(txt_xeste_ad);
		txt_xeste_ad.setColumns(10);

		JButton btn_xeste_qeydiyyat = new JButton("qeydiyyat");
		btn_xeste_qeydiyyat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI gui=new RegisterGUI();
				gui.setVisible(true);
				dispose();
			}
		});
		btn_xeste_qeydiyyat.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btn_xeste_qeydiyyat.setBounds(55, 124, 132, 50);
		panel_xeste.add(btn_xeste_qeydiyyat);

		JButton btn_xeste_giris = new JButton("giri\u015F");
		btn_xeste_giris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txt_xeste_ad.getText().length() == 0 || txt_xeste_parol.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {

					try {
						Connection connection = conn.connectDb();
						Statement statement = connection.createStatement();
						ResultSet resultset = statement.executeQuery("select* from users");
						boolean a=true;
						while (resultset.next()) {
							if( txt_xeste_ad.getText().equals(resultset.getString("seria"))
									&& txt_xeste_parol.getText().equals(resultset.getString("parol"))) {
								if (resultset.getString("type").equals("xeste")) {
									Xeste xeste=new Xeste();
									xeste.setId(resultset.getInt("id"));
									xeste.setName(resultset.getString("name"));
									xeste.setParol(resultset.getString("parol"));
									xeste.setSeria(resultset.getString("seria"));
									xeste.setType(resultset.getString("type"));
									XesteGUI bhGUI;
									try {
										bhGUI = new XesteGUI(xeste);
										bhGUI.setVisible(true);
										dispose();
										a=false;
									} catch (Throwable e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

								}

							}
						}
						if(a) {
						Helper.showMsg("Seria ve ya Şifrə Səhvdir!");
						txt_xeste_parol.setText(null);}

					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				}

			}
		});
		btn_xeste_giris.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btn_xeste_giris.setBounds(256, 124, 132, 50);
		panel_xeste.add(btn_xeste_giris);

		txt_xeste_parol = new JPasswordField();
		txt_xeste_parol.setBounds(165, 71, 223, 26);
		panel_xeste.add(txt_xeste_parol);

		JPanel panel_hekim = new JPanel();
		panel_hekim.setBackground(Color.WHITE);
		w_tabbedpane.addTab("hekim girisi", null, panel_hekim, null);
		panel_hekim.setLayout(null);

		JLabel lbl_hekim_ad = new JLabel("seria:");
		lbl_hekim_ad.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl_hekim_ad.setBounds(54, 13, 52, 21);
		panel_hekim.add(lbl_hekim_ad);

		txt_hekim_ad = new JTextField();
		txt_hekim_ad.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_hekim_ad.setColumns(10);
		txt_hekim_ad.setBounds(164, 11, 223, 26);
		panel_hekim.add(txt_hekim_ad);

		JLabel lbl_hekim_parol = new JLabel("parol:");
		lbl_hekim_parol.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl_hekim_parol.setBounds(54, 76, 63, 21);
		panel_hekim.add(lbl_hekim_parol);

		JButton btn_hekim_giris = new JButton("G\u0130R\u0130\u015E");
		btn_hekim_giris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txt_hekim_ad.getText().length() == 0 || txt_hekim_parol.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {

					try {
						Connection connection = conn.connectDb();
						Statement statement = connection.createStatement();
						ResultSet resultset = statement.executeQuery("select* from users");
						boolean a=true;
						while (resultset.next()) {

							if (txt_hekim_ad.getText().equals(resultset.getString("seria"))
									&& txt_hekim_parol.getText().equals(resultset.getString("parol"))) {
								if (resultset.getString("type").equals("bashekim")) {
									BasHekim bhekim = new BasHekim();
									bhekim.setId(resultset.getInt("id"));
									bhekim.setName(resultset.getString("name"));
									bhekim.setParol(resultset.getString("parol"));
									bhekim.setSeria(resultset.getString("seria"));
									bhekim.setType(resultset.getString("type"));
									BasHekimGUI bhGUI = new BasHekimGUI(bhekim);
									bhGUI.setVisible(true);
									dispose();
								}
								if (resultset.getString("type").equals("doktor")) {
									Doktor doktor = new Doktor();
									doktor.setId(resultset.getInt("id"));
									doktor.setName(resultset.getString("name"));
									doktor.setParol(resultset.getString("parol"));
									doktor.setSeria(resultset.getString("seria"));
									doktor.setType(resultset.getString("type"));
									DoktorGUI doktorGUI = new DoktorGUI(doktor);
									doktorGUI.setVisible(true);
									dispose();
								}
							    a=false;
							}
						}
						if(a) {
						Helper.showMsg("Seria ve ya Şifrə Səhvdir!");
						txt_hekim_parol.setText(null);}

					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				}

			}
		});
		btn_hekim_giris.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btn_hekim_giris.setBounds(54, 124, 333, 50);
		panel_hekim.add(btn_hekim_giris);

		txt_hekim_parol = new JPasswordField();
		txt_hekim_parol.setBounds(164, 71, 223, 26);
		panel_hekim.add(txt_hekim_parol);
	}
}
