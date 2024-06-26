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
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String code;
    private String username;
    private String password;
    @ManyToOne
    private Role role;
    private Date createdBy;
    private Date updatedBy;
    private Date createdAt;
    private Date updateAt;
    private Integer status;

}
