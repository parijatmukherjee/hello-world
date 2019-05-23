package com.twodarray.helloworld.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder
{
	private TemplateEngine templateEngine;
	
	@Autowired
	public MailContentBuilder(TemplateEngine templateEngine)
	{
		this.templateEngine = templateEngine;
	}
	
	public String build(String employeeName, String mailImgSrc, String headLine,
			String introductionContent, String mainContent, String regardsFrom,
			String logoSourceLink)
	{
		Context context = new Context();
		context.setVariable("employeeName", employeeName);
		context.setVariable("introductionContent", introductionContent);
		context.setVariable("mainContent", mainContent);
		context.setVariable("regardsFrom", regardsFrom);
		context.setVariable("logoSrc", logoSourceLink);
		context.setVariable("mailImgSrc",mailImgSrc);
		context.setVariable("headLine", headLine);
		return templateEngine.process("mailTemplate", context);
	}
}
