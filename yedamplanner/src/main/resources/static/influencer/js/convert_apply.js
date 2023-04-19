/* $('#convert_submit').on('click', function(){
    $.ajax({
        type : 'POST',           // 타입 (get, post, put 등등)
        url : '/influ/influencerapply',           // 요청할 서버url
        async : true,            // 비동기화 여부 (default : true)
        headers : {              // Http header
          "Content-Type" : "application/json",
        },
        dataType : 'json',       // 데이터 타입 (html, xml, json, text 등등)
        success : function(result) { // 결과 성공 콜백함수
            console.log(result.result)
            if(result.result == "success"){
                alert("신청 성공")
                location.href;
            } else if(result.result == "fail"){
                alert("신청 실패")
            }
        },
        error : function(request, status, error) { // 결과 에러 콜백함수
            console.log(error)
        }
    })
}) */