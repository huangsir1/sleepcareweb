package taiyi.web.model;

public class DiseaseHistoryUser {
    private Integer id;

    private Integer diseaseHistoryId;

    private String userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDiseaseHistoryId() {
        return diseaseHistoryId;
    }

    public void setDiseaseHistoryId(Integer diseaseHistoryId) {
        this.diseaseHistoryId = diseaseHistoryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}