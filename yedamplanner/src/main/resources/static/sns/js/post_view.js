/**
 * post_view.js
 */
// 게시글 조회
function openModal(postId, memberId) {
  fetch('/sns/getPost/' + postId)
    .then(response => response.json())
    .then(data => {
      // 게시글에 회원 아이디, 글 내용
      document.getElementById('memberId').innerText = data.memberId;
      document.getElementById('snsPostContent').innerText = data.snsPostContent;
      document.getElementById('memberPhoto').setAttribute('src', data.memberPhoto);

      // console.log(data);
      
      // 좋아요 카운트 표시
      fetchLikeCount(postId);

      // 댓글 리스트
      getCommentList(postId);
      let postReadModal = new bootstrap.Modal(document.getElementById('postReadModal'), {}); // 부트스트랩 모달 열기 명령어
      postReadModal.show();
      
      //postModifyButton 요소의 data-post-id 데이터 속성에 postId를 할당하는 코드
      document.getElementById('postModifyButton').dataset.postId = postId; 

      // 작성자 체크
      $.ajax({
        url: "/sns/memberCheck",
        type: "POST",
        data: {
          postId: postId
        },
        success: function(nowMemberId) {
          // 수정, 삭제 버튼이 보이게 처리
          // 수정, 삭제 버튼이 보이지 않게 처리
          if (nowMemberId.toString() === data.memberId.toString()) {
            
            // 작성자
            $("#postModifyButton").show();
            $("#deletePostButton").show();
          } else {
            // 작성자X
            $("#postModifyButton").hide();
            $("#deletePostButton").hide();
          }
        },
        error: function(xhr, status, error) {
          console.log("에러 발생", error);
        }
      });

    })
    .catch((error) => {
      console.error(error);
    }); 
}

// 댓글 리스트 전송
function getCommentList(postId) {
$.ajax({
  type: 'GET',
  url: '/sns/getCommentList?postId=' + postId,
  dataType: 'JSON',
  success: function (data) {
    postCommentList(data);
  },
  error: function (request, status, error) {
    console.log(error);
  }
});
}

// 댓글 리스트 생성
function postCommentList(commentList) {
let postId = document.getElementById("postModifyButton").dataset.postId;
let commentListContainer = document.getElementById('commentList');
commentListContainer.innerHTML = '';

commentList.forEach(function (comment) {
  viewComment(comment, postId, memberId);
});
}

// 댓글 엘리먼트 생성
function viewComment(comment, postId, memberId) {

let commentList = document.getElementById('commentList'); // 리스트
let commentItem = document.createElement('li'); // li
commentItem.className = 'comment_css';

let commentAuthor = document.createElement('span'); // 작성자
commentAuthor.className = 'comment-author';
commentAuthor.textContent = comment.memberId + " : ";

let commentContent = document.createElement('span'); // 댓글 내용
commentContent.className = 'comment-content';
commentContent.textContent = comment.snsCommentContent;

let commentDate = document.createElement('span'); // 등록 일자
commentDate.className = 'comment-date';
commentDate.style.float = 'right';

let commentDelBtn = document.createElement('button'); // 댓글 삭제 버튼
commentDelBtn.className = 'comment-delete';
commentDelBtn.style.marginLeft = '10px';
commentDelBtn.style.display = 'inline-block';
commentDelBtn.dataset.commentNum = comment.snsCommentNum;
commentDelBtn.style.border = 'none'; // 테두리 제거
commentDelBtn.style.backgroundColor = 'transparent'; // 배경 색상 제거
commentDelBtn.style.outline = 'none'; // 포커스 시 테두리 제거

let btnIcon = document.createElement('i'); // X 마크 설정
btnIcon.className = 'fas fa-times';
btnIcon.style.fontSize = '16px';
btnIcon.style.color = 'red';
commentDelBtn.appendChild(btnIcon); // 버튼에 붙이기

// 날짜 형식
let commentDateFormat = new Date (comment.snsCommentDate).toLocaleDateString();
commentDate.textContent = commentDateFormat;

commentItem.appendChild(commentAuthor);
commentItem.appendChild(commentContent);
commentItem.appendChild(commentDelBtn);
commentItem.appendChild(commentDate);
commentList.appendChild(commentItem);

commentDelBtn.addEventListener('click', function() {
  let commentNum = commentDelBtn.dataset.commentNum;
  let commentMemberId = comment.memberId;
  deleteComment(commentNum, postId, commentMemberId, memberId);
});
}

