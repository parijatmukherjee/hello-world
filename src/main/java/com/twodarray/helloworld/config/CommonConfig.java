package com.twodarray.helloworld.config;

import freemarker.template.DefaultObjectWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class CommonConfig
{
	@Value("${hello-world.threadpool.size:25}")
	private String threadPoolSize;
	
	@Bean(name = "mailSender")
	public JavaMailSender getJavaMailSender()
	{
		return new JavaMailSenderImpl();
	}
	
	/**
	 * Gets executor service.
	 *
	 * @return the executor service
	 */
	@Bean
	public ExecutorService getExecutorService()
	{
		int threadNumbers = Integer.parseInt(threadPoolSize);
		return Executors.newFixedThreadPool(threadNumbers);
	}
}
