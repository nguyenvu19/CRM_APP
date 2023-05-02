import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "projectServlet", urlPatterns = {"/product"})
public class ProductServlet extends HttpServlet {
    List<Product> list = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("product.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String productName = req.getParameter("product_name");
        String strQuality = req.getParameter("quality");
        String strPrice = req.getParameter("price");
        int quality = 0;
        double price = 0;
// trim() : Loại bỏ khoảng trắng
        if(!strQuality.isEmpty()){
            quality = Integer.parseInt(strQuality);
        }

        if(!strPrice.isEmpty()){
            price = Integer.parseInt(strPrice);
        }

        Product product = new Product();
        product.setProductName(productName);
        product.setPrice(price);
        product.setQuality(quality);

        list.add(product);
        req.setAttribute("listProduct",list);
        req.getRequestDispatcher("product.jsp").forward(req,resp);
    }
}
