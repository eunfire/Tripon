$(document).ready(function () {
    var pw = $("#pw");
    var changePw = $("#changePw");
    var checkPw = $("#checkPw");
    var pwRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,14}$/;
    var check = {
        changePw: false,
        checkPw: false
    };

    // 폼 제출 활성화 가능한지 확인
    function validateForm() {
        return check.changePw && check.checkPw && (pw.val() !== changePw.val());
    }

    // 비밀번호 유효성 검사
    function validatePw() {
        if (!pwRegex.test(changePw.val())) {
            check.changePw = false;
            $("#faPw").show();
            $("#duPw").hide();
            $("#suPw").hide();
        } else if (pw.val() === changePw.val()) {
            check.changePw = false;
            $("#faPw").hide();
            $("#duPw").show();
            $("#suPw").hide();
        } else {
            check.changePw = true;
            $("#faPw").hide();
            $("#duPw").hide();
            $("#suPw").show();
        }
    }

    // 비밀번호 일치 확인
    function validateCheckPw() {
        if (changePw.val() !== checkPw.val()) {
            check.checkPw = false;
            $("#faPwCheck").show();
            $("#suPwCheck").hide();
        } else {
            check.checkPw = true;
            $("#faPwCheck").hide();
            $("#suPwCheck").show();
        }
    }

    // 키보드에서 손을 땠을 때 각각의 function() 실행
    changePw.on("keyup", validatePw);
    checkPw.on("keyup", validateCheckPw);

    $("#editPw").submit(function (event) {
        event.preventDefault();// 기본 이벤트 중지
        if (validateForm()) {
            // Form 데이터 가져오기
            var formData = {
                pw : pw.val(),
                changePw : changePw.val(),
                checkPw : checkPw.val()
            };

            // 비밀번호 변경 요청
            $.ajax({
                type: "POST",
                url: "/mypage/pwchangeProcess",
                data: formData,
                success: function (response) {
                    if (response === "invalidPw") {
                        alert("현재 비밀번호를 확인해주세요.");
                    } else if (response === "pwMismatch") {
                        alert("새로운 비밀번호와 비밀번호 확인이 일치하지 않습니다.\n다시 확인해주세요.");
                    } else if (response === "success") {
                        alert("비밀번호가 변경되었습니다. 다시 로그인해 주세요.");
                        window.location.href = "/logout";
                    }
                },
                error: function (xhr, status, error) {
                    alert('오류가 발생하였습니다. 나중에 다시 시도해주세요.');
                }
            });
        } else {
            if (changePw.val() !== checkPw.val()) {
                alert("새로운 비밀번호와 비밀번호 확인이 일치하지 않습니다.\n다시 확인해주세요.");
            } else if (pw.val() === changePw.val()) {
                alert("현재 비밀번호와 새로운 비밀번호가 일치합니다.\n다시 확인해주세요.")
            }
        }
    });
});