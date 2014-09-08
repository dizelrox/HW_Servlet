import java.io.*;
import java.net.*;
import java.util.Scanner;

import com.example.dizelrox.hooliganwarsandroid.Logic.Player;

public class ThreadHandler extends Thread
{

	// socket number
	Socket				clientSocket1;
	Socket				clientSocket2;

	ObjectInputStream	inputFromClient1;
	ObjectInputStream	inputFromClient2;

	ObjectOutputStream	outputToClient1;
	ObjectOutputStream	outputToClient2;

	Integer				STARTING_NEW_GAME	= new Integer(1);

	public ThreadHandler(Socket socket1, Socket socket2, ObjectInputStream client1Input, ObjectOutputStream client1Output,
			ObjectInputStream client2Input, ObjectOutputStream client2Output)
	{

		// initialize upon creation
		clientSocket1 = socket1;
		clientSocket2 = socket2;

		inputFromClient1 = client1Input;
		inputFromClient2 = client2Input;

		outputToClient1 = client1Output;
		outputToClient2 = client2Output;
		
	}

	public void run()
	{
		try
		{
			System.out.println("In game thread");
			Player player1 = (Player) inputFromClient1.readObject();
			Player player2 = (Player) inputFromClient2.readObject();
			
			System.out.println(player1.getName());
			System.out.println(player2.getName());
			
			outputToClient1.writeObject(player2);
			outputToClient2.writeObject(player1);
			
			outputToClient1.writeObject(true);
			outputToClient2.writeObject(false);
			
			while (true)
			{
			Object player2DefencekArea = inputFromClient2.readObject();	
			Object player1AtteackArea = inputFromClient1.readObject();	
				
			outputToClient1.writeObject(player2DefencekArea);
			outputToClient2.writeObject(player1AtteackArea);

			Object player1DefencekArea = inputFromClient1.readObject();	
			Object player2AtteackArea = inputFromClient2.readObject();	
				
			outputToClient1.writeObject(player2AtteackArea);
			outputToClient2.writeObject(player1DefencekArea);
			
			
			}
			
			
		} catch (ClassNotFoundException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}