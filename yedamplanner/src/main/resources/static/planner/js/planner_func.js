function chatbox_fnc() {
    let chatbox = document.querySelector("chat_box");
    console.log(chatbox);
    /*if( chatbox == 'none'){
        chatbox = '';
    }else {
        chatbox = 'none'
    }*/

}

// 모달 on/off
$(document).ready(function () {
    $('#addBtn').click(function () {
        $('#addModal').modal('show');
    });
});

// modalCloseBtn 클릭 이벤트 핸들러 등록
$('#modalCloseBtn').click(function () {
    // addModal을 숨기기 (종료하기)
    $('#addModal').modal('hide');
});

// 플래너 입장
$(document).ready(function () {
    $(".openPlanner").click(function () {
        let planId = $(this).data("plan-id");
        console.log(planId);

        $.ajax({
            type: 'get',
            url: '/planner/main/' + planId,
            async: true,
            success: function (result) {
                console.log(result)

                alert("입장 성공")
                location.href = "/planner/main/" + planId;

            },
            error: function (request, status, error) {
                console.log(error)
                alert("입장 실패")
            }
        });
    });
});

//플래너 개설
$('#modalAddBtn').click(function (e) {
    e.preventDefault();
    $.ajax({
        type: "POST",
        url: "/planner/plannerInsert",
        data: $('form').serialize(),
        success: function (planId) {
            console.log(planId);
            alert("개설이 완료되었습니다.");
            window.location.href = "/planner/main/" + planId;
        },
        error: function (xhr, status, error) {
            alert("공란을 채워주세요!");
        }
    });
});

//맴버 가입 신청 승인
$(document).ready(function () {
    $('.modBtn').click(function () {
        var applicantNo = $(this).closest('tr').find('td:nth-child(1)').text();
        var memberId = $(this).closest('tr').find('td:nth-child(2)').text();
        console.log(applicantNo);
        console.log(memberId);
        console.log(planId);

        $.ajax({
            type: "POST",
            url: "/planner/main/insertMember/" + planId + "/" + memberId + "/" + applicantNo,
            data: JSON.stringify({
                "memberId": memberId, "applicantNo": applicantNo
            }),
            contentType: "application/json",
            success: function (response) {
                console.log(response);
                alert("가입 승인 완료");
                location.reload();
            },
            error: function (error) {
                console.log(error);
                alert("처리 실패")
            }
        });
    });
});

//맴버 가입 신청 거부
$(document).ready(function () {
    $('.delBtn').click(function () {
        var applicantNo = $(this).closest('tr').find('td:nth-child(1)').text();
        console.log(applicantNo);

        $.ajax({
            type: "POST",
            url: "/planner/main/deleteReq/" + applicantNo,
            data: JSON.stringify({
                "applicantNo": applicantNo
            }),
            contentType: "application/json",
            success: function (response) {
                console.log(response);
                alert("요청이 삭제되었습니다.");
                location.reload();
            },
            error: function (error) {
                console.log(error);
                alert("처리 실패")
            }
        });
    });
});

//맴버 신고
$(document).ready(function () {
    $('.sinBtn').click(function () {
        var pmemKey = $(this).closest('tr').find('td:nth-child(1)').text();
        console.log(pmemKey);
        alert("신고가 완료되었습니다.")
    });
});

//강퇴
$(document).ready(function () {
    $('.outBtn').click(function () {
        var pmemKey = $(this).closest('tr').find('td:nth-child(1)').text();
        console.log(pmemKey);

        $.ajax({
            type: 'post', // 타입 (get, post, put 등등)
            url: '/planner/deleteMember', // 요청할 서버url
            async: true, // 비동기화 여부 (default : true)
            data: {
                "pmemKey": pmemKey
            },
            dataType: 'json', // 데이터 타입 (html, xml, json, text 등등)
            success: function (result) { // 결과 성공 콜백함수
                console.log(result.result)
                if (result.result == "success") {
                    alert("강퇴 처리 완료")
                    location.reload();
                } else if (result.result == "fail") {
                    alert("강퇴 실패")
                }
            },
            error: function (request, status, error) { // 결과 에러 콜백함수
                console.log(error)
            }
        })
    });
});

//플래너 종결
$(document).ready(function () {
    $('.endBtn').click(function () {
        var pmemKey = $(this).closest('tr').find('td:nth-child(1)').text();
        console.log(pmemKey);

        $.ajax({
            type: 'post', // 타입 (get, post, put 등등)
            url: '/planner/endPlanner', // 요청할 서버url
            async: true, // 비동기화 여부 (default : true)
            data: {
                "pmemKey": pmemKey
            },
            dataType: 'json', // 데이터 타입 (html, xml, json, text 등등)
            success: function (result) { // 결과 성공 콜백함수
                console.log(result.result)
                if (result.result == "success") {
                    alert("종결 처리 완료!")
                    location.reload();
                } else if (result.result == "fail") {
                    alert("처리 실패")
                }
            },
            error: function (request, status, error) { // 결과 에러 콜백함수
                console.log(error)
            }
        })

    });
});

//호스트 이양
$(document).ready(function () {
    $('.changeBtn').click(function () {
        var pmemKey = $(this).closest('tr').find('td:nth-child(1)').text();
        var memberId = $(this).closest('tr').find('td:nth-child(3)').text();
        console.log(pmemKey);
        console.log(memberId);

        $.ajax({
            type: 'POST', // 요청 방식
            url: '/planner/changeHost/' + planId, // 요청할 서버 url
            async: true,
            data: {
                "pmemKey": pmemKey, "memberId": memberId
            },
            dataType: 'json',
            success: function (result) {
                console.log(result.result)
                if (result.result == "success") {
                    alert("권한 이양 완료!")
                    location.reload();
                } else if (result.result == "fail") {
                    alert("처리 실패")
                }
            },
            error: function (request, status, error) {
                console.log(error)
            }
        })

    });
});

//리뷰 상세조회
$('.planName').on('click', function () {

    let td = $(this).parent().children()[0]

    let planReviewId = td.innerText;

    console.log(planReviewId);

    $.ajax({
        type: 'get',
        url: '/planner/planReviewInfo/' + planReviewId,
        async: true,
        success: function (result) {
            console.log(result)
            location.href = "/planner/planReviewInfo/" + planReviewId;
        },
        error: function (request, status, error) {
            console.log(error)
            alert("입장 실패")
        }
    });

})

// 플래너 최종 종료
$(document).ready(function() {
    // 모든 맴버가 종결한 경우에만 플래너 종료 기능 활성화
    var allO = true;
    $('td:nth-child(1)').each(function() {
        if ($(this).text() != "O") {
            allO = false;
            return false;
        }
    });
    if (allO) {
        $('a[href="notification.html"]').click(function(event) {
            event.preventDefault();
            $.ajax({
                url: "/planner/endAllPlanner/" + planId,
                type: "POST",
                data: {},
                success: function(data) {
                    if (data.result == "success") {
                        alert("플래너 종료가 완료되었습니다.");
                        location.reload();
                    } else {
                        alert("플래너 종료 처리 중 오류가 발생하였습니다.");
                    }
                },
                error: function() {
                    alert("서버 오류가 발생하였습니다.");
                }
            });
        });
    } else {
        $('a[href="notification.html"]').click(function(event) {
            event.preventDefault();
            alert("모든 맴버가 종결 상태여야 플래너 종료가 가능합니다.");
        });
    }
});