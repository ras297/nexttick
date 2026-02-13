package ir.maleki.sideprojects.nexttick.application.reservation;

import ir.maleki.sideprojects.nexttick.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> {
}
