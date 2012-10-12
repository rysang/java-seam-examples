package ro.penteker.auktion.beans;

import java.io.Serializable;

public class Complaint implements Serializable {
  private String level_1;
  private String level_2;
  private String transaction_value;
  private String transaction_currency;
  private String loss_value;
  private String loss_currency;
  private String payment_means;

  public Complaint() {

  }

  public String getLevel_1() {
    return level_1;
  }

  public void setLevel_1(String level_1) {
    this.level_1 = level_1;
  }

  public String getLevel_2() {
    return level_2;
  }

  public void setLevel_2(String level_2) {
    this.level_2 = level_2;
  }

  public String getTransaction_value() {
    return transaction_value;
  }

  public void setTransaction_value(String transaction_value) {
    this.transaction_value = transaction_value;
  }

  public String getTransaction_currency() {
    return transaction_currency;
  }

  public void setTransaction_currency(String transaction_currency) {
    this.transaction_currency = transaction_currency;
  }

  public String getLoss_value() {
    return loss_value;
  }

  public void setLoss_value(String loss_value) {
    this.loss_value = loss_value;
  }

  public String getLoss_currency() {
    return loss_currency;
  }

  public void setLoss_currency(String loss_currency) {
    this.loss_currency = loss_currency;
  }

  public String getPayment_means() {
    return payment_means;
  }

  public void setPayment_means(String payment_means) {
    this.payment_means = payment_means;
  }

  @Override
  public String toString() {
    return String
        .format(
            "Complaint [level_1=%s, level_2=%s, transaction_value=%s, transaction_currency=%s, loss_value=%s, loss_currency=%s, payment_means=%s]",
            level_1, level_2, transaction_value, transaction_currency, loss_value, loss_currency, payment_means);
  }

}
