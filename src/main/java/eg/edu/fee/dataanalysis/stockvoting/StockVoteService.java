package eg.edu.fee.dataanalysis.stockvoting;

import eg.edu.fee.dataanalysis.user.User;
import eg.edu.fee.dataanalysis.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class StockVoteService {

    private final StockVoteRepository stockVoteRepository;
    private final UserRepository userRepository;
    private final UserVoteRepository userVoteRepository;

    @Transactional
    public void vote(Long stockId, Long voteValue) {

        if (LocalDateTime.now().isAfter(LocalDateTime.of(LocalDate.now(),
                LocalTime.of(22, 15, 0)))) {
            throw new RuntimeException("Not allow for now to vote, see you tomorrow");
        }

        StockVote stockVote = stockVoteRepository.findByStockId(stockId).orElseThrow(
                () -> new RuntimeException("Stock is not founded")
        );

        stockVote.setNoOfVotes(stockVote.getNoOfVotes() + 1);
        if (stockVote.getClosing() < voteValue) {
            stockVote.setClosing(voteValue);
        }

        updateUserVote(stockVote);
    }

    public StockVoteResponseModel getStock(Long id) {
        return stockVoteRepository.findByStockVoteById(id).orElseThrow(
                () -> new RuntimeException("Stock not founded to fetch")
        );
    }

    @Transactional
    public void updateUserVote(StockVote stockVote) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User not exists in application")
        );

        UserVote userVote = UserVote.builder()
                .user(user)
                .stockVote(stockVote)
                .build();

        user.getUserVotes().add(userVote);
        userRepository.save(user);

        stockVote.getUserVotes().add(userVote);
        stockVoteRepository.save(stockVote);

        userVoteRepository.save(userVote);
    }
}
