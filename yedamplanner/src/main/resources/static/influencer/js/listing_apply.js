$('#listing_submit').on('click', function(){

    let result = document.querySelectorAll('.complete')
    if(result.length != 0){
    if(result[0].innerText == 'N'){
        swal('이전에 신청한 내역이 존재합니다.')
    return;
    }
    }

    $.ajax({
        type : 'get',           // 타입 (get, post, put 등등)
        url : '/influ/insertlisting',           // 요청할 서버url
        async : true,            // 비동기화 여부 (default : true)
        headers : {              // Http header
          "Content-Type" : "application/json",
        },
        dataType : 'json',       // 데이터 타입 (html, xml, json, text 등등)
        success : function(result) { // 결과 성공 콜백함수
            console.log(result.result)
            if(result.result == "success"){
                swal("신청 성공")
                location.href = "/influ/listingapplyform";
            } else if(result.result == "fail"){
                swal("신청 실패")
            }
        },
        error : function(request, status, error) { // 결과 에러 콜백함수
            console.log(error)
        }
    })
})