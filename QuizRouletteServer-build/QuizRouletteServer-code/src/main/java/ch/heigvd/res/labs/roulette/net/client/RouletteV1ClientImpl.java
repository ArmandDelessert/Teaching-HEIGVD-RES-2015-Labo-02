package ch.heigvd.res.labs.roulette.net.client;

import ch.heigvd.res.labs.roulette.data.EmptyStoreException;
import ch.heigvd.res.labs.roulette.data.JsonObjectMapper;
import ch.heigvd.res.labs.roulette.net.protocol.RouletteV1Protocol;
import ch.heigvd.res.labs.roulette.data.Student;
import ch.heigvd.res.labs.roulette.net.protocol.InfoCommandResponse;
import ch.heigvd.res.labs.roulette.net.protocol.RandomCommandResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the client side of the protocol specification (version 1).
 * 
 * @author Olivier Liechti
 */
public class RouletteV1ClientImpl implements IRouletteV1Client {

	private static final Logger LOG = Logger.getLogger(RouletteV1ClientImpl.class.getName());

	protected Socket socket = null;
	protected PrintWriter writer;
	protected BufferedReader reader;

	@Override
	public void connect(String server, int port) throws IOException {

		if (isConnected()) {
			disconnect();
		}

		socket = new Socket(server, port);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
	}

	@Override
	public void disconnect() throws IOException {
		if (isConnected()) {
			writer.println(RouletteV1Protocol.CMD_BYE);
			writer.flush();
			socket.close();
			socket = null;
			writer = null;
			reader = null;
		}
	}

	@Override
	public boolean isConnected() {
		return socket != null && socket.isConnected();
	}

	@Override
	public void loadStudent(String fullname) throws IOException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void loadStudents(List<Student> students) throws IOException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Student pickRandomStudent() throws EmptyStoreException, IOException {
		if (this.getNumberOfStudents() == 0) {
			throw new EmptyStoreException();
		}
		return null;
	}

	@Override
	public int getNumberOfStudents() throws IOException {
		return 0;
	}

	@Override
	public String getProtocolVersion() throws IOException {
		return RouletteV1Protocol.VERSION;
	}

}
