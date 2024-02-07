/*$(document).ready(function () {
    var login = '${memId}';

    function liked(p, l) {
        if (login == "null") {
            alert ("로그인이 필요합니다.");
        }
        else {
            location.href = "/Community/post/" + p + "/like?postId=" + p + "&isLiked=" + l;
        }
    }
});*/

$(document).ready(function () {
    var memId = '${memId}'; // principal.getName()을 통해 가져온 사용자 아이디

    // 좋아요 버튼에 대한 클릭 이벤트 핸들러
    $('.fullHeart, .emptyHeart').click(function() {
        var postId = $(this).data('post-id'); // 클릭한 SVG의 data-post-id 속성 값 가져오기
        var isLiked = $(this).data('is-liked'); // 클릭한 SVG의 data-is-liked 속성 값 가져오기

        if (memId === "null") {
            alert("로그인이 필요합니다.");
        } else {
            location.href = "/Community/post/like?postId=" + postId + "&isLiked=" + isLiked;
        }
    });
});