package bank_system;

import java.util.Locale;

/**
 * Ngoại lệ khi số tiền giao dịch không hợp lệ.
 */
public class InvalidFundingAmountException extends BankException {

  /**
   * Khởi tạo ngoại lệ số tiền không hợp lệ.
   *
   * @param amount số tiền giao dịch
   */
  public InvalidFundingAmountException(double amount) {
    super("Số tiền không hợp lệ: $" + String.format(Locale.US, "%.2f", amount));
  }
}

