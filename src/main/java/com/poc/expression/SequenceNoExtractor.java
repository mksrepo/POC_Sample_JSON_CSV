package com.poc.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;

public class SequenceNoExtractor implements Expression {

	@Override
	public <T> T evaluate(Exchange exchange, Class<T> type) {
		// TODO Auto-generated method stub
		List<Map> row =(ArrayList<Map>) exchange.getIn().getBody();        
     	return (T) row.get(0).get("CurrentSequenceNumber");
	}

}
