package com.sm.gwid.domain;

public abstract class Domain implements Comparable<Object> {

	private long createTimeStamp;

	public Domain() {
		super();
		this.createTimeStamp = System.currentTimeMillis();
	}
	
	public long getCreateTimeStamp() {
		return createTimeStamp;
	}

	public void setCreateTimeStamp(long createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}
	
	public int compareTo(Object o) {
		if (o != null && o instanceof Domain) {
			Domain that = (Domain)o;
			return ((Long)this.createTimeStamp).compareTo(that.createTimeStamp);
		}
		
		return -1;
	}
}
