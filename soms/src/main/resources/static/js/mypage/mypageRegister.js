	// 계정 생성 []
	const Register_Url = "employee.register";
	// 계정 생성 - 관리자 체크 [URL]
	const Register_Check_Url = "";
	// 계정 생성 [모달]
	const Register_Mod = $('#employeeRegisterModal');
	// 계정 생성 [정보]
	const Register_Name = $('#inputRegisterName');
	const Register_Id = $('#inputRegisterId');
	const Register_Manage = $('#inputRegisterManage');
	const Register_No = $('#inputRegisterNo');
	const Register_Phone = $('#inputRegisterPhone');
	const Register_Email = $('#inputRegisterEmail');
	const Register_Addr = $('#inputRegisterAddr');
	const Register_Team = $('#inputRegisterTeam');
	const Register_Admin = $('#inputRegisterAdmin');
	// 계정 생성 [버튼]
	const Register_Btn = $('#registerBtn');
	
	const Register_Infos = ["Name", "No", "Manage", "Id", "Phone", "Email", "Addr", "Team", "Admin"];

	const Register_Form = $('#registerform');
	

	Register_Btn.click(function(){
		var Count = 0;
		
		for(var i = 0; i<Register_Infos.length; i++){
			
			var RegisterValues = $('#inputRegister'+ Register_Infos[i]).val();
		
			if((RegisterValues != "") && (RegisterValues != 0)){	
				Count += 1;
			}
			
		}

		if(Count == Register_Infos.length){
			
			var RegisterNo = Register_No.val();
			
			if(RegisterNo.length != 11){
				alert("사번이 잘못되었습니다.");
				return
			}else if(!phone_regex.test(Register_Phone.val())){
				alert("연락처 방식이 맞지않습니다.");
				return
			}else{
				Register_Form.submit();
			}
			
		}else{
			alert("누락된 정보가 있습니다.");
			return
		}
	})
	
	
	
	
	
	

	
	