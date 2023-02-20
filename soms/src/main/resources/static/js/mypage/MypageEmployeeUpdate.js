

const MypageEmployeeUpdate_Btn = $('#buttonUpdate');
const MypageEmployeeUpdate_Sucess_Btn = $('#buttonUpdateSuccess');

const MypageEmployeeUpdate_Mod = $('#employeeUpdateModal');
const MyPageEmployeeUpdate_Url = "myInfomation.change";


const MyPageEmployeeUpdate_Id = $('#inputUpdateId');
const MyPageEmployeeUpdate_No = $('#inputUpdateNo');
const MyPageEmployeeUpdate_Manage = $('#inputUpdateManage');
const MyPageEmployeeUpdate_Name = $('#inputUpdateName');
const MyPageEmployeeUpdate_Pw = $('#inputUpdatePw');
const MyPageEmployeeUpdate_Team = $('#inputUpdateTeam');
const MyPageEmployeeUpdate_Admin = $('#inputUpdateAdmin');
const MyPageEmployeeUpdate_Addr = $('#inputUpdateAddr');
const MyPageEmployeeUpdate_Phone = $('#inputUpdatePhone');
const MyPageEmployeeUpdate_Email = $('#inputUpdateEmail');

const mypageEmployeeUpdate_Form = $("form[name=infomationUpdateForm]");

const MyPageEmployeeUpdate_Input = [ 'Pw', 'Addr', 'Phone', 'Email' ];

MypageEmployeeUpdate_Btn.click(function(){
	
	for(var i = 0; i < MyPageEmployeeUpdate_Input.length; i++){
		var input = $("#inputUpdate" + MyPageEmployeeUpdate_Input[i]);
		input.attr('readonly', false);
	}
	
	MyPageEmployeeUpdate_Pw.focus();
	MypageEmployeeUpdate_Sucess_Btn.attr('disabled', false);
	
})

// 비밀번호 변경 유효성 검사
const english = /[a-zA-Z]/;
const special = /[~!@#\#$%<>^&*]/;
const number = /[0-9]/;
	
const PasswordChange_Min = 8;
const PasswordChange_Max = 16;

const phone_regex = /\d{3}-\d{4}-\d{4}/; 


// 비밀번호 변경 유효성 검사
MypageEmployeeUpdate_Sucess_Btn.click(function(){
				
	var pw = MyPageEmployeeUpdate_Pw.val();
	var pw_length = pw.length;
		
	var phone = MyPageEmployeeUpdate_Phone.val();
		
	var email = MyPageEmployeeUpdate_Email.val();
		
	if(pw_length < PasswordChange_Min){
		alert( "비밀번호가 너무 적습니다 " + PasswordChange_Min + " 글자 이상을 입력해주세요.");
		return
	}else if(pw_length > PasswordChange_Max){
		alert( "비밀번호가 너무 깁니다" + PasswordChange_Max + "글자 이하로 입력해주세요.");
		return
	}else if(!english.test(pw)){
		alert( "비밀번호가 영문이 포함되어있지 않습니다");
		return
	}else if(!number.test(pw)){
		alert( "비밀번호가 숫자가 포함되어있지 않습니다.");
		return
	}else if(special.test(pw)){
		alert("비밀번호가 특수문자가 포함되어 있습니다.");
		return
	}else if(!phone_regex.test(phone)){
		alert("연락처 방식이 맞지않습니다.");
		return
	}else if((!email.includes('@')) || !email.includes('.com')){
		alert("이메일 형식이 올바르지 않습니다.");
		return
	}

			
	mypageEmployeeUpdate_Form.submit();
			alert("수정이 완료되었습니다.")
})


MypageEmployeeUpdate_Mod.on('hidden.bs.modal', function () {
 	window.location.reload();
})

	