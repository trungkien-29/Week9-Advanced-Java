package bank_system;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp trừu tượng đại diện cho một tài khoản ngân hàng.
 */
public abstract class Account {

  private static final Logger LOGGER = LoggerFactory.getLogger(Account.class);

  public static final String CHECKING_TYPE = "CHECKING";
  public static final String SAVINGS_TYPE = "SAVINGS";

  private long accountNumber;
  private double balance;
  protected List<Transaction> transactionList;

  /**
   * Khởi tạo tài khoản với số tài khoản và số dư ban đầu.
   *
   * @param accountNumber số tài khoản
   * @param balance số dư ban đầu
   */
  public Account(long accountNumber, double balance) {
    this.accountNumber = accountNumber;
    this.balance = balance;
    this.transactionList = new ArrayList<>();
  }

  public long getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(long accountNumber) {
    this.accountNumber = accountNumber;
  }

  public double getBalance() {
    return balance;
  }

  protected void setBalance(double balance) {
    this.balance = balance;
  }

  public List<Transaction> getTransactionList() {
    return transactionList;
  }

  /**
   * Thiết lập danh sách giao dịch cho tài khoản.
   *
   * @param transactionList danh sách giao dịch mới
   */
  public void setTransactionList(List<Transaction> transactionList) {
    if (transactionList == null) {
      this.transactionList = new ArrayList<>();
    } else {
      this.transactionList = transactionList;
    }
  }

  /**
   * Phương thức nạp tiền trừu tượng.
   *
   * @param amount số tiền nạp
   */
  public abstract void deposit(double amount);

  /**
   * Phương thức rút tiền trừu tượng.
   *
   * @param amount số tiền rút
   */
  public abstract void withdraw(double amount);

  protected void doDepositing(double amount) throws InvalidFundingAmountException {
    if (amount <= 0) {
      LOGGER.warn("Account {}: Deposit attempt with non-positive amount {}",
          accountNumber, amount);
      throw new InvalidFundingAmountException(amount);
    }
    balance += amount;
    LOGGER.info("Account {}: Deposited {}. New balance: {}", accountNumber, amount, balance);
  }

  protected void doWithdrawing(double amount) throws BankException {
    if (amount <= 0) {
      LOGGER.warn("Account {}: Withdraw attempt with non-positive amount {}",
          accountNumber, amount);
      throw new InvalidFundingAmountException(amount);
    }
    if (amount > balance) {
      LOGGER.warn("Account {}: Insufficient funds. Requested: {}, Current: {}",
          accountNumber, amount, balance);
      throw new InsufficientFundsException(amount);
    }
    balance -= amount;
    LOGGER.info("Account {}: Withdrew {}. Remaining balance: {}",
        accountNumber, amount, balance);
  }

  /**
   * Thêm một giao dịch vào lịch sử giao dịch.
   *
   * @param transaction giao dịch cần thêm
   */
  public void addTransaction(Transaction transaction) {
    if (transaction != null) {
      transactionList.add(transaction);
    }
  }

  /**
   * Lấy lịch sử giao dịch dạng chuỗi văn bản.
   *
   * @return chuỗi lịch sử giao dịch
   */
  public String getTransactionHistory() {
    LOGGER.debug("Fetching transaction history for account: {}", accountNumber);
    StringBuilder sb = new StringBuilder();
    sb.append("Lịch sử giao dịch của tài khoản ").append(accountNumber).append(":\n");
    for (int i = 0; i < transactionList.size(); i++) {
      sb.append(transactionList.get(i).getTransactionSummary());
      if (i < transactionList.size() - 1) {
        sb.append("\n");
      }
    }
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Account)) {
      return false;
    }
    Account other = (Account) obj;
    return this.accountNumber == other.accountNumber;
  }

  @Override
  public int hashCode() {
    return (int) (accountNumber ^ (accountNumber >>> 32));
  }
}
