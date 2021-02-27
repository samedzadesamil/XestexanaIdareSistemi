package Helper;

import javax.swing.JOptionPane;

public class Helper {

	public static void showMsg(String str) {
		String msg;
		switch (str) {
		case "fill":
			msg = "butun girisi bilgilerini tamamlayin";
			break;
		case "success":
			msg = "sisteme elave olundu";
			break;
		case "delete":
			msg = "sisteminden silindi";
			break;
		case "update":
			msg="ugurla deyisdirildi";
			break;
		default:
			msg = str;
		}
		JOptionPane.showMessageDialog(null, msg, "mesaj: ", JOptionPane.INFORMATION_MESSAGE);
	}

	public static boolean confrim(String str) {
		String msg;
		switch (str) {
		case "delete":
			msg = "Silmek istediyinizden eminmisiniz?";
			break;
		case "update":
			msg = "Adi deyisdirmek istediyinizden eminmisiniz?";
			break;
		default:
			msg = str;

		}
		int res = JOptionPane.showConfirmDialog(null, msg, "DIQQET!!! ", JOptionPane.YES_NO_OPTION);
		if (res == 0)
			return true;
		else
			return false;
	}

}
