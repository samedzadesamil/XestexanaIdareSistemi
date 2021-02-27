package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.*;
import Helper.*;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class UpdateClinicGUI extends JFrame {

	private JPanel w_pane;
	
	private static Clinic clinic;
	private JTextField txt_clinicName;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateClinicGUI frame = new UpdateClinicGUI(clinic);
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
	public UpdateClinicGUI(Clinic clinic) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 229, 162);
		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblKlinic = new JLabel("Klinika Ad\u0131n\u0131 Daxil et");
		lblKlinic.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
		lblKlinic.setBounds(10, 11, 193, 25);
		w_pane.add(lblKlinic);
		
		JButton btn_AddClinicName = new JButton("əlavə et");
		btn_AddClinicName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Helper.confrim("update")) {
					try {
						clinic.updateClinic(clinic.getId(),txt_clinicName.getText());
						Helper.showMsg("update");
						
						dispose();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
				
			}
		});
		btn_AddClinicName.setFont(new Font("Tahoma", Font.BOLD, 13));
		btn_AddClinicName.setBounds(10, 83, 193, 32);
		w_pane.add(btn_AddClinicName);
		
		txt_clinicName = new JTextField();
		txt_clinicName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_clinicName.setColumns(10);
		txt_clinicName.setBounds(10, 47, 193, 25);
		txt_clinicName.setText(clinic.getName());
		w_pane.add(txt_clinicName);
		
		
	}
}
