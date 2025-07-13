package control;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.bean.ProductBean;
import model.dao.ProductDAO;
import util.ServerLogger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/search")
public class SearchController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String query = request.getParameter("query");
            List<ProductBean> results = null;

            if (query == null || query.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.sendRedirect("views/fallback.jsp");
                return;
            }

            results = new ProductDAO().searchProduct(query);

            // You can filter the result to include only needed fields if necessary
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(), results);
        } catch (SQLException e) {
            ServerLogger.severe(CatalogController.class.getName(), "searchByName SQLException: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.sendRedirect("views/fallback.jsp");
        }
    }
}
