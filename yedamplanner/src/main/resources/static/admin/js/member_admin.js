

$('.member').on('click', function(){
    let memberId = $(this).text()
    $('.wheretogo').val(memberId);
    $('.postform').submit();
})



