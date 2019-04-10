package com.poc.routes;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFileOperationFailedException;
import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.GroupedBodyAggregationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonSyntaxException;
import com.poc.biblio.pojo.DatesBiblioUK;
import com.poc.biblio.pojo.DatesBiblioUKGroup;
import com.poc.sap.pojo.DatesSAPUK;

@Component
public class MainRouteForACADDates extends RouteBuilder{

		
	@Override
	public void configure() throws Exception {
				
		BindyCsvDataFormat bindy = new BindyCsvDataFormat(DatesSAPUK.class);
		
		onException(JsonSyntaxException.class,IllegalStateException.class,NullPointerException.class)
		.maximumRedeliveries(0)
		.log(LoggingLevel.ERROR,"Exceptions due to data issues in the ${header.InterfaceFullName} message processing in Route MainRouteForACADDates .\\n Exception Message: ${exchangeProperty.CamelExceptionCaught}")
		.setBody(simple("Exceptions due to data issues in the ${header.InterfaceFullName} message processing in Route MainRouteForACADDates .\\n Exception Message: ${exchangeProperty.CamelExceptionCaught}"))
		.to("file:{{file.backup.location}}/Error?fileName=${date:now:yyyy/MM/dd/}$simple{header.InterfaceName}_$simple{header.RequestReceivedTime}.json&fileExist=Append");

		onException(IOException.class,GenericFileOperationFailedException.class,FileSystemException.class,FileAlreadyExistsException.class)
		.maximumRedeliveries(3)
		.maximumRedeliveryDelay(300000)
		.log(LoggingLevel.ERROR,"Exception due to IO operations in ${header.InterfaceFullName} message processing in Route MainRouteForACADDates .\\n Exception Message: ${exchangeProperty.CamelExceptionCaught}")
		.setBody(simple("Exception due to IO operations in ${header.InterfaceFullName} message processing in Route MainRouteForACADDates .\\n Exception Message: ${exchangeProperty.CamelExceptionCaught}"))
		.to("file:{{file.backup.location}}/Error?fileName=${date:now:yyyy/MM/dd/}$simple{header.InterfaceName}_$simple{header.RequestReceivedTime}.json&fileExist=Append");
		
		onException(Exception.class)
		.maximumRedeliveries(0)
		.log(LoggingLevel.ERROR,"Exception occurred in ${header.InterfaceFullName} message processing in Route MainRouteForACADDates.\\n Exception Message: ${exchangeProperty.CamelExceptionCaught}")
		.setBody(simple("Exception occurred in ${header.InterfaceFullName} message processing in Route MainRouteForACADDates.\\n Exception Message: ${exchangeProperty.CamelExceptionCaught}"))
		.to("file:{{file.backup.location}}/Error?fileName=${date:now:yyyy/MM/dd/}$simple{header.InterfaceName}_$simple{header.RequestReceivedTime}.json&fileExist=Append");
		
		from("direct:ReceivedDatesAggregatedMessage")		
		.log(LoggingLevel.INFO, "com.poc", "Received Aggregated ${header.InterfaceFullName} Message ${body}")
		.setProperty("InputBody", simple("${body}"))
		.convertBodyTo(String.class)
		.wireTap("file:{{file.backup.location}}/1.0 ReceivedFromBiblio?fileName=${date:now:yyyy/MM/dd/}$simple{header.InterfaceName}_$simple{header.RequestReceivedTime}.json")
		.to("controlbus:route?async=true&routeId=datesSchedulerRoute&action=stop")
		.to("direct:GetDatesLatestSequenceNumber")
		.log(LoggingLevel.INFO, "com.poc", "Current ${header.InterfaceFullName} Sequence Number being processed : ${header.SequenceNumber}")
		.setHeader("DestinationFileName",simple("${header.InterfaceName}${header.SequenceNumber}"))
		.setBody(simple("${property.InputBody}"))
		.convertBodyTo(String.class)
		.unmarshal().json(JsonLibrary.Jackson,DatesBiblioUKGroup.class)
		.log(LoggingLevel.INFO, "com.poc", "${header.InterfaceFullName} Split Started for file ${header.DestinationFileName}")		
		.split(body(), new GroupedBodyAggregationStrategy() )
		       .streaming()
		       .log(LoggingLevel.INFO, "com.poc", "Processing Split AC15 message : ${body}")
		       .to("direct:transform_dates_biblio_to_cdm")
		       .log(LoggingLevel.INFO, "com.poc", "AC15 Message body for after Biblio to CDM transformation : ${body}")
               .to("direct:transform_dates_cdm_to_sap")
               .log(LoggingLevel.INFO, "com.poc", "AC15 Message body for after CDM to SAP transformation : ${body}")
         .end()
         .marshal(bindy)
         .log(LoggingLevel.INFO, "com.poc", "Generated CSV message for ${header.InterfaceFullName} interface : ${body}")         
         .process("DatesFileGenerationProcessor")
         .setBody(simple("${header.fileHeader}${body}${header.fileFooter}"))
         .wireTap("file:{{file.backup.location}}/2.0 SentToSAP?fileName=${date:now:yyyy/MM/dd/}$simple{header.DestinationFileName}_$simple{header.RequestReceivedTime}.dat")
         .toD("ftp:{{ftp.uk.server}}:{{ftp.uk.port}}{{ftp.uk.drop.location}}?FileName=$simple{header.DestinationFileName}.dat&doneFileName=$simple{header.DestinationFileName}.go&password={{ftp.uk.password}}&username={{ftp.uk.username}}&passiveMode=true")
		 .log(LoggingLevel.INFO, "com.poc", "${header.InterfaceFullName} file ${header.DestinationFileName} generated ")
		 .to("direct:UpdateDatesSequenceNumber")
		 .log(LoggingLevel.INFO, "com.poc", "${header.InterfaceFullName} Sequence number updated");	
		
	}

}
