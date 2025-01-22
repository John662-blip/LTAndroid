package ObjectEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class OTP_Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int idAccount;
    @Column
    private String otp_code;
    @Column
    private Date created_at;
    @Column
    private Date expires_at;
    @Column
    private boolean is_verified;//true : da su dung | false : chua su dung
}
