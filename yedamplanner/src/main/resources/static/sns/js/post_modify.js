/**
 * post_modify.js
 */
$('#postModifyButton').on('click', function(){
  let postContent = $('#snsPostContent').text();
  $('#editPostContent').val(postContent);
});

$('#saveUpdatePost').on('click', function() {
    let postId = $('#postModifyButton').data('post-id') // 게시글 번호
    let updatePostContent = $('#editPostContent').val();  // 본문 내용
    var postObj = JSON.stringify({ "snsPostNum" : postId, "snsPostContent" : updatePostContent});
    console.log('postId : ' + postId);
    console.log('updatePostContent : ' + updatePostContent);
    console.log('postObj : ' + postObj);
  
  $.ajax({
      type : 'post',           // 타입 (get, post, put 등등)
      url : '/sns/updatePost',           // 요청할 서버url
      async : true,            // 비동기화 여부 (default : true)
      headers : {              // Http header
        "Content-Type" : "application/json",
      },
      data: postObj,
      dataType : 'json',       // 데이터 타입 (html, xml, json, text 등등)
      success : function(result) { // 결과 성공 콜백함수
              //location.href = "/admin/listingadmin";
              const postElement = $(`.post-item[data-post-id='${postId}'] .post-content`);
              postElement.text(updatePostContent);
              $('#snsPostContent').text(updatePostContent);
              $('#postEditModal').modal('hide');
      },
      error : function(request, status, error) { // 결과 에러 콜백함수
          console.log(error)
      }
    })
});