// 댓글 삭제 처리 기능
// 댓글번호, 게시글 번호, 회원 아이디, 댓글 작성한 회원 아이디
function deleteComment(commentNum, postId, memberId, commentMemberId) {
if (memberId == commentMemberId) { // 작성자와 현재 사용자의 아이디가 같을 경우
  let deleteAlert = confirm("정말 삭제하시겠습니까?");

  if (deleteAlert) { // 작성자와 현재 사용자의 아이디가 동일한 경우
    $.ajax({
      url: '/sns/deleteComment',
      type: 'POST',
      data: {
        snsCommentNum: commentNum,
        snsPostNum: postId
      },
      success: function() {
        getCommentList(postId); // 성공
        alert("삭제 완료되었습니다")
      },
      error: function(xhr, status, error) {
        console.log("에러 발생", error);
      }
    });
  }
} else { // 작성자와 현재 사용자의 아이디가 다를 경우
  alert("작성자만 삭제가 가능합니다"); // 실패
}
}


// 댓글 작성
document.getElementById("commentForm").addEventListener("submit", function (e) {
e.preventDefault();

// 작성후 처리
let postId = document.getElementById("postModifyButton").dataset.postId;
let snsCommentContent = e.target.querySelector("textarea").value;

insertComment(postId, snsCommentContent);
})

// 댓글 작성 이벤트
function insertComment(postId, snsCommentContent) {
$.ajax ({
  type: "POST",
  url: "/sns/insertComment",  
  data: {
    snsPostNum: postId,
    snsCommentContent: snsCommentContent,
  },
  success: function (data) {
    getCommentList(postId);
    let commentModal = bootstrap.Modal.getInstance ( // 모달창 닫기
      document.getElementById("postCommentModal")
    );
    commentModal.hide();
    document.getElementById("commentForm").reset();
  },
  error: function (request, status, error) {
    console.log(error);
  },
});
}

// 좋아요 이벤트
document.getElementById("likeButton").addEventListener("click", function() {
let postId = document.getElementById("postModifyButton").dataset.postId;
console.log('postId = ' + postId);
snsLikeCheck(postId);
});

// 좋아요 ajax
function snsLikeCheck(postId) {
console.log(postId);
$.ajax({
  type: "post",
  url: "/sns/like",
  data: JSON.stringify({ snsPostNum: postId }),
  contentType: "application/json",
  success: function(response) {
    console.log('response : ' + response);
    if (response.likeAdd) {
      alert("좋아요 완료");
    } else {
      alert("이미 좋아요를 하셨습니다.");
    }
    var likeCountElem = document.getElementById("likeCount");
    var likeCount = response.likeCount;
    likeCountElem.innerHTML = '<i class="fas fa-heart"></i> ' + likeCount;
    console.log(likeCount);
    // 좋아요 카운트 증가
    document.getElementById("likeCount").innerHTML = '<i class="fas fa-heart"></i> ' + response.likeCount;

  },
  error: function(request, status, error) {
    alert("이미 좋아요를 하셨습니다." + error);
  }
});
}

// 게시글 조회 Modal 좋아요 카운트 가져오기
function fetchLikeCount(postId) {
  fetch('/sns/getLikeCount?postId=' + postId)
    .then(response => response.json())
    .then(data => {
      document.getElementById("likeCount").innerHTML = '<i class="fas fa-heart"></i> ' + data.likeCount;
    })
    .catch((error) => {
      console.error(error);
    });
}

// 게시글 리스트 좋아요 카운트 표시
function fetchLikeCounts() {
  const postItems = document.querySelectorAll('.post-item');
  
  postItems.forEach((postItem) => {
      const postId = postItem.getAttribute('data-post-id');
      const likeCountElement = postItem.querySelector('.like_count span');
      
      // RequestParam으로 변경된 API에 맞게 수정합니다.
      fetch(`/sns/getLikeCount?postId=${postId}`)
          .then((response) => response.json())
          .then((data) => {
              likeCountElement.textContent = data.likeCount;
          });
  });
}

document.addEventListener('DOMContentLoaded', fetchLikeCounts);