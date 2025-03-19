import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.JsonArray;
import com.mysql.cj.xdevapi.JsonParser;
import dao.CustomerDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/test_db")
public class TestController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // only for testing, to be replaced with actual code
        var res = CustomerDAO.test();

        if (res == null) {
            System.err.println("Ops");
        }

        res.iterator().forEachRemaining(response.getWriter()::println);
    }


    // Assume this is the login method
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder res = new StringBuilder();

        request.getReader().lines().forEach(res::append);

        JsonNode node = new ObjectMapper().readTree(res.toString());

        System.out.println(node.toString());

        // node.get("user")
        // node.get("password")

        // check the two variables in db ( a new query must be created )

        // cookie logic goes here

        // send cookie back to client

        // done

    }
}

