package bank_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp đại diện cho tài khoản vãng lai.
 */
public class CheckingAccount extends Account {

  private static final Logger LOGGER = LoggerFactory.getLogger(CheckingAccount.class);

  public CheckingAccount(long accountNumber, double balance) {
    super(accountNumber, balance);
  }

  @Override
  public void deposit(double amount) {
    double initialBalance = getBalance();
    try {
      doDepositing(amount);
      double finalBalance = getBalance();
      Transaction transaction = new Transaction(
          Transaction.TYPE_DEPOSIT_CHECKING,
          amount,
          initialBalance,
          finalBalance);
      addTransaction(transaction);
      LOGGER.info("CheckingAccount {}: Deposited {}", getAccountNumber(), amount);
    } catch (BankException e) {
      LOGGER.error("CheckingAccount {}: Deposit error - {}", getAccountNumber(), e.getMessage());
    }
  }

  @Override
  public void withdraw(double amount) {
    double initialBalance = getBalance();
    try {
      doWithdrawing(amount);
      double finalBalance = getBalance();
      Transaction transaction = new Transaction(
          Transaction.TYPE_WITHDRAW_CHECKING,
          amount,
          initialBalance,
          finalBalance);
      addTransaction(transaction);
      LOGGER.info("CheckingAccount {}: Withdrew {}", getAccountNumber(), amount);
    } catch (BankException e) {
      LOGGER.error("CheckingAccount {}: Withdraw error - {}", getAccountNumber(), e.getMessage());
    }
  }
}

