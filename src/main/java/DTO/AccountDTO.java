package DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {
    private int id;
    private String avatar;
    private String email;
    private String password;
    private String name;
    private String phone;
    private int type;// type : 0 : Admin . type: 1 :Khach
    private int account_status; // = 0 : Tai khoan khong hoat dong , 1 : dang hoat dong
}
