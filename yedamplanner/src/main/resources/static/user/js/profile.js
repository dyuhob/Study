
// 인풋, 수정 완료 버튼 활성화
$('#modify').on('click', function(){
    $('.profile_update_form input').attr('disabled', false)
    $(this).css('display', 'none');
    $('#hidden_modify').css('display', '').on('click',function(){
        $('#profile_form_area').submit()
    });

    
})

// 비밀번호 변경
$('#pwmodify').on('click', function(){
    console.log($('input[name=pwnow]').val())
    console.log($('input[name=pwchange]').val())
    fetch('/user/modifypw',{
        method: "POST",
        headers : {"Content-Type" : "application/json"
        },
        body : JSON.stringify({
            pwnow : $('input[name=pwnow]').val(),
            pwchange : $('input[name=pwchange]').val()
        })
    })
    .then(response => response.json())
    .then(result => {
       if(result.result == 'success'){
        alert('비밀번호를 변경했습니다.')
       } else if(result.result == 'fail2'){
        alert('비밀번호가 일치하지 않습니다.')
       } else if(result.result == 'fail1'){
        alert('데이터베이스 오류')
       }

    })
    .catch(reject => console.log(reject));
})






