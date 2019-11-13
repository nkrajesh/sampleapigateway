package com.raj;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

//import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Service;

//@Service
public class TestZuulFilter extends ZuulFilter {
	private static Logger log = LoggerFactory.getLogger(TestZuulFilter.class);
	@Override	
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();		
	    HttpServletRequest request = ctx.getRequest();
	    String s = request.getRequestURI();
	    String customParam = request.getParameter("invokemetadata");	    
        String serviceId = (String) ctx.get(FilterConstants.SERVICE_ID_KEY);
	    log.info("serviceId.."+serviceId + ".. custommetadata value.. "+customParam+"......request uri ..." + s);
	    //we can create a db and fetch the version to set specfic to services
	    if ("true".equalsIgnoreCase(customParam)) {
	    	//call some service and set RibbonFilterContextHolder
	    	log.info("setting metadata in ribbon");
//	    	RibbonFilterContextHolder.getCurrentContext().add("versions", "v1,v2");
	    } else {
	    	//nothing
	    }
	    log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return FilterConstants.PRE_DECORATION_FILTER_ORDER + 3;		
	}

}
