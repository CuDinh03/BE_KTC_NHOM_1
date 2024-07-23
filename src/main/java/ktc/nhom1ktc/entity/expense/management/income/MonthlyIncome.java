package ktc.nhom1ktc.entity.expense.management.income;

import jakarta.persistence.*;
import ktc.nhom1ktc.entity.expense.management.MonthOrderConverter;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "monthly_income", uniqueConstraints = {
        @UniqueConstraint(name = "uc_monthlyincome_year_month", columnNames = {"year", "month", "account_id"})
})

public class MonthlyIncome {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "year", nullable = false)
    private Year year;

    @Column(name = "month", nullable = false)
    @Convert(converter = MonthOrderConverter.class)
    private Month month;

//    @ManyToOne
//    @JoinColumn(name = "account_id")
//    private Account account;
    @JoinColumn(name = "account_id")
    private UUID accountId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @Column(name = "income_sum", nullable = false)
    private BigDecimal incomeSum;
}