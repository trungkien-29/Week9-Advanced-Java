package bank_system;

import java.util.Locale;

/**
 * Ngoại lệ khi số dư tài khoản không đủ để thực hiện giao dịch.
 */
public class InsufficientFundsException extends BankException {

  /**
   * Khởi tạo ngoại lệ số dư không đủ.
   *
   * @param amount số tiền yêu cầu rút
   */
  public InsufficientFundsException(double amount) {
    super("Số dư tài khoản không đủ $" + String.format(Locale.US, "%.2f", amount)
        + " để thực hiện giao dịch");
  }
}

