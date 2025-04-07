package model.dao;

import model.bean.ProductBean;
import util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private static final Connection con = ConnectionUtil.connect();

    private ProductDAO() {}

    public static List<ProductBean> getAllProducts() throws SQLException {
        String sql = "select * from product;";

        ResultSet rs = con.prepareStatement(sql).executeQuery();


        while(rs.next()){
            ProductBean productBean = new ProductBean();
        }

        return new ArrayList<ProductBean>();

    }


}
