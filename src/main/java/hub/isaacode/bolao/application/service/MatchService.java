package hub.isaacode.bolao.application.service;

import hub.isaacode.bolao.domain.enums.RoundStatus;
import hub.isaacode.bolao.domain.model.Match;
import hub.isaacode.bolao.domain.model.Round;
import hub.isaacode.bolao.infra.repository.BetRepository;
import hub.isaacode.bolao.infra.repository.MatchRepository;
import hub.isaacode.bolao.web.exception.BusinessException;
import hub.isaacode.bolao.web.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final BetRepository betRepository;
    private final RoundService roundService;
    private final ScoringService scoringService;

    @Transactional
    public Match create(UUID roundId, Match match) {
        Round round = roundService.findById(roundId);
        if (round.getStatus() == RoundStatus.CLOSED)
            throw new BusinessException("Não é possível adicionar jogos a uma rodada fechada");
        match.setRound(round);
        return matchRepository.save(match);
    }

    @Transactional
    public Match registerResult(UUID matchId, int scoreA, int scoreB) {
        Match match = findById(matchId);
        match.setScoreA(scoreA);
        match.setScoreB(scoreB);

        betRepository.findAllByMatchId(matchId).forEach(bet -> {
            bet.setPoints(scoringService.calculatePoints(bet, scoreA, scoreB));
            betRepository.save(bet);
        });
        return match;
    }

    public Match findById(UUID id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado"));
    }

    public List<Match> findByRound(UUID roundId) {
        return matchRepository.findAllByRoundId(roundId);
    }
}
