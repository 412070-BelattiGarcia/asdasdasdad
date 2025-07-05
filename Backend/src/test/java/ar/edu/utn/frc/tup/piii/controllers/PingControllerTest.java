package ar.edu.utn.frc.tup.piii.controllers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PingControllerTest {

    private final PingController pingController = new PingController();

    @Test
    void pongReturnsPong() {
        // Al llamar al endpoint interno, debe devolverse "pong"
        String response = pingController.pong();
        assertEquals("pong", response, "El método pong() debe retornar exactamente “pong”");
    }
}
