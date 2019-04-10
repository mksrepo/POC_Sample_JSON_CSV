package com.poc.strategy.aggregation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("DatesBiblioAggregationStrategy")
public class DatesBiblioAggregationStrategy implements AggregationStrategy {

	protected Logger LOGGER = LoggerFactory.getLogger(getClass());
	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		
		Exchange exchange = oldExchange == null ? newExchange : oldExchange;
		String body = null;
		JSONArray bodyJsonArray = new JSONArray();
		try {
			body = newExchange.getIn().getBody(String.class);
			bodyJsonArray = new JSONArray(body);
		} catch (JSONException e) {
			exchange.setProperty("ErrorOccured", "true");
			LOGGER.error("Error occured while aggregating Biblio Json message : " + body + ", Exception Message :" + e);
		}

		if (oldExchange == null) {
			newExchange.getIn().setBody(bodyJsonArray);
		} else {
			JSONArray list = oldExchange.getIn().getBody(JSONArray.class);
			for (int i = 0; i < bodyJsonArray.length(); i++) {
				list.put(bodyJsonArray.getJSONObject(i));
			}
			oldExchange.getIn().setBody(list);
		}

		return exchange;
	}


}