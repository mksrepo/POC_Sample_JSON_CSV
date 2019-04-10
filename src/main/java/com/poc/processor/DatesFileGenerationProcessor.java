package com.poc.processor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component("DatesFileGenerationProcessor")
public class DatesFileGenerationProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		String sequenceNumber=exchange.getIn().getHeader("SequenceNumber").toString();
		String interfaceName=exchange.getIn().getHeader("InterfaceName").toString();
		String dateAsString = new SimpleDateFormat("ddMMyyyy").format(new Date());
		String timeAsString=new SimpleDateFormat("HHmmss").format(new Date());
		String inputBody=exchange.getIn().getBody(String.class);
		
		String fileHeader=interfaceName+String.format("%04d",Integer.parseInt(sequenceNumber))+" "+dateAsString+" "+timeAsString+",,,,"+"\n";
		
		String fileFooter= "ZZRC="+String.format("%09d", inputBody.split("[\n]").length+2)+",,,,"+"\n";
		
		String dataFile= fileHeader  + inputBody + fileFooter ;		
		exchange.getOut().setHeaders(exchange.getIn().getHeaders());		

		exchange.getOut().setHeader("FileHeader", fileHeader);
		exchange.getOut().setHeader("FileFooter", fileFooter);
		exchange.getOut().setBody(exchange.getIn().getBody());
		
	}

}

