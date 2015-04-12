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
import java.util.logging.Logger;

/**
 * This class implements the client side of the protocol specification (version 1).
 *
 * @author Olivier Liechti
 */
public class RouletteV1ClientImpl implements IRouletteV1Client {

	private static final Logger LOG = Logger.getLogger(RouletteV1ClientImpl.class.getName());
	Socket clientS = null;
	protected BufferedReader bufferedReader = null;
	// output to server (dont forget to flush before expecting response!)
	protected PrintWriter printWriter = null;

	private String win = "Hello. Online HELP is available. Will you find it?";
	private String fail = "Huh? please use HELP if you don't know what commands are available.";

	public String reader() throws IOException {
		String line;
		do {
			line = bufferedReader.readLine();
		} while (line.equals(win) || line.equals(fail));
		return line;
	}
	@Override
	public void connect(String server, int port) throws IOException {
		System.out.println("Demande de connexion");
		clientS = new Socket(server, port);
		printWriter = new PrintWriter(new OutputStreamWriter(clientS.getOutputStream()));
		bufferedReader = new BufferedReader(new InputStreamReader(clientS.getInputStream()));
		System.out.println("Connexion établie avec le serveur");
	}

	@Override
	public void disconnect() throws IOException {
		if (clientS != null) {
			printWriter.println(RouletteV1Protocol.CMD_BYE);
			clientS.shutdownInput();
			clientS.shutdownOutput();
			printWriter.flush();
			printWriter.close();
			bufferedReader.close();
			clientS.close();
			System.out.println("Deconexion");
		}
	}

	@Override
	public boolean isConnected() {
		if (clientS != null) {
			return clientS.isConnected() && !clientS.isClosed();
		}
		return false;
	}

	@Override
	public void loadStudent(String fullname) throws IOException {
		printWriter.println(RouletteV1Protocol.CMD_LOAD);
		printWriter.flush();
		if (!reader().equals(RouletteV1Protocol.RESPONSE_LOAD_START)) {
			throw new IOException("failed...");
		}
		printWriter.println(fullname);
		printWriter.println(RouletteV1Protocol.CMD_LOAD_ENDOFDATA_MARKER);
		printWriter.flush();
		reader();
	}

	@Override
	public void loadStudents(List<Student> students) throws IOException {
		printWriter.println(RouletteV1Protocol.CMD_LOAD);
		printWriter.flush();
		if (!reader().equals(RouletteV1Protocol.RESPONSE_LOAD_START)) {
			throw new IOException("failed...");
		}
		for (Student student : students) {
			printWriter.println(student.getFullname());
		}
		printWriter.println(RouletteV1Protocol.CMD_LOAD_ENDOFDATA_MARKER);
		printWriter.flush();
		reader();
	}

	@Override
	public Student pickRandomStudent() throws EmptyStoreException, IOException {
		printWriter.println(RouletteV1Protocol.CMD_RANDOM);
		printWriter.flush();
		RandomCommandResponse rcr = JsonObjectMapper.parseJson(reader(), RandomCommandResponse.class);
		if (rcr.getError() != null) {
			throw new EmptyStoreException();
		}
		return new Student(rcr.getFullname());
	}

	@Override
	public int getNumberOfStudents() throws IOException {
		printWriter.println(RouletteV1Protocol.CMD_INFO);
		printWriter.flush();
		InfoCommandResponse icr = JsonObjectMapper.parseJson(reader(), InfoCommandResponse.class);
		return icr.getNumberOfStudents();
	}

	@Override
	public String getProtocolVersion() throws IOException {
		printWriter.println(RouletteV1Protocol.CMD_INFO);
		printWriter.flush();
		InfoCommandResponse icr = JsonObjectMapper.parseJson(reader(), InfoCommandResponse.class);
		return icr.getProtocolVersion();
	}

}
