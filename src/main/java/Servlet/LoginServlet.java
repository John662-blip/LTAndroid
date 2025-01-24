package Servlet;

import Modal.AccountModal;
import ObjectEntity.Account;
import Untils.Valid;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    Valid valid = new Valid();
    AccountModal modal = new AccountModal();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        JSONObject result = new JSONObject();

        try {
            // Lấy dữ liệu từ query string
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            // Kiểm tra xem email và password có tồn tại không
            if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
                result.put("errCode", 1);
                result.put("message", "Email hoặc mật khẩu không được để trống");
                resp.getWriter().write(result.toString());
                return;
            }

            // Kiểm tra xem email có hợp lệ không
            if (!valid.isValidEmail(email)) {
                result.put("errCode", 1);
                result.put("message", "Email không hợp lệ");
                resp.getWriter().write(result.toString());
                return;
            }

            int loginResult = modal.checkLogin(email, password);
            if (loginResult > 0) { // Đăng nhập thành công, trả về ID tài khoản
                JSONObject data = new JSONObject();
                data.put("id", loginResult);
                data.put("email", email);

                result.put("errCode", 0);
                result.put("message", "Đăng nhập thành công");
                result.put("data", data);
                resp.getWriter().write(result.toString());
            } else if (loginResult == -1) {
                result.put("errCode", 2);
                result.put("message", "Không tìm thấy tài khoản hoặc tài khoản không hoạt động");
                resp.getWriter().write(result.toString());
            } else if (loginResult == -2) {
                result.put("errCode", 3);
                result.put("message", "Mật khẩu không chính xác");
                resp.getWriter().write(result.toString());
            }
        } catch (Exception e) {
            result.put("errCode", 4);
            result.put("message", "Có lỗi xảy ra trong quá trình xử lý");
            resp.getWriter().write(result.toString());
            e.printStackTrace();
        }
    }
}
