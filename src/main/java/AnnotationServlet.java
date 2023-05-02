import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "annotationServlet", urlPatterns = {"/demo"})
public class AnnotationServlet extends HttpServlet {
//    Phương thức
//    GET  : Mặc định khi gọi đường dẫn. Không bảo mật tham số mà client truyền lên
//          Cách truyền tham số : ?tenthamso=giatri&tenthamso=giatri
//          Có giới hạn về tham số gọi trên trình duyệt
//    POST : Tham số sẽ được truyền ngầm. Tham số sẽ được bảo mật
//          Tham số sẽ được truyền thông qua code và thẻ form trong HTML
//          Không có giới hạn về số lượng tham số

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        PrintWriter writer = resp.getWriter();
        writer.println("Hello " + username + " - password : " + password);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Hello Do Post");
    }
}
