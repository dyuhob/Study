var stompClient = null;

function connect() {
	//WebSocketConfig.java에 설정된 endpoint로 SockJs 객체, StompClient 객체 생성
    var socket = new SockJS('/chatserver');
    //do handShake
    stompClient = Stomp.over(socket);
    
    // connect (header, 연결 성공시 콜백)
    stompClient.connect({}, function (frame) {
        //console.log('Connected: ' + frame);
        //subscribe(subsribe url, 해당 url로 메세지를 받을 때 마다 실행할 함수)
        stompClient.subscribe('/topic/'+planId, function (e) {
        	//e.body에 전송된 data가 들어있다.
            showMessage(JSON.parse(e.body));
        });
    });
}

//화면에 채팅창 보여주기
function showChatBox(){
	document.querySelector('.chat_box').style.display="block"
	const $target = document.querySelector(".chat");
	draggable($target);
	connect();
	$.ajax({
		url: '/getChatList',
		type: 'get',
		data: {"planId" : planId,},
		success: function(result){
			makeChatLog(result);
		}
	}).fail(function (error) {
		console.log("채팅 로그 출력 실패");
	})
}
//채팅 로그 만들기
function makeChatLog(result){
	for(let i = 0; i < result.length; i ++){
		let timeList = document.querySelectorAll('.time');
		let div = document.createElement('div');
		//날짜 붙이는 거
		for(let j = timeList.length-1; j >= 0; j--){
			if(timeList[j].innerText == result[i].chatDate.substr(2, 8)){
    			break;
    		}else{
				div = document.createElement('div');
				div.setAttribute('class', 'time');
				div.innerText=result[i].chatDate.substr(2, 8);
				document.querySelector('.messages').append(div);
		    	break;
    		}
		}
		//채팅 내역 붙이는 거
		if (result[i].userId == userId){
			div = document.createElement('div');
   	   		div.setAttribute('class', 'message parker');
   	   		div.innerText = result[i].chatLog;
		}else if (result[i].userId != userId){
			div = document.createElement('div');
   	    	div.setAttribute('class', 'message stark');
   	    	div.innerText = result[i].userId + ' : ' + result[i].chatLog;
		}
   		document.querySelector('.messages').append(div);
	}
	scrollbottom();
}

//엔터키 입력시 메세지 전송
function pressEnter(event){
	document.querySelector('.sendMessage').value;
	if(event.keyCode == 13){
		sendContent();
	}
	scrollbottom();
}

//메세지 보내는거
function sendContent() {
    stompClient.send("/app/hello/"+planId, {}, 
    JSON.stringify({
    'planId' : planId,
    'userId' : userId,
    'content': document.querySelector('.sendMessage').value}));
	document.querySelector('.sendMessage').value = '';
}

//화면에 메시지를 표시하는 함수
function showMessage(message) {
	let divTag = document.createElement('div');
	if (message.userId == userId){
	   divTag = document.createElement('div');
   	   divTag.setAttribute('class', 'message parker');
   	   divTag.innerText = message.content;
	}else{
		divTag = document.createElement('div');
   	    divTag.setAttribute('class', 'message stark');
   	    divTag.innerText = message.userId + ' : ' + message.content;
	}
   document.querySelector('.messages').append(divTag);
   scrollbottom();
}

//메세지 하단 고정
function scrollbottom(){
	const el = document.querySelector(".messages");
	    console.log(el.scrollHeight <= el.clientHeight + el.scrollTop);
	
	    // 요소가 추가 되기 전에 비교
	    // 스크롤이 최하단 일때만 고정
	    if (el.scrollHeight > el.clientHeight + el.scrollTop) {
	      el.scrollTop = el.scrollHeight;
	    }

}