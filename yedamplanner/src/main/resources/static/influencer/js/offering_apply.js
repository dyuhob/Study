
if(document.querySelector('.move') != undefined){
document.querySelector('.move').addEventListener('click', function(){
    let listingNo = document.querySelector('#listingNo').value
    let uri = "/influ/offerapplyform?listingNo=" + listingNo;
    if(listingNo != null){
    location.href = uri;
    }
})
}

if(document.querySelector('.result') != undefined){
document.querySelector('.result').addEventListener('click', function(){

    document.querySelector('.view').setAttribute('style', 'display : block;')
})
}

if(document.querySelector('.submit') != undefined){
    document.querySelector('.submit').addEventListener('click', function(){
        let offerAmount = document.querySelector('.offerAmount').value
        let listingPrice = document.querySelector('.listingPrice').value
        let myPoint = document.querySelector('.myPoint').value
        let offerPoint = document.querySelector('.offerPoint').value
        
        if((offerAmount)*(listingPrice) > myPoint){
            swal('구매에 필요한 포인트보다 보유 포인트가 부족합니다.')
            return;
        }

        if(Number(offerPoint) > Number(myPoint)){
            swal('보유한 포인트보다 많은 포인트를 투자할 수 없습니다.')
            return;
        }

        swal({
            title: "경고",
            text: "기존 신청내역은 현재 신청 내역으로 대체됩니다. 신청하시겠습니까?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
          })
          .then((willDelete) => {
            if (willDelete) {
                document.querySelector('#contact_form_content').submit()
            }
        })
    })
}