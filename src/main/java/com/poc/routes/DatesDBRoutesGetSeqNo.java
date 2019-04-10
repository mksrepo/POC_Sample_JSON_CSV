package com.poc.routes;

import java.sql.SQLException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.poc.expression.SequenceNoExtractor;

@Component("getSeqNumber")
public class DatesDBRoutesGetSeqNo extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		onException(SQLException.class)
		.maximumRedeliveries(3)
		.maximumRedeliveryDelay(300000)
		.log(LoggingLevel.ERROR,"Exception occurred when connecting to DB in Route getSeqNumber .\n Exception Message: ${exchangeProperty.CamelExceptionCaught}")
		.setBody(constant("Exception occurred when connecting to DB in Route getSeqNumber .\\n Exception Message: ${exchangeProperty.CamelExceptionCaught}"))
		.to("file:{{file.backup.location}}/Error?fileName=${date:now:yyyy/MM/dd/}$simple{header.DestinationFileName}_$simple{header.RequestReceivedTime}.json");
		
		onException(RuntimeException.class)
		.maximumRedeliveries(3)
		.maximumRedeliveryDelay(300000)
		.log(LoggingLevel.ERROR,"Exception occurred when connecting to DB in Route getSeqNumber .\n Exception Message: ${exchangeProperty.CamelExceptionCaught}")
		.setBody(constant("Exception occurred when connecting to DB in Route getSeqNumber .\\n Exception Message: ${exchangeProperty.CamelExceptionCaught}"))
		.to("file:{{file.backup.location}}/Error?fileName=${date:now:yyyy/MM/dd/}$simple{header.DestinationFileName}_$simple{header.RequestReceivedTime}.json");		
		
		from("direct:GetDatesLatestSequenceNumber")
		.routeId("getSeqNumber")
		.to("sql:SELECT LPAD(CurrentSequenceNumber,4,0) as CurrentSequenceNumber FROM sap_sequencenumber WHERE InterfaceName=:#InterfaceName AND Region=:#Region?dataSource=dataSource")
		.setHeader("SequenceNumber", new SequenceNoExtractor());
		
		
	}
}
