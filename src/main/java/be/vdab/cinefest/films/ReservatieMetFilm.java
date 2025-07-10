package be.vdab.cinefest.films;

import java.time.LocalDateTime;

public record ReservatieMetFilm(long id, String titel, int plaatsen, LocalDateTime besteld) {
}
