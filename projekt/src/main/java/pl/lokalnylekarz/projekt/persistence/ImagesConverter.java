package pl.lokalnylekarz.projekt.persistence;

import jakarta.persistence.AttributeConverter;
import pl.lokalnylekarz.projekt.dataTypes.Image;

import java.util.ArrayList;
import java.util.List;

public class ImagesConverter implements AttributeConverter<List<Image>, String> {

    private static final String SEPARATOR = ";";
    private static final String INNER_SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(List<Image> images) {
        if (images == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        for (Image image : images) {
            if (image.getId() == null || image.getUrl() == null) {
                return null;
            }

            sb.append(image.getId())
                    .append(INNER_SEPARATOR)
                    .append(image.getUrl())
                    .append(SEPARATOR);
        }

        return sb.toString();
    }

    @Override
    public List<Image> convertToEntityAttribute(String dbImage) {
        if (dbImage == null || dbImage.isEmpty()) {
            return null;
        }
        List<Image> images = new ArrayList<>();

        String[] imagesParts = dbImage.split(SEPARATOR);

        for (String s: imagesParts) {
            String[] imagePart = s.split(INNER_SEPARATOR);

            if (imagePart.length != 2) {
                return null;
            }

            images.add(
                    Image.builder()
                    .id(imagePart[0])
                    .url(imagePart[1])
                    .build());
        }

        return images;
    }
}
