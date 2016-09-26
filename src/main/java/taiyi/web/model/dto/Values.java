/**
 * 
 */
package taiyi.web.model.dto;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.model.dto
 *
 * 2016年9月26日
 */
public class Values {
	private String name;
	private int value;
	
	public Values(){}
	
	
	/**
	 * @param name
	 * @param value
	 */
	public Values(String name, int value) {
		super();
		this.name = name;
		this.value = value;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	
	
}
