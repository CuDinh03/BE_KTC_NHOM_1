package ktc.nhom1ktc.entity.expense.management;

import jakarta.persistence.*;
import ktc.nhom1ktc.entity.expense.management.category.Category;
import ktc.nhom1ktc.entity.expense.management.income.MonthlyIncome;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.UUID;

@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "monthly_log",
        uniqueConstraints = {
        @UniqueConstraint(name = "uc_monthlylog_year_month_category_account", columnNames = {"year", "month", "category_id", "account_id"}),
        @UniqueConstraint(name = "uc_monthlylog_year_month_category_username", columnNames = {"year", "month", "category_id", "created_by"})

        })
public class MonthlyLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "year", nullable = false)
    private Year year;

    @Column(name = "month", nullable = false)
    @Convert(converter = MonthOrderConverter.class)
    private Month month;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "category_id", nullable = false)
//    private Category category;

    @JoinColumn(name = "category_id", nullable = false)
    private UUID categoryId;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "monthly_income_id", nullable = false)
    private MonthlyIncome monthlyIncome;

    @Column(name = "budget", nullable = false)
    private BigDecimal budget;

    @Column(name = "expense_sum", nullable = false)
    private BigDecimal expenseSum;

}