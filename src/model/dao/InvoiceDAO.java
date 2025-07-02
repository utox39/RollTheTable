package model.dao;

import db.DatabaseConnectionPool;
import model.bean.InvoiceBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    private static final String SELECT_BY_ID_SQL = "SELECT * FROM invoice WHERE invoice_id = ?";

    private static final String SELECT_ALL_SQL = "SELECT * FROM invoice ORDER BY invoice_id DESC";

    private static final String DELETE_BY_ID_SQL = "DELETE FROM invoice WHERE invoice_id = ?";

    public int doSave(InvoiceBean invoice) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;

        int invoiceId;

        String insertInvoiceSql = "INSERT INTO invoice (customer_id, purchase_id, iva, notes, taxable, tax) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            connection = DatabaseConnectionPool.getConnection();
            ps = connection.prepareStatement(insertInvoiceSql,  Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, invoice.getCustomerId());
            ps.setInt(2, invoice.getPurchaseId());
            ps.setBigDecimal(3, invoice.getIva());
            ps.setString(4, invoice.getNotes());
            ps.setBigDecimal(5, invoice.getTaxable());
            ps.setBigDecimal(6, invoice.getTax());

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                invoiceId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Order creation failed, no ID generated.");
            }

            connection.commit();
        } finally {
            if (ps != null) {
                ps.close();
            }

            DatabaseConnectionPool.releaseConnection(connection);
        }

        return invoiceId;
    }

    public InvoiceBean doGetInvoiceById(int invoiceId) throws SQLException {
        Connection conn = DatabaseConnectionPool.getConnection();
        PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL);

        ps.setInt(1, invoiceId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return extractInvoiceFromResultSet(rs);
            }
        }


        return null;
    }

    public List<InvoiceBean> doGetAllInvoices() throws SQLException {
        List<InvoiceBean> invoices = new ArrayList<>();
        Connection conn = DatabaseConnectionPool.getConnection();
        PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            invoices.add(extractInvoiceFromResultSet(rs));
        }

        return invoices;
    }

    public void doDeleteInvoice(int invoiceId) throws SQLException {
        Connection conn = DatabaseConnectionPool.getConnection();
        PreparedStatement ps = conn.prepareStatement(DELETE_BY_ID_SQL);

        ps.setInt(1, invoiceId);
        ps.executeUpdate();
    }

    private InvoiceBean extractInvoiceFromResultSet(ResultSet rs) throws SQLException {
        InvoiceBean invoice = new InvoiceBean();
        invoice.setInvoiceId(rs.getInt("invoice_id"));
        invoice.setCustomerId(rs.getInt("customer_id"));
        invoice.setPurchaseId(rs.getInt("purchase_id"));
        invoice.setIva(rs.getBigDecimal("iva"));
        invoice.setNotes(rs.getString("notes"));
        invoice.setTaxable(rs.getBigDecimal("taxable"));
        invoice.setTax(rs.getBigDecimal("tax"));
        return invoice;
    }
}
