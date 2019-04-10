package com.oup.integration.sps.acad.dates.test;

import java.io.File;

import java.io.IOException;

import java.nio.file.Files;

import java.text.SimpleDateFormat;

import java.util.Date;



import org.apache.camel.EndpointInject;

import org.apache.camel.ProducerTemplate;

import org.apache.camel.component.mock.MockEndpoint;

import org.apache.camel.test.spring.CamelSpringBootRunner;

import org.apache.camel.test.spring.MockEndpoints;

import org.apache.camel.test.spring.MockEndpointsAndSkip;

import org.junit.After;

import org.junit.Before;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockftpserver.fake.FakeFtpServer;

import org.mockftpserver.fake.UserAccount;

import org.mockftpserver.fake.filesystem.DirectoryEntry;

import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.annotation.DirtiesContext;

import org.springframework.test.annotation.DirtiesContext.ClassMode;

import org.springframework.util.ResourceUtils;



@RunWith(CamelSpringBootRunner.class)

@SpringBootTest

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD) //ensure that the CamelContext, routes, and mock endpoints are reinitialized between test methods.

@MockEndpointsAndSkip("ftp:{{ftp.server}}:{{ftp.port}}{{ftp.drop.location}}?*|file:{{file.backup.location}}?*") //All endpoints are sniffed and recorded in a mock endpoint. The original endpoint is not invoked.

@MockEndpoints // All endpoints are sniffed and recorded in a mock endpoint.

public class TestApp {

	@EndpointInject(uri = "direct:ReceivedDatesAggregatedMessage")

    protected ProducerTemplate queue;

	
    
	@EndpointInject(uri="mock:ftp:{{ftp.server}}:{{ftp.port}}{{ftp.drop.location}}")

	private MockEndpoint dropLocation;

	private FakeFtpServer fakeFtpServer;
	
	@Before

	public void setUp() throws Exception {

		fakeFtpServer = new FakeFtpServer();

		fakeFtpServer.setServerControlPort(9999); // use any free port

		UnixFakeFileSystem fileSystem = new UnixFakeFileSystem();

		fileSystem.add(new DirectoryEntry("/oxedabc_dev/export"));

		fakeFtpServer.setFileSystem(fileSystem);

		UserAccount userAccount = new UserAccount("ftpunix", "12Xdirect", "/");

		fakeFtpServer.addUserAccount(userAccount);

		fakeFtpServer.start();

	}
	
	@Test

	public void csvFile() throws InterruptedException, IOException {

		dropLocation.expectedMessageCount(1);
		
		File outfile = ResourceUtils.getFile("classpath:sampleSAP.dat");

		String outcontent = new String(Files.readAllBytes(outfile.toPath()));

		outcontent = outcontent.replace("${date}", new SimpleDateFormat("ddMMyyyy").format(new Date()) );

		outcontent = outcontent.replace("${time}", new SimpleDateFormat("HHmmss").format(new Date()) );

		dropLocation.expectedBodiesReceived(outcontent);
	
		File infile = ResourceUtils.getFile("classpath:sampleBiblio.json");

		String incontent = new String(Files.readAllBytes(infile.toPath()));

		
		queue.sendBody(incontent);

		dropLocation.assertIsSatisfied();
		
	}

	

	@After

	public void tearDown() throws Exception {

		fakeFtpServer.stop();

	}

}