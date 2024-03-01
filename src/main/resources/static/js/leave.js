var withdrawButton = document.getElementById("withdrawButton");
var closeButton = document.getElementById("closeButton")
var passwordModal = document.getElementById("passwordModal");
var passwordInput = document.getElementById("passwordInput");

// 탈퇴하기 버튼 클릭 시 모달 창 나타나기
withdrawButton.addEventListener("click", function (event) {
    event.preventDefault(); // 기본 이벤트 중지
    passwordModal.style.display = "block";
});

// 모달 창 닫기 클릭 시 비밀번호 입력 값 비우면서 사라짐
closeButton.addEventListener("click", function () {
    passwordModal.style.display = "none";
    passwordInput.value = ""; // 입력 필드 비움
});

// 모달 창 외부 클릭 시 모달 창 닫기
window.addEventListener("click", function (event) {
    if (event.target === passwordModal) {
        passwordModal.style.display = "none";
        passwordInput.value = ""; // 입력 필드 비움
    }
});
