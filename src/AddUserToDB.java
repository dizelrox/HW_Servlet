

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

/**
 * Servlet implementation class GetUserFromDB
 */
@WebServlet("/AddUserToDB")
public class AddUserToDB extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUserToDB() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @throws IOException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html");
		Gson gson = new Gson();
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		Player player = gson.fromJson(request.getParameter("jsonObject"), Player.class);
		try {
			new InteractionWithDB().addUser(login,password, player);
	        writer.println("Added player successfully");
		} catch (CommunicationsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			writer.println("Failed to add player");
			System.out.println("Tried to add player with username"+login+" already in database!");
		}
	}

}
