package ktc.nhom1ktc.repository.expense.management.income;

import ktc.nhom1ktc.entity.expense.management.income.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface IncomeRepository extends JpaRepository<Income, UUID> {

    List<Income> findAllByDateBetweenAndCreatedBy(LocalDate start, LocalDate end, String name);

    Income findByIdAndCreatedBy(UUID id, String name);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE Income i SET i.amount = :amount WHERE i.id = :id")
    int setAmount(@Param("id") UUID id,
                  @Param("amount") BigDecimal amount);

    int deleteByIdAndCreatedBy(UUID id, String name);
}