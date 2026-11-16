package bank_system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp quản lý danh sách khách hàng và các nghiệp vụ của ngân hàng.
 */
public class Bank {

  private static final Logger LOGGER = LoggerFactory.getLogger(Bank.class);
  private static final String ID_REGEX = "\\d{9}";

  private List<Customer> customerList;

  public Bank() {
    this.customerList = new ArrayList<>();
  }

  public List<Customer> getCustomerList() {
    return customerList;
  }

  /**
   * Thiết lập danh sách khách hàng.
   *
   * @param customerList danh sách khách hàng mới
   */
  public void setCustomerList(List<Customer> customerList) {
    if (customerList == null) {
      this.customerList = new ArrayList<>();
    } else {
      this.customerList = customerList;
    }
  }

  /**
   * Đọc danh sách khách hàng từ luồng đầu vào.
   *
   * @param inputStream luồng dữ liệu đầu vào
   */
  public void readCustomerList(InputStream inputStream) {
    LOGGER.info("Starting to read customer list from input stream...");
    if (inputStream == null) {
      LOGGER.warn("Input stream is null. Aborting read operation.");
      return;
    }

    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
      String line;
      Customer currentCustomer = null;

      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (line.isEmpty()) {
          continue;
        }

        int lastSpaceIndex = line.lastIndexOf(' ');
        if (lastSpaceIndex > 0) {
          String token = line.substring(lastSpaceIndex + 1).trim();

          if (token.matches(ID_REGEX)) {
            String name = line.substring(0, lastSpaceIndex).trim();
            long idNumber = Long.parseLong(token);
            currentCustomer = new Customer(idNumber, name);
            customerList.add(currentCustomer);
            LOGGER.info("Loaded new customer: {} with ID: {}", name, idNumber);
          } else if (currentCustomer != null) {
            String[] parts = line.split("\\s+");
            if (parts.length >= 3) {
              long accountNumber = Long.parseLong(parts[0]);
              String accountType = parts[1];
              double initialBalance = Double.parseDouble(parts[2]);

              if (Account.CHECKING_TYPE.equals(accountType)) {
                currentCustomer.addAccount(new CheckingAccount(accountNumber, initialBalance));
                LOGGER.debug("Added checking account {} to customer {}",
                    accountNumber, currentCustomer.getFullName());
              } else if (Account.SAVINGS_TYPE.equals(accountType)) {
                currentCustomer.addAccount(new SavingsAccount(accountNumber, initialBalance));
                LOGGER.debug("Added savings account {} to customer {}",
                    accountNumber, currentCustomer.getFullName());
              }
            }
          }
        }
      }
      LOGGER.info("Successfully finished reading customer list. Total customers: {}",
          customerList.size());
    } catch (IOException e) {
      LOGGER.error("Error reading customer list: {}", e.getMessage(), e);
    }
  }

  /**
   * Lấy thông tin khách hàng sắp xếp theo số CMND tăng dần.
   *
   * @return chuỗi thông tin khách hàng
   */
  public String getCustomersInfoByIdOrder() {
    List<Customer> sortedList = new ArrayList<>(customerList);
    sortedList.sort(Comparator.comparingLong(Customer::getIdNumber));

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < sortedList.size(); i++) {
      sb.append(sortedList.get(i).getCustomerInfo());
      if (i < sortedList.size() - 1) {
        sb.append("\n");
      }
    }
    return sb.toString();
  }

  /**
   * Lấy thông tin khách hàng sắp xếp theo họ tên (A-Z).
   *
   * @return chuỗi thông tin khách hàng
   */
  public String getCustomersInfoByNameOrder() {
    List<Customer> sortedList = new ArrayList<>(customerList);
    sortedList.sort((c1, c2) -> {
      int nameComparison = c1.getFullName().compareTo(c2.getFullName());
      if (nameComparison != 0) {
        return nameComparison;
      }
      return Long.compare(c1.getIdNumber(), c2.getIdNumber());
    });

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < sortedList.size(); i++) {
      sb.append(sortedList.get(i).getCustomerInfo());
      if (i < sortedList.size() - 1) {
        sb.append("\n");
      }
    }
    return sb.toString();
  }

  /**
   * Phương thức chính để chạy thử nghiệm và quan sát các cấp độ Logging.
   *
   * @param args tham số dòng lệnh
   */
  public static void main(String[] args) {
    LOGGER.info("=== BẮT ĐẦU CHẠY THỬ NGHIỆM HỆ THỐNG NGÂN HÀNG ===");

    Bank bank = new Bank();

    // 1. Khởi tạo khách hàng và tài khoản
    Customer customer = new Customer(123456789L, "Nguyen Van A");
    CheckingAccount checking = new CheckingAccount(1000000001L, 5000.0);
    SavingsAccount savings = new SavingsAccount(2000000001L, 8000.0);

    customer.addAccount(checking);
    customer.addAccount(savings);
    bank.customerList.add(customer);
    LOGGER.info("Đã tạo khách hàng: {} với 2 tài khoản (Vãng lai & Tiết kiệm)",
        customer.getFullName());

    // 2. Thao tác giao dịch thành công (Ghi log INFO)
    LOGGER.info("--- THỬ NGHIỆM GIAO DỊCH HỢP LỆ (INFO) ---");
    checking.deposit(2000.0);
    savings.withdraw(500.0);

    // 3. Thao tác vi phạm nghiệp vụ (Ghi log WARN / CẢNH BÁO)
    LOGGER.info("--- THỬ NGHIỆM CẢNH BÁO VI PHẠM NGHIỆP VỤ (WARN) ---");
    checking.withdraw(20000.0);  // Rút quá số dư -> WARN: Insufficient funds
    savings.deposit(-500.0);     // Nạp số tiền âm -> WARN: non-positive amount
    savings.withdraw(2000.0);    // Rút quá hạn mức $1000 -> WARN: non-positive amount / Invalid

    // 4. Thao tác kiểm tra lỗi hệ thống (Ghi log ERROR / LỖI)
    LOGGER.info("--- THỬ NGHIỆM XỬ LÝ LỖI HỆ THỐNG (ERROR) ---");
    bank.readCustomerList(null); // Stream bị null -> Ghi log cảnh báo/lỗi

    // 5. Thao tác truy vết thông tin (Ghi log DEBUG)
    LOGGER.info("--- LỊCH SỬ GIAO DỊCH CHI TIẾT ---");
    LOGGER.info("\n{}", checking.getTransactionHistory());

    LOGGER.info("=== KẾT THÚC CHƯƠNG TRÌNH THỬ NGHIỆM THÀNH CÔNG ===");
  }
}

