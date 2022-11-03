package be.abis.thirdpartyapiclient;

import be.abis.thirdpartyapiclient.service.RateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RateServiceTest {
    @Autowired
    RateService rateService;

    @Test
    void getCurrencyRateFromEURtoJPYis144_709915(){
        System.out.println(rateService.getExchangeRate("EUR", "JPY"));
    }

}
