package model.idao;

import model.bean.AddressBean;

import java.sql.SQLException;
import java.util.List;

public interface AddressModel {
    public AddressBean doRetrieveById(int id) throws SQLException;
    public List<AddressBean> doRetrieveAllByCustomerId(int customerId) throws SQLException;
    public void doSave(AddressBean addressBean) throws SQLException;
}
