package helper;

import java.sql.Date;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class Validation {

    public static Boolean isEmpty(String input) {
        if (input == null) {
            return true;
        }
        return input.equals("");
    }

    public static Boolean isEmpty(Date input) {
        if (input == null) {
            return true;
        }
        return false;
    }

  public static Boolean isEmail(String email) {
    // Biểu thức chính quy để kiểm tra định dạng email
    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    Pattern pat = Pattern.compile(emailRegex); // Dùng để biên dịch biểu thức chính quy

    if (email == null) {
        return false; // Nếu email là null, trả về false
    }

    return pat.matcher(email).matches(); // Kiểm tra xem email có khớp với biểu thức chính quy không
}


    public static boolean isNumber(String num) {
        boolean result = true;
        if (num == null) return false;
        try {
            long k = Long.parseLong(num);
            if(k < 0) {
                result = false;
            }
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }
 


public static boolean isPhoneNumber(String str) {
    // Loại bỏ khoảng trắng và dấu ngoặc đơn nếu có
    str = str.replaceAll("\\s+", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", "");

    // Kiểm tra số điện thoại bắt đầu bằng 0
    if (!str.startsWith("0")) {
        showMessage("Số điện thoại phải bắt đầu bằng số 0.");
        return false;
    }

    // Kiểm tra độ dài số điện thoại
    if (str.length() != 10) {
        showMessage("Số điện thoại phải có 10 chữ số.");
        return false;
    }

    // Kiểm tra xem có phải là số hay không
    if (!str.matches("\\d+")) {
        showMessage("Số điện thoại chỉ được chứa các chữ số.");
        return false;
    }

    // Kiểm tra định dạng với dấu gạch ngang
    if (str.matches("0\\d{2}-\\d{3}-\\d{4}")) { // Kiểm tra số điện thoại có dấu gạch ngang
        return true;
    } 
    
    // Kiểm tra định dạng với dấu ngoặc đơn
    if (str.matches("\\(0\\d{2}\\)\\d{3}-\\d{4}")) { // Kiểm tra số điện thoại có dấu ngoặc đơn
        return true;
    } 
    
    // Kiểm tra số điện thoại có 10 chữ số
    if (str.matches("0\\d{9}")) { // Kiểm tra số điện thoại 10 chữ số bắt đầu bằng 0
        return true;
    }

    // Thông báo lỗi nếu số điện thoại không hợp lệ
 //   showMessage("Số điện thoại không hợp lệ. Vui lòng kiểm tra lại.");
    return false; // Trả về false nếu chuỗi không phải là số điện thoại hợp lệ
}

// Hàm hiển thị thông báo
private static void showMessage(String message) {
    JOptionPane.showMessageDialog(null, message, "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
}


 public static boolean isNameValid(String name) {
    // Kiểm tra nếu họ tên rỗng
    if (isEmpty(name)) {
        return false; // Họ tên không được rỗng
    }

    // Biểu thức chính quy để kiểm tra họ tên
    String nameRegex = "^[\\p{L} .]+$"; 
    // \\p{L}: Cho phép mọi ký tự chữ cái Unicode, bao gồm chữ cái có dấu
    // .: Cho phép khoảng trắng

    return name.matches(nameRegex); // Trả về true nếu họ tên hợp lệ
}


}
