package project.soms.submission.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ProposerDto;

import java.util.List;

@Mapper
public interface EmployeeMapper {

  ProposerDto proposer(Long employeeNo);

  List<ApproverDto> expenseApprover(@Param("proposerDto") ProposerDto proposerDto);
  List<ApproverDto> overtimeApprover(@Param("proposerDto") ProposerDto proposerDto);
  List<ApproverDto> annualLeaveApprover(@Param("proposerDto") ProposerDto proposerDto);
  List<ApproverDto> businessTripApprover(@Param("proposerDto") ProposerDto proposerDto);
  List<ApproverDto> incidentApprover(@Param("proposerDto") ProposerDto proposerDto);

}
