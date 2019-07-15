package com.lc.bean;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
/**
 * 这个使用来处理日期的类
 * @author lenovo
 *
 */
@Component("SpringDateAuditorAware")
public class SpringDateAuditorAware implements AuditorAware<Date> {
	@Override
	public Optional<Date> getCurrentAuditor() {
		return Optional.of(new Date());
	}
}
