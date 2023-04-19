

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
            swal("가입가능한 ID입니다.")
			document.querySelector('.idtest_result').innerHTML = idTest + '는 가입가능한 ID입니다'
               if(document.querySelector('.hidden_crn').value != ""){
				$('#signupsubmit').attr("disabled", false)
			}
       } else {
            swal("이미 존재하는 ID입니다.")
       }
    })
    .catch((reject) => console.log(reject));
})

// 사업자등록번호 진위 확인
document.getElementById('crn').addEventListener('click', function(e){
     e.preventDefault;

     let crn = document.querySelector('#crn_input').value 
     var data = {
          "b_no": [crn]
         }; 
      $.ajax({
		// serviceKey 값을 xxxxxx에 입력
        url: "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=79Lbwke3JPWi1gqkkuOPwO9q%2FIZCBg6Kv3%2BuJOTL8EumipxFuMkNOfMfPHHXUlB7aAm8xeL2BAPUIgOi7TFkbQ%3D%3D&returnType=JSON",
  
        type: "POST",
        data: JSON.stringify(data), // json 을 string으로 변환하여 전송
        dataType: "JSON",
        contentType: "application/json",
        accept: "application/json",
        success: function(result) {
			console.log(result);
			if(result.data[0]["tax_type"] != "국세청에 등록되지 않은 사업자등록번호입니다."){
				crnTest(crn)
			} else {
				document.querySelector('.crntest_result').innerHTML = '국세청에 등록되지 않은 사업자등록번호입니다.'
			}
        },
        error: function(result) {
            console.log(result.responseText); //responseText의 에러메세지 확인
        }
      });
})


// 사업자등록번호 중복검사
let crnTest = function(crn){
	fetch(`/common/crntest?crn=${crn}`)
	.then(response => response.json())
	.then(result => {
		if(result.result == "yes"){
            document.querySelector('.hidden_crn').value = crn
            swal("결과","가입가능한 사업자등록번호입니다.", "success")
			document.querySelector('.crntest_result').innerHTML = '가입가능한 사업자등록번호 입니다.'
			if(document.querySelector('.hidden_id').value != ""){
				$('#signupsubmit').attr("disabled", false)
			}
       } else {
            swal("이미 존재하는 사업자등록번호입니다.")
			document.querySelector('.crntest_result').innerHTML = '이미 존재하는 사업자등록번호입니다.'
       }
	})
}




