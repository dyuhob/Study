function combineDateTime() {
    var dateInput = document.getElementsByName("programDate")[0];
    var timeInput = document.getElementsByName("startTime")[0];
    var startInput = document.getElementsByName("programStart")[0];

    var dateValue = dateInput.value;
    var timeValue = timeInput.value;

    var datetimeValue = dateValue + " " + timeValue;

    startInput.value = datetimeValue;
}

$('.ttdelBtn').on('click', function () {

    let td = $(this).parent().parent().children()[0]

    let proId = td.innerText;

    console.log(proId);

    $.ajax({
        type: 'post', // 타입 (get, post, put 등등)
        url: '/planner/timetableRemove', // 요청할 서버url
        async: true, // 비동기화 여부 (default : true)
        data: {
            "programId": proId
        },
        dataType: 'json', // 데이터 타입 (html, xml, json, text 등등)
        success: function (result) { // 결과 성공 콜백함수
            console.log(result.result)
            if (result.result == "success") {
                alert("삭제 성공")
                location.href = "/planner/main/" + planId + "/timetable";
            } else if (result.result == "fail") {
                alert("삭제 실패")
            }
        },
        error: function (request, status, error) { // 결과 에러 콜백함수
            console.log(error)
        }
    })

})

document.getElementById('ttList').addEventListener('click', function() {
    location.href = '/planner/main/' + planId + '/timetable';
  });

  const planStartInput = document.querySelector("input[name='planStart']");
  const planEndInput = document.querySelector("input[name='planEnd']");
  const buttonsElement = document.querySelector("#buttons");
  const programEndInput = document.getElementsByName("programEnd")[0];
  const programDateInput = document.getElementsByName("programDate")[0];
  
  // 'YYYY-MM-DD' 형식의 문자열을 Date 객체로 변환하는 함수
  function parseDate(dateString) {
    const [year, month, day] = dateString.split("-");
    return new Date(year, month - 1, day);
  }
  
  // planStart부터 planEnd까지의 모든 날짜에 해당하는 button 요소를 생성하여 #buttons 요소에 추가
  const planStart = parseDate(planStartInput.value);
  const planEnd = parseDate(planEndInput.value);
  for (let d = planStart; d <= planEnd; d = new Date(d.getTime() + 86400000)) {
    const button = document.createElement("button");
    button.textContent = `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}`;
    button.classList.add("btn", "btn_theme", "btn-sm", "mr-2"); // 클래스 추가 및 margin-right 속성 추가
    button.addEventListener("click", function() {
      const programDate = button.textContent;
      location.href = `/planner/main/${planId}/timetable/${programDate}`;
    });
    buttonsElement.appendChild(button);
  }
