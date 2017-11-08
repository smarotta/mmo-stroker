package com.sm.gwid.domain;

import java.util.UUID;

public abstract class IdentifiableDomain extends Domain {
	
	private UUID id;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof IdentifiableDomain) {
			IdentifiableDomain that = (IdentifiableDomain)o;
			if (this.id == null && that.id != null) {
				return 1;
			} else if (this.id != null && that.id == null) {
				return -1;
			} else if (this.id != null && that.id != null) {
				return this.id.compareTo(that.id);
			}
		}
		return super.compareTo(o);
	}
}
