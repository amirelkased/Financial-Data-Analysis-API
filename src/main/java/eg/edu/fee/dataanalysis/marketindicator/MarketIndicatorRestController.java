package eg.edu.fee.dataanalysis.marketindicator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/market-indication")
public class MarketIndicatorRestController {
    private final MarketIndicatorService marketIndicatorService;

    @GetMapping({"", "/"})
    public ResponseEntity<ResponseData> getMarkets() {
        return ResponseEntity.ok(marketIndicatorService.getMarkets());
    }
}

