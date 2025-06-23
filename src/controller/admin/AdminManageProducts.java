package admin;

import model.bean.ProductBean;
import model.dao.ProductDAO;
import util.ServerLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/secure/manageProducts")
public class AdminManageProducts extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        ProductDAO productDAO = new ProductDAO();

        try {
            if (op == null) {
                // Mostra lista prodotti
                request.setAttribute("products", productDAO.doRetriveAll());
                request.getRequestDispatcher("/views/admin/secure/products.jsp").forward(request, response);
                return;
            }

            switch (op) {
                case "showAddForm":
                    request.getRequestDispatcher("/views/admin/secure/addProduct.jsp").forward(request, response);
                    break;

                case "add":
                    ProductBean newProduct = new ProductBean();
                    newProduct.setName(request.getParameter("name"));
                    newProduct.setVendor(request.getParameter("vendor"));
                    newProduct.setPurchasePrice(Double.parseDouble(request.getParameter("purchase_price")));
                    newProduct.setSalePrice(Double.parseDouble(request.getParameter("sale_price")));
                    newProduct.setIva(Double.parseDouble(request.getParameter("iva")));
                    newProduct.setDescription(request.getParameter("description"));
                    newProduct.setStockQuantity(Integer.parseInt(request.getParameter("stock_quantity")));
                    newProduct.setDiscount(Double.parseDouble(request.getParameter("discount")));
                    newProduct.setCategory(request.getParameter("category"));
                    newProduct.setMinimumAge(Integer.parseInt(request.getParameter("minimum_age")));
                    newProduct.setBarcode(request.getParameter("barcode"));
                    newProduct.setSalesStatus(ProductBean.SaleStatus.valueOf(request.getParameter("sale_status")));
                    newProduct.setImagesPath(request.getParameter("images_path"));

                    productDAO.doSave(newProduct);
                    response.sendRedirect(request.getContextPath() + "/admin/secure/manageProducts");
                    break;

                case "edit":
                    String id = request.getParameter("id");
                    ProductBean product = productDAO.doRetrieveById(id);
                    if (product != null) {
                        request.setAttribute("product", product);
                        request.getRequestDispatcher("/views/admin/secure/editProduct.jsp").forward(request, response);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/admin/secure/manageProducts?error=notfound");
                    }
                    break;

                case "update":
                    // Raccolta parametri e aggiornamento prodotto
                    ProductBean updatedProduct = new ProductBean();
                    updatedProduct.setProductId(request.getParameter("product_id"));
                    updatedProduct.setName(request.getParameter("name"));
                    updatedProduct.setPurchasePrice(Double.parseDouble(request.getParameter("purchase_price")));
                    updatedProduct.setIva(Double.parseDouble(request.getParameter("iva")));
                    updatedProduct.setSalePrice(Double.parseDouble(request.getParameter("sale_price")));
                    updatedProduct.setImagesPath(request.getParameter("images_path"));
                    updatedProduct.setDescription(request.getParameter("description"));
                    updatedProduct.setVendor(request.getParameter("vendor"));
                    updatedProduct.setStockQuantity(Integer.parseInt(request.getParameter("stock_quantity")));
                    updatedProduct.setDiscount(Double.parseDouble(request.getParameter("discount")));
                    updatedProduct.setCategory(request.getParameter("category"));
                    updatedProduct.setMinimumAge(Integer.parseInt(request.getParameter("minimum_age")));
                    updatedProduct.setBarcode(request.getParameter("barcode"));
                    updatedProduct.setSalesStatus(ProductBean.SaleStatus.valueOf(request.getParameter("sale_status")));
                    productDAO.doUpdate(updatedProduct, Integer.parseInt(updatedProduct.getProductId()));
                    response.sendRedirect(request.getContextPath() + "/admin/secure/manageProducts");
                    break;

                case "del":
                    int deleteId = Integer.parseInt(request.getParameter("id"));
                    productDAO.doDelete(deleteId);
                    response.sendRedirect(request.getContextPath() + "/admin/secure/manageProducts");
                    break;

                default:
                    response.sendRedirect(request.getContextPath() + "/admin/secure/manageProducts?error=invalid_op");
            }

        } catch (SQLException | NumberFormatException e) {
            ServerLogger.severe(AdminManageProducts.class.getName(), "Exception: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
