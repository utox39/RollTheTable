package model.idao;

import model.bean.InvoiceBean;

import java.sql.SQLException;

public interface InvoiceModel {
    public int doSave(InvoiceBean invoice) throws SQLException;
}
