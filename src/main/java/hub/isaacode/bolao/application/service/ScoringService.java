package hub.isaacode.bolao.application.service;


import hub.isaacode.bolao.domain.model.Bet;
import hub.isaacode.bolao.web.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class ScoringService {

    public static final int EXACT_SCORE_POINTS = 25;
    public static final int CORRECT_RESULT_POINTS = 10;

    public int calculatePoints(Bet bet, Integer actualScoreA, Integer actualScoreB) {
        if (actualScoreA == null || actualScoreB == null)
            throw new BusinessException("Resultado do jogo ainda não registrado");

        boolean exactScore = bet.getScoreA().equals(actualScoreA)
                && bet.getScoreB().equals(actualScoreB);
        if (exactScore) return EXACT_SCORE_POINTS;

        int betOutcome = Integer.compare(bet.getScoreA(), bet.getScoreB());
        int actualOutcome = Integer.compare(actualScoreA, actualScoreB);

        return betOutcome == actualOutcome ? CORRECT_RESULT_POINTS : 0;
    }
}
