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
public class LoadCommandResponse {

    private String status;

    private int nbrStudents;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getnbrStudents() {
        return nbrStudents;
    }

    public void setnbrStudents(int nbrStudents) {
        this.nbrStudents = nbrStudents;
    }
}
