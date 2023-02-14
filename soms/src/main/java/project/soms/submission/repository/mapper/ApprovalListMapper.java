package project.soms.submission.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import project.soms.submission.dto.SubmissionDto;

import java.util.List;

@Mapper
public interface ApprovalListMapper {

  List<SubmissionDto> underApprovalList(@Param("employeeNo") long employeeNo,
                                        @Param("submissionSection") String subnissionSection,
                                        @Param("submissionDatetime") String submissionDatetime);
}