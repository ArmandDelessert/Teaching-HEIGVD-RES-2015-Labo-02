/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.res.labs.roulette.net.client;

import ch.heigvd.res.labs.roulette.data.EmptyStoreException;
import ch.heigvd.res.labs.roulette.data.Student;
import ch.heigvd.res.labs.roulette.net.protocol.RouletteV2Protocol;
import ch.heigvd.schoolpulse.TestAuthor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Simon
 */
public class RouletteV2BaehlerTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public EphemeralClientServerPair roulettePair = new EphemeralClientServerPair(RouletteV2Protocol.VERSION);

    @Test
    @TestAuthor(githubId = {"simon-baehler", "armanddelessert"})
    public void SameProtocol() throws IOException {
        assertEquals(RouletteV2Protocol.VERSION, roulettePair.getClient().getProtocolVersion());
    }

    @Test
    @TestAuthor(githubId = {"simon-baehler", "armanddelessert"})
    public void clearDataStoreStudent() throws IOException {
        IRouletteV2Client client = new RouletteV2ClientImpl();
        client.connect("localhost", roulettePair.getServer().getPort());
        assertTrue(client.isConnected());
        client.loadStudent("Simon");
        client.clearDataStore();
        assertTrue(client.getNumberOfStudents() == 0);
    }

    @Test
    @TestAuthor(githubId = {"simon-baehler", "armanddelessert"})
    public void listStudentsEmpty() throws IOException {
        IRouletteV2Client client = new RouletteV2ClientImpl();
        client.connect("localhost", roulettePair.getServer().getPort());
        assertTrue(client.listStudents().isEmpty());
    }

    @Test
    @TestAuthor(githubId = {"simon-baehler", "armanddelessert"})
    public void ClientConnected() throws IOException {
        IRouletteV2Client client = new RouletteV2ClientImpl();
        client.connect("localhost", roulettePair.getServer().getPort());
        assertTrue(client.isConnected());
    }

    @Test
    @TestAuthor(githubId = {"simon-baehler", "armanddelessert"})
    public void ClientDeonnected() throws IOException {
        IRouletteV2Client client = new RouletteV2ClientImpl();
        client.connect("localhost", roulettePair.getServer().getPort());
        client.disconnect();
        assertFalse(client.isConnected());
    }

    @Test
    @TestAuthor(githubId = {"simon-baehler", "armanddelessert"})
    public void AddStudent() throws IOException {
        IRouletteV2Client client = new RouletteV2ClientImpl();
        client.connect("localhost", roulettePair.getServer().getPort());
        client.loadStudent("simon");
        assertEquals(1, client.listStudents().size());
    }

    @Test
    @TestAuthor(githubId = {"simon-baehler", "armanddelessert"})
    public void ContainTheStudent() throws IOException, EmptyStoreException {
        IRouletteV2Client client = new RouletteV2ClientImpl();
        client.connect("localhost", roulettePair.getServer().getPort());
        assertTrue(client.isConnected());
        client.loadStudent("Simon");
        client.loadStudent("Armand");
        assertEquals(client.listStudents().get(0).getFullname(), "Simon");
    }
}
