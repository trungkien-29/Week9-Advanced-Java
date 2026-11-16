package bank_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp đại diện cho tài khoản tiết kiệm.
 */
public class SavingsAccount extends Account {

  private static final Logger LOGGER = LoggerFactory.getLogger(SavingsAccount.class);

  public static final double MAX_WITHDRAW_AMOUNT = 1000.0;
  public static final double MIN_BALANCE_REQUIRED = 5000.0;

  public SavingsAccount(long accountNumber, double balance) {
    super(accountNumber, balance);
  }

  @Override
  public void deposit(double amount) {
    LOGGER.debug("SavingsAccount {}: Processing deposit: {}", getAccountNumber(), amount);
    double initialBalance = getBalance();
    try {
      doDepositing(amount);
      double finalBalance = getBalance();
      Transaction transaction = new Transaction(
          Transaction.TYPE_DEPOSIT_SAVINGS,
          amount,
          initialBalance,
          finalBalance);
      addTransaction(transaction);
      LOGGER.info("SavingsAccount {}: Deposited {}. New balance: {}",
          getAccountNumber(), amount, finalBalance);
    } catch (BankException e) {
      LOGGER.error("SavingsAccount {}: Deposit error - {}", getAccountNumber(), e.getMessage());
    }
  }

  @Override
  public void withdraw(double amount) {
    LOGGER.debug("SavingsAccount {}: Processing withdraw: {}", getAccountNumber(), amount);
    double initialBalance = getBalance();
    try {
      if (amount > MAX_WITHDRAW_AMOUNT) {
        throw new InvalidFundingAmountException(amount);
      }
      if (initialBalance - amount < MIN_BALANCE_REQUIRED) {
        throw new InsufficientFundsException(amount);
      }

      doWithdrawing(amount);
      double finalBalance = getBalance();

      Transaction transaction = new Transaction(
          Transaction.TYPE_WITHDRAW_SAVINGS,
          amount,
          initialBalance,
          finalBalance);
      addTransaction(transaction);

      LOGGER.info("SavingsAccount {}: Withdrew {}. Remaining balance: {}",
          getAccountNumber(), amount, finalBalance);
    } catch (BankException e) {
      LOGGER.error("SavingsAccount {}: Withdraw error - {}", getAccountNumber(), e.getMessage());
    }
  }
}
