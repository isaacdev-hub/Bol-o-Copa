package hub.isaacode.bolao.infra.repository;


import hub.isaacode.bolao.domain.enums.RoundStatus;
import hub.isaacode.bolao.domain.model.Round;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoundRepository extends JpaRepository<Round, UUID> {
    List<Round> findAllByStatus(RoundStatus status);
}