package Servlet;

import Modal.AccountModal;
import Untils.RequestUtils;
import Untils.Valid;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;

@WebServlet(urlPatterns = {"/create_account","/update_account"})
public class AccountServlet extends HttpServlet {
    Valid valid = new Valid();
    AccountModal modal = new AccountModal();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        switch (path){
            case "/create_account":
                create_account(req, resp);
                break;
            case "/update_account":
                update_account(req, resp);
                break;
        }
    }
    void create_account(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = RequestUtils.getBody(req);
        JSONObject jsonObject = new JSONObject(json);
        JSONObject result = new JSONObject();
        try {
            String email = jsonObject.getString("email");
            String password = jsonObject.optString("password",null);
            String name = jsonObject.optString("name",null);
            String phone = jsonObject.optString("phone",null);
            int type = jsonObject.optInt("type",1);
            if (!valid.isValidEmail(email)){
                result.put("errCode", 1);
                result.put("message","Dữ liệu truyền vào không phù hợp");
                resp.getWriter().write(result.toString());
                return;
            }
            int a = modal.insert(email, password, name, phone, type);
            if (a!=-1){
                JSONObject data = new JSONObject();
                data.put("id_account", a);
                result.put("errCode", 0);
                result.put("message","Chèn thành công");
                result.put("data", data);
                resp.getWriter().write(result.toString());
                return;
            };
            result.put("errCode", 1);
            result.put("message","email không hợp lệ");
            resp.getWriter().write(result.toString());
            return;
        }
        catch (Exception e){
            result.put("errCode", 1);
            result.put("message","Dữ liệu truyền vào không phù hợp");
            resp.getWriter().write(result.toString());
            return;
        }
    }
    void update_account(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = RequestUtils.getBody(req);
        JSONObject jsonObject = new JSONObject(json);
        JSONObject result = new JSONObject();
        try {
            String email = jsonObject.getString("email");
            String password = jsonObject.getString("password");
            String name = jsonObject.optString("name",null);
            String phone = jsonObject.optString("phone",null);
            int idAccount = jsonObject.getInt("idAccount");
            String avatar  = jsonObject.optString("avatar",null);
            int type = jsonObject.getInt("type");
            if (!valid.isValidEmail(email)){
                result.put("errCode", 1);
                result.put("message","Dữ liệu truyền vào không phù hợp");
                resp.getWriter().write(result.toString());
                return;
            }
            int a = modal.update(idAccount,avatar,email,password,name,phone,type);
            if (a!=-1){
                result.put("errCode", 0);
                result.put("message","Update thành công");
                resp.getWriter().write(result.toString());
                return;
            };
            result.put("errCode", 1);
            result.put("message","email không hợp lệ");
            resp.getWriter().write(result.toString());
            return;
        }
        catch (Exception e){
            result.put("errCode", 1);
            result.put("message","Dữ liệu truyền vào không phù hợp");
            resp.getWriter().write(result.toString());
            return;
        }
    }
}
