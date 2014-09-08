import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import com.example.dizelrox.hooliganwarsandroid.Logic.Player;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;


public class SaveProgresThread extends Thread
{
	Socket clientSocket;
	ObjectOutputStream clientOutput;
	ObjectInputStream clientInput;
	
	public SaveProgresThread(Socket clientSocket,ObjectInputStream inputStrean,ObjectOutputStream outputStream)
	{
		this.clientSocket = clientSocket;
		clientOutput = outputStream;
		clientInput = inputStrean;
	}
	
	public void run()
	{
		try
		{
			Object player = clientInput.readObject();
			new InteractionWithDB().savePlayerToDatabase((Player) player);
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
