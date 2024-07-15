package ktc.nhom1ktc.entity.expense.management.category;

import jakarta.persistence.*;
import ktc.nhom1ktc.entity.Account;
import ktc.nhom1ktc.entity.expense.management.income.MonthlyIncome;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "monthly_log",
        uniqueConstraints = {
        @UniqueConstraint(name = "uc_monthlylog_year_month", columnNames = {"year", "month", "category_id", "account_id"})
})
public class MonthlyLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "year", nullable = false)
    private Year year;

    @Column(name = "month", nullable = false)
    private Month month;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @ManyToOne(optional = false)
    @JoinColumn(name = "monthly_income_id", nullable = false)
    private MonthlyIncome monthlyIncome;

    @Column(name = "budget", nullable = false)
    private BigDecimal budget;

    @Column(name = "expense_sum", nullable = false)
    private BigDecimal expenseSum;

}