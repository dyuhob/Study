/**
 * 
 */
 
 $(document).ready(function() {
    $('#addBtn').click(function() {
      $('#addModal').modal('show');
    });
});

// modalCloseBtn 클릭 이벤트 핸들러 등록
$('#modalCloseBtn').click(function() {
    // addModal을 숨기기 (종료하기)
    $('#addModal').modal('hide');
});

// 결제 api
