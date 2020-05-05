package servlet;

import elements.Book;
import user.ClientClass;
import user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;



public class Servlet extends HttpServlet {
    private String getUserDataXMLRepresentation(User user) throws JAXBException, SQLException, ClassNotFoundException {
        user.setBooks(new ClientClass().getUsersBook(user));

        StringWriter writer = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(User.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(user, writer);

        return writer.toString();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        User user = (User) httpSession.getAttribute("user");
        List<Book> rations = null;

        if (user != null) {
            resp.setContentType("application/xml");
            try (PrintWriter writer = resp.getWriter()) {
                writer.println(getUserDataXMLRepresentation(user)/*"<h2>Here should be the response from ResultServlet</h2>"*/);
            } catch (JAXBException | SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            resp.setContentType("text/html");
            try (PrintWriter writer = resp.getWriter()) {
                writer.println(
                        "<p class=\"main-redirect\">\n" +
                                "    User not found<br>you will be redirected to the login page after 5 seconds\n" +
                                "    <a href=\"index.jsp\">return now</a>\n" +
                                "</p>\n" +
                                "<meta http-equiv=\"refresh\" content=\"5; URL=index.jsp\">"
                );
            }
        }
    }
}
