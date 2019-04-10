package com.poc.transformations;

import org.apache.commons.lang3.StringUtils;

public class DatesCustomTransformations {

	public String mapPublicationDate(String input) {
		if(StringUtils.isBlank(input))
			return "99999999";
		return input;
	}

	public String mapEstimatedStockInDate(String input) {
		if(StringUtils.isBlank(input))
			return "99999999";
		return input;
	}
	
	public String mapFormatImpressionNumber(String input) {
		if(StringUtils.isBlank(input))
			return null;
		return String.format("%2s", input).replace(' ', '0');
	}

}
