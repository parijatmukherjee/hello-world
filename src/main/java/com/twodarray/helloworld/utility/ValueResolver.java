package com.twodarray.helloworld.utility;

import com.twodarray.helloworld.entity.Employee;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ValueResolver
{
	private static final Logger LOG = LoggerFactory.getLogger(ValueResolver.class);
	
	private Configuration configuration;
	
	private static String dateFormat = "dd-MM-yyyy";
	
	@PostConstruct
	private void init()
	{
		configuration = new Configuration();
		configuration.setObjectWrapper(new DefaultObjectWrapper());
	}
	
	public String buildString(Employee employee, String text)
	{
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		
		Map<String,Object> map=new HashMap<>();
		map.put("name", employee.getName());
		map.put("email", employee.getEmail());
		map.put("birthDate", formatter.format(employee.getAnniversaryDate()));
		map.put("anniversaryDate", formatter.format(employee.getAnniversaryDate()));
		map.put("age", getDiff(employee.getBirthday())+"");
		map.put("workYears", getDiff(employee.getAnniversaryDate())+"");
		
		String receiptText = text;
		try
		{
			StringReader reader = new StringReader(text);
			Template receiptTemplate = new Template("text", reader, configuration);
			StringWriter out = new StringWriter();
			receiptTemplate.process(map, out);
			receiptText = out.toString();
		}
		catch (Exception e)
		{
			LOG.warn("Unable to render message template:" + e.getMessage());
		}
		return receiptText;
	}
	
	private int getDiff(Date date)
	{
		DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		int d1 = Integer.parseInt(formatter.format(date));
		int d2 = Integer.parseInt(formatter.format(new Date()));
		int age = (d2 - d1) / 10000;
		return age;
	}
}
