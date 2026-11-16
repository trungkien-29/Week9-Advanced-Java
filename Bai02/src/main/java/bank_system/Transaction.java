package bank_system;

import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Đại diện cho một giao dịch trong hệ thống ngân hàng.
 */
public class Transaction {

  private static final Logger LOGGER = LoggerFactory.getLogger(Transaction.class);

  public static final int TYPE_DEPOSIT_CHECKING = 1;
  public static final int TYPE_WITHDRAW_CHECKING = 2;
  public static final int TYPE_DEPOSIT_SAVINGS = 3;
  public static final int TYPE_WITHDRAW_SAVINGS = 4;

  private int type;
  private double amount;
  private double initialBalance;
  private double finalBalance;

  /**
   * Khởi tạo một giao dịch mới.
   *
   * @param type kiểu giao dịch
   * @param amount số tiền giao dịch
   * @param initialBalance số dư ban đầu
   * @param finalBalance số dư cuối
   */
  public Transaction(int type, double amount, double initialBalance, double finalBalance) {
    this.type = type;
    this.amount = amount;
    this.initialBalance = initialBalance;
    this.finalBalance = finalBalance;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public double getInitialBalance() {
    return initialBalance;
  }

  public void setInitialBalance(double initialBalance) {
    this.initialBalance = initialBalance;
  }

  public double getFinalBalance() {
    return finalBalance;
  }

  public void setFinalBalance(double finalBalance) {
    this.finalBalance = finalBalance;
  }

  /**
   * Lấy chuỗi mô tả kiểu giao dịch.
   *
   * @param transactionType mã kiểu giao dịch
   * @return chuỗi mô tả tiếng Việt
   */
  public static String getTypeString(int transactionType) {
    switch (transactionType) {
      case TYPE_DEPOSIT_CHECKING:
        return "Nạp tiền vãng lai";
      case TYPE_WITHDRAW_CHECKING:
        return "Rút tiền vãng lai";
      case TYPE_DEPOSIT_SAVINGS:
        return "Nạp tiền tiết kiệm";
      case TYPE_WITHDRAW_SAVINGS:
        return "Rút tiền tiết kiệm";
      default:
        return "Không rõ";
    }
  }

  /**
   * Trả về tóm tắt thông tin giao dịch.
   *
   * @return chuỗi tóm tắt giao dịch
   */
  public String getTransactionSummary() {
    LOGGER.debug("Generating summary for transaction type: {}", this.type);

    String typeStr = getTypeString(type);
    String initialStr = String.format(Locale.US, "%.2f", initialBalance);
    String amountStr = String.format(Locale.US, "%.2f", amount);
    String finalStr = String.format(Locale.US, "%.2f", finalBalance);

    return "- Kiểu giao dịch: " + typeStr
        + ". Số dư ban đầu: $" + initialStr
        + ". Số tiền: $" + amountStr
        + ". Số dư cuối: $" + finalStr + ".";
  }
}

