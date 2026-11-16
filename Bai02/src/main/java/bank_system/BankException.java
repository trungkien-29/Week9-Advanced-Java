package bank_system;

/**
 * Ngoại lệ chung trong hệ thống ngân hàng.
 */
public class BankException extends Exception {

  /**
   * Khởi tạo ngoại lệ với thông điệp lỗi.
   *
   * @param message thông điệp mô tả lỗi
   */
  public BankException(String message) {
    super(message);
  }
}

