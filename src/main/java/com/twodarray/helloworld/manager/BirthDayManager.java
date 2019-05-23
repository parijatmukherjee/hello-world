package com.twodarray.helloworld.manager;

import com.twodarray.helloworld.entity.BirthDayConfig;
import com.twodarray.helloworld.entity.Employee;
import com.twodarray.helloworld.repository.BirthDayConfigRepo;
import com.twodarray.helloworld.service.MailContentBuilder;
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

@Component
public class BirthDayManager
{
	Logger logger = LoggerFactory.getLogger(BirthDayManager.class);
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private BirthDayConfigRepo birthDayConfigRepo;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailContentBuilder contentBuilder;
	
	private List<BirthDayConfig> config = new ArrayList<>();
	
	private static String dateFormat="dd-MM";
	
	@Scheduled(cron = "0 0 0 * * ?")
	//@PostConstruct
	private void sendBirthdayMail()
	{
		List<Employee> employeeList = employeeRepository.findAll();
		List<Employee> birthDayToday = new ArrayList<>();
		
		config = birthDayConfigRepo.findAll();
		
		for(Employee employee : employeeList)
		{
			if(isItBirthday(employee))
			{
				birthDayToday.add(employee);
			}
		}
		
		try
		{
			String[] ccList = config.get(0).getCcRecipients().split(",");
			
			List<InternetAddress> ccAddresses = new ArrayList<>();
			
			for (String cc : ccList)
			{
				InternetAddress address = new InternetAddress();
				address.setAddress(cc);
				ccAddresses.add(address);
			}
			
			if (birthDayToday.size() > 0)
			{
				for (Employee employee : birthDayToday)
				{
					sendBirthDayMail(employee.getEmail().trim(),
							config.get(0).getFromEmail().trim(),
							ccAddresses.toArray(new InternetAddress[] {}),
							employee.getName());
				}
			}
		}
		catch (Exception e)
		{
			logger.error("Error sending mails. - "+e.getMessage());
		}
	}
	
	private boolean isItBirthday(Employee employee)
	{
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		Date today = new Date();
		Date birthDay = employee.getBirthday();
		return (formatter.format(today).equals(formatter.format(birthDay)));
	}
	
	private void sendBirthDayMail(String toEmail, String fromEmail, InternetAddress[] cc, String employee)
	{
		try
		{
			MimeMessagePreparator messagePreparator = mimeMessage ->
			{
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
				messageHelper.setFrom(fromEmail, config.get(0).getFromName());
				messageHelper.setTo(toEmail);
				messageHelper.setSubject(config.get(0).getMailSubject()+" "+employee);
				messageHelper.setCc(cc);
				String content = contentBuilder.build(
						employee,
						config.get(0).getMailImageLink(),
						config.get(0).getMailHeadLine(),
						config.get(0).getMailIntroContent(),
						config.get(0).getMailMainContent(),
						config.get(0).getRegardsFrom(),
						config.get(0).getCompanyLogoLink());
				messageHelper.setText(content,true);
				
				logger.info("MAIL sent to :"+toEmail+" with content "+content);
			};
			mailSender.send(messagePreparator);
		}
		catch (Exception ex)
		{
			logger.error("Birthday Mail could not be sent for "+toEmail);
		}
	}
}
