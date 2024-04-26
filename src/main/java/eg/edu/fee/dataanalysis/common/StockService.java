package eg.edu.fee.dataanalysis.common;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    @Transactional
    public List<StockDto> saveStock(String... stock) {
        List<Stock> mappedStock = Arrays.stream(stock)
                .map(e -> Stock.builder()
                        .name(e)
                        .build()
                ).toList();

        List<Stock> savedStock = stockRepository.saveAll(mappedStock);

        return getStockDtoList(savedStock);
    }

    private static List<StockDto> getStockDtoList(List<Stock> savedStock) {
        return savedStock.stream().map(e -> StockDto.builder()
                .id(e.getId())
                .name(e.getName())
                .build()
        ).toList();
    }

    @Transactional
    public void deleteStock(Long id) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock with id:%d not exists".formatted(id)));
        stockRepository.delete(stock);
    }

    public List<StockDto> getAllStocks() {
        List<Stock> stockList = stockRepository.findAll();
        return getStockDtoList(stockList);
    }
}
