package com.poc.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.poc.strategy.aggregation.DatesBiblioAggregationStrategy;

@Component("datesSchedulerRoute")
public class DatesSchedulerRoute extends RouteBuilder{

	@Autowired
	DatesBiblioAggregationStrategy DatesBiblioAggregationStrategy;
	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		from("activemq:{{activemq.uk.queuename}}")
		.routeId("datesSchedulerRoute")
		.autoStartup(false)
		.routePolicyRef("startPolicyUK")
		.log(LoggingLevel.INFO, "com.poc", "Adding AC15 Dates Message --- ${body}")
		.setHeader("RequestReceivedTime", simple("${date:now:HHmmssSSS}"))
		.setHeader("InterfaceName", simple("ac15"))
		.setHeader("Region", simple("UK"))
		.setHeader("InterfaceFullName", simple("ACAD AC15 Dates"))
		.aggregate(DatesBiblioAggregationStrategy)
		.constant(true)
		.completionTimeout(30000)
		.choice()
			.when(simple("${property.ErrorOccured} == 'true'"))
				.log(LoggingLevel.ERROR, "com.poc", "Exception occurred while aggregating the Biblio json message in AC15 interface.The application processing has been terminated")
				.to("controlbus:route?async=true&routeId=datesSchedulerRoute&action=stop")
				.convertBodyTo(String.class)
				.to("file:{{file.backup.location}}/Error?fileName=${date:now:yyyy/MM/dd/}$simple{header.InterfaceName}_$simple{header.RequestReceivedTime}.json")
		    .otherwise()				
				.to("direct:ReceivedDatesAggregatedMessage")
		.end();	
	}	
}
