package user;

import elements.Author;
import elements.Book;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class Constants {
    public static final String authorName = new String(new String("HelenS"));
    public static final Author name = new Author(authorName);
    public static final User user = new User(9, "Oleg", "bbbbb", "88888");
    public static final Book book = new Book("Hugge", 2019, 649, 499);
}

public class ClientClass {
    String url = "jdbc:mysql://localhost/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String username = "root";
    String password = "megapassword";

    private Connection connection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }

        public void createTables() throws SQLException, ClassNotFoundException {
        Connection connection = this.connection();
        Statement statement = connection.createStatement();
            String sqlCommand = "CREATE TABLE customer (Id INT PRIMARY KEY AUTO_INCREMENT not null, name VARCHAR(20) not null, " +
                    "login VARCHAR(20) not null, password VARCHAR(20) not null)";
            String sqlCommand2 = "CREATE TABLE book (title  VARCHAR(20) PRIMARY KEY not null, year INT, pages INT, price INT)";
            String sqlCommand3 = "CREATE TABLE author (Id INT not null, authorName VARCHAR(20) PRIMARY KEY)";
            String sqlCommand4 = "CREATE TABLE orderBook (authorName  VARCHAR(20) PRIMARY KEY not null, title VARCHAR(20) not null)";
            statement.execute(sqlCommand);
            statement.execute(sqlCommand2);
            statement.execute(sqlCommand3);
            statement.execute(sqlCommand4);
            System.out.println("the tables have been created");
            statement.close();
            connection.close();
        }

        public void filltables()throws SQLException, ClassNotFoundException{
        Connection connection = this.connection();
        //Statement statement = connection.createStatement();
        String rows = "INSERT customer(id, name, login, password) VALUES (?,?,?,?)";
        String rows2 = "INSERT book(title, year, pages, price) VALUES (?,?,?,?)";
        String rows3 = "INSERT author(id , authorName) VALUES (?,?)";
        String rows4 = "INSERT orderBook(authorName, title) VALUES (?,?)";
        /*(1, 'Anna', 'qwerty', '12345')," +
                    "(2, 'Petr', 'qwertyuiop', '098765'), (3, 'Kate', 'asdfgh', '456789'), (4, 'Yana', 'dffff', '0000'), (5, 'Egor', 'pppp', '6666')");*/
       /* //int rows2 = statement.executeUpdate("INSERT book(title, year, pages, price) VALUES ('Last_hunter', 2020, 600, 499 )," +
                        "('Korporatsia_geniev', 2019, 344, 644), ('Teremok', 2020, 900, 1440), " +
                "('Postscript',  2020, 256, 359)");
        //int rows3 = statement.executeUpdate("INSERT author(id , authorName) VALUES (1, 'Granzhe')," +
                    "(2, 'Ed Ketmel'), ( 3, 'Tolstoi A.N'), (4, 'Ahern Sesiliya')");
        //int rows4 = statement.executeUpdate("INSERT orderBook(authorName, title) VALUES ('Granzhe', 'Last_hunter')," +
                    "('Ed Ketmel','Korporatsia_geniev' ), ('Tolstoi A.N','Teremok' ), ('Ahern Sesiliya','Postscript' )");*/
        PreparedStatement preparedStatement = connection.prepareStatement(rows);
        preparedStatement.setInt(1, 1);
        preparedStatement.setString(2, "Anna");
        preparedStatement.setString(3, "qwerty");
        preparedStatement.setString(4, "12345");
        /*preparedStatement.setInt(1, 2);
        preparedStatement.setString(2, "Petr");
        preparedStatement.setString(3, "qwertyuiop");
        preparedStatement.setString(4, "098765");
        preparedStatement.setInt(1, 3);
        preparedStatement.setString(2, "Kate");
        preparedStatement.setString(3, "asdfgh");
        preparedStatement.setString(4, "456789");*/

        PreparedStatement preparedStatement2 = connection.prepareStatement(rows2);
            preparedStatement2.setString(1, "Last_hunter");
            preparedStatement2.setInt(2, 2020);
            preparedStatement2.setInt(3, 600);
            preparedStatement2.setInt(4, 499);
            /*preparedStatement2.setString(1, "Korporatsia_geniev");
            preparedStatement2.setInt(2, 2019);
            preparedStatement2.setInt(3, 344);
            preparedStatement2.setInt(4, 644);
            preparedStatement2.setString(1, "Teremok");
            preparedStatement2.setInt(2, 2020);
            preparedStatement2.setInt(3, 222);
            preparedStatement2.setInt(4, 644);
            preparedStatement2.setString(1, "Postscript");
            preparedStatement2.setInt(2, 2002);
            preparedStatement2.setInt(3, 212);
            preparedStatement2.setInt(4, 1000);*/

        PreparedStatement preparedStatement3 = connection.prepareStatement(rows3);
            preparedStatement3.setInt(1, 1);
            preparedStatement3.setString(2, "Granzhe");
           /* preparedStatement3.setInt(1, 2);
            preparedStatement3.setString(2, "Ed Ketmel");
            preparedStatement3.setInt(1, 3);
            preparedStatement3.setString(2, "Tolstoi A.N");*/

        PreparedStatement preparedStatement4 = connection.prepareStatement(rows4);
            preparedStatement4.setString(1, "Granzhe");
            preparedStatement4.setString(2, "Last_hunter");
            /*preparedStatement4.setString(1, "Ed Ketmel");
            preparedStatement4.setString(2, "Korporatsia_geniev");
            preparedStatement4.setString(1, "Tolstoi A.N");
            preparedStatement4.setString(2, "Teremok");*/

        int r = preparedStatement.executeUpdate();
        int r2 = preparedStatement2.executeUpdate();
        int r3 = preparedStatement3.executeUpdate();
        int r4 = preparedStatement4.executeUpdate();

        System.out.printf("Added %d rows in table /customer/", r );
        System.out.printf("\n"+" Added %d rows in table /book/", r2);
        System.out.printf("\n"+" Added %d rows in table /author/", r3);
        System.out.printf("\n" +" Added %d rows in table /orderBook/", r4);
        connection.close();
        }

        public void addUser() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = this.connection();

            PreparedStatement statem = connection.prepareStatement("INSERT customer(id, name, login, password) VALUES (?,?,?,?)");
            statem.setInt(1, Constants.user.getId());
            statem.setString(2, Constants.user.getName());
            statem.setString(3, Constants.user.getLogin());
            statem.setString(4, Constants.user.getPassword());

            User user = new User(Constants.user.getId(), Constants.user.getName(), Constants.user.getLogin(), Constants.user.getPassword());
                addAuthor();

            int r = statem.executeUpdate();
        System.out.printf("Added rows in table /customer/");
        connection.close();
        }

        public void addAuthor() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = this.connection();
            String rows2 = "INSERT author(id, authorName) VALUES (?,?)";
            String rows3 = "INSERT orderBook(authorName, title) VALUES (?,?)";


            PreparedStatement preparedStatement3 = connection.prepareStatement(rows2);
            preparedStatement3.setInt(1, Constants.user.getId());
            preparedStatement3.setString(2, Constants.authorName);

            PreparedStatement preparedStatement4 = connection.prepareStatement(rows3);
            preparedStatement4.setString(1, Constants.authorName);
            preparedStatement4.setString(2, Constants.book.getTitle());

            Author a = new Author(Constants.book.getTitle());
                addBook();
            int r3 = preparedStatement3.executeUpdate();
            int r4 = preparedStatement4.executeUpdate();
