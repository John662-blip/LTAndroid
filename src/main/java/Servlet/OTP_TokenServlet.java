package Servlet;

import Modal.AccountModal;
import Modal.OTPModal;
import ObjectEntity.Account;
import Service.EmailService;
import Untils.RequestUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;

@WebServlet(urlPatterns = {"/sendOTP","/verified_otp","/sendOTPReset","/verified_otpReset"})
public class OTP_TokenServlet extends HttpServlet {
    private OTPModal otpModal = new OTPModal();
    private EmailService emailService = new EmailService();
    private AccountModal accountModal = new AccountModal();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        switch (path){
            case "/sendOTP":
                sendOTP(req,resp);
                break;
            case "/verified_otp":
                verifyOTP(req,resp);
                break;
            case "/sendOTPReset":
                sendOTPReset(req,resp);
                break;
            case "/verified_otpReset":
                verifyOTP_reset(req,resp);
                break;
        }
    }
    private void sendOTP(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String otp = otpModal.Gen_OTP(4);
        String json = RequestUtils.getBody(req);
        JSONObject jsonObject = new JSONObject(json);
        JSONObject result = new JSONObject();
        try{
            int idAccount = jsonObject.getInt("idAccount");
            if (otpModal.InsertToken(idAccount, otp,1)==0){
                result.put("errCode",0);
                result.put("message","Đã thực hiện");
                String email = accountModal.SearchEmailByIdAccount(idAccount);
                emailService.SendOTP(email, otp);
                resp.getWriter().write(result.toString());
                return;
            }
            else {
                result.put("errCode",1);
                result.put("message","Dữ liệu truyền vào không phù hợp");
                resp.getWriter().write(result.toString());
                return;
            }
        }
        catch (Exception e){
            result.put("errCode",1);
            result.put("message","Dữ liệu truyền vào không phù hợp");
            resp.getWriter().write(result.toString());
            return;
        }
    }
    private void verifyOTP(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = RequestUtils.getBody(req);
        JSONObject jsonObject = new JSONObject(json);
        JSONObject result = new JSONObject();
        try{
            int idAccount = jsonObject.getInt("idAccount");
            String otp_code = jsonObject.getString("otp_code");
            if (accountModal.verifyOTP(otp_code,idAccount)==0){
                result.put("errCode",0);
                result.put("message","Đã thực hiện");
                resp.getWriter().write(result.toString());
                return;
            }
            else {
                result.put("errCode",1);
                result.put("message","Dữ liệu truyền vào không phù hợp");
                resp.getWriter().write(result.toString());
                return;
            }
        }
        catch (Exception e){
            result.put("errCode",1);
            result.put("message","Dữ liệu truyền vào không phù hợp");
            resp.getWriter().write(result.toString());
            return;
        }
    }
    void sendOTPReset(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String otp = otpModal.Gen_OTP(4);
        String json = RequestUtils.getBody(req);
        JSONObject jsonObject = new JSONObject(json);
        JSONObject result = new JSONObject();
        try{
            String email = jsonObject.getString("email");
            Account account = accountModal.SearchAccountByEmail(email);
            if (account==null){
                result.put("errCode",1);
                result.put("message","Không tìm thấy tài khoản này");
                resp.getWriter().write(result.toString());
                return;
            }
            if (otpModal.InsertToken(account.getId(), otp,2)==0){
                JSONObject data = new JSONObject();
                data.put("idAccount",account.getId());
                result.put("errCode",0);
                result.put("message","Đã thực hiện");
                result.put("data",data);
                emailService.SendOTPResetPassword(email, otp);
                resp.getWriter().write(result.toString());
                return;
            }
            else {
                result.put("errCode",1);
                result.put("message","Dữ liệu truyền vào không phù hợp");
                resp.getWriter().write(result.toString());
                return;
            }
        }
        catch (Exception e){
            result.put("errCode",1);
            result.put("message","Dữ liệu truyền vào không phù hợp");
            resp.getWriter().write(result.toString());
            return;
        }
    }
    private void verifyOTP_reset(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = RequestUtils.getBody(req);
        JSONObject jsonObject = new JSONObject(json);
        JSONObject result = new JSONObject();
        try{
            int idAccount = jsonObject.getInt("idAccount");
            String otp_code = jsonObject.getString("otp_code");
            if (accountModal.verifyOTP_reset(otp_code,idAccount)==0){
                JSONObject data = new JSONObject();
                data.put("idAccount",idAccount);
                result.put("errCode",0);
                result.put("message","Đã thực hiện");
                result.put("data",data);
                resp.getWriter().write(result.toString());
                return;
            }
            else {
                result.put("errCode",1);
                result.put("message","Dữ liệu truyền vào không phù hợp");
                resp.getWriter().write(result.toString());
                return;
            }
        }
        catch (Exception e){
            result.put("errCode",1);
            result.put("message","Dữ liệu truyền vào không phù hợp");
            resp.getWriter().write(result.toString());
            return;
        }
    }
}
