package com.poc.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.google.gson.JsonSyntaxException;

@Component
public class RouteToGenerateIndividualDatesSAP extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		onException(JsonSyntaxException.class,IllegalStateException.class,NullPointerException.class)
		.maximumRedeliveries(0)
		.log(LoggingLevel.ERROR,"Exceptions due to data issues in the PO file processing in Route id_RouteToGenerateIndividualDatesSAP .\\n Exception Message: ${exchangeProperty.CamelExceptionCaught}")
		.setBody(constant("Exceptions due to data issues in the PO file processing in Route id_RouteToGenerateIndividualDatesSAP .\\n Exception Message: ${exchangeProperty.CamelExceptionCaught"))
		.to("file:{{file.backup.location}}/Error?fileName=${date:now:yyyy/MM/dd/}$simple{header.DestinationFileName}_$simple{header.RequestReceivedTime}.json");
		
		onException(Exception.class)
		.maximumRedeliveries(0)		
		.log(LoggingLevel.ERROR,"Exception occurred while processing PO messages in Route id_RouteToGenerateIndividualDatesSAP Exception Message: ${exchangeProperty.CamelExceptionCaught}")
		.setBody(constant("Exception occurred while processing PO messages in Route id_RouteToGenerateIndividualDatesSAP Exception Message: ${exchangeProperty.CamelExceptionCaught}"))
		.to("file:{{file.backup.location}}/Error?fileName=${date:now:yyyy/MM/dd/}$simple{header.DestinationFileName}_$simple{header.RequestReceivedTime}.json");
		
		from("direct:transform_dates_cdm_to_sap")
		.to("dozer:Dates_CDM_To_SAP_Transformation?sourceModel=com.poc.cdm.pojo.DatesCDMUK&targetModel=com.poc.sap.pojo.DatesSAPUK&mappingFile=Transformations/Dates_CDM_To_SAP_UK_Transformation.xml");
		
	}

}
