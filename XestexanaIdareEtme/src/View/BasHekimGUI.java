package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Model.BasHekim;
import Model.Clinic;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;
import java.sql.SQLException;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

import Helper.*;
import javax.swing.JComboBox;

public class BasHekimGUI extends JFrame {

	static BasHekim bashekim = new BasHekim();
	static Clinic clinic = new Clinic();
	private JPanel w_pane;
	private JTextField txt_hekimAd;
	private JTextField txt_hekimSeria;
	private JTextField txt_hekimİd;
	private JPasswordField txt_hekimParol;
	private JTable tbl_hekim;

	private DefaultTableModel doktorModel;
	private Object[] doktorData = null;

	private DefaultTableModel clinicModel = null;
	private Object[] clinicData = null;

	private JTable tbl_clinic;
	private JTextField txt_clinicName;
	private JPopupMenu clinicMenu;
	private JTable tbl_worker;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BasHekimGUI frame = new BasHekimGUI(bashekim);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public BasHekimGUI(BasHekim bashekim) throws SQLException {
		setResizable(false);
		setTitle("Xəstəxana İdarə Sistemi");
//---------------------------------------------------------------------------
		doktorModel = new DefaultTableModel();
		Object[] colDoktorData = new Object[4];
		colDoktorData[0] = "ID";
		colDoktorData[1] = "Ad, Soyad";
		colDoktorData[2] = "Seria";
		colDoktorData[3] = "Şifre";
		doktorModel.setColumnIdentifiers(colDoktorData);
		doktorData = new Object[4];
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			doktorData[0] = bashekim.getDoctorList().get(i).getId();
			doktorData[1] = bashekim.getDoctorList().get(i).getName();
			doktorData[2] = bashekim.getDoctorList().get(i).getSeria();
			doktorData[3] = bashekim.getDoctorList().get(i).getParol();
			doktorModel.addRow(doktorData);
		}
		// ---------------------------------------------------------------------------
		// clinic model
		clinicModel = new DefaultTableModel();
		Object[] colClinicData = new Object[2];
		colClinicData[0] = "ID";
		colClinicData[1] = "Klinika";
		clinicModel.setColumnIdentifiers(colClinicData);
		clinicData = new Object[2];
		for (int i = 0; i < clinic.getClinicList().size(); i++) {
			clinicData[0] = clinic.getClinicList().get(i).getId();
			clinicData[1] = clinic.getClinicList().get(i).getName();
			clinicModel.addRow(clinicData);
		}
		// ------------------------------------------------------------------------------
		DefaultTableModel workerTableModel = new DefaultTableModel();
		Object[] colWorkerTable = new Object[2];
		colWorkerTable[0] = "id";
		colWorkerTable[1] = "Ad, Soyad";
		workerTableModel.setColumnIdentifiers(colWorkerTable);
		Object[] workerData = new Object[2];

