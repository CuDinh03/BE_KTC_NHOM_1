package ktc.nhom1ktc.repository.expense.management.outcome;

import ktc.nhom1ktc.entity.expense.management.outcome.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    Expense findByIdAndCreatedBy(UUID id, String name);

    UUID deleteByIdAndCreatedBy(UUID id, String name);

    List<Expense> findAllByDateBetweenAndCreatedBy(LocalDate start, LocalDate end, String name);
}