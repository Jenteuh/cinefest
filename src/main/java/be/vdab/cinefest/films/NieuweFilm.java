package be.vdab.cinefest.films;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NieuweFilm(@NotBlank String titel, @NotNull @Positive int jaar) {
}
