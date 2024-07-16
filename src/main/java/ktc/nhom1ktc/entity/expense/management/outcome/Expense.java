package ktc.nhom1ktc.entity.expense.management.outcome;

import jakarta.persistence.*;
import ktc.nhom1ktc.entity.Account;
import ktc.nhom1ktc.entity.expense.management.MonthOrderConverter;
import ktc.nhom1ktc.entity.expense.management.category.Category;
import ktc.nhom1ktc.entity.expense.management.MonthlyLog;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "account_id", nullable = false)
//    private Account account;

    @JoinColumn(name = "account_id", nullable = false)
    private UUID accountId;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "category_id", nullable = false)
//    private Category category;

    @JoinColumn(name = "category_id", nullable = false)
    private UUID categoryId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "year")
    private Year year;

    @Column(name = "month")
    @Convert(converter = MonthOrderConverter.class)
    private Month month;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "monthly_log_id", nullable = false)
//    private MonthlyLog monthlyLog;

    @JoinColumn(name = "monthly_log_id", nullable = false)
    private UUID monthlyLogId;
}