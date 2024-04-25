package eg.edu.fee.dataanalysis.common;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockRestController {
    private final StockService stockService;

    @PostMapping("")
    public ResponseEntity<List<StockDto>> saveStocks(@RequestBody List<StockDto> stockDtoList) {
        List<StockDto> saveStocks = stockService.saveStock(stockDtoList);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saveStocks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<List<StockDto>> getAllStocks() {
        return ResponseEntity.ok()
                .body(stockService.getAllStocks());
    }
}
