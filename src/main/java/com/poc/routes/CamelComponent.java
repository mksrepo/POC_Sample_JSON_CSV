package com.poc.routes;


import org.apache.camel.routepolicy.quartz.CronScheduledRoutePolicy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelComponent {
	
	@Bean("startPolicyUK")
	public CronScheduledRoutePolicy startPolicyUK(@Value("${activemq.uk.corntab.timezone:#{null}}") String timeZone,
			@Value("${activemq.uk.corntab.starttime}") String routeStartTime,
			@Value("${activemq.uk.corntab.stoptime}") String routeStopTime) {
		CronScheduledRoutePolicy cronScheduledRoutePolicy = new CronScheduledRoutePolicy();
		cronScheduledRoutePolicy.setRouteStartTime(routeStartTime);
		cronScheduledRoutePolicy.setRouteStopTime(routeStopTime);
		if(StringUtils.isNotBlank(timeZone) ) {
			cronScheduledRoutePolicy.setTimeZone(timeZone);
		}
		return cronScheduledRoutePolicy;
	}

	@Bean("startPolicyUS")
	public CronScheduledRoutePolicy startPolicyUS(@Value("${activemq.us.corntab.timezone:#{null}}") String timeZone,
			@Value("${activemq.us.corntab.starttime}") String routeStartTime,
			@Value("${activemq.us.corntab.stoptime}") String routeStopTime) {
		CronScheduledRoutePolicy cronScheduledRoutePolicy = new CronScheduledRoutePolicy();
		cronScheduledRoutePolicy.setRouteStartTime(routeStartTime);
		cronScheduledRoutePolicy.setRouteStopTime(routeStopTime);
		if(StringUtils.isNotBlank(timeZone) ) {
			cronScheduledRoutePolicy.setTimeZone(timeZone);
		}
		return cronScheduledRoutePolicy;
	}

	
}
