package fpt.captonse.dfn.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link fpt.captonse.dfn.domain.Article} entity.
 */
public class ArticleDTO implements Serializable {
    
    private Long id;

    private String newsContent;

    private LocalDate dateDetected;

    private String emailAddress;

    private Boolean isDeleted;


    private Long labelIdId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public LocalDate getDateDetected() {
        return dateDetected;
    }

    public void setDateDetected(LocalDate dateDetected) {
        this.dateDetected = dateDetected;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getLabelIdId() {
        return labelIdId;
    }

    public void setLabelIdId(Long labelId) {
        this.labelIdId = labelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleDTO)) {
            return false;
        }

        return id != null && id.equals(((ArticleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleDTO{" +
            "id=" + getId() +
            ", newsContent='" + getNewsContent() + "'" +
            ", dateDetected='" + getDateDetected() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", isDeleted='" + isIsDeleted() + "'" +
            ", labelIdId=" + getLabelIdId() +
            "}";
    }
}
