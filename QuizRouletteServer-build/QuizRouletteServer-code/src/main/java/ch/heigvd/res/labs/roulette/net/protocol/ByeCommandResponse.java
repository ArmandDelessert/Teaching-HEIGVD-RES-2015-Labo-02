/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.res.labs.roulette.net.protocol;

/**
 *
 * @author Simon
 */
public class ByeCommandResponse {

    private String status;

    private int nbrCmd;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNbrCmd() {
        return nbrCmd;
    }

    public void setNbrCmd(int nbrCmd) {
        this.nbrCmd = nbrCmd;
    }
}
