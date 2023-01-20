package pl.lokalnylekarz.projekt.dataTypes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Image {
    private String id;
    private String url;
}
