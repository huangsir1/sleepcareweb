/**
 * 
 */
package taiyi.web.model.dto;

import java.util.Date;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.model.dto
 *
 * 2016年9月26日
 */
public class ImageModel implements Comparable<ImageModel>{
	 private Date time;
	 private int percent;
	 private int frequency;
	 
	 
	 /**
	 * @param time
	 * @param percent
	 * @param frequency
	 */
	public ImageModel(Date time, int percent, int frequency) {
		super();
		this.time = time;
		this.percent = percent;
		this.frequency = frequency;
	}


	public ImageModel() {
		 
	 }
	 
	 
	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	/**
	 * @return the percent
	 */
	public int getPercent() {
		return percent;
	}
	/**
	 * @param percent the percent to set
	 */
	public void setPercent(int percent) {
		this.percent = percent;
	}
	/**
	 * @return the frequency
	 */
	public int getFrequency() {
		return frequency;
	}
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}


	/* 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ImageModel imageModel) {
		return (int) (this.getTime().getTime() - imageModel.getTime().getTime());
	}
	 
	 
	 /* 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
	/* 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		ImageModel other =  (ImageModel) obj;
		int thisSecond = (int) (this.getTime().getTime() / 1000);
		int otherSecond = (int) (other.getTime().getTime() / 1000);
		return thisSecond == otherSecond;
	}


	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ImageModel [time=" + time + ", percent=" + percent + ", frequency=" + frequency + "]";
	}
	
	
}
