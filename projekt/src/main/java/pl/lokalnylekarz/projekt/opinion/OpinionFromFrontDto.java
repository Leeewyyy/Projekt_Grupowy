package pl.lokalnylekarz.projekt.opinion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class OpinionFromFrontDto {
    private Integer rating;
    private String text;
}
