package com.twodarray.helloworld.manager;

import com.twodarray.helloworld.entity.AnniversaryConfig;
import com.twodarray.helloworld.entity.Employee;
import com.twodarray.helloworld.repository.AnniversaryConfigRepo;
import com.twodarray.helloworld.service.MailContentBuilder;
import com.twodarray.helloworld.utility.ValueResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.twodarray.helloworld.repository.EmployeeRepository;

import javax.annotation.PostConstruct;
import javax.mail.internet.InternetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class AnniversaryManager
{
	Logger logger = LoggerFactory.getLogger(AnniversaryManager.class);
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailContentBuilder mailContentBuilder;
	
	@Autowired
	private AnniversaryConfigRepo anniversaryConfigRepo;
	
	@Autowired
	private ValueResolver valueResolver;
	
	private List<AnniversaryConfig> config = new ArrayList<>();
	
	private int configPicker=0;
	
	private static String dateFormat="dd-MM";
	
	@Scheduled(cron = "0 0 0 * * ?")
	//@PostConstruct
	private void sendAnniversaryMail()
	{
		List<Employee> employeeList = employeeRepository.findAll();
		List<Employee> anniversaryToday = new ArrayList<>();
		
		config = anniversaryConfigRepo.findAll();
		
		for(Employee employee : employeeList)
		{
			if(isItAnniversary(employee))
			{
				anniversaryToday.add(employee);
			}
		}
		
		try
		{
			String[] ccList = config.get(0).getCcRecipients().split(",");
			List<InternetAddress> ccAddresses = new ArrayList<>();
			
			for(String cc : ccList)
			{
				InternetAddress address = new InternetAddress();
				address.setAddress(cc);
				ccAddresses.add(address);
			}
			
			if(anniversaryToday.size() > 0)
			{
				for (Employee employee : anniversaryToday)
				{
					chooseConfig();
					sendAnniversaryMail(employee.getEmail().trim(),config.get(configPicker).getFromEmail().trim(),
							ccAddresses.toArray(new InternetAddress[]{}), employee);
				}
			}
		}
		catch (Exception e)
		{
			logger.error("Error sending mails - "+e.getMessage());
		}
	}
	
	private boolean isItAnniversary(Employee employee)
	{
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		Date today = new Date();
		String todayFormatted = formatter.format(today);
		Date anniversary = employee.getAnniversaryDate();
		String formattedAnniversary = formatter.format(anniversary);
		return todayFormatted.equals(formattedAnniversary);
	}
	
	
	private void sendAnniversaryMail(String toEmail, String fromEmail, InternetAddress[] cc, Employee employee)
	{
		try
		{
			MimeMessagePreparator messagePreparator = mimeMessage -> {
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
				messageHelper.setFrom(fromEmail, config.get(configPicker).getFromName());
				messageHelper.setTo(toEmail);
				messageHelper.setSubject(valueResolver.buildString(employee,config.get(configPicker).getMailSubject()));
				messageHelper.setCc(cc);
				String content = mailContentBuilder.build(
						employee.getName(),
						config.get(configPicker).getMailImageLink(),
						valueResolver.buildString(employee,config.get(configPicker).getMailHeadLine()),
						valueResolver.buildString(employee,config.get(configPicker).getMailIntroContent()),
						valueResolver.buildString(employee,config.get(configPicker).getMailMainContent()),
						config.get(configPicker).getRegardsFrom(),
						config.get(configPicker).getCompanyLogoLink());
				messageHelper.setText(content,true);
				
				logger.info("MAIL sent to :"+toEmail+" with content "+content);
			};
			mailSender.send(messagePreparator);
		}
		catch (Exception ex)
		{
			logger.error("Anniversary Mail could not be sent for "+toEmail);
		}
	}
	
	private void chooseConfig()
	{
		Random random = new Random();
		int randomInt = random.nextInt(10 + config.size());
		configPicker = randomInt % config.size();
	}
}
