

$('.approve').on('click', function(){
    let td = $(this).parent().parent().parent().children()[0]
    let applyNo = td.innerText;
    console.log(applyNo);
    var applyobj = JSON.stringify({ "no" : applyNo });
    $.ajax({
        type : 'post',           // 타입 (get, post, put 등등)
        url : '/admin/approveinfluencer',           // 요청할 서버url
        async : true,            // 비동기화 여부 (default : true)
        headers : {              // Http header
          "Content-Type" : "application/json",
        },
        data: applyobj,
        dataType : 'json',       // 데이터 타입 (html, xml, json, text 등등)
        success : function(result) { // 결과 성공 콜백함수
            console.log(result.result)
            if(result.result == "success"){
                alert("승인 성공")
                location.href = "/admin/influenceradmin";
            } else if(result.result == "fail"){
                alert("승인 실패")
            }
        },
        error : function(request, status, error) { // 결과 에러 콜백함수
            console.log(error)
        }
    })  
})


$('.reject').on('click', function(){
    let td = $(this).parent().parent().parent().children()[0]
    let applyNo = td.innerText;
    console.log(applyNo);
    var applyobj = JSON.stringify({ "no" : applyNo });
    $.ajax({
        type : 'post',           // 타입 (get, post, put 등등)
        url : '/admin/rejectinfluencer',           // 요청할 서버url
        async : true,            // 비동기화 여부 (default : true)
        headers : {              // Http header
          "Content-Type" : "application/json",
        },
        data: applyobj,
        dataType : 'json',       // 데이터 타입 (html, xml, json, text 등등)
        success : function(result) { // 결과 성공 콜백함수
            console.log(result.result)
            if(result.result == "success"){
                alert("거부 성공")
                location.href = "/admin/influenceradmin";
            } else if(result.result == "fail"){
                alert("거부 실패")
            }
        },
        error : function(request, status, error) { // 결과 에러 콜백함수
            console.log(error)
        }
    })  
})
