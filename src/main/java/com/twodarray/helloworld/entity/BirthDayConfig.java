package com.twodarray.helloworld.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "BIRTHDAY_MAIL_CONFIG")
public class BirthDayConfig
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AUTO_ID")
	private Long birthDayConfigId;
	
	@Column(name = "CONTENT_HEAD_LINE")
	private String mailHeadLine;
	
	@Column(name = "MAIL_INTO_CONTENT", columnDefinition = "LONGTEXT")
	private String mailIntroContent;
	
	@Column(name = "MAIL_MAIN_CONTENT", columnDefinition = "LONGTEXT")
	private String mailMainContent;
	
	@Column(name = "REGARDS_FROM")
	private String regardsFrom;
	
	@Column(name = "MAIL_IMAGE_LINK")
	private String mailImageLink;
	
	@Column(name = "FROM_EMAIL")
	private String fromEmail;
	
	@Column(name = "FROM_NAME")
	private String fromName;
	
	@Column(name = "SUBJECT_LINE")
	private String mailSubject;
	
	@Column(name = "CC_RECIPIENTS", columnDefinition = "LONGTEXT")
	private String ccRecipients;
	
	@Column(name = "COMPANY_LOGO_LINK")
	private String companyLogoLink;
}
