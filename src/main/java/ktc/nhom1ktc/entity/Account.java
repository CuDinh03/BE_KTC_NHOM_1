package ktc.nhom1ktc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import java.time.Year;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String code;
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
    private Year lastLoginedYear;
}
