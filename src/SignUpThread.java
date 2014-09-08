import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class SignUpThread extends Thread
{
	Socket clientSocket;
	ObjectOutputStream clientOutput;
	ObjectInputStream clientInput;

	public SignUpThread(Socket clientSocket,ObjectInputStream inputStrean, ObjectOutputStream outputStream)
	{
		this.clientSocket = clientSocket;
		clientOutput = outputStream;
		clientInput = inputStrean;
		

	}

	@Override
	public void run()
	{
		try
		{
			Object player = clientInput.readObject();
			String login = (String) clientInput.readObject();
			String password = (String) clientInput.readObject();
			
			new InteractionWithDB().addUser(login, password, player);
			
			clientOutput.writeObject("Successfully added new player");
			
		} catch (SQLException e)
		{
			try
			{
				clientOutput.writeObject("Failed to add player");
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		catch (ClassNotFoundException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
