/**
 * post_delete.js
 */
// 게시글 삭제
$('#deletePostButton').on('click', function() {
  let postId = $('#postModifyButton').data('post-id');
  console.log(postId);

  if (confirm('정말 삭제하시겠습니까?')) {
    $.ajax({
      type: 'post',
      url: '/sns/deletePost',
      async: true,
      headers: {
        'Content-Type': 'application/json',
      },
      data: JSON.stringify({ 'snsPostNum': postId }),
      dataType: 'json',
      success: function(result) {
        alert('삭제되었습니다.');
        location.reload(); // 새로고침
      },
      error: function(request, status, error) {
        console.log(error);
      },
    });
  }
});
