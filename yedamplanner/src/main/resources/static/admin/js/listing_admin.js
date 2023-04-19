

$('.approve').on('click', function(){
    let td = $(this).parent().parent().parent().children()[0]
    let listingNo = td.innerText;
    console.log(listingNo);
    var listingobj = JSON.stringify({ "no" : listingNo });
    $.ajax({
        type : 'post',           // 타입 (get, post, put 등등)
        url : '/admin/approvelisting',           // 요청할 서버url
        async : true,            // 비동기화 여부 (default : true)
        headers : {              // Http header
          "Content-Type" : "application/json",
        },
        data: listingobj,
        dataType : 'json',       // 데이터 타입 (html, xml, json, text 등등)
        success : function(result) { // 결과 성공 콜백함수
            console.log(result.result)
            if(result.result == "success"){
                alert("승인 성공")
                location.href = "/admin/listingadmin";
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
    let listingNo = td.innerText;
    console.log(listingNo);
    var listingobj = JSON.stringify({ "no" : listingNo });
    $.ajax({
        type : 'post',           // 타입 (get, post, put 등등)
        url : '/admin/rejectlisting',           // 요청할 서버url
        async : true,            // 비동기화 여부 (default : true)
        headers : {              // Http header
          "Content-Type" : "application/json",
        },
        data: listingobj,
        dataType : 'json',       // 데이터 타입 (html, xml, json, text 등등)
        success : function(result) { // 결과 성공 콜백함수
            console.log(result.result)
            if(result.result == "success"){
                alert("거부 성공")
                location.href = "/admin/listingadmin";
            } else if(result.result == "fail"){
                alert("거부 실패")
            }
        },
        error : function(request, status, error) { // 결과 에러 콜백함수
            console.log(error)
        }
    })  
})

$('.listing').on('click', function(){
    
    let stockAmount = $(this).parent().parent().parent().find('.stockAmount').val()
    if(stockAmount < 1000){
        swal('신청된 주식 수량이 1000개 미만입니다.')
        return;
    }
    let td = $(this).parent().parent().parent().children()[0]
    let listingNo = td.innerText;
    var listingobj = JSON.stringify({ "no" : listingNo });
    $.ajax({
        type : 'post',           // 타입 (get, post, put 등등)
        url : '/admin/completelisting',           // 요청할 서버url
        async : true,            // 비동기화 여부 (default : true)
        headers : {              // Http header
          "Content-Type" : "application/json",
        },
        data: listingobj,
        dataType : 'json',       // 데이터 타입 (html, xml, json, text 등등)
        success : function(result) { // 결과 성공 콜백함수
            if(result.result == "success"){
                swal("상장 성공")
                location.href = "/admin/listingadmin";
            } else if(result.result == "fail"){
                swal("상장 실패")
            }
        },
        error : function(request, status, error) { // 결과 에러 콜백함수
            console.log(error)
        }
    })  
})

// 상장 버튼 활성화
let listingBtn = function(){
    let end = document.querySelectorAll('.end')
    for(let each of end){
        eachDate = new Date(each.innerText)
        if(eachDate > new Date()){
            if(each.parentElement.querySelector('.listing') != null){
                each.parentElement.querySelector('.listing').removeAttribute('disabled')
            }
        }
    }
}

listingBtn()


//페이징
$('.col-lg-12 a').on('click', function(e){
    var actionForm = $('#actionForm');
    e.preventDefault();
    actionForm.find("input[name='pageNum']").val($(this).attr('href'))
    actionForm.submit();
})
