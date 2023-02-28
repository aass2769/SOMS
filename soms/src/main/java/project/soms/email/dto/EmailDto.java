package project.soms.email.dto;

import lombok.Data;

import java.util.List;

@Data
public class EmailDto {

  private Long emailNo;
  private String emailSubject;
  private String emailFrom;
  private String emailContent;
  private String emailSentDate;
  private boolean emailSeen;
  private boolean emailHasAttachment;
  private List<String> emailAttachmentFileName;
  private List<String> emailAttachment;

  public EmailDto(Long emailNo, String emailSubject, String emailFrom, String emailContent, String emailSentDate, boolean emailSeen, boolean emailHasAttachment, List<String> emailAttachmentFileName, List<String> emailAttachment) {
    this.emailNo = emailNo;
    this.emailSubject = emailSubject;
    this.emailFrom = emailFrom;
    this.emailContent = emailContent;
    this.emailSentDate = emailSentDate;
    this.emailSeen = emailSeen;
    this.emailHasAttachment = emailHasAttachment;
    this.emailAttachmentFileName = emailAttachmentFileName;
    this.emailAttachment = emailAttachment;
  }

  public EmailDto() {
  }
}
