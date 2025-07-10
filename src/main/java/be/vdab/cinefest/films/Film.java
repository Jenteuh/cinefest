package be.vdab.cinefest.films;

import java.math.BigDecimal;

public class Film {

    private final long id;
    private final String titel;
    private final int jaar;
    private int vrijePlaatsen;
    private final BigDecimal aankoopprijs;

    public Film(long id, String titel, int jaar, int vrijePlaatsen, BigDecimal aankoopprijs) {
        this.id = id;
        this.titel = titel;
        this.jaar = jaar;
        this.vrijePlaatsen = vrijePlaatsen;
        this.aankoopprijs = aankoopprijs;
    }

    public long getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public int getJaar() {
        return jaar;
    }

    public int getVrijePlaatsen() {
        return vrijePlaatsen;
    }

    public BigDecimal getAankoopprijs() {
        return aankoopprijs;
    }

    public void reserveer(int plaatsen) {
        if (plaatsen > vrijePlaatsen) {
            throw new OnvoldoendeVrijePlaatsenException();
        }
        vrijePlaatsen -= plaatsen;
    }
}
