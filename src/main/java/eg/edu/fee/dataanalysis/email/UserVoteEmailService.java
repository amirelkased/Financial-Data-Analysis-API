package eg.edu.fee.dataanalysis.email;

import eg.edu.fee.dataanalysis.common.Stock;
import eg.edu.fee.dataanalysis.stockvoting.StockVote;
import eg.edu.fee.dataanalysis.stockvoting.StockVoteResponseModel;
import eg.edu.fee.dataanalysis.user.User;
import eg.edu.fee.dataanalysis.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class UserVoteEmailService {
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final SpringTemplateEngine templateEngine;

    @Scheduled(cron = "0 10 17 * * *")
    @Async
    public void notifyUserWithStockClosing() throws MessagingException {
        Map<User, List<StockVoteResponseModel>> userWithStocks = getUserWithStockVotes();

        for (Map.Entry<User, List<StockVoteResponseModel>> user : userWithStocks.entrySet()) {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED,
                    StandardCharsets.UTF_8.name()
            );

            Map<String, Object> properties = new HashMap<>();
            properties.put("username", user.getKey().fullName());
            properties.put("stocks", user.getValue());

            Context context = new Context();
            context.setVariables(properties);

            helper.setTo(user.getKey().getEmail());
            helper.setSubject("Stock Vote Closing");

            String template = templateEngine.process(EmailTemplateName.STOCK_VOTING_NOTIFY.getName(), context);
            helper.setText(template, true);
            javaMailSender.send(mimeMessage);
        }
    }

    private Map<User, List<StockVoteResponseModel>> getUserWithStockVotes() {
        List<Object[]> usersAndStockVotes = userRepository.findUsersAndTheirStockVotes();
        Map<User, List<StockVoteResponseModel>> userStockVotesMap = new HashMap<>();

        for (Object[] userAndStockVote : usersAndStockVotes) {
            User user = (User) userAndStockVote[0];
            StockVote stockVote = (StockVote) userAndStockVote[1];
            Stock stock = (Stock) userAndStockVote[2];

            StockVoteResponseModel stockVoteResponse = StockVoteResponseModel.builder()
                    .name(stock.getName())
                    .open(stockVote.getOpening())
                    .close(stockVote.getClosing())
                    .votes(stockVote.getNoOfVotes())
                    .build();

            if (!userStockVotesMap.containsKey(user)) {
                userStockVotesMap.put(user, new ArrayList<>());
            }
            userStockVotesMap.get(user).add(stockVoteResponse);
        }

        return userStockVotesMap;
    }
}
