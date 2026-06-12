package hub.isaacode.bolao.infra.repository;

import hub.isaacode.bolao.domain.model.Round;
import hub.isaacode.bolao.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoundRepository extends JpaRepository<Round, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
