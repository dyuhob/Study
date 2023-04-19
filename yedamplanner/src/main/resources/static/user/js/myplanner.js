// 버튼 이벤트 등록
let prBtns = document.querySelectorAll('.plannerInsert')
let mrBtns = document.querySelectorAll('.memberInsert')

for(let prBtn of prBtns){
    prBtn.addEventListener('click', function(){
        let Content = this.parentElement.querySelector('textarea').value
        let planId = this.parentElement.getAttribute('data-planId')
        let prStar = this.parentElement.querySelector('.prStar').value
        let planReviewId = this.parentElement.querySelector('.planReviewId').value
        
        fetch("/user/insertplannerreview", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
              content: Content,
              planId: planId,
              prStar: prStar,
              planReviewId : planReviewId
            }),
          })
          .then((response) => response.json())
          .then((result) => {
            if(result.result == "success"){
                swal('성공')
                location.href = "/user/myplanner"
            } else if(result.result == "fail"){
                swal('실패')
            }

          })
          .catch(reject => console.log(reject));

    })    
}

for(let mrBtn of mrBtns){
    mrBtn.addEventListener('click', function(){
        let memberReviewVOList = [];
        let mrDivs = document.querySelectorAll('.memberReviewDiv')
        for(let mrDiv of mrDivs){
            
        let mrvo = {
        "mrKey" : mrDiv.querySelector('.mrKey').value,
        "mrContent" : mrDiv.querySelector('.mrContent').value,
        "mrPlanId" : mrDiv.querySelector('.mrPlanId').dataset.planid,
        "mrLike" : mrDiv.querySelector('.mrLike').value,
        "mrSubject" : mrDiv.querySelector('.mrSubject').innerText
        }
        memberReviewVOList.push(mrvo)
        }
        
        fetch("/user/insertmemberreview", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(memberReviewVOList),
          })
          .then((response) => response.json())
          .then((result) => {
            if(result.result == "success"){
                swal('성공')
                location.href = "/user/myplanner"
            } else if(result.result == "fail"){
                swal('실패')
            }

          })
          .catch(reject => console.log(reject));


    })    
}
