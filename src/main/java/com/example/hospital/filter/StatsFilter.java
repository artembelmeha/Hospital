package com.example.hospital.filter;

import static java.lang.System.currentTimeMillis;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


@Component
@WebFilter("/*")
public class StatsFilter implements Filter {
	private static final Logger LOGGER = getLogger(StatsFilter.class);

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		long time = currentTimeMillis();

		try {
			chain.doFilter(req, resp);
		} finally {
			time = currentTimeMillis() - time;
			String requestURI = ((HttpServletRequest) req).getRequestURI();
			int statusCode = ((HttpServletResponse) resp).getStatus();

			LOGGER.trace("{}: {}, {} ms ", requestURI, HttpStatus.valueOf(statusCode), time);
		}
	}
}
