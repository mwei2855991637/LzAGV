package com.lc.bean.enu;

/**
 * 用于表示状态， 0/1 [false/true]
 * @author lenovo
 *
 */
public enum Active {
	False, True;
	Active get(int index) {
		for (Active active : getDeclaringClass().getEnumConstants()) {
			if (active.ordinal() == index) {
				return active;
			}
		}
		return null;
	}

	public byte val() {
		return (byte) ordinal();
	}
}
