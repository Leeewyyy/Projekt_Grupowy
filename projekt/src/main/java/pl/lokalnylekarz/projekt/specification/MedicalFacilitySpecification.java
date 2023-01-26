package pl.lokalnylekarz.projekt.specification;

import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityFilter;
import pl.lokalnylekarz.projekt.model.MedicalFacility;
import pl.lokalnylekarz.projekt.model.Specialist;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class MedicalFacilitySpecification implements Specification<MedicalFacility> {
    private MedicalFacilityFilter searchCriteria;

    public Predicate toPredicate(Root<MedicalFacility> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (searchCriteria.getIsNFZ() != null) {
            predicates.add(builder.equal(
                    root.get("isNFZ"),
                    searchCriteria.getIsNFZ()
            ));
        }

        if (searchCriteria.getType() != null) {
            predicates.add(builder.equal(
                    root.get("type"),
                    searchCriteria.getType()
            ));
        }

        if (searchCriteria.getSpecialization() != null) {
            Path<Specialist> u = root.get("specialist");

            predicates.add(builder.equal(
                    u.get("specialization"),
                    searchCriteria.getSpecialization()
            ));
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
