package project.soms.submission.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ProposerDto;
import project.soms.submission.dto.SubmissionDto;
import project.soms.submission.dto.form.ExpenseApprovalDetailForm;
import project.soms.submission.repository.mapper.ApprovalListMapper;
import project.soms.submission.repository.mapper.EmployeeMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ApprovalListRepositoryImpl implements ApprovalListRepository{

  private final ApprovalListMapper approvalListMapper;
  private final EmployeeMapper employeeMapper;
  SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


  //결재 중인 내역을 가져오는 메서드
  @Override
  public List<SubmissionDto> underApprovalList(Long employeeNo, String submissionSection, String submissionDatetime) {

    //해당 사원의 결재 내역 전체를 가져오고, 거기서 결재중 내역만 담을 새로운 배열을 생성
    List<SubmissionDto> approvalList = approvalListMapper.underApprovalList(employeeNo, submissionSection, submissionDatetime);
    List<SubmissionDto> underApprovalList = new ArrayList<>();

    for(SubmissionDto approval : approvalList) {
      //서식번호가 널이 아니것, status가 0이면 반려건, 1이면 결재 중건, 2면 결재 완료건
      if (approval.getSubmissionNo() != null && approval.getSubmissionStatus().equals("1") && approval.getSubmissionShowable().equals("가능")) {
        //datetime으로 저장된 값을 date(yyyy-MM-dd)로 변환
        Date date = dateParse(approval.getSubmissionDatetime());
        String submissionDate = dateFormat.format(date);
        approval.setSubmissionDatetime(submissionDate);
        underApprovalList.add(approval);
      }
    }
    return underApprovalList;
  }

  @Override
  public void approvalOpen(Long submissionNo) {
    approvalListMapper.approvalOpen(submissionNo);
  }

  @Override
  public ExpenseApprovalDetailForm expenseApprovalDetail(Long submissionNo, String submissionPri) {
    List<ExpenseApprovalDetailForm> expenseApprovalDetails = approvalListMapper.expenseApprovalDetail(submissionNo, submissionPri);
    ExpenseApprovalDetailForm expenseApprovalDetail = new ExpenseApprovalDetailForm(expenseApprovalDetails.get(0).getSubmissionNo(),
        expenseApprovalDetails.get(0).getSubmissionPri(), expenseApprovalDetails.get(0).getApprovalAble(),
        expenseApprovalDetails.get(0).getProposerEmployeeNo(), expenseApprovalDetails.get(0).getApproverEmployeeNo(),
        expenseApprovalDetails.get(0).getExpenseNo(), expenseApprovalDetails.get(0).getExpenseSection(), expenseApprovalDetails.get(0).getExpensePjt(),
        expenseApprovalDetails.get(0).getExpenseDate(), expenseApprovalDetails.get(0).getExpenseCost(), expenseApprovalDetails.get(0).getExpenseContent());
    return expenseApprovalDetail;
  }

  @Override
  public List<ApproverDto> expenseApproverList(Long submissionNo, String submissionPri) {
    List<ExpenseApprovalDetailForm> expenseApprovalDetails = approvalListMapper.expenseApprovalDetail(submissionNo, submissionPri);
    List<ApproverDto> approverList = new ArrayList<>();
    for (ExpenseApprovalDetailForm detailList : expenseApprovalDetails) {
      ProposerDto getApproverName = employeeMapper.proposer(detailList.getApproverEmployeeNo());
      approverList.add(new ApproverDto(detailList.getApproverEmployeeNo(), getApproverName.getEmployeeName(),
          detailList.getSubmissionSection(), detailList.getSubmissionStatus()));
    }
    return approverList;
  }

  @Override
  public void approve(Long submissionNo) {
    approvalListMapper.approve(submissionNo);
  }

  @Override
  public void nextApproverShowable(String submissionPri, Long approverEmployeeNo) {
    approvalListMapper.nextApproverShowable(submissionPri, approverEmployeeNo);
  }

  @Override
  public void rejectApproval(Long submissionNo, String submissionComent) {
    approvalListMapper.rejectApproval(submissionNo, submissionComent);
  }


  //datetime을 date로 변화하기 위해 해당 값을 date타입으로 parsing 함
  private Date dateParse(String submissionDatetime) {
    Date date = null;
    try {
      date = (Date) datetimeFormat.parse(submissionDatetime);
    } catch (ParseException e) {
      log.warn("submissionDatetime parse fail ={}", e);
    }
    return date;
  }
}
