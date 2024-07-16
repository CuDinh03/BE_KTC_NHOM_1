package ktc.nhom1ktc.repository.expense.management.outcome;

import ktc.nhom1ktc.entity.expense.management.outcome.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {


}