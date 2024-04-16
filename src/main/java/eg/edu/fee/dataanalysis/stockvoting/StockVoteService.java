package eg.edu.fee.dataanalysis.stockvoting;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockVoteService {

    private final StockVotesRepository stockVotesRepository;

    @Transactional
    public void vote(Long stockId, Long voteValue) {

        /*if (LocalDateTime.now().isAfter(LocalDateTime.of(LocalDate.now(),
                LocalTime.of(15, 0, 0)))) {
            throw new RuntimeException("Not allow for now to vote");
        }*/

        StockVotes stockVotes = stockVotesRepository.findByStockId(stockId).orElseThrow(
                () -> new RuntimeException("Stock is not founded")
        );

        stockVotes.setNoOfVotes(stockVotes.getNoOfVotes() + 1);
        if (stockVotes.getClosing() < voteValue) {
            stockVotes.setClosing(voteValue);
        }

        stockVotesRepository.save(stockVotes);
    }

    public StockVoteResponseModel getStock(Long id) {
        return stockVotesRepository.findByStockVoteById(id).orElseThrow(
                () -> new RuntimeException("Stock not founded to fetch")
        );
    }
}
