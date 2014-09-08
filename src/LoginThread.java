import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;


public class LoginThread extends Thread
{
	Socket clientSocket;
	ObjectOutputStream clientOutput;
	ObjectInputStream clientInput;
	
	public LoginThread(Socket clientSocket,ObjectInputStream inputStrean,ObjectOutputStream outputStream)
	{
		this.clientSocket = clientSocket;
		clientOutput = outputStream;
		clientInput = inputStrean;
		
	}
	
	public void run()
	{
	
	
			String login;
			try
			{
				login = (String) clientInput.readObject();
				String password = (String) clientInput.readObject();
				
				Object player = new InteractionWithDB().getUser(login, password);
				
				clientOutput.writeObject("Player found");
				clientOutput.writeObject(player);
			} catch (ClassNotFoundException | IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CommunicationsException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}
}
