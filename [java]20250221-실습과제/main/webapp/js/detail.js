$(document).ready(function () {
    console.log("detail.js 로드됨!"); // JS 파일이 제대로 로드되는지 확인

    $("#commentForm").off("submit").on("submit", function (event) {
        event.preventDefault(); // 기본 폼 제출 방지
        console.log("폼 제출 이벤트 실행됨!"); // AJAX 실행 여부 확인

        let boardId = $("input[name='boardId']").val();
        let content = $("textarea[name='content']").val().trim();

        if (content === "") {
            alert("댓글을 입력하세요!");
            return false;
        }

        console.log("보낼 데이터:", {boardId: boardId, content: content}); // 데이터 확인

        $.ajax({
            type: "POST",
            url: "/comment/post",
            contentType: "application/json",
            dataType: "json", // 응답을 JSON으로 자동 파싱
            data: JSON.stringify({
                boardId: boardId,
                content: content
            }),
            success: function (response) {
                console.log("댓글 추가 성공", response);

                if (response.success) { // 서버 응답이 성공일 경우에만 추가
                    console.log("추가시작")
                    $("#commentList").append(
                        `<div class="comment">
                    <p><strong>사용자</strong> (방금 전)</p>
                    <p>` + content.replace(/\n/g, "<br>") + `</p>
                </div>`
                    );

                    $("textarea[name='content']").val("");
                } else {
                    alert("댓글 추가에 실패했습니다.");
                }
            },
            error: function (error) {
                console.error("댓글 추가 실패", error);
                alert("댓글을 추가하는 중 오류가 발생했습니다.");
            }
        });


        return false; // 폼 제출 방지 (중복 요청 막음)
    });
});
// 좋아요 버튼 클릭 이벤트
$(document).on("click", ".likeBtn", function () {
    let likeButton = $(this);
    let userId = 1;
    let boardId = parseInt($("input[name='boardId']").val(), 10);

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/like/delete",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            userId: userId,
            boardId: boardId
        }),
        success: function (response) {
            if (response.success) {
                alert("좋아요가 삭제되었습니다.");

                // 🔥 기존 요소를 삭제하고 새로운 요소로 교체
                likeButton.replaceWith(`
                        <img class="unLikeBtn" src="/img/unlike.png" height="50" width="50">
                    `);
            } else {
                alert("좋아요 삭제 실패!");
            }
        },
        error: function (error) {
            console.error("좋아요 삭제 요청 실패", error);
            alert("좋아요 삭제 중 오류가 발생했습니다.");
        }
    });
});

// ❗❗ 새로운 `unLikeBtn`에 클릭 이벤트 바인딩
$(document).on("click", ".unLikeBtn", function () {
    let unLikeButton = $(this);

    let userId = 1;
    let boardId = parseInt($("input[name='boardId']").val(), 10);

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/like/post",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            userId: userId,
            boardId: boardId
        }),
        success: function (response) {
            if (response.success) {
                alert("좋아요가 등록되었습니다.");

                // 🔥 `unLikeBtn`을 `likeBtn`으로 다시 변경
                unLikeButton.replaceWith(`
                        <img class="likeBtn" src="/img/like.png" height="50" width="50">
                    `);
            } else {
                alert("좋아요 등록 실패!");
            }
        },
        error: function (error) {
            console.error("좋아요 등록 요청 실패", error);
            alert("좋아요 등록 중 오류가 발생했습니다.");
        }
    });
});
// $(document).ready(function () {
//     $(".unLikeBtn").on("click", function () {
//         let userId = 1;
//         let boardId = parseInt($("input[name='boardId']").val(), 10);
//
//         if (!userId || !boardId) {
//             alert("유효하지 않은 요청입니다.");
//             return;
//         }
//
//         console.log("좋아요 요청 보냄:", { userId: userId, boardId: boardId });
//
//         $.ajax({
//             type: "POST",
//             url: "http://localhost:8080/like/post",
//             contentType: "application/json",
//             dataType: "json",
//             data: JSON.stringify({
//                 userId: userId,
//                 boardId: boardId
//             }),
//             success: function (response) {
//                 console.log("좋아요 요청 성공", response);
//
//                 if (response.success) {
//                     alert("좋아요가 등록되었습니다.");
//                     $(".unLikeBtn").replaceWith(`
//                         <img class="likeBtn" src="/img/like.png" height="50" width="50">
//                     `);
//                 } else {
//                     alert("좋아요 요청 실패!");
//                 }
//             },
//             error: function (error) {
//                 console.error("좋아요 요청 실패", error);
//                 alert("좋아요 요청 중 오류가 발생했습니다.");
//             }
//         });
//     });
// });
//
// $(document).ready(function () {
//     $(".likeBtn").on("click", function () {
//         let userId = 1;
//         let boardId = parseInt($("input[name='boardId']").val(), 10);
//
//         if (!userId || !boardId) {
//             alert("유효하지 않은 요청입니다.");
//             return;
//         }
//
//         console.log("좋아요 취소 요청 보냄:", { userId: userId, boardId: boardId });
//
//         $.ajax({
//             type: "POST",
//             url: "http://localhost:8080/like/delete",
//             contentType: "application/json",
//             dataType: "json",
//             data: JSON.stringify({
//                 userId: userId,
//                 boardId: boardId
//             }),
//             success: function (response) {
//                 console.log("좋아요 삭제 요청 성공", response);
//
//                 if (response.success) {
//                     alert("좋아요가 삭제되었습니다.");
//                     $(".likeBtn").replaceWith(`
//                         <img class="unLikeBtn" src="/img/unlike.png" height="50" width="50">
//                     `);
//                 } else {
//                     alert("삭제 요청 실패!");
//                 }
//             },
//             error: function (error) {
//                 console.error("삭제 요청 실패", error);
//                 alert("삭제 요청 중 오류가 발생했습니다.");
//             }
//         });
//     });
// });

$(document).ready(function () {
    $(".deleteCommentBtn").on("click", function () {
        let commentId = parseInt($(this).data("commentid"), 10); // 정수 변환 보장
        let commentDiv = $(this).closest(".comment"); // 해당 댓글 div 가져오기

        if (!confirm("정말로 이 댓글을 삭제하시겠습니까?")) {
            return;
        }

        console.log("삭제 요청 보냄: commentId =", commentId);

        $.ajax({
            type: "POST",
            url: "http://localhost:8080/comment/delete",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({
                commentId: commentId
            }),
            success: function (response) {
                console.log("댓글 삭제 성공", response);

                if (response.success) {
                    alert("댓글이 삭제되었습니다.");
                    commentDiv.remove(); // 성공하면 화면에서 댓글 삭제
                } else {
                    alert("댓글 삭제 실패!");
                }
            },
            error: function (error) {
                console.error("댓글 삭제 요청 실패", error);
                alert("댓글 삭제 중 오류가 발생했습니다.");
            }
        });
    });
});


