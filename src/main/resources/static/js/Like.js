$(document).ready(function () {

    $('.fullHeart, .emptyHeart').click(function() {
        var postId = $(this).data('postid');
        var isLiked = $(this).data('isliked');
        console.log(postId)
        console.log(isLiked)

        liked(postId, isLiked);
        function liked(postId, isLiked) {
            $.ajax({
                url: "/Community/post/" + postId + "/like",
                method: "POST",
                data: { postId: postId, isLiked: isLiked },
                success: function(response) {
                    if(response === "Login required" ) {
                        alert("좋아요를 누를려면 로그인이 필요합니다.");
                    } else {
                        window.location.reload();
                    }
                },
                error: function(xhr, status, error) {
                    // 오류 처리
                    console.error('좋아요 요청에 실패했습니다.');
                }
            });
        }
    });
});
