package taiyi.web.model;

public class EssUser {
	private Integer id;

	private String userId;

	private Integer essId;

	private Integer rank;

	public EssUser() {

	}

	public EssUser(Integer essId, Integer rank) {
		this.essId = essId;
		this.rank = rank;
	}

	/**
	 * @param id
	 * @param userId
	 * @param essId
	 * @param rank
	 */
	public EssUser(String userId, Integer essId, Integer rank) {
		super();
		this.userId = userId;
		this.essId = essId;
		this.rank = rank;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getEssId() {
		return essId;
	}

	public void setEssId(Integer essId) {
		this.essId = essId;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EssUser [id=" + id + ", userId=" + userId + ", essId=" + essId + ", rank=" + rank + "]";
	}

}