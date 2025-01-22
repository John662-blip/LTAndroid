package Modal;

import JpaConfig.JpaConfig;
import ObjectEntity.Account;
import ObjectEntity.OTP_Token;
import Service.EmailService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;


import java.util.Date;
import java.util.List;
import java.util.Random;

public class OTPModal {
    EmailService emailService = new EmailService();
    public String Gen_OTP(int length){
        String OTP = "";
        Random rand = new Random();
        for(int i=0; i<length; i++){
            OTP += rand.nextInt(10);
        }
        return OTP;
    }
    public int InsertToken(int idAccount,String OTP,int type){
        EntityManager entityManager = JpaConfig.getEmFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            List<Account> lstAcc = entityManager.createQuery("From Account a where a.id = :id_Account",Account.class)
                    .setParameter("id_Account",idAccount)
                    .getResultList();
            if (lstAcc.size()==0){
                return -1;
            }
            Date now = new Date();
            long expiresInMillis = now.getTime() + 5 * 60 * 1000;//them 5 phut
            OTP_Token token = new OTP_Token();
            token.setIdAccount(idAccount);
            token.setOtp_code(OTP);
            token.set_verified(false);
            token.setCreated_at(now);
            token.setExpires_at(new Date(expiresInMillis));
            token.setType(type);
            transaction.begin();
            entityManager.persist(token);
            transaction.commit();
            return 0;
        }
        catch(Exception e){
            return -1;
        }
    }
}
