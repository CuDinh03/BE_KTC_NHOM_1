package ktc.nhom1ktc.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "account", uniqueConstraints = {
        @UniqueConstraint(name = "uc_account_code", columnNames = {"code"}),
        @UniqueConstraint(name = "uc_account_username", columnNames = {"username"})
})
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true, name = "code")
    private String code;
    @Column(nullable = false, unique = true, name = "username")
    private String username;
    private String password;
    @ManyToOne
    private Role role;
    private String createdBy;
    private String updatedBy;
    private Date createdAt;
    private Date updateAt;
    private Integer status;
    private BigDecimal incomeSum;
    private BigDecimal expenseSum;
}
