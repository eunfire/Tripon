$(document).ready(function () {
    var memId = $("#memId");
    var pw = $("#pw");
    var checkPw = $("#checkPw");
    var nick = $("#nick");
    var email = $("#email");
    var code = $("#code");
    var idRegex = /^[A-Za-z0-9]{4,11}$/;
    var pwRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,14}$/;
    var nickRegex = /^[a-zA-Z0-9가-힣]{2,11}$/;
    var emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    var codeRegex = /^[a-zA-Z0-9]{6}$/;
    var check = {
        memId: false,
        pw: false,
        checkPw: false,
        nick: false,
        email: false,
        code: false
    };

    // 키보드에서 손을 땠을 때 각각의 function() 실행
    memId.on("keyup", validateMemId);
    pw.on("keyup", validatePw);
    checkPw.on("keyup", validateCheckPw);
    nick.on("keyup", validateNick);
    email.on("keyup", validateEmail);

    // 아이디 유효성 검사
    function validateMemId() {
        if (!idRegex.test(memId.val())) {
            check.memId = false;
            $("#faId").show();
            $("#duId").hide();
            $("#suId").hide();
        } else {
            $.ajax({
                url: "/sign/checkid",
                type: "POST",
                data: {"memId": memId.val()},
                success: function (data) {
                    if (data === 1) {
                        check.memId = false;
                        $("#faId").hide();
                        $("#duId").show();
                        $("#suId").hide();
                    } else {
                        check.memId = true;
                        $("#faId").hide();
                        $("#duId").hide();
                        $("#suId").show();
                    }
                }
            });
        }
    }

    // 비밀번호 유효성 검사
    function validatePw() {
        if (!pwRegex.test(pw.val())) {
            check.pw = false;
            $("#faPw").show();
            $("#suPw").hide();
        } else {
            check.pw = true;
            $("#faPw").hide();
            $("#suPw").show();
        }
    }

    // 비밀번호 일치 확인
    function validateCheckPw() {
        if (pw.val() !== checkPw.val()) {
            check.checkPw = false;
            $("#faPwCheck").show();
            $("#suPwCheck").hide();
        } else {
            check.checkPw = true;
            $("#faPwCheck").hide();
            $("#suPwCheck").show();
        }
    }

    // 닉네임 유효성 검사
    function validateNick() {
        if (!nickRegex.test(nick.val())) {
            check.nick = false;
            $("#faNick").show();
            $("#duNick").hide();
            $("#suNick").hide();
        } else {
            $.ajax({
                url: "/sign/checknick",
                type: "POST",
                data: {"nick": nick.val()},
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
                }
            });
        }
    }

    // 이메일 유효성 검사
    function validateEmail() {
        if (!emailRegex.test(email.val())) {
            check.email = false;
            $("#faEmail").show();
            $("#duEmail").hide();
            $("#suEmail").hide();
        } else {
            $.ajax({
                url: "/sign/checkemail",
                type: "POST",
                data: {"email": email.val()},
                success: function (data) {
                    if (data === 1) {
                        check.email = false;
                        $("#faEmail").hide();
                        $("#duEmail").show();
                        $("#suEmail").hide();
                    } else {
                        check.email = true;
                        $("#faEmail").hide();
                        $("#duEmail").hide();
                        $("#suEmail").show();
                    }
                }
            });
        }
    }

    // 인증 코드 이메일 발송
    function sendMail() {
        $.ajax({
            url: "/sign/mail",
            type: "post",
            data: {"email": email.val()},
            success: function (data) {
                $("#checkCode").show();
                $("#suEmail").hide();
                $("#MyTimer").show();
                alert("인증코드가 발송되었습니다. 이메일을 확인해주세요.");

            }
        });
    }

    // 이메일 인증 버튼에 클릭 이벤트 리스너 추가
    $("#emailbtn").click(function() {
        // 이메일이 유효하다면 sendMail 함수 실행
        if (check.email) {
            sendMail();
            var minutes = 5;
            var display = document.querySelector('#MyTimer');
            var fiveMinutes = (60 * minutes) - 1;
            startTimer(fiveMinutes, display);
        } else {
            // 유효하지 않을 경우 사용자에게 메시지 표시
            alert("이메일 주소를 다시 확인해 주세요.");
        }
    });

    // 인증 코드 검증
    function checkCode() {
        $.ajax({
            url: "/sign/checkcode",
            type: "post",
            data: {"code": code.val()},
            success: function (response) {
                if (response === "success") {
                    $("#suCode").css("display", "block");
                    $("#time").hide();
                } else if (response === "failure") {
                    $("#faCode").css("display", "block");
                } else {
                    $("#duCode").css("display", "block");
                }
            }
        });
    }

    // 인증코드 검증 버튼에 클릭 이벤트 리스너 추가
    $("#codebtn").click(function() {
        // 이메일이 유효하다면 sendMail 함수 실행
        if (codeRegex.test(code.val())) {
            checkCode();
        } else {
            // 유효하지 않을 경우 사용자에게 메시지 표시
            alert("인증 코드를 다시 확인해 주세요.");
        }
    });

    //타이머
    function startTimer(duration, display) {
        var timer = duration, minutes, seconds;
        var interval = setInterval(function () {
            // 분과 초를 계산
            minutes = parseInt(timer / 60, 10)
            seconds = parseInt(timer % 60, 10);

            // 시간을 두 자리 숫자로 표시하도록 포맷팅
            minutes = minutes < 10 ? "0" + minutes : minutes;
            seconds = seconds < 10 ? "0" + seconds : seconds;

            // HTML 요소에 시간을 표시
            display.textContent = minutes + ":" + seconds;
            // 타이머가 0일 때
            if(timer === 0) {
                // 타이머를 멈추고
                clearInterval(interval);
                // "인증 코드 만료"라고 표시
                display.textContent = "인증 코드 만료";
            }
            // 타이머 감소
            timer--;
        }, 1000); // 1초 간격으로 타이머 업데이트
    }


    // 폼 제출 활성화 가능한지 확인
    function validateForm() {
        return check.memId && check.pw && check.checkPw && check.nick && check.email;
    }

    // 유효성 검사 false일 때 실행
    $("#formSignup").submit(function(event) {
        if (!validateForm()) {
            event.preventDefault(); // 기본 이벤트 중지
            alert("잘못된 부분이 있습니다. 다시 한번 확인해 주세요.");
        }
    });
});
