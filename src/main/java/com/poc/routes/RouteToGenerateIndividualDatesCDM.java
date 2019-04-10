package com.poc.routes;

import java.sql.SQLException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.google.gson.JsonSyntaxException;

@Component
public class RouteToGenerateIndividualDatesCDM extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		onException(JsonSyntaxException.class,IllegalStateException.class,NullPointerException.class)
		.maximumRedeliveries(0)
		.log(LoggingLevel.ERROR,"Exceptions due to data issues in the PO file processing in Route RouteToGenerateIndividualDatesCDM .\\n Exception Message: ${exchangeProperty.CamelExceptionCaught}")
		.setBody(constant("Exceptions due to data issues in the PO file processing in Route RouteToGenerateIndividualDatesCDM .\\n Exception Message: ${exchangeProperty.CamelExceptionCaught}"))
		.to("file:{{file.backup.location}}/Error?fileName=${date:now:yyyy/MM/dd/}$simple{header.DestinationFileName}_$simple{header.RequestReceivedTime}.json");
		
		onException(Exception.class)
		.maximumRedeliveries(0)
		.log(LoggingLevel.ERROR,"Exception occurred while processing PO messages in Route RouteToGenerateIndividualDatesCDM Exception Message: ${exchangeProperty.CamelExceptionCaught}")
		.setBody(constant("Exception occurred while processing PO messages in Route RouteToGenerateIndividualDatesCDM Exception Message: ${exchangeProperty.CamelExceptionCaught}"))
		.to("file:{{file.backup.location}}/Error?fileName=${date:now:yyyy/MM/dd/}$simple{header.DestinationFileName}_$simple{header.RequestReceivedTime}.json");
		
		from("direct:transform_dates_biblio_to_cdm")
		.to("dozer:Dates_Biblio_To_CDM_Transformation?sourceModel=com.poc.biblio.pojo.DatesBiblioUK&targetModel=com.poc.cdm.pojo.DatesCDMUK&mappingFile=Transformations/Dates_Biblio_To_CDM_UK_Transformation.xml");
		
	}

}
