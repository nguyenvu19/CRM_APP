package controller;

import config.MysqlConfig;
import model.UserModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "loginController",urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
////        Cookie
//        Cookie cookie = new Cookie("username","nguyenvana");
//        //yêu cầu client khởi tạo cookie
//        resp.addCookie(cookie);
//        Cookie[] cookies = req.getCookies();
////        [ Cookie("name","value"),Cookie("name","value"),Cookie("name","value")]
//        for (Cookie item : cookies) {
//            if(item.getName().equals("username")){
//                System.out.println("Kiem tra " + item.getValue());
//            }
//        }
//
//        HttpSession session = req.getSession();
//        session.setAttribute("password","123456");
//
//        System.out.println("Session : " + session.getAttribute("password"));
//
        Cookie[] cookies = req.getCookies();
        String userName = "";
        String password = "";

        for (Cookie item : cookies) {
            if(item.getName().equals("username")){
                userName = item.getValue();
            }

            if(item.getName().equals("password")){
                password = item.getValue();
            }
        }

        req.setAttribute("username",userName);
        req.setAttribute("password",password);
        req.getRequestDispatcher("login.jsp").forward(req,resp);
    }

    /**
     * Tạo ra checkbox nhớ mật khẩu, khi người dùng click chọn
     * checkbox thì khi đăng nhập thành công thì sẽ lưu lại giá trị
     * đăng nhập là email, password
     *
     * Khi quay lại màn hình login thì tự động điền email và password
     * vào
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = null;
        //        Bước 1 : Lấy tham số username và password người dùng nhập
        String email = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");
//        Bước 2 : Viết câu query
//        ? : Tham số sẽ được truyền ở JDBC
        String sql = "select * from users u where u.email = ? and u.password = ?";
//        Bước 3 : Đưa câu query vào Statement để chuẩn bị thực thi
        try{
            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
//          Truyền tham số cho dấu chấm hỏi ( ? ) trong câu query
            statement.setString(1,email);
            statement.setString(2,password);
//        Bước 4 : Thực thi câu query
//        statement có 2 loại thực thi
//        excuteQuery : select
//        excuteUpdate : insert, delete, update...
            ResultSet resultSet = statement.executeQuery();
            List<UserModel> list = new ArrayList<>();
//        Bước 5 : Duyệt từng dòng dữ liệu trong ResultSet và lưu vào trong list user model
            while(resultSet.next()){
                //Duyệt từng dòng dữ liệu
                UserModel userModel = new UserModel();
                //Lấy giá trị của cột chỉ định và lưu vào đối tượng
                userModel.setId(resultSet.getInt("id"));
                userModel.setEmail(resultSet.getString("email"));
                userModel.setFullname(resultSet.getString("fullname"));
                userModel.setRoleId(resultSet.getInt("role_id"));

                list.add(userModel);
            }

            boolean isSuccess = list.size() > 0;

            if(isSuccess && remember != null){
                //Lưu cookie
                Cookie cUserName = new Cookie("username",email);
                Cookie cPassword = new Cookie("password",password);

                resp.addCookie(cUserName);
                resp.addCookie(cPassword);
            }

            PrintWriter writer = resp.getWriter();
            writer.println(isSuccess ? "Login Success" : "Login Fail");
            writer.close();

        }catch (Exception e){
            System.out.println("Lỗi thực thi query login " + e.getMessage());
        }finally {
            if(connection != null){
                try {
                    connection.close();
                }catch (Exception e){
                    System.out.println("Lỗi đóng kết nối login " + e.getMessage());
                }
            }
        }

    }
}
