package fpt.captonse.dfn.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Article.
 */
@Entity
@Table(name = "articles")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "news_content")
    private String newsContent;

    @Column(name = "date_detected")
    private LocalDate dateDetected;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    @JsonIgnoreProperties(value = "ids", allowSetters = true)
    private Label labelId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public Article newsContent(String newsContent) {
        this.newsContent = newsContent;
        return this;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public LocalDate getDateDetected() {
        return dateDetected;
    }

    public Article dateDetected(LocalDate dateDetected) {
        this.dateDetected = dateDetected;
        return this;
    }

    public void setDateDetected(LocalDate dateDetected) {
        this.dateDetected = dateDetected;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Article emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Boolean isIsDeleted() {
        return isDeleted;
    }

    public Article isDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Label getLabelId() {
        return labelId;
    }

    public Article labelId(Label label) {
        this.labelId = label;
        return this;
    }

    public void setLabelId(Label label) {
        this.labelId = label;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article)) {
            return false;
        }
        return id != null && id.equals(((Article) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", newsContent='" + getNewsContent() + "'" +
            ", dateDetected='" + getDateDetected() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", isDeleted='" + isIsDeleted() + "'" +
            "}";
    }
}
