package ktc.nhom1ktc.repository;

import ktc.nhom1ktc.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {
    boolean existsByEmail(String email);

    Optional<Users> findByEmail(String email);

    @Query("select us from Users us where us.account.id = :idAccount")
    Optional<Users> findByIdAccount(@Param("idAccount") UUID idAccount);
}
