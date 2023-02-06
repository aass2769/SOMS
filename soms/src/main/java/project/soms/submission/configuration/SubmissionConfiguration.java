package project.soms.submission.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.soms.submission.repository.ApprovalSubmitRepository;
import project.soms.submission.repository.ApprovalSubmitRepositoryImpl;
import project.soms.submission.repository.EmployeeRepository;
import project.soms.submission.repository.EmployeeRepositoryImpl;
import project.soms.submission.repository.mapper.ApprovalSubmitMapper;
import project.soms.submission.repository.mapper.EmployeeMapper;
import project.soms.submission.service.ApprovalSubmitService;
import project.soms.submission.service.ApprovalSubmitServiceImpl;
import project.soms.submission.service.EmployeeService;
import project.soms.submission.service.EmployeeServiceImpl;

@Configuration
@RequiredArgsConstructor
public class SubmissionConfiguration {

  private final ApprovalSubmitMapper approvalSubmitMapper;
  private final EmployeeMapper employeeMapper;

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
}
