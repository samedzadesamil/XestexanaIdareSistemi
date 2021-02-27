package View;

import Helper.*;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.Item;
import Model.Appointment;
import Model.BasHekim;
import Model.Clinic;
import Model.Doktor;
import Model.Xeste;

public class XesteGUI extends JFrame {

	private JPanel w_pane;
	private static Xeste xeste = new Xeste();
	private static Clinic clinic = new Clinic();
	private static BasHekim basHekim = new BasHekim();
	private static Doktor doktor = new Doktor();
	private static Appointment appointment = new Appointment();
	private DefaultTableModel doktorModel = null;
	private Object[] doktorData = null;
	private DefaultTableModel whourModel = null;
	private Object[] whourData = null;

	private DefaultTableModel appDateModel = null;
	private Object[] appDateData = null;
	private JTable tbl_doktor;
	private JTable tbl_whour;
	private int selDoktorId = 0;
	private String selDoktorName = null;
	private JTable tbl_appDate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					XesteGUI frame = new XesteGUI(xeste);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws Throwable
	 */
	public XesteGUI(Xeste xeste) throws Throwable {
		doktorModel = new DefaultTableModel();
		Object[] colDoktor = new Object[2];
		colDoktor[0] = "ID";
		colDoktor[1] = "Ad, Soyad";
		doktorModel.setColumnIdentifiers(colDoktor);

		whourModel = new DefaultTableModel();
		Object[] colwhour = new Object[2];
		colwhour[0] = "Id";
		colwhour[1] = "Tarix, Zaman";
		whourModel.setColumnIdentifiers(colwhour);
		whourData = new Object[2];

		appDateModel = new DefaultTableModel();
		Object[] colAppdate = new Object[4];
		colAppdate[0] = "ID";
		colAppdate[1] = "Həkim İD";
		colAppdate[2] = "Həkim";
		colAppdate[3] = "Qəbul Saatı";
		appDateModel.setColumnIdentifiers(colAppdate);
		appDateData =new Object[4];
		for (int i = 0; i < appointment.getAppointmentList(xeste.getId()).size(); i++) {
			appDateData[0] = appointment.getAppointmentList(xeste.getId()).get(i).getId();
			appDateData[1] = appointment.getAppointmentList(xeste.getId()).get(i).getDoktorId();
			appDateData[2] = appointment.getAppointmentList(xeste.getId()).get(i).getDoktorName();
			appDateData[3] = appointment.getAppointmentList(xeste.getId()).get(i).getAppDate();
			appDateModel.addRow(appDateData);
		}

		setResizable(false);
		setTitle("X\u0259st\u0259xana \u0130dar\u0259 Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 550);
		w_pane = new JPanel();
		w_pane.setBackground(SystemColor.inactiveCaptionBorder);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lbl_whourDoktorname = new JLabel();
		lbl_whourDoktorname.setText("Xoş Gəldiniz Hörmətli " + xeste.getName());
		lbl_whourDoktorname.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 16));
		lbl_whourDoktorname.setBounds(10, 11, 576, 36);
		w_pane.add(lbl_whourDoktorname);

