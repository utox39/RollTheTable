package model;

import model.bean.PaymentMethodBean;

import java.sql.SQLException;

public interface PaymentMethodModel {
    public PaymentMethodBean doRetriveById(int id) throws SQLException;
    public void doSave(PaymentMethodBean paymentMethodBean) throws SQLException;
}
