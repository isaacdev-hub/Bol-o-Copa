package hub.isaacode.bolao.application.service;

import hub.isaacode.bolao.domain.enums.RoundStatus;
import hub.isaacode.bolao.domain.model.Round;
import hub.isaacode.bolao.infra.repository.RoundRepository;
import hub.isaacode.bolao.web.exception.BusinessException;
import hub.isaacode.bolao.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoundService {
    private final RoundRepository roundRepository;

    @Transactional
    public Round create(Round round) {
        if (round.getEndDate().isBefore(round.getStartDate()))
            throw new BusinessException("Data final não pode ser anterior à inicial");
        return roundRepository.save(round);
    }

    @Transactional
    public Round close(UUID roundId) {
        Round round = findById(roundId);
        round.setStatus(RoundStatus.CLOSED);
        return round;
    }

    public Round findById(UUID id) {
        return roundRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rodada não encontrada"));
    }

    public List<Round> findAll() {
        return roundRepository.findAll();
    }
}
