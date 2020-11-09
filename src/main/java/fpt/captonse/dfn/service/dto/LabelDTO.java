package fpt.captonse.dfn.service.dto;

import java.io.Serializable;
import fpt.captonse.dfn.domain.enumeration.LabelType;

/**
 * A DTO for the {@link fpt.captonse.dfn.domain.Label} entity.
 */
public class LabelDTO implements Serializable {
    
    private Long id;

    private LabelType label;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LabelType getLabel() {
        return label;
    }

    public void setLabel(LabelType label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LabelDTO)) {
            return false;
        }

        return id != null && id.equals(((LabelDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LabelDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
