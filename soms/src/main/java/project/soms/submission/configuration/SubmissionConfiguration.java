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

  //결재 상신을 위한 쿼리가 담긴 맵퍼
  private final ApprovalSubmitMapper approvalSubmitMapper;
  //기안자 결재자 생성을 위한 쿼리가 담긴 맵퍼
  private final EmployeeMapper employeeMapper;
  //결재 내역과 상세보기를 위한 쿼리가 담긴 맵퍼
  private final ApprovalListMapper approvalListMapper;

  //기안, 결재자 정보를 가져오는 repository에 impl클래스와 mapper클래스를 주입하여 빈 설정
  @Bean
  public EmployeeRepository employeeRepository() {
    return new EmployeeRepositoryImpl(employeeMapper);
  }
  //기안, 결재자 정보를 가져오는 service에 impl클래스와 repository클래스를 주입하여 빈 설정
  @Bean
  public EmployeeService employeeService() {
    return new EmployeeServiceImpl(employeeRepository());
  }

  //결재 정보 인서트 repository에 impl클래스와 mapper클래스를 주입하여 빈 설정
  @Bean
  public ApprovalSubmitRepository approvalSubmitRepository() {
    return new ApprovalSubmitRepositoryImpl(approvalSubmitMapper);
  }
  //결재 정보 인서트 service에 impl클래스와 repository클래스를 주입하여 빈 설정
  @Bean
  public ApprovalSubmitService approvalSubmitService() {
    return new ApprovalSubmitServiceImpl(approvalSubmitRepository(), employeeService());
  }

  //결재 내역 조회 repository에 impl클래스와 mapper클래스를 주입하여 빈 설정
  @Bean
  public ApprovalListRepository approvalListRepository() {
    return new ApprovalListRepositoryImpl(approvalListMapper, employeeMapper);
  }
  //결재 내역 조회 service에 impl클래스와 mapper클래스를 주입하여 빈 설정
  @Bean
  public ApprovalListService approvalListService() {
    return new ApprovalListServiceImpl(approvalListRepository());
  }
}