		JButton btn_logOut = new JButton("çıxış");
		btn_logOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI g = new LoginGUI();
				g.setVisible(true);
				dispose();

			}
		});
		btn_logOut.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_logOut.setBounds(642, 19, 84, 23);
		w_pane.add(btn_logOut);

		JTabbedPane w_tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		w_tabbedPane.setBounds(9, 52, 716, 450);
		w_pane.add(w_tabbedPane);

		JPanel w_appointment = new JPanel();
		w_appointment.setBackground(SystemColor.inactiveCaption);
		w_tabbedPane.addTab("Qəbul", null, w_appointment, null);
		w_appointment.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 41, 295, 370);
		w_appointment.add(scrollPane);

		tbl_doktor = new JTable(doktorModel);
		scrollPane.setViewportView(tbl_doktor);

		JLabel lblNewLabel = new JLabel("Həkimlərin Siyahısı");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 16, 212, 25);
		w_appointment.add(lblNewLabel);

		JComboBox select_clinic = new JComboBox();
		select_clinic.setBounds(315, 41, 160, 25);
		select_clinic.addItem("Klinika sec");
		for (int i = 0; i < clinic.getClinicList().size(); i++) {
			select_clinic
					.addItem(new Item(clinic.getClinicList().get(i).getId(), clinic.getClinicList().get(i).getName()));
		}
		select_clinic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (select_clinic.getSelectedIndex() != 0) {
					JComboBox c = (JComboBox) e.getSource();
					Item item = (Item) c.getSelectedItem();
					DefaultTableModel clearModel = (DefaultTableModel) tbl_doktor.getModel();
					clearModel.setRowCount(0);
					doktorData = new Object[2];
					try {
						for (int i = 0; i < basHekim.getClinicDoctorList(item.getKey()).size(); i++) {
							doktorData[0] = basHekim.getClinicDoctorList(item.getKey()).get(i).getId();
							doktorData[1] = basHekim.getClinicDoctorList(item.getKey()).get(i).getName();
							doktorModel.addRow(doktorData);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else {
					DefaultTableModel clearModel = (DefaultTableModel) tbl_doktor.getModel();
					clearModel.setRowCount(0);
				}
			}
		});

		w_appointment.add(select_clinic);

		JLabel lblKlinikalar = new JLabel("Klinikalar");
		lblKlinikalar.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblKlinikalar.setBounds(315, 16, 135, 25);
		w_appointment.add(lblKlinikalar);

		JLabel safdas = new JLabel("Həkim seç");
		safdas.setFont(new Font("Tahoma", Font.BOLD, 16));
		safdas.setBounds(315, 95, 149, 25);
		w_appointment.add(safdas);

		JButton btn_selectDoktor = new JButton("seç");
		btn_selectDoktor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tbl_doktor.getSelectedRow();
				if (row >= 0&&select_clinic.getSelectedIndex()!=0) {
					DefaultTableModel clearModel = (DefaultTableModel) tbl_whour.getModel();
					clearModel.setRowCount(0);
					String value = tbl_doktor.getModel().getValueAt(row, 0).toString();
					int selectDoktorId = Integer.parseInt(value);
					for (int i = 0; i < xeste.getWhourListForXeste(selectDoktorId).size(); i++) {
						whourData[0] = xeste.getWhourListForXeste(selectDoktorId).get(i).getId();
						whourData[1] = xeste.getWhourListForXeste(selectDoktorId).get(i).getWdate();
						whourModel.addRow(whourData);
					}
					selDoktorId = selectDoktorId;
					selDoktorName = tbl_doktor.getModel().getValueAt(row, 1).toString();
				} else {
					Helper.showMsg("xais olunur bir hekim secin");
				}
				
			}
		});
		btn_selectDoktor.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_selectDoktor.setBounds(315, 131, 160, 25);
		w_appointment.add(btn_selectDoktor);

		JScrollPane w_scrool = new JScrollPane();
		w_scrool.setBounds(485, 41, 216, 370);
		w_appointment.add(w_scrool);

		tbl_whour = new JTable(whourModel);
		tbl_whour.getColumnModel().getColumn(0).setPreferredWidth(5);
		w_scrool.setViewportView(tbl_whour);

		JLabel lblQebulSaatlar = new JLabel("Qəbul saatları");
		lblQebulSaatlar.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblQebulSaatlar.setBounds(485, 16, 212, 25);
		w_appointment.add(lblQebulSaatlar);

		JLabel Qebulsad = new JLabel("Qebul Al");
		Qebulsad.setFont(new Font("Tahoma", Font.BOLD, 16));
		Qebulsad.setBounds(315, 192, 149, 25);
		w_appointment.add(Qebulsad);

		JButton btn_addAppointment = new JButton("qeyd ol");
		btn_addAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = tbl_whour.getSelectedRow();
				int docSelRow = tbl_doktor.getSelectedRow();
				if (selectRow >= 0 && docSelRow >= 0 && select_clinic.getSelectedIndex() != 0) {
					String date = tbl_whour.getModel().getValueAt(selectRow, 1).toString();
					boolean control=false;
					try {
						control = xeste.addAppointment(selDoktorId, selDoktorName, xeste.getId(), xeste.getName(),
								date);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (control) {
						Helper.showMsg("success");
						try {
							xeste.updateStatus(selDoktorId, date,"passive");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						updateWhourModel(selDoktorId);
						updateAppDateModel(xeste.getId());
					} else {
						Helper.showMsg("error");
					}
				} else {
					Helper.showMsg("zehmet olmasa hekim ve qebul saati secin");
				}
			}
		});
		btn_addAppointment.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_addAppointment.setBounds(315, 228, 160, 25);
		w_appointment.add(btn_addAppointment);

		JPanel w_appDate = new JPanel();
		w_tabbedPane.addTab("Qebul Saatlar", null, w_appDate, null);
		w_appDate.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 691, 380);
		w_appDate.add(scrollPane_1);

		tbl_appDate = new JTable(appDateModel);
		scrollPane_1.setViewportView(tbl_appDate);
		
		JButton btn_deleteAppoint = new JButton("sil");
		btn_deleteAppoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row=tbl_appDate.getSelectedRow();
				boolean control=false;
				boolean control2=false;
				if(row>=0) {
				String date=tbl_appDate.getModel().getValueAt(row, 3).toString();	
				String a=tbl_appDate.getModel().getValueAt(row, 0).toString();
				String strdokId=tbl_appDate.getModel().getValueAt(row, 1).toString();
				int selectId=Integer.parseInt(a);
				int dokId=Integer.parseInt(strdokId);
				try {
					control=xeste.deleteAppoint(selectId);
					control2=xeste.updateStatus(dokId, date, "active");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				updateAppDateModel(xeste.getId());
				if(control&&control2) {
				Helper.showMsg("delete");
				
				}else {
					Helper.showMsg("error");
				}
				}else {
					Helper.showMsg("bir qebul saati secin");
				}
			}
		});
		btn_deleteAppoint.setFont(new Font("Tahoma", Font.BOLD, 17));
		btn_deleteAppoint.setBounds(307, 399, 105, 23);
		w_appDate.add(btn_deleteAppoint);
	}

	public void updateWhourModel(int doktorId) {
		DefaultTableModel clearModel = (DefaultTableModel) tbl_whour.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < xeste.getWhourListForXeste(doktorId).size(); i++) {
			whourData[0] = xeste.getWhourListForXeste(doktorId).get(i).getId();
			whourData[1] = xeste.getWhourListForXeste(doktorId).get(i).getWdate();
			whourModel.addRow(whourData);
		}
	}

	public void updateAppDateModel(int xesteID) {
		DefaultTableModel clear= (DefaultTableModel) tbl_appDate.getModel();
		clear.setRowCount(0);
		try {
			for (int i = 0; i < appointment.getAppointmentList(xesteID).size(); i++) {
				appDateData[0] = appointment.getAppointmentList(xesteID).get(i).getId();
				appDateData[1] = appointment.getAppointmentList(xesteID).get(i).getDoktorId();
				appDateData[2] = appointment.getAppointmentList(xesteID).get(i).getDoktorName();
				appDateData[3] = appointment.getAppointmentList(xesteID).get(i).getAppDate();
				appDateModel.addRow(appDateData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
