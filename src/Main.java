import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main
{

	public static void main(String[] args)
	{
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		Socket waitingSocket = null;
		ObjectInputStream waitingInput = null;
		ObjectOutputStream waitingOutput = null;
		try
		{

			serverSocket = new ServerSocket(55555);
			System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date())+" :Server started");
			while (true)
			{
				System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date())+" :Waiting for client..");
				clientSocket = serverSocket.accept();
				ObjectInputStream requestFromSocket = new ObjectInputStream(clientSocket.getInputStream());
				ObjectOutputStream requestToSocket = new ObjectOutputStream(clientSocket.getOutputStream());
				
				
				String request = (String) requestFromSocket.readObject();
				
				switch(request)
				{
				case "Are you alive?":
				{
					ObjectOutputStream clientOutput = new ObjectOutputStream(clientSocket.getOutputStream());
                    String textConnection = "Yes";
                    clientOutput.writeObject(textConnection);
					System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date())+" :Request: "+request);
					clientSocket.close();
					break;
				}
				case "Let me add a player!":
				{
					System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date())+" :Starting signup thread.");
					Thread signUpThread = new SignUpThread(clientSocket,requestFromSocket,requestToSocket);
					signUpThread.start();
					break;
				}
				case "Let me in!":
				{
					System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date())+" :Starting login thread.");
					Thread loginThread = new LoginThread(clientSocket,requestFromSocket,requestToSocket);
					loginThread.start();
					break;
				}
				case "Save Progress":
				{
					System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date())+" :Starting save progress thread.");
					Thread saveThread = new SaveProgresThread(clientSocket,requestFromSocket,requestToSocket);
					saveThread.start();
					break;
				}
				case "I just want to play":
				{
					if(waitingSocket == null)
					{
						waitingSocket = clientSocket;
						waitingInput = requestFromSocket;
						waitingOutput = requestToSocket;
						System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date())+" :Client added to waiting list.");
					}
					else
					{
						waitingSocket = null;
						System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date())+" :Client went to new gaming thread.");
						Thread thread = new ThreadHandler(clientSocket,waitingSocket,requestFromSocket,requestToSocket,waitingInput,waitingOutput);
						thread.start();
					}
					
					break;
				}
				}
			
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				serverSocket.close();
				System.out.println("Server stopped");
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