;
        System.out.printf("Added rows in table /author/");
        System.out.printf("Added rows in table /orderBook/");
        connection.close();
        }

        public void addBook() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = this.connection();
        Statement statement = connection.createStatement();
        String rows4 = "INSERT book(title, year, pages, price) VALUES (?,?,?,?)";

        PreparedStatement preparedStatement2 = connection.prepareStatement(rows4);
        preparedStatement2.setString(1, Constants.book.getTitle());
        preparedStatement2.setInt(2, Constants.book.getYear());
        preparedStatement2.setInt(3, Constants.book.getPage());
        preparedStatement2.setDouble(4, Constants.book.getPrice());
        int r2 = preparedStatement2.executeUpdate();

        System.out.printf("Added rows in table /book/");
        connection.close();
        }

        public void deleteBook()throws SQLException, ClassNotFoundException{
        Scanner scanner = new Scanner(System.in);
        Connection connection = this.connection();
        System.out.print("Input year: ");
        int year = scanner.nextInt();
        System.out.print("Input price: ");
        int price = scanner.nextInt();
        //first operation
        connection.setAutoCommit(false);
        try {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM book WHERE year = ?");
        preparedStatement.setInt(1, year);
        int rows = preparedStatement.executeUpdate();
        System.out.printf("Deleted book with year " + year + "\n");
        //second operation
        PreparedStatement preparedStatement2 = connection.prepareStatement("DELETE FROM book WHERE price = ?");
        preparedStatement2.setInt(1, price);
        int rows2 = preparedStatement2.executeUpdate();
        System.out.printf("Deleted book with price " + price + "\n");
        connection.commit();}
        catch(SQLException e){
            connection.rollback();
        }
        connection.close();
        }

        public boolean checkUsers() throws SQLException, ClassNotFoundException {
        Connection connection = this.connection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer WHERE login = ? AND password = ?");
        statement.setString(1, Constants.user.getLogin());
        statement.setString(2, Constants.user.getPassword());
        ResultSet check = statement.executeQuery();
        boolean user = check.next();
        connection.close();
        return user;
        }

    public Author getBookByAuthorId(int id) throws SQLException, ClassNotFoundException {
        Connection connection = this.connection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM book WHERE title IN(SELECT title FROM orderbook WHERE authorName in" +
                "(SELECT authorName FROM " + "author WHERE id = ?))" );
        statement.setObject(1, id);
        ResultSet resultSet = statement.executeQuery();
        Author aut = new Author();
        while (resultSet.next()) {
            String title = resultSet.getString(1);
            int year = resultSet.getInt(2);
            int pages = resultSet.getInt(3);
            double price = resultSet.getDouble(4);
            /*System.out.printf("title:"+String.valueOf(title)+ " ");
            System.out.printf("publishOfYear:"+String.valueOf(year)+ " ");
            System.out.printf("pages:"+String.valueOf(pages)+ " ");
            System.out.printf("price:"+String.valueOf(price)+ " ");*/
            aut.add(new Book(title,year,pages,price));
        }
        connection.close();
        return aut;
    }

        public List<Author> getUsersBook(User user) throws SQLException, ClassNotFoundException {
        int userid = user.getId();
        Connection connection = this.connection();
        PreparedStatement statement = connection.prepareStatement("SELECT id FROM author WHERE id = ? ");
        statement.setObject(1, userid );
        ResultSet resultSet = statement.executeQuery();
        List<Author> books = new LinkedList<>();
        while (resultSet.next()) {
            //getBookByAuthorId(resultSet.getInt(1));
            books.add(this.getBookByAuthorId(resultSet.getInt(1)));
        }
        connection.close();
        return books;
        }

    public Author getUsersAuthors(User user) throws SQLException, ClassNotFoundException {
        int userid = user.getId();
        Connection connection = this.connection();
        PreparedStatement statement = connection.prepareStatement("SELECT authorName FROM author WHERE id = ? ");
        statement.setObject(1, userid );
        ResultSet resultSet = statement.executeQuery();
        Author aut = new Author();
        while (resultSet.next()) {
            String name = resultSet.getString(1);
            aut = new Author(name);
        }
        connection.close();
        return aut;
    }

        public List<Book> selectAll() throws SQLException, ClassNotFoundException {
        Connection connection = this.connection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM book");
        ResultSet resultSet = statement.executeQuery();
        List<Book> b = new LinkedList<>();
        while(resultSet.next()){
            String title = resultSet.getString(1);
            int year = resultSet.getInt(2);
            int pages = resultSet.getInt(3);
            int price = resultSet.getInt(4);
            System.out.printf("title:"+String.valueOf(title)+ " "+"\n");
            System.out.printf("publishOfYear:"+String.valueOf(year)+ " "+"\n");
            System.out.printf("pages:"+String.valueOf(pages)+ " "+"\n");
            System.out.printf("price:"+String.valueOf(price)+ " "+"\n");
            System.out.println();
            b.add(new Book(title,year,pages,price));
            }
        connection.close();
        return b;
        }

        public List<User> getUsersByBook() throws SQLException, ClassNotFoundException {
        Connection connection = this.connection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer WHERE id IN(SELECT id FROM author WHERE authorName IN(" +
                "SELECT authorName FROM orderBook WHERE title = ?))");
        statement.setObject(1, Constants.book.getTitle());
        ResultSet resultSet = statement.executeQuery();
        List<User> b = new LinkedList<>();
            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String login = resultSet.getString(3);
                String password = resultSet.getString(4);
                System.out.printf("id:"+ String.valueOf(id)+ " " +"\n");
                System.out.printf("name:"+String.valueOf(name)+ " "+"\n");
                System.out.printf("login:"+String.valueOf(login)+ " "+"\n");
                System.out.printf("password:"+String.valueOf(password)+ " "+"\n");
                b.add(new User(id,name,login,password));
            }
            connection.close();
            return b;
        }

    public User getUser(String login, String password) throws SQLException, ClassNotFoundException {
        Connection connection = this.connection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer WHERE login = ? AND password = ?");
        statement.setString(1, login);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        User user = null;
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String login2 = resultSet.getString(3);
            String password2 = resultSet.getString(4);
            user = new User(id, name, login2, password2);
        }
        statement.close();
        connection.close();
        return user;
    }

    public static void main(String ...args) throws SQLException, ClassNotFoundException {
        ClientClass helper = new ClientClass();
        //helper.createTables(); //создание таблиц
        //helper.filltables(); // заполнение таблиц
        //helper.deleteBook(); // удаление элементов таблицы
        //helper.addUser();
        //helper.addAuthor();
        //helper.addBook();

        //boolean check = helper.checkUsers(); //проверка наличия пользователя
        //System.out.println(check);

        //List<Author> a = helper.getUsersBook(Constants.user); // вывод всех книг пользователя
        //System.out.println(a);

        //Author aq = helper.getUsersAuthors(Constants.user);
        //System.out.println(aq);

        //Author a1 = helper.getBookByAuthorId(Constants.user.getId()); //вывод книг по заданному Id
        //System.out.println(a1);

        //List<Book> a2 = helper.selectAll(); // вывод содержимого списка Book
        //System.out.println(a2);

        //List<User> a3 = helper.getUsersByBook(); //вывод пользователя по заданной книге
        //System.out.println(a3);
    }
}
