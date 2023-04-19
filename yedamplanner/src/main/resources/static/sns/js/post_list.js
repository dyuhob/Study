/**
 * post_list.js/ follow
 */

// 팔로우 여부 체크
async function btnStatus() {
    // 버튼 아이디 값
    let followBtns = document.querySelectorAll(".follow_btn");
    // followBtns 배열 값 호출후 버튼 비동기 처리
    followBtns.forEach(async (button) => {
        // memberid값만 추출하기위해 필요없는 부분 자름
        let memberId = button.id.replace(/^(followBtn_|unfollowBtn_)/, '');
        console.log('memberId : ' + memberId);
        try {
            // fetch 전송 url
            let followApi = 
            await fetch(`/sns/follow/check?memberId=${memberId}`);
            // 정상이면 처리 실행
            if (followApi.ok) {
                // json형태로 저장
                let data = await followApi.json(); 
                // 가져온 값중 followCheck에 값 저장.
                let followCheck = data.result; 

                // followCheck가 follow상태라면,
                if (followCheck == "follow") {
                    button.textContent = "팔로잉";
                    button.id = 'followBtn_' + memberId;
                    button.classList.remove("btn-primary");
                    button.classList.add("btn-warning");
                    // followCehck가 follow상태가 아니라면,
                } else {
                    button.textContent = "팔로우";
                    button.id = 'unfollowBtn_' + memberId;
                    button.classList.remove("btn-warning");
                    button.classList.add("btn-primary");
                }
            } else {
                throw new Error('서버 에러');
            }
        } catch (error) {
            console.log(error);
            alert("catch 에러");
        }
    });
}
async function changeFollow(event) {
    let button = event.target;
    // 버튼 id follow_, unfollow_ 제거한 $memberId 값을 let memberId에 저장
    let memberId = button.id.replace(/^(followBtn_|unfollowBtn_)/, '');
    // 팔로잉 여부에 따른 처리 방지 체크(알림뜨기도 전에 팔로잉 해제등)
    let check = true;
    console.log('button : ' + button);
    console.log('memberId : ' + memberId);

    if (button.textContent != "팔로우") {
        check = confirm("정말 팔로우를 해제하시겠습니까?");
    }
    // 서버에 memberId 보내 팔로우 또는 언팔로우를 요청
    if (check) {
        fetch(`/sns/follow`, {
                method: 'post',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    "memberId": memberId
                }),
            })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw Error('실패');
                }
            })
            // fcnt 체크
            .then(data => {
                let fcnt = data.fcnt;
                console.log(data);
                // fcnt가 follow상태.
                if (fcnt == "follow") {
                    button.textContent = "팔로잉";
                    button.id = 'followBtn_' + memberId;
                    button.classList.remove("btn-primary");
                    button.classList.add("btn-warning");
                    console.log('follow');
                    if (data.result = "iResult") {

                    }
                } else if (fcnt == "!follow") {
                    button.textContent = "팔로우";
                    button.id = 'unfollowBtn_' + memberId;
                    button.classList.remove("btn-warning");
                button.classList.add("btn-primary");
                    console.log('unfollow');
                    alert("팔로우를 해제하셨습니다.")
                }
                button.style.border = "1px solid black";
            })
            .catch(error => {
                console.log(error);
                alert("에러");
            });
    }
}

document.addEventListener('DOMContentLoaded', btnStatus);

// 나의 글모음
function myPostLink(memberId) {
    window.location.href = `/myPost/${memberId}`;
}

// 회원 검색바
async function searchUser() {
    let memberName = document.getElementById("searchInput");

    // 3자리 수 검사
    if (memberName.value.length >= 3) {
        try {
            // fetch 경로
            let response = await fetch('/sns/search?searchText=${memberName.value}');
            if (response.ok) {
                let results = await response.json();

                let user = results.find((result) => result.memberId == memberName.value);
                if (user) {
                    window.location.href = `/sns/main/${memberName.value}`;
                } else {
                    alert("존재하지 않는 회원입니다.");
                }
            } else {
                throw new Error("에러");
            }
        } catch (error) {
            console.error("에러", error);
        }
    } else {
        alert("회원 아이디를 3자 이상 입력해주세요.");
    }
}

  // 엔터 입력해야 검색
  document.getElementById("searchInput").addEventListener("keydown", (event) => {
    if (event.key == "Enter") {
      searchUser(event);
    }
});

  