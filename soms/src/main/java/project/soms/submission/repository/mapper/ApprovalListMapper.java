package project.soms.submission.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import project.soms.submission.dto.SubmissionDto;
import project.soms.submission.dto.form.ExpenseApprovalDetailForm;
import project.soms.submission.dto.form.OvertimeApprovalDetailForm;

import java.util.List;

@Mapper
public interface ApprovalListMapper {

  List<SubmissionDto> approvalList(@Param("employeeNo") long employeeNo,
                                        @Param("submissionSection") String subnissionSection,
                                        @Param("submissionDatetime") String submissionDatetime);

  void approvalOpen(@Param("submissionNo") Long submissionNo);

  void rejectOpen(@Param("submissionPri") String submissionPri, @Param("proposerEmployeeNo") Long approverEmployeeNo);

  void comProposerOpen(@Param("submissionNo") Long submissionNo);

  List<ExpenseApprovalDetailForm> expenseApprovalDetail(@Param("submissionNo") Long submissionNo,
                                                            @Param("submissionPri") String submissionPri);

  List<OvertimeApprovalDetailForm> overtimeApprovalDetail(@Param("submissionNo") Long submissionNo,
                                                         @Param("submissionPri") String submissionPri);

  void approve(@Param("submissionNo") Long submissionNo);

  void nextApproverShowable(@Param("submissionPri") String submissionPri, @Param("proposerEmployeeNo") Long proposerEmployeeNo);

  void rejectApproval(@Param("submissionNo") Long submissionNo, @Param("submissionComent") String submissionComent);

  void deleteApproval(@Param("submissionNo") Long submissionNo);
}