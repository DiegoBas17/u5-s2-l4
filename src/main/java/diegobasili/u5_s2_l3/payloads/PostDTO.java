package diegobasili.u5_s2_l3.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record PostDTO(@NotEmpty(message = "La categorie è obbligatoria!")
                      @Size(min = 3, max = 40)
                      String category,
                      @NotEmpty(message = "La titolo è obbligatoria!")
                      @Size(min = 3, max = 40)
                      String title,
                      @NotEmpty(message = "Il contenuto è obbligatoria!")
                      @Size(min = 3, max = 200)
                      String content,
                      @NotNull(message = "Il tempo di lettura è obbligatorio!")
                      @Positive
                      int timeForRead,
                      //@NotEmpty(message = "L'id del autore è obbligatorio!")
                      //@NotNull(message = "L'id del autore è obbligatorio!")
                      UUID authorId) {
}
