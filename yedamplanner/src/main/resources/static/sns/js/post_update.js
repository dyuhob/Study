/**
 * post_update.js
 */
function showEditModal() {
    var postId = document.querySelector("#postEditModal").getAttribute("data-post-id");
    var content = document.querySelector(".post-content").textContent;

    document.querySelector("#editPostTitle").value = `게시글 제목(가칭) - ${postId}`;
    document.querySelector("#editPostContent").value = content;

    $("#postReadModal").modal("hide");
    $("#postEditModal").modal("show");
}

document.querySelector("#saveUpdatePost").addEventListener("click", function () {
    const postId = document.querySelector("#postEditModal").getAttribute("data-post-id");
    const editedTitle = document.querySelector("#editPostTitle").value;
    const editedContent = document.querySelector("#editPostContent").value;

    console.log("게시글 ID: ", postId);
    console.log("수정된 제목: ", editedTitle);
    console.log("수정된 내용: ", editedContent);

    $("#postEditModal").modal("hide");
});

//
$('sns_post_border').on('click', function(){
    let pno = $(this).data("pno");
    console.log(pno);
})
