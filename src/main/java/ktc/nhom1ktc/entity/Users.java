package ktc.nhom1ktc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String code;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
    private Integer gender;
    private Date birthday;
    private String createdBy;
    private String updatedBy;
    private Date createdAt;
    private Date updateAt;
    private Integer status;

}
