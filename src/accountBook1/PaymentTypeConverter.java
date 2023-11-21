package accountBook1;

import java.util.HashMap;
import java.util.Map;

import java.util.HashMap;
import java.util.Map;

public class PaymentTypeConverter {
	// 결제 유형을 한글->영어 또는 영어->한글로 변환하는 클래스

	private final Map<String, String> korToEngMap; // 한글에서 영어로 변환하는 맵
	private final Map<String, String> engToKorMap; // 영어에서 한글로 변환하는 맵

	// 생성자: 한글에서 영어로 변환하는 맵과 영어에서 한글로 변환하는 맵 초기화
	public PaymentTypeConverter() {
		korToEngMap = new HashMap<>();
		// 한글 결제 유형을 영어 결제 유형으로 매핑
		korToEngMap.put("현금", "cash");
		korToEngMap.put("계좌1", "account1");
		korToEngMap.put("계좌2", "account2");
		korToEngMap.put("계좌3", "account3");
		engToKorMap = new HashMap<>();
		// 영어 결제 유형을 한글 결제 유형으로 매핑
		for (Map.Entry<String, String> entry : korToEngMap.entrySet()) {
			engToKorMap.put(entry.getValue(), entry.getKey());
		}
	}

	// 한글 결제 유형을 영어 결제 유형으로 변환
	public String convertKorToEngPayType(String korPaymentType) {
		return korToEngMap.getOrDefault(korPaymentType, korPaymentType);
	}

	// 영어 결제 유형을 한글 결제 유형으로 변환
	public String convertEngToKorPayType(String engPaymentType) {
		return engToKorMap.getOrDefault(engPaymentType, engPaymentType);
	}
}
