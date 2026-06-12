package hub.isaacode.bolao.infra.repository;

import hub.isaacode.bolao.domain.model.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BetRepository extends JpaRepository<Bet, UUID> {
    Optional<Bet> findByUserIdAndMatchId(UUID userId, UUID matchId);
    boolean existsByUserIdAndMatchId(UUID userId, UUID matchId);
    List<Bet> findAllByUserId(UUID userId);
    List<Bet> findAllByMatchId(UUID matchId);
}
