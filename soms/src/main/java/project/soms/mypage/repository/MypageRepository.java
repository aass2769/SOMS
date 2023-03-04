package project.soms.mypage.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.soms.employee.dto.EmployeeDto;
import project.soms.mypage.dto.AnnualLeaveDto;
import project.soms.mypage.dto.ManageListDto;
import project.soms.mypage.dto.OvertimeDto;
import project.soms.mypage.repository.mapper.MypageMapper;

@Repository
@RequiredArgsConstructor
public class MypageRepository {
	
	private final MypageMapper mypageMapper; 
	
	// 계정 업데이트
	public void mypageInfomationUpdate(EmployeeDto employee) {
		mypageMapper.mypageInfomationUpdate(employee);
	}
	
	// 직급리스트 
	public List<ManageListDto> mypageRegisterManageList(){
		return mypageMapper.mypageRegisterManageList();
	}
	
	// 현재 사번의 매니저직급
	public Integer getEmployeeManageNo(long employeeNo) {
		return mypageMapper.getEmployeeManageNo(employeeNo);
	}
	
	// 계정 생성
	public void mypageRegisterInsert(EmployeeDto employee) {
		mypageMapper.mypageRegisterInsert(employee);
	}
	
	// 게정생성시 중복체크
	public Integer mypageRegisterUniqueCheck(EmployeeDto employee) {
		return mypageMapper.mypageRegisterUniqueCheck(employee);
	}
	
	// 사번으로 회원정보 조회(모달용)
	public EmployeeDto mypageEmployeeSearch(long employeeNo) {
		return mypageMapper.mypageEmployeeSearch(employeeNo);
	}
	
	// 연차 조회
	public List<AnnualLeaveDto> getEmployeeAnnualLeave(long employeeNo, Integer year){
		return mypageMapper.getEmployeeAnnualLeave(employeeNo, year);
	}
	
	// 연장근무 조회
	public OvertimeDto getEmployeeOvertime(long employeeNo, String NowDate) {
		return mypageMapper.getEmployeeOvertime(employeeNo, NowDate);
	}
	
	// 회원 삭제
	public void mypageDelete(long employeeNo) {
		mypageMapper.mypageDelete(employeeNo);
	}
	
	// 회원 삭제 후 Null 처리
	public void mypageDeleteNull(long employeeNo) {
		mypageMapper.mypageDeleteNull(employeeNo);
	}
}
