$(document).ready(function () {
    var nick = $("#nick");
    var nickRegex = /^[a-zA-Z0-9가-힣]{2,11}$/;
    var oldNick = nick.val(); // 기존의 닉네임 값을 가져옴
    var check = {
        nick: false
    };

    // 폼 제출 활성화 가능한지 확인
    function validateForm() {
        return check.nick;
    }

    // 닉네임 유효성 검사
    function validateNick() {
        var newNick = nick.val();

        // 새로운 닉네임이 기존의 닉네임과 다른 경우에만 AJAX 요청을 보냄
        if (newNick !== oldNick) {
            if (!nickRegex.test(newNick)) {
                check.nick = false;
                $("#faNick").show();
                $("#duNick").hide();
                $("#suNick").hide();
            } else {
                $.ajax({
                    url: "/sign/checknick",
                    type: "POST",
                    data: {"nick": newNick},
                    success: function (data) {
                        if (data === 1) {
                            check.nick = false;
                            $("#faNick").hide();
                            $("#duNick").show();
                            $("#suNick").hide();
                        } else {
                            check.nick = true;
                            $("#faNick").hide();
                            $("#duNick").hide();
                            $("#suNick").show();
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error("Error checking nick:", error);
                        check.nick = false;
                    }
                });
            }
        } else {
            // 새로운 닉네임이 기존의 닉네임과 동일한 경우에는 유효성 검사 통과
            check.nick = true;
            $("#faNick").hide();
            $("#duNick").hide();
            $("#suNick").hide();
        }
    }

    // 키보드에서 손을 땠을 때 유효성 검사 실행
    nick.on("keyup", validateNick);

    // 유효성 검사 false일 때 실행
    $("#editInfo").submit(function(event) {
        validateNick();
        if (!validateForm()) {
            event.preventDefault(); // 기본 이벤트 중지
            alert("잘못된 부분이 있습니다. 다시 한번 확인해 주세요.");
        }
    });
});