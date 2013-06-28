package jee.reference.ext.drools.fact;


public class Passport {
    private Long id;
    private Person owner;

    private String documentId;
    private String issuingCountry;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getIssuingCountry() {
        return issuingCountry;
    }

    public void setIssuingCountry(String issuingCountry) {
        this.issuingCountry = issuingCountry;
    }

    @Override
    public String toString() {
        return "Passport [id=" + id + ", ownerId=" + (owner == null ? "N/A" : owner.getId()) + ", documentId=" + documentId + ", issuingCountry="
            + issuingCountry + "]";
    }

}
