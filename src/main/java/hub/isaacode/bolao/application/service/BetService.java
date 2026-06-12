package hub.isaacode.bolao.application.service;

import hub.isaacode.bolao.domain.enums.RoundStatus;
import hub.isaacode.bolao.domain.model.Bet;
import hub.isaacode.bolao.domain.model.Match;
import hub.isaacode.bolao.domain.model.User;
import hub.isaacode.bolao.infra.repository.BetRepository;
import hub.isaacode.bolao.infra.repository.UserRepository;
import hub.isaacode.bolao.web.exception.BusinessException;
import hub.isaacode.bolao.web.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BetService {

    private final BetRepository betRepository;
    private final MatchService matchService;
    private final UserRepository userRepository;

    @Transactional
    public Bet placeBet(UUID userId, UUID matchId, int scoreA, int scoreB) {
        Match match = matchService.findById(matchId);

        if (match.getRound().getStatus() == RoundStatus.CLOSED)
            throw new BusinessException("Apostas encerradas: rodada fechada");

        if (match.getScoreA() != null)
            throw new BusinessException("Jogo já possui resultado registrado");

        if (betRepository.existsByUserIdAndMatchId(userId, matchId))
            throw new BusinessException("Você já apostou neste jogo");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Bet bet = Bet.builder()
                .user(user)
                .match(match)
                .scoreA(scoreA)
                .scoreB(scoreB)
                .build();

        return betRepository.save(bet);
    }

    public List<Bet> findByUser(UUID userId) {
        return betRepository.findAllByUserId(userId);
    }
}
