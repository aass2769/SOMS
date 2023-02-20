package project.soms.submission.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ProposerDto;

import java.util.List;

@Mapper
//기안자 정보 및 결재자 리스트 담은 mapper인터페이스
public interface EmployeeMapper {

  //기안자의 정보 조회
  ProposerDto proposer(Long employeeNo);

  /**
   * 해당 기안자에게 맞는 결재자 리스트 자동 생성
   * 지출결의서 결재자 리스트는 기본으로 최상위 관리자까지
   * 2단계씩 올라가도록 설정함
   */
  List<ApproverDto> expenseApprover(@Param("proposerDto") ProposerDto proposerDto);
  /**
   * 해당 기안자에게 맞는 결재자 리스트 자동 생성
   * 연장근로신청서 결재자 리스트는 기본으로 2명의 관리자만
   * 2단계씩 올라가도록 설정함
   */
  List<ApproverDto> overtimeApprover(@Param("proposerDto") ProposerDto proposerDto);
  /**
   * 해당 기안자에게 맞는 결재자 리스트 자동 생성
   * 연차신청서 결재자 리스트는 기본으로 2명의 관리자만
   * 2단계씩 올라가도록 설정함
   */
  List<ApproverDto> annualLeaveApprover(@Param("proposerDto") ProposerDto proposerDto);
  /**
   * 해당 기안자에게 맞는 결재자 리스트 자동 생성
   * 출장신청서 결재자 리스트는 기본으로 4명의 관리자만
   * 2단계씩 올라가도록 설정함
   */
  List<ApproverDto> businessTripApprover(@Param("proposerDto") ProposerDto proposerDto);
  /**
   * 해당 기안자에게 맞는 결재자 리스트 자동 생성
   * 시말서 결재자 리스트는 기본으로 최상위 관리자까지
   * 2단계씩 올라가도록 설정함
   */
  List<ApproverDto> incidentApprover(@Param("proposerDto") ProposerDto proposerDto);

}
