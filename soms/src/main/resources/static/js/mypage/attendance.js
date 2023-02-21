

const Attendance_Btn = $('#Attendance_Btn');
const Attendance_Url = "attendance.gotowork";
const Attendance_Form = $('#mypage_reflash');
const Attendance_Dummy_Url = "attendance.dummy";

Attendance_Btn.click(function(){
	
	var gotowork = "출근 하시겠습니까?";
	var leavetowork = "퇴근 하시겠습니까?";
	var text;
	
	var gotowork_success = "성공적으로 출근 하셨습니다.";
	var leavetowork_success = "성공적으로 퇴근처리가 완료되었습니다.";
	var success_text;
	
	if(Attendance_Btn.attr("value") ==  '1'){
		text = leavetowork;
		success_text = leavetowork_success;
	}else if(Attendance_Btn.attr("value") ==  '2'){
		success_text = gotowork_success;
		text = gotowork;
	}
	
	if(confirm(text)){
		
		$.ajax({
			url : Attendance_Url,
			success : function(){
				alert(success_text);
				location.href = Attendance_Dummy_Url;
			},
			error : function(){
				alert("서버 요청 실패");
			}
		})
		
	}
})







