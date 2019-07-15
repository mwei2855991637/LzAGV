package com.lc.util.binder;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public class Binder {
	@InitBinder("page")
	public void initPage(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("page.");
	}
}
