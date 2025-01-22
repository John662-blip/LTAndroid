package ObjectEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String avatar;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String phone;
    @Column
    private int type;// type : 0 : Admin . type: 1 :Khach
    @Column
    private int account_status; // = 0 : Tai khoan khong hoat dong , 1 : dang hoat dong
}
