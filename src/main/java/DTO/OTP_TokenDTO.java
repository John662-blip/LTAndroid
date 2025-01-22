package DTO;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OTP_TokenDTO {
    private int id;
    private int idAccount;
    private String otp_code;
    private Date created_at;
    private Date expires_at;
    private boolean is_verified;
    private int type;
}
