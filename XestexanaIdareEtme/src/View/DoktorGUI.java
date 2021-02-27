package View;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.DefaultComboBoxModel;
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

import com.toedter.calendar.JDateChooser;

import Helper.Helper;
import Model.Doktor;
import Model.Whour;

public class DoktorGUI extends JFrame {

	private JPanel w_pane;

	private static Doktor doktor = new Doktor();
	private JTable tbl_whour;
	private DefaultTableModel whourModel = null;
	private Object[] whourData = null;;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoktorGUI frame = new DoktorGUI(doktor);
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
	public DoktorGUI(Doktor doktor) {
		Whour whour= new Whour();
		whourModel=new DefaultTableModel();
		Object[] colWhourModel = new Object[3];
		
		colWhourModel[0] = "ID";
		colWhourModel[1] = "Tarix, Zaman";
		colWhourModel[2] = "Rezerv veziyyeti";
		whourModel.setColumnIdentifiers(colWhourModel);
	    whourData = new Object[3];
		  for(int i=0;i<doktor.getWhourList(doktor.getId()).size();i++) {
		  whourData[0]=doktor.getWhourList(doktor.getId()).get(i).getId();
		  whourData[1]=doktor.getWhourList(doktor.getId()).get(i).getWdate();
		  whourData[2]=doktor.getWhourList(doktor.getId()).get(i).getStatus();
		  whourModel.addRow(whourData); }
		 

		setTitle("Xəstəxana İdarə Sistemi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 550);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lbl_whourDoktorname = new JLabel();
		lbl_whourDoktorname.setText("Xoş Gəldiniz Hörmətli " + doktor.getName());
		lbl_whourDoktorname.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 16));
		lbl_whourDoktorname.setBounds(10, 11, 576, 36);
		w_pane.add(lbl_whourDoktorname);

		JButton btn_logOut = new JButton("çıxış");
		btn_logOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI gui=new LoginGUI();
				gui.setVisible(true);
				dispose();
			}
		});
		btn_logOut.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_logOut.setBounds(642, 19, 84, 23);
		w_pane.add(btn_logOut);

		JTabbedPane w_tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		w_tabbedPane.setBounds(9, 52, 716, 450);
		w_pane.add(w_tabbedPane);

		JPanel w_workHourPanel = new JPanel();
		w_workHourPanel.setBackground(Color.DARK_GRAY);
		w_tabbedPane.addTab("İş saatları", null, w_workHourPanel, null);
		w_workHourPanel.setLayout(null);

		JDateChooser select_date = new JDateChooser();
		select_date.setBounds(10, 11, 155, 21);
		w_workHourPanel.add(select_date);

		JComboBox select_time = new JComboBox();
		select_time.setModel(new DefaultComboBoxModel(
				new String[] { "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "14:00", "14:30", "15:00" }));
		select_time.setBounds(175, 11, 75, 21);
		w_workHourPanel.add(select_time);

		JButton btn_addWhour = new JButton("əlavə et");
		btn_addWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = "";
				try {
					date = sdf.format(select_date.getDate());
				} catch (Exception e2) {

				}
				String time = select_time.getSelectedItem().toString();
				String workHour = date + " " + time + ":00";
				if (date.length() == 0) {
					Helper.showMsg("xais edirik tarix  daxil edin");
				} else {
					boolean control=false;
					try {
						control = doktor.addWhour(doktor.getId(), doktor.getName(), workHour);
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if (control) {
						Helper.showMsg("success");
						try {
							updateWhourModel(doktor);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						Helper.showMsg("error");
					}
				}

			}
		});
		btn_addWhour.setFont(new Font("Tahoma", Font.BOLD, 11));
		btn_addWhour.setBounds(261, 11, 88, 21);
		w_workHourPanel.add(btn_addWhour);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 691, 351);
		w_workHourPanel.add(scrollPane);

		tbl_whour = new JTable(whourModel);
		scrollPane.setViewportView(tbl_whour);
		
		JButton btn_deleteWhour = new JButton("sil");
		btn_deleteWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			int selectRow=tbl_whour.getSelectedRow();
			if(selectRow>=0) {
				String selectTableRow=tbl_whour.getModel().getValueAt(selectRow,0).toString();
				String date=tbl_whour.getModel().getValueAt(selectRow, 1).toString();
				String status=tbl_whour.getModel().getValueAt(selectRow, 2).toString();
				int selectRowId=Integer.parseInt(selectTableRow);
				 boolean control=false;
				try {
					control = doktor.deleteWhour(selectRowId);
					 if(control) {
						  Helper.showMsg("delete");
						updateWhourModel(doktor);
						 if(status.equals("passive")) {
							 doktor.deleteAppointFromDoktor(doktor.getId(), date);
						 }
						
						
					 }else {
						 Helper.showMsg("error");
					 }

				
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}else {
				Helper.showMsg("xais olunur silmek ucun bir tarix secin");
			}
				
			}
		});
		btn_deleteWhour.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_deleteWhour.setBounds(359, 11, 88, 21);
		w_workHourPanel.add(btn_deleteWhour);
	}
	public void updateWhourModel(Doktor doktor) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) tbl_whour.getModel();
		clearModel.setRowCount(0);
	    whourData = new Object[3];
	    for(int i=0;i<doktor.getWhourList(doktor.getId()).size();i++) {
			  whourData[0]=doktor.getWhourList(doktor.getId()).get(i).getId();
			  whourData[1]=doktor.getWhourList(doktor.getId()).get(i).getWdate();
			  whourData[2]=doktor.getWhourList(doktor.getId()).get(i).getStatus();
			  whourModel.addRow(whourData); }

	}
}