		// -----------------------------------------------------------------------------
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 550);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lbl_bashekim_message = new JLabel();
		lbl_bashekim_message.setText("Xoş Gəldiniz Hörmətli " + bashekim.getName());
		lbl_bashekim_message.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 16));
		lbl_bashekim_message.setBounds(10, 11, 326, 36);
		w_pane.add(lbl_bashekim_message);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 52, 716, 445);
		w_pane.add(tabbedPane);

		JPanel w_doctor = new JPanel();
		w_doctor.setBackground(Color.WHITE);
		tabbedPane.addTab("Həkim idarəsi", null, w_doctor, null);
		w_doctor.setLayout(null);

		JLabel lblNewLabel = new JLabel("Ad, Soyad");
		lblNewLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
		lblNewLabel.setBounds(561, 21, 81, 25);
		w_doctor.add(lblNewLabel);

		JLabel lblSeiaNo = new JLabel("Seia No");
		lblSeiaNo.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
		lblSeiaNo.setBounds(561, 93, 81, 25);
		w_doctor.add(lblSeiaNo);

		JLabel lblParol = new JLabel("Parol");
		lblParol.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
		lblParol.setBounds(561, 165, 81, 25);
		w_doctor.add(lblParol);

		JLabel lblIstifadiId = new JLabel("istifadəçi İD");
		lblIstifadiId.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
		lblIstifadiId.setBounds(561, 303, 124, 25);
		w_doctor.add(lblIstifadiId);

		txt_hekimAd = new JTextField();
		txt_hekimAd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_hekimAd.setBounds(561, 57, 140, 25);
		w_doctor.add(txt_hekimAd);
		txt_hekimAd.setColumns(10);

		txt_hekimSeria = new JTextField();
		txt_hekimSeria.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_hekimSeria.setColumns(10);
		txt_hekimSeria.setBounds(561, 129, 140, 25);
		w_doctor.add(txt_hekimSeria);

		txt_hekimİd = new JTextField();
		txt_hekimİd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_hekimİd.setColumns(10);
		txt_hekimİd.setBounds(561, 347, 140, 25);
		w_doctor.add(txt_hekimİd);

		txt_hekimParol = new JPasswordField();
		txt_hekimParol.setBounds(561, 201, 140, 25);
		w_doctor.add(txt_hekimParol);
		// --------------------------------------------------------------------------------------------------------------------------------------
		JButton btn_hekimElaveEt = new JButton("əlavə et");
		btn_hekimElaveEt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txt_hekimAd.getText().length() == 0 || txt_hekimSeria.getText().length() == 0
						|| txt_hekimParol.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {

						boolean control = bashekim.addDoktor(txt_hekimSeria.getText(), txt_hekimParol.getText(),
								txt_hekimAd.getText());

						if (control) {
							Helper.showMsg("success");
							txt_hekimAd.setText(null);
							txt_hekimSeria.setText(null);
							txt_hekimParol.setText(null);
							updateDoctorModel();}
						else {
							Helper.showMsg("Daxil etdiyiniz seria nomresi sehv ola biler");
							txt_hekimSeria.setText(null);
						}
						updateDoctorModel();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_hekimElaveEt.setFont(new Font("Tahoma", Font.BOLD, 13));
		btn_hekimElaveEt.setBounds(561, 246, 140, 23);
		w_doctor.add(btn_hekimElaveEt);

		// ----------------------------------------------------------------------------------------------------------------------------------

		JButton btn_hekimSil = new JButton("sil");
		btn_hekimSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txt_hekimİd.getText().length() == 0) {
					Helper.showMsg("kecerli bir hekim ID si secin");
				} else {

					if (Helper.confrim("delete")) {
						try {
							int selectId = Integer.parseInt(txt_hekimİd.getText());
							boolean netice = bashekim.deleteDoktor(selectId);
							if (netice)
								Helper.showMsg("delete");
							txt_hekimİd.setText(null);
							updateDoctorModel();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btn_hekimSil.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_hekimSil.setBounds(561, 383, 140, 23);
		w_doctor.add(btn_hekimSil);
		// ----------------------------------------------------------------------------------------------------------------------------------------
		JScrollPane w_scrollHekim = new JScrollPane();
		w_scrollHekim.setBounds(10, 31, 541, 375);
		w_doctor.add(w_scrollHekim);

		tbl_hekim = new JTable(doktorModel);
		w_scrollHekim.setViewportView(tbl_hekim);

		// ------------------------------------------------------------------------------------------------------------------

		tbl_hekim.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					txt_hekimİd.setText(tbl_hekim.getValueAt(tbl_hekim.getSelectedRow(), 0).toString());
				} catch (Exception ex) {

				}

			}
		});
		// -------------------------------------------------------------------------------------------------------------------------

		tbl_hekim.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int selectId = Integer.parseInt(tbl_hekim.getValueAt(tbl_hekim.getSelectedRow(), 0).toString());
					String SelectName = tbl_hekim.getValueAt(tbl_hekim.getSelectedRow(), 1).toString();
					String SelectSeria = tbl_hekim.getValueAt(tbl_hekim.getSelectedRow(), 2).toString();
					String Selectparol = tbl_hekim.getValueAt(tbl_hekim.getSelectedRow(), 3).toString();
					try {
						bashekim.updateDoktor(selectId, SelectSeria, Selectparol, SelectName);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}
		});

		// -------------------------------------------------------------------------------------------------------------------------
		// klinika ile elaqeli

		JPanel w_clinic = new JPanel();
		w_clinic.setBackground(Color.WHITE);
		tabbedPane.addTab("Klinika", null, w_clinic, null);
		w_clinic.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 269, 395);
		w_clinic.add(scrollPane);

		clinicMenu = new JPopupMenu();
		JMenuItem updateMenu = new JMenuItem("Deyisdir");
		JMenuItem deleteMenu = new JMenuItem("Sil");
		clinicMenu.add(updateMenu);
		clinicMenu.add(deleteMenu);

		updateMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selId = Integer.parseInt(tbl_clinic.getValueAt(tbl_clinic.getSelectedRow(), 0).toString());
				Clinic selClinic;
				try {
					selClinic = clinic.getFech(selId);
					UpdateClinicGUI updateClinicGUI = new UpdateClinicGUI(selClinic);
					updateClinicGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					updateClinicGUI.setVisible(true);
					updateClinicGUI.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							try {
								updateClinicModel();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
			}
		});

		deleteMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selId = Integer.parseInt(tbl_clinic.getValueAt(tbl_clinic.getSelectedRow(), 0).toString());
				if (Helper.confrim("delete")) {
					try {
						if (clinic.deleteClinic(selId)) {
							Helper.showMsg("delete");
							updateClinicModel();
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});

		tbl_clinic = new JTable(clinicModel);
		tbl_clinic.setComponentPopupMenu(clinicMenu);
		tbl_clinic.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				int selectedRow = tbl_clinic.rowAtPoint(point);
				tbl_clinic.setRowSelectionInterval(selectedRow, selectedRow);
			}

		});

		scrollPane.setViewportView(tbl_clinic);

		JScrollPane w_scrolWorker = new JScrollPane();
		w_scrolWorker.setBounds(432, 11, 269, 395);
		w_clinic.add(w_scrolWorker);

		tbl_worker = new JTable();
		w_scrolWorker.setViewportView(tbl_worker);

		JLabel lblKlinikaAd = new JLabel("klinika adı:");
		lblKlinikaAd.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		lblKlinikaAd.setBounds(282, 25, 146, 25);
		w_clinic.add(lblKlinikaAd);

		txt_clinicName = new JTextField();
		txt_clinicName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_clinicName.setColumns(10);
		txt_clinicName.setBounds(282, 61, 146, 25);
		w_clinic.add(txt_clinicName);

		JButton btn_clinicAdd = new JButton("əlavə et");
		btn_clinicAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txt_clinicName.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						boolean a = clinic.addClinic(txt_clinicName.getText());
						if (a) {
							Helper.showMsg("success");
							updateClinicModel();
							txt_clinicName.setText(null);
						}

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_clinicAdd.setFont(new Font("Tahoma", Font.BOLD, 13));
		btn_clinicAdd.setBounds(282, 91, 146, 51);
		w_clinic.add(btn_clinicAdd);

		JComboBox select_doctor = new JComboBox();
		select_doctor.setBounds(282, 273, 146, 32);
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			select_doctor.addItem(
					new Item(bashekim.getDoctorList().get(i).getId(), bashekim.getDoctorList().get(i).getName()));
		}
		select_doctor.addActionListener(e -> {
			JComboBox c = (JComboBox) e.getSource();
			Item item = (Item) c.getSelectedItem();
		});

		w_clinic.add(select_doctor);

		JButton btn_addWorker = new JButton("əlavə et");
		btn_addWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectTableRow = tbl_clinic.getSelectedRow();
				if (selectTableRow >= 0) {
					String selectClinic = tbl_clinic.getModel().getValueAt(selectTableRow, 0).toString();
					int selectClinicId = Integer.parseInt(selectClinic);
					Item item = (Item) select_doctor.getSelectedItem();

					boolean control=false;
					try {
						control = bashekim.addWorker(item.getKey(), selectClinicId);
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if (control) {
						Helper.showMsg("success");
						int selectRow = tbl_clinic.getSelectedRow();
						String selClinic = tbl_clinic.getModel().getValueAt(selectRow, 0).toString();
						int selClinicId = Integer.parseInt(selClinic);
						DefaultTableModel clearModel=(DefaultTableModel) tbl_worker.getModel();
						clearModel.setRowCount(0);
						try {
							for(int i=0;i<bashekim.getClinicDoctorList(selClinicId).size();i++) {
								workerData[0]=bashekim.getClinicDoctorList(selClinicId).get(i).getId();
								workerData[1]=bashekim.getClinicDoctorList(selClinicId).get(i).getName();
								workerTableModel.addRow(workerData);
								tbl_worker.setModel(workerTableModel);
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
					} else {
						Helper.showMsg("error");
					}
				} else {
					Helper.showMsg("bir klinika secin");
				}
			}
		});
		btn_addWorker.setFont(new Font("Tahoma", Font.BOLD, 13));
		btn_addWorker.setBounds(282, 316, 146, 51);
		w_clinic.add(btn_addWorker);

		JLabel lblKlinikaAd_1 = new JLabel("klinika adı:");
		lblKlinikaAd_1.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		lblKlinikaAd_1.setBounds(282, 156, 146, 25);
		w_clinic.add(lblKlinikaAd_1);

		JButton btn_clinicSelect = new JButton("SEC");
		btn_clinicSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = tbl_clinic.getSelectedRow();
				if (selectRow >= 0) {
					String selClinic = tbl_clinic.getModel().getValueAt(selectRow, 0).toString();
					int selClinicId = Integer.parseInt(selClinic);
					DefaultTableModel clearModel=(DefaultTableModel) tbl_worker.getModel();
					clearModel.setRowCount(0);
					try {
						for(int i=0;i<bashekim.getClinicDoctorList(selClinicId).size();i++) {
							workerData[0]=bashekim.getClinicDoctorList(selClinicId).get(i).getId();
							workerData[1]=bashekim.getClinicDoctorList(selClinicId).get(i).getName();
							workerTableModel.addRow(workerData);
							tbl_worker.setModel(workerTableModel);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				} else {
					Helper.showMsg("xais olunur bir klinika secin");
				}

			}
		});
		btn_clinicSelect.setFont(new Font("Tahoma", Font.BOLD, 13));
		btn_clinicSelect.setBounds(282, 192, 146, 51);
		w_clinic.add(btn_clinicSelect);
		
		JButton btn_logOut = new JButton("çıxış");
		btn_logOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI gui=new LoginGUI();
				gui.setVisible(true);
				dispose();
			}
		});
		btn_logOut.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_logOut.setBounds(637, 21, 89, 23);
		w_pane.add(btn_logOut);

		// -------------------------------------------------------------------------------------------------------------------------
	}

	public void updateClinicModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) tbl_clinic.getModel();
		clearModel.setRowCount(0);
		clinicData = new Object[2];
		for (int i = 0; i < clinic.getClinicList().size(); i++) {
			clinicData[0] = clinic.getClinicList().get(i).getId();
			clinicData[1] = clinic.getClinicList().get(i).getName();
			clinicModel.addRow(clinicData);
		}
	}

	public void updateDoctorModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) tbl_hekim.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			doktorData[0] = bashekim.getDoctorList().get(i).getId();
			doktorData[1] = bashekim.getDoctorList().get(i).getName();
			doktorData[2] = bashekim.getDoctorList().get(i).getSeria();
			doktorData[3] = bashekim.getDoctorList().get(i).getParol();
			doktorModel.addRow(doktorData);
		}
	}
}
