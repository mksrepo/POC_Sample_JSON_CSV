package com.poc.routes;

import java.sql.SQLException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component("UpdateSeqNo")
public class DatesDBRoutesUpdSeqNo extends RouteBuilder  {

	@Override
	public void configure() throws Exception {
				
		
		onException(SQLException.class)
		.maximumRedeliveries(3)
		.maximumRedeliveryDelay(300000)
		.log(LoggingLevel.ERROR,"Exception occurred when connecting to DB in Route UpdateSeqNo .\n Exception Message: ${exchangeProperty.CamelExceptionCaught}")
		.setBody(constant("Exception occurred when connecting to DB in Route UpdateSeqNo .\\n Exception Message: ${exchangeProperty.CamelExceptionCaught}"))
		.to("file:{{file.backup.location}}/Error?fileName=${date:now:yyyy/MM/dd/}$simple{header.DestinationFileName}_$simple{header.RequestReceivedTime}.json");
		
		onException(RuntimeException.class,SQLException.class)
		.maximumRedeliveries(3)
		.maximumRedeliveryDelay(300000)
		.log(LoggingLevel.ERROR,"Exception occurred when connecting to DB in Route UpdateSeqNo .\\n Exception Message: ${exchangeProperty.CamelExceptionCaught}")
		.setBody(constant("Exception occurred when connecting to DB in Route UpdateSeqNo .\\n Exception Message: ${exchangeProperty.CamelExceptionCaught}"))
		.to("file:{{file.backup.location}}/Error?fileName=${date:now:yyyy/MM/dd/}$simple{header.DestinationFileName}_$simple{header.RequestReceivedTime}.json");	
		
		from("direct:UpdateDatesSequenceNumber")
		.routeId("UpdateSeqNo")
		.to("sql:UPDATE sap_sequencenumber SET CurrentSequenceNumber=CurrentSequenceNumber+1 WHERE InterfaceName=:#InterfaceName AND Region=:#Region?dataSource=dataSource");
		
	}
	
}
