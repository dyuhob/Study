$('.approve').on('click', function(){

    let reportKey = $(this).parent().parent().parent().find('.rpkey').text()
    let reportedId = $(this).parent().parent().parent().find('.reportedId').text()
    let reportedPost = $(this).parent().parent().parent().find('.reportPost').text()

    $.ajax({
        type : 'post',           // 타입 (get, post, put 등등)
        url : '/admin/reportapprove',           // 요청할 서버url
        async : true,            // 비동기화 여부 (default : true)
        headers : {              // Http header
          "Content-Type" : "application/json",
        },
        data: JSON.stringify({
            "reportKey" : reportKey,
            "reportedId" : reportedId,
            "reportedPost" : reportedPost
        }),
        dataType : 'json',       // 데이터 타입 (html, xml, json, text 등등)
        success : function(result) { // 결과 성공 콜백함수
            console.log(result.result)
            if(result.result == "success"){
                alert("승인 성공")
                location.href = "/admin/reportadmin";
            } else if(result.result == "fail"){
                alert("승인 실패")
                location.href = "/admin/reportadmin";
            }
        },
        error : function(request, status, error) { // 결과 에러 콜백함수
            console.log(error)
        }
    })  
})

$('.reject').on('click', function(){
    $.ajax({
        type : 'post',           // 타입 (get, post, put 등등)
        url : '/admin/reportreject',           // 요청할 서버url
        async : true,            // 비동기화 여부 (default : true)
        headers : {              // Http header
          "Content-Type" : "application/json",
        },
        data: JSON.stringify({
            "reportKey" : reportKey,
            "reportedId" : reportedId,
            "reportedPost" : reportedPost
        }),
        dataType : 'json',       // 데이터 타입 (html, xml, json, text 등등)
        success : function(result) { // 결과 성공 콜백함수
            console.log(result.result)
            if(result.result == "success"){
                alert("승인 성공")
                location.href = "/admin/reportadmin";
            } else if(result.result == "fail"){
                alert("승인 실패")
            }
        },
        error : function(request, status, error) { // 결과 에러 콜백함수
            console.log(error)
        }
    })  
})