<%@ page import="user.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="user.ClientClass" %>
<%@ page import="elements.Author" %>
<%@ page import="elements.Book" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet/less" type="text/css" href="main.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto+Slab:wght@400;500;531;600;700;800;900&display=swap"
          rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Ubuntu:ital,wght@0,700;1,700&display=swap" rel="stylesheet">
    <script src="//cdnjs.cloudflare.com/ajax/libs/less.js/3.9.0/less.min.js"></script>
    <title>Library</title>
</head>
<body>
<%
    HttpSession httpSession = request.getSession();
    User user = (User) httpSession.getAttribute("user");
    List<Author> books = null;
    Author aut = null;
    if (user != null) {
        try {
            books = new ClientClass().getUsersBook(user);
            aut = new ClientClass().getUsersAuthors(user);
%>
<header class="main-header header">
    <h1 class="main-header__title title">Library</h1>
    <div class="main-header__wrapper">
        <h2 class="main-header__greeting"> Welcome, <%=user.getName()%></h2>

        <%
            if (request.getParameter("logout") != null) {
                /*HttpSession httpSession2 = request.getSession();*/
                httpSession.removeAttribute("user");
                response.sendRedirect("index.jsp");
            }
        %>
        <form action="#" method="post">
            <input class="main-header__logout button" name="logout" type="submit" value="Log out">
        </form>
    </div>
</header>

<main>
    <div class="main-wrapper">
        <table class="main-table table">
        <h3 class="main-header__greeting"> Your book: </h3>
        <%--<caption>ration name</caption>--%>
        <tr class="main-table__head-row">
            <th>Title</th>
            <th>Year</th>
            <th>Page</th>
            <th>Price</th>
        </tr>

        <%
            assert books != null;
            for (Author b1 : books) {
        %>
            <%
                List<Book> listb = b1.getBooks();
                for (Book book : listb) {
            %>

            <tr class="main-table__row">
                <td><%=book.getTitle()%>
                </td>
                <td><%=book.getYear()%>
                </td>
                <td><%=book.getPage()%>
                </td>
                <td><%=book.getPrice()%>
                </td>
            </tr>

            <%
                }
            %>

        <%
            }
        %>
        </table>
    </div>




</main>
<%

    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        response.sendRedirect("index.jsp");
    }
} else {
%>

<p class="main-redirect">
    User not found<br>you will be redirected to the main page in 5 seconds
    <a href="index.jsp">return now</a>
</p>
<meta http-equiv="refresh" content="5; URL=index.jsp">
<%
    }
%>

</body>
</html>
