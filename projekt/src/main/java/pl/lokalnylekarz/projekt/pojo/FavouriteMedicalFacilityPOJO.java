package pl.lokalnylekarz.projekt.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FavouriteMedicalFacilityPOJO {
    public ArrayList<Long> facilitiesId;
}
