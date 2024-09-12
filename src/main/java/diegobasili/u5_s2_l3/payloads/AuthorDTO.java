package diegobasili.u5_s2_l3.payloads;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AuthorDTO(@NotEmpty(message = "Il nome è obbligatorio!")
                        @Size(min=3, max = 40)
                        String name,
                        @NotEmpty(message = "Il cognome è obbligatorio!")
                        @Size(min=3, max = 40)
                        String surname,
                        @Email
                        String email,
                        @NotNull
                        @Past
                        LocalDate dateOfBirth) {
}
