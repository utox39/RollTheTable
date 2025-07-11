package model.idao;

import model.bean.PaymentMethodBean;

import java.sql.SQLException;
import java.util.List;

public interface PaymentMethodModel {
    public PaymentMethodBean doRetriveById(int id) throws SQLException;
    public List<PaymentMethodBean> doRetrieveAllByCustomerId(int customerId) throws SQLException;
    public void doSave(PaymentMethodBean paymentMethodBean) throws SQLException;
}
