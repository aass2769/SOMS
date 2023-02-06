package project.soms.submission.dto;

import lombok.Data;

@Data
public class IncidentDto {

  private Long incidentNo;
  private String incidentSection;
  private String incidentContent;

  public IncidentDto(String incidentSection, String incidentContent) {
    this.incidentSection = incidentSection;
    this.incidentContent = incidentContent;
  }
}
