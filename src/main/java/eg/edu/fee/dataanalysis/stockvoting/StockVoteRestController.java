package eg.edu.fee.dataanalysis.stockvoting;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stocks/votes")
@RequiredArgsConstructor
public class StockVoteRestController {
    private final StockVoteService stockVoteService;

    @PostMapping("/{id}")
    public ResponseEntity<?> voteStock(@PathVariable Long id, @RequestParam(name = "value") Long value) {
        stockVoteService.vote(id, value);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockVoteResponseModel> getStockDetails(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(stockVoteService.getStock(id));
    }
}
