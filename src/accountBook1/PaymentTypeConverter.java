package accountBook1;

import java.util.HashMap;
import java.util.Map;

public class PaymentTypeConverter {
    private final Map<String, String> korToEngMap;
    private final Map<String, String> engToKorMap;

    public PaymentTypeConverter() {
    	korToEngMap = new HashMap<>();
        // Add mappings for Korean payment types to English payment types
    	korToEngMap.put("현금", "cash");
		korToEngMap.put("계좌1", "account1");
		korToEngMap.put("계좌2", "account2");
		korToEngMap.put("계좌3", "account3");
		
		
		
        engToKorMap = new HashMap<>();
        for (Map.Entry<String, String> entry : korToEngMap.entrySet()) {
            engToKorMap.put(entry.getValue(), entry.getKey());
        }
    }

	public String convertKorToEngPayType(String korPaymentType) {
        return korToEngMap.getOrDefault(korPaymentType, korPaymentType);
    }
	
    public String convertEngToKorPayType(String engPaymentType) {
        return engToKorMap.getOrDefault(engPaymentType, engPaymentType);
    }
}
