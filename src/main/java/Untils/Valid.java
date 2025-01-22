package Untils;

public class Valid {
    public boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }
    public boolean isValidEmail(String email) {
        // Regex kiểm tra email
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
    public boolean isValidString(String string) {
        return string!=null && !string.isEmpty();
    }
}
