// 4글자 이상이면 true
function isMoreThan4Length(value) {
  return value.length >= 4;
}

// 두 값이 같으면 true
function isMatch (password1, password2) {
  return password1 === password2;
}

// 숫자로만 최소 11자리
function onlyNumber(num){
  return /^\d{11,}$/.test(num);
}

// [유효성 검증 함수]: 영어 또는 숫자만 가능(무조건 영어로 시작)
function onlyNumberAndEnglish(str) {
  return /^[A-Za-z][A-Za-z0-9]*$/.test(str);
}

// [유효성 검증 함수]: 최소 8자 이상하면서, 알파벳과 숫자 및 특수문자(@$!%*#?&) 는 하나 이상 포함
function strongPassword(str) {
  return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/.test(str);
}

// [유효성 검증 함수]: 이메일 검사
// 4글자 이상(\w = [a-zA-Z0-9_], [\w-\.]) @가 나오고
// 1글자 이상(주소). 글자 가 1~3번 반복됨
function strongemail(str) {
  return /^[\w]{4,}@[\w]+(\.[\w]+){1,3}$/.test(str);
}