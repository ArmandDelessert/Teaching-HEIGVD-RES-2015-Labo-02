package ch.heigvd.res.labs.roulette.net.client;

import ch.heigvd.res.labs.roulette.data.JsonObjectMapper;
import ch.heigvd.res.labs.roulette.data.Student;
import ch.heigvd.res.labs.roulette.data.StudentsList;
import ch.heigvd.res.labs.roulette.net.protocol.RouletteV2Protocol;
import java.io.IOException;
import java.util.List;

/**
 * This class implements the client side of the protocol specification (version
 * 2).
 *
 * @author Olivier Liechti
 */
public class RouletteV2ClientImpl extends RouletteV1ClientImpl implements IRouletteV2Client {
	@Override
	public void clearDataStore() throws IOException {
			printWriter.println(RouletteV2Protocol.CMD_CLEAR);
			printWriter.flush();
			if (!reader().equals(RouletteV2Protocol.RESPONSE_CLEAR_DONE)) {
				throw new IOException("Error, clear apperently failed");
		}
	}

	@Override
	public List<Student> listStudents() throws IOException {
		printWriter.println(RouletteV2Protocol.CMD_LIST);
		printWriter.flush();

		StudentsList sl = JsonObjectMapper.parseJson(reader(), StudentsList.class);
		return sl.getStudents();
	}

}
