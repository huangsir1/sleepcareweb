/**
 * 
 */
package taiyi.web.model;

import java.util.Date;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.model
 *
 * 2016年4月10日
 */
public class Pulse {
	 private int average;
	 private int max;
	 private Date maxTime;
	 private int min;
	 private Date minTime;
	 
	/**
	 * @return the average
	 */
	public int getAverage() {
		return average;
	}
	/**
	 * @param average the average to set
	 */
	public void setAverage(int average) {
		this.average = average;
	}
	/**
	 * @return the max
	 */
	public int getMax() {
		return max;
	}
	/**
	 * @param max the max to set
	 */
	public void setMax(int max) {
		this.max = max;
	}
	/**
	 * @return the maxTime
	 */
	public Date getMaxTime() {
		return maxTime;
	}
	/**
	 * @param maxTime the maxTime to set
	 */
	public void setMaxTime(Date maxTime) {
		this.maxTime = maxTime;
	}
	/**
	 * @return the min
	 */
	public int getMin() {
		return min;
	}
	/**
	 * @param min the min to set
	 */
	public void setMin(int min) {
		this.min = min;
	}
	/**
	 * @return the minTime
	 */
	public Date getMinTime() {
		return minTime;
	}
	/**
	 * @param minTime the minTime to set
	 */
	public void setMinTime(Date minTime) {
		this.minTime = minTime;
	}
	
	
	 
	 
}
