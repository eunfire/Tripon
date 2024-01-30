$(document).ready(function () {
    var memId = $("#memId");
    var pw = $("#pw");
    var checkPw = $("#checkPw");
    var nick = $("#nick");
    var idRegex = /^[A-Za-z0-9]{4,11}$/;
    var pwRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,14}$/;
    var nickRegex = /^[a-zA-Z0-9가-힣]{2,11}$/;
    var check = {
        memId: false,
        pw: false,
        checkPw: false,
        nick: false
    };

    // 폼 제출 활성화 가능한지 확인
    function validateForm() {
        return check.memId && check.pw && check.checkPw && check.nick;
    }

    // 폼 제출 활성화 여부 업데이트하여 validateForm()값이 true되면 폼 제출 활성화
    function updateSubmitButton() {
        if (validateForm()) {
            $("#formSignup").submit();
        }
    }

    // 아이디 유효성 검사
    function validateMemId() {
        if (!idRegex.test(memId.val())) {
            $("#faId").show();
            $("#duId").hide();
            $("#suId").hide();
            check.memId = false;
        } else {
            $.ajax({
                url: "/sign/checkid",
                type: "POST",
                data: {"memId": memId.val()},
                success: function (data) {
                    if (data === 1) {
                        $("#faId").hide();
                        $("#duId").show();
                        $("#suId").hide();
                        check.memId = false;
                    } else {
                        $("#faId").hide();
                        $("#duId").hide();
                        $("#suId").show();
                        check.memId = true;
                    }
                    updateSubmitButton();
                }
            });
        }
    }

    // 비밀번호 유효성 검사
    function validatePw() {
        if (!pwRegex.test(pw.val())) {
            $("#faPw").show();
            $("#suPw").hide();
            check.pw = false;
        } else {
            $("#faPw").hide();
            $("#suPw").show();
            check.pw = true;
        }
        updateSubmitButton();
    }

    // 비밀번호 일치 확인
    function validateCheckPw() {
        if (pw.val() !== checkPw.val()) {
            $("#faPwCheck").show();
            $("#suPwCheck").hide();
            check.checkPw = false;
        } else {
            $("#faPwCheck").hide();
            $("#suPwCheck").show();
            check.checkPw = true;
        }
        updateSubmitButton();
    }

    // 닉네임 유효성 검사
    function validateNick() {
        if (!nickRegex.test(nick.val())) {
            $("#faNick").show();
            $("#duNick").hide();
            $("#suNick").hide();
            check.nick = false;
        } else {
            $.ajax({
                url: "/sign/checknick",
                type: "POST",
                data: {"nick": nick.val()},
                success: function (data) {
                    if (data === 1) {
                        $("#faNick").hide();
                        $("#duNick").show();
                        $("#suNick").hide();
                        check.nick = false;
                    } else {
                        $("#faNick").hide();
                        $("#duNick").hide();
                        $("#suNick").show();
                        check.nick = true;
                    }
                    updateSubmitButton();
                }
            });
        }
    }

    // 키보드에서 손을 땠을 때 각각의 function() 실행
    memId.on("keyup", validateMemId);
    pw.on("keyup", validatePw);
    checkPw.on("keyup", validateCheckPw);
    nick.on("keyup", validateNick);

    // 유효성 검사 false일 때 실행
    $("#formSignup").submit(function(event) {
        if (!validateForm()) {
            event.preventDefault(); // 기본 이벤트 중지
            alert("잘못된 부분이 있습니다. 다시 한번 확인해 주세요.");
        }
    });
});
