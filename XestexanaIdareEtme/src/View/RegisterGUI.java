package View;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Xeste;
import javax.swing.JPasswordField;

public class RegisterGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txt_xesteAd;
	private JTextField txt_xesteSeria;
	static Xeste xeste=new Xeste();
	private JPasswordField txt_xesteParol;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setTitle("Xəstəxana İdarə Sistemi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 360, 400);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ad, Soyad");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		lblNewLabel.setBounds(10, 11, 102, 25);
		contentPane.add(lblNewLabel);
		
		txt_xesteAd = new JTextField();
		txt_xesteAd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_xesteAd.setColumns(10);
		txt_xesteAd.setBounds(10, 47, 324, 25);
		contentPane.add(txt_xesteAd);
		
		JLabel lblAzSeriaNo = new JLabel("AZ seria No");
		lblAzSeriaNo.setForeground(Color.WHITE);
		lblAzSeriaNo.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		lblAzSeriaNo.setBackground(Color.WHITE);
		lblAzSeriaNo.setBounds(10, 83, 102, 25);
		contentPane.add(lblAzSeriaNo);
		
		txt_xesteSeria = new JTextField();
		txt_xesteSeria.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_xesteSeria.setColumns(10);
		txt_xesteSeria.setBounds(10, 119, 324, 25);
		contentPane.add(txt_xesteSeria);
		
		JLabel lblifr = new JLabel("\u015Eifr\u0259");
		lblifr.setForeground(Color.WHITE);
		lblifr.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		lblifr.setBackground(Color.WHITE);
		lblifr.setBounds(10, 155, 102, 25);
		contentPane.add(lblifr);
		
		JButton btn_register = new JButton("qeydiyyatdan ke\u00E7");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txt_xesteAd.getText().length()==0||txt_xesteSeria.getText().length()==0||txt_xesteParol.getText().length()==0) {
					Helper.showMsg("fill");
				}else {
					boolean a=false;
					try {
						a = xeste.addXeste(txt_xesteSeria.getText(), txt_xesteParol.getText(),txt_xesteAd.getText());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(a) {
					Helper.showMsg(txt_xesteAd.getText()+"  adlı xeste sisteme elave olundu");
					LoginGUI gui=new LoginGUI();
					gui.setVisible(true);
					dispose();
					}
					else {
						txt_xesteSeria.setText(null);
					}

				}
				
				
				
			}
		});
		btn_register.setFont(new Font("Tahoma", Font.BOLD, 19));
		btn_register.setBackground(UIManager.getColor("Button.darkShadow"));
		btn_register.setBounds(10, 240, 324, 37);
		contentPane.add(btn_register);
		
		JButton btn_back = new JButton("geri d\u00F6n");
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI gui=new LoginGUI();
				gui.setVisible(true);
				dispose();
			}
		});
		btn_back.setFont(new Font("Tahoma", Font.BOLD, 19));
		btn_back.setBackground(UIManager.getColor("Button.focus"));
		btn_back.setBounds(10, 299, 324, 37);
		contentPane.add(btn_back);
		
		txt_xesteParol = new JPasswordField();
		txt_xesteParol.setBounds(10, 195, 324, 25);
		contentPane.add(txt_xesteParol);
	}
}
