$(document).ready(function () {
    var pw = $("#checkPw");

    $("#editPw").submit(function (event) {
        event.preventDefault();// 기본 이벤트 중지

        // 비밀번호 변경 요청
        $.ajax({
            type: "POST",
            url: "/mypage/checkpwprocess",
            data: {pw : pw.val()},
            success: function (response) {
                if (response === "fail") {
                    alert("현재 비밀번호를 확인해주세요.");
                } else {
                    window.location.href = "/mypage/myinfoedit";
                }
            },
            error: function (xhr, status, error) {
                alert('오류가 발생하였습니다. 나중에 다시 시도해주세요.');
            }
        });
    });
});