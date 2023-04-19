
//일반 로그인
/*document.querySelector('.common_form_submit button').addEventListener('click', function(){

    let id = document.querySelector('.common_author_form input[type="text"]').value
    let pw = document.querySelector('.common_author_form input[type="password"]').value
    fetch(`/common/login`,{
        method : 'POST',
        headers : {"Content-Type" : "application/json"
                    //"Accept" : "application/json"
        },
        body : JSON.stringify({
            memberId : id,
            memberPw : pw
        })
    })
    .then((response) => response.json())
    .then((result) => {
        console.log(result)
        if(result.result == "failid"){
            alert("존재하지 않는 ID입니다.")
       } else if(result.result == "failpw") {
            alert("잘못된 PW입니다.")
       } else if(result.result == "success"){
            alert("로그인 성공")
            document.querySelector('#main_author_form').submit()
       } else {
            alert("로그인 오류")
       }
    })
    .catch((reject) => console.log(reject));
}) */

//카카오 로그인 
document.querySelector(".kakao_login_button").addEventListener('click', function(){
					Kakao.init('6a7176b58ec6b36c1784d76f3af76a11');
					Kakao.Auth.login({
						success:function(auth){
							Kakao.API.request({
								url: '/v2/user/me',
								success: function(response){
									var account = response.kakao_account;
									let emailIndex = account.email.indexOf("@")
									let id = account.email.substring(0, emailIndex-1)
									console.log(account)
									fetch("/common/kakaologin", {
										method : 'POST',
										headers : { "Content-Type" : "application/json"
										},
										body : JSON.stringify({
											memberId : id,
											memberAge : account.age_range,
											memberGender : account.gender,
											memberName : account.profile.nickname,
											memberEmail : account.email
										})
									})
									.then(response => response.json())
									.then(result => {
										if(result.result == "success"){
											location.href = "/common/main"
										} else {
											alert('로그인 실패')
										}
									})
									.catch(reject => console.log(reject))
									
									
								},
								fail: function(error){
									// 경고창에 에러메시지 표시
								//	
								}
							}); // api request
						}, // success 결과.
						fail:function(error){
							// 경고창에 에러메시지 표시
							
						}
					});
				})

// 아이디 찾기
document.querySelector('.forgotSubmit button').addEventListener('click', function(){

	let forgotSsh = document.querySelector('.forgotSsh').value;
	fetch('/common/forgot',{
        method : 'POST',
        headers : {"Content-Type" : "application/json"
        },
        body : JSON.stringify({
            memberSsh : forgotSsh
        })
    })
    .then(response => response.json())
	.then(result => {
		console.log(result)
		alert('해당된 주민번호로 가입된 아이디는 ' + result[0].memberId + ' 입니다.')
	})
	.catch(reject => console.log(reject))
})

// 임시 비밀번호 발급
document.querySelector('.temporaryPw button').addEventListener('click', function(){

	let forgotSsh = document.querySelector('.forgotSsh2').value;
	console.log(forgotSsh)
	fetch('/common/temporarypw',{
        method : 'POST',
        headers : {"Content-Type" : "application/json"
        },
        body : JSON.stringify({
            memberSsh : forgotSsh
        })
    })
    .then(response => response.json())
	.then(result => {
		alert('새로운 임시 비밀번호 : ' + result.tempPw )
	})
	.catch(reject => console.log(reject))
})


