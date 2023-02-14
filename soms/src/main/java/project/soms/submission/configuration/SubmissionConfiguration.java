package project.soms.submission.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.soms.submission.repository.*;
import project.soms.submission.repository.mapper.ApprovalListMapper;
import project.soms.submission.repository.mapper.ApprovalSubmitMapper;
import project.soms.submission.repository.mapper.EmployeeMapper;
import project.soms.submission.service.*;

@Configuration
@RequiredArgsConstructor
public class SubmissionConfiguration {

  private final ApprovalSubmitMapper approvalSubmitMapper;
  private final EmployeeMapper employeeMapper;
  private final ApprovalListMapper approvalListMapper;

  @Bean
  public EmployeeRepository employeeRepository() {
    return new EmployeeRepositoryImpl(employeeMapper);
  }

  @Bean
  public EmployeeService employeeService() {
    return new EmployeeServiceImpl(employeeRepository());
  }

  @Bean
  public ApprovalSubmitRepository approvalSubmitRepository() {
    return new ApprovalSubmitRepositoryImpl(approvalSubmitMapper);
  }

  @Bean
  public ApprovalSubmitService approvalSubmitService() {
    return new ApprovalSubmitServiceImpl(approvalSubmitRepository(), employeeService());
  }

  @Bean
  public ApprovalListRepository approvalListRepository() {
    return new ApprovalListRepositoryImpl(approvalListMapper);
  }

  @Bean
  public ApprovalListService approvalListService() {
    return new ApprovalListServiceImpl(approvalListRepository());
  }
}
