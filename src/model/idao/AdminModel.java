package model.idao;

import model.bean.AdminBean;

import java.sql.SQLException;

public interface AdminModel {
    public AdminBean doRetriveById(int id) throws SQLException;
}
