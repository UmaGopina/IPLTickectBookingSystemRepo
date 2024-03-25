package matchBooking.payments;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Payment {
	private String transactionID;
	private PaymentType modeOfPayment;
	private Boolean paymentStatus;
	private Integer totalAmount;
	
	public void doPayment(PaymentType modeOfPayment,Integer totalAmount) {
		//Do transaction
		//set TransactionId and set PaymentStatus
		transactionID="123456";
		paymentStatus=false;
	}
}
