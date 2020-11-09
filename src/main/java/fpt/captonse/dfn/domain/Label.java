package fpt.captonse.dfn.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import fpt.captonse.dfn.domain.enumeration.LabelType;

/**
 * A Label.
 */
@Entity
@Table(name = "labels")
public class Label implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "label")
    private LabelType label;

    @OneToMany(mappedBy = "labelId")
    private Set<Article> ids = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LabelType getLabel() {
        return label;
    }

    public Label label(LabelType label) {
        this.label = label;
        return this;
    }

    public void setLabel(LabelType label) {
        this.label = label;
    }

    public Set<Article> getIds() {
        return ids;
    }

    public Label ids(Set<Article> articles) {
        this.ids = articles;
        return this;
    }

    public Label addId(Article article) {
        this.ids.add(article);
        article.setLabelId(this);
        return this;
    }

    public Label removeId(Article article) {
        this.ids.remove(article);
        article.setLabelId(null);
        return this;
    }

    public void setIds(Set<Article> articles) {
        this.ids = articles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Label)) {
            return false;
        }
        return id != null && id.equals(((Label) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Label{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
