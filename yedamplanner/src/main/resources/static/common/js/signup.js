// id 중복 검사
let idTestButton = document.querySelector('.idTestForm button')

idTestButton.addEventListener('click', function(){
    let idTest = document.querySelector('.idTestForm input').value
    fetch(`/common/idtest?id=${idTest}`)
    .then((response) => response.json())
    .then((result) => {
     console.log(result)
       if(result.result == "yes"){
            document.querySelector('input[type=hidden]').value = idTest
            swal("조회 결과", "가입가능한 ID입니다.", "success");
            document.querySelector('.idtest_result').innerHTML = idTest + '는 가입가능한 ID입니다';
			if(document.querySelector('.hidden_ssh').value != ""){
				$('#signupsubmit').attr("disabled", false)
			}
       } else {
            swal("조회 결과", "이미 존재하는 ID입니다.", "success");
       }
    })
    .catch((reject) => console.log(reject));
})

// 주민번호 암호화 (AES256 암호화/복호화)
document.getElementById('ssh').addEventListener('click', function(e){
     
	 e.preventDefault;

     let ssh = document.querySelector('input[name=sshTest]').value
     if(ssh.length != 13){
          swal("가운데 -를 제외한 13자리를 입력해주세요")
          return;
     }
	 sshTest(ssh);

}) 

// 주민등록번호 중복검사
let sshTest = function(ssh){
	fetch(`/common/sshtest?ssh=${ssh}`)
	.then(response => response.json())
	.then(result => {
		if(result.result == "yes"){
            document.querySelector('.hidden_ssh').value = ssh
			swal("조회 결과", "가입가능한 주민등록번호입니다.", "success");
			document.querySelector('.sshTest_result').innerHTML = '가입가능한 주민등록번호 입니다.'
			if(document.querySelector('.hidden_id').value != ""){
				$('#signupsubmit').attr("disabled", false)
			}
       } else {
            alert("이미 존재하는 주민등록번호입니다.")
			document.querySelector('.sshtest_result').innerHTML = '이미 존재하는 주민등록번호입니다.'
       }
	})
}







// <--  주민등록증 진위 확인 api / 사용하지 않음 -->
/* 	 let date = document.querySelector('input[name=sshDate]').value
	 if(date.length != 8){
          alert("특수문자를 제외한 8자리를 입력해주세요")
          return;
     }

	
      fetch(`/common/encrypt?ssh=${ssh}&name=${name}&date=${date}`)
      .then((response) => response.json())
      .then((result) => {
          fetch(`https://datahub-dev.scraping.co.kr/scrap/docInq/gov/ResidentPromotionCommittee`,{
               
               method : 'POST',
               headers : {"Content-Type" : "application/json;charset=UTF-8",
                         "Content-Length" : 99,
                         "Host" : "datahub-dev.scraping.co.kr",
                         "Authorization" : "ecb065914cad40bb8725fd4affb68c4faec3179b"
               },
               body : JSON.stringify({
                   "JUMIN" : result.encSsh,
                   "NAME" : result.encName,
                   "ISSUEDATE" : result.encDate
               })
           })
           .then((response) => response.json())
           .then((result) => {
               console.log(result)
           })
           .catch((reject) => console.log(reject));
          
      })
      .catch((reject) => console.log(reject));*/

