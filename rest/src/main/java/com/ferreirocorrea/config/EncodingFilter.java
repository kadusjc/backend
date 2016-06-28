package com.ferreirocorrea.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
	
@Configuration
public class EncodingFilter {

	@Bean
	public Filter getCharacterEncodingFilter() {
	    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	    characterEncodingFilter.setEncoding("ISO-8859-1");
	    characterEncodingFilter.setForceEncoding(true);
	    return characterEncodingFilter;
	}
}
