package Modal;

import JpaConfig.JpaConfig;
import ObjectEntity.Account;
import ObjectEntity.OTP_Token;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Date;
import java.util.List;

public class AccountModal {
    public int insert(String email, String password,String name , String phone, int type) {
        EntityManager entityManager = JpaConfig.getEmFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Account account = new Account();

        account.setName(name);
        account.setPhone(phone);
        account.setEmail(email);
        account.setPassword(password);
        account.setType(type);
        account.setAccount_status(0);
        try {
            List<Account> lstAcc = entityManager.createQuery("from Account a where a.email = :email and a.account_status = :account_st",Account.class)
                    .setParameter("email",email)
                    .setParameter("account_st",1).getResultList();
            if (!lstAcc.isEmpty()) {
                return -1;
            }
            transaction.begin();
            entityManager.persist(account); // Sử dụng persist thay vì merge cho insert
            transaction.commit();
            return account.getId();
        } catch (Exception e) {
            System.out.println("Error inserting customer: " + e.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
        return -1;
    }
    public String SearchEmailByIdAccount(int idAccount) {
        EntityManager entityManager = JpaConfig.getEmFactory().createEntityManager();
        try {
             Account account = (Account) entityManager.createQuery("from Account a where a.id = :id_Acc")
                    .setParameter("id_Acc", idAccount)
                     .getSingleResult();
             return account.getEmail();
        } catch (Exception e) {
            return null;
        }
    }
    public int verifyOTP(String otp,int idAccount) {
        EntityManager entityManager = JpaConfig.getEmFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            Date date = new Date();
            OTP_Token otpToken = entityManager.createQuery("from OTP_Token otp where " +
                    "otp.created_at<= :date and otp.expires_at>=:date " +
                            "and otp.otp_code = :code and otp.is_verified = :bool " +
                            "and otp.idAccount = :id_Acc",OTP_Token.class)
                    .setParameter("date",date)
                    .setParameter("code",otp)
                    .setParameter("bool",false)
                    .setParameter("id_Acc",idAccount)
                    .getSingleResult();
            Account account = (Account) entityManager.createQuery("from Account a where a.id = :id_Acc")
                    .setParameter("id_Acc", idAccount)
                    .getSingleResult();
            if (otpToken != null && account != null) {
                transaction.begin();
                otpToken.set_verified(true);
                account.setAccount_status(1);
                transaction.commit();
                return 0;
            }
            else{
                return -1;
            }
        }
        catch (Exception e){
            return -1;
        }
    }
    public int update(int idAccount,String avatar,String email, String password,String name , String phone, int type) {
        EntityManager entityManager = JpaConfig.getEmFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            List<Account> lstAcc = entityManager.createQuery("from Account a where a.id = :idAccount and a.account_status = :accSta",Account.class)
                    .setParameter("idAccount",idAccount)
                    .setParameter("accSta",1).getResultList();
            if (lstAcc.isEmpty()) {
                return -1;
            }
            Account acc = lstAcc.get(0);
            transaction.begin();
            acc.setName(name);
            acc.setPhone(phone);
            acc.setEmail(email);
            acc.setPassword(password);
            acc.setAvatar(avatar);
            acc.setType(type);
            transaction.commit();
            return 0;
        } catch (Exception e) {
            System.out.println("Error inserting customer: " + e.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
        return -1;
    }
}
