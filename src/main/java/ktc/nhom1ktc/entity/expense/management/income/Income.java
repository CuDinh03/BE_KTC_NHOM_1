package ktc.nhom1ktc.entity.expense.management.income;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "income")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "account_id", nullable = false)
//    private Account account;
    @JoinColumn(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

//    @Column(name = "type", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private IncomeType type;

//    @Column(name = "status", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private Status status;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "monthly_income_id", nullable = false)
//    private MonthlyIncome monthlyIncome;

    @JoinColumn(name = "monthly_income_id", nullable = false)
    private UUID monthlyIncomeId;
}