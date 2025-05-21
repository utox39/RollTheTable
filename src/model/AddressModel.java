package model;

import model.bean.AddressBean;

import java.sql.SQLException;

public interface AddressModel {
    public AddressBean doRetrieveById(int id) throws SQLException;
    public void doSave(AddressBean addressBean) throws SQLException;
}
