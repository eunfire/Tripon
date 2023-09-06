//아이디 유효성검사
let elInputUsername = document.querySelector('#username');
let elFailureMessage = document.querySelector('.failure-message');
let elSuccessMessage = document.querySelector('.success-message');

//비밀번호 유효성검사
let pwInputPassword = document.querySelector('#password'); 
let pwInputPasswordRe = document.querySelector('#password-retype'); 
let pwFailureMessage = document.querySelector('.mismatch-message'); 
let pwSuccessMessage = document.querySelector('.success-password');
let pwSuccessMessageRe = document.querySelector('.success-passwordRe');
let pwMissMessage = document.querySelector('.miss-message');

//휴대폰번호 유효성검사
let InputCellPhone = document.querySelector('#cellPhone');
let cpFailureMessage = document.querySelector('.miss-phone');
let cpSuccessMessage = document.querySelector('.success-phone');

//이메일 유효성검사
let emInputEmail = document.querySelector('#email');
let emFailureMessage = document.querySelector('.emfailure-message');
let emSuccessMessage = document.querySelector('.emsuccess-email');

//변경 비밀번호 유효성검사
let pwInputPasswordch = document.querySelector('#password-change'); 
let pwInputPasswordRech = document.querySelector('#password-retype-ch'); 
let pwFailureMessagech = document.querySelector('.mismatch-message-ch'); 
let pwSuccessMessagech = document.querySelector('.success-password-ch');
let pwSuccessMessageRech = document.querySelector('.success-passwordRe-ch');
let pwMissMessagech = document.querySelector('.miss-message-ch');


//아이디
elInputUsername.onkeyup = function (){ 

  //4자리 이상이 true면 && 오직숫자,영어만
  if(isMoreThan4Length(elInputUsername.value) && onlyNumberAndEnglish(elInputUsername.value)){ 
    //성공 메시지가 보여야 함
    elSuccessMessage.classList.remove('hide');
    //실패 메시지가 가려져야 함
    elFailureMessage.classList.add('hide');
  }
  //4자리 이상이 false면
  else{
    //성공 메시지가 가려져야 함
    elSuccessMessage.classList.add('hide');
    //실패 메시지가 보여야 함
    elFailureMessage.classList.remove('hide');
  }
}

//비밀번호
pwInputPassword.onkeyup = function(){

  // 비밀번호 양식(유효성 함수 검증)이 true면
  if(strongPassword(pwInputPassword.value)){
    pwMissMessage.classList.add('hide');
    
    pwSuccessMessage.classList.remove('hide');
  }
  // 비밀번호 양식(유효성 함수 검증)이 false면
  else{
    pwMissMessage.classList.remove('hide');
    
    pwSuccessMessage.classList.add('hide');
  }
  
  // 비밀번호 입력 값과 비밀번호 확인 입력 값이 같다면
  if(isMatch (pwInputPassword.value, pwInputPasswordRe.value)){
    pwFailureMessage.classList.add('hide');
    
    pwSuccessMessageRe.classList.remove('hide');  
  }
  // 비밀번호 입력 값과 비밀번호 확인 입력 값이 다르면
  else{
    pwFailureMessage.classList.remove('hide');
    
    pwSuccessMessageRe.classList.add('hide');
  }
}

//비밀번호 확인
pwInputPasswordRe.addEventListener('keyup', () => { //addEventHandler 함수 사용  keyup

  if(isMatch (pwInputPassword.value, pwInputPasswordRe.value)){ //유효성 검사
    pwFailureMessage.classList.add('hide');
    
    pwSuccessMessageRe.classList.remove('hide');
  }
  else{
    pwFailureMessage.classList.remove('hide');
    
    pwSuccessMessageRe.classList.add('hide');
  }
})

//휴대전화
InputCellPhone.addEventListener('keyup', () => { //addEventHandler 함수 사용  keyup

  if(onlyNumber(InputCellPhone.value)){ //유효성 검사
    cpFailureMessage.classList.add('hide');
    
    cpSuccessMessage.classList.remove('hide');
  }
  else{
    cpFailureMessage.classList.remove('hide');
    
    cpSuccessMessage.classList.add('hide');
  }
})

//이메일
emInputEmail.addEventListener('keyup', () => {

  
   if(strongemail(emInputEmail.value)){ //유효성 검사
    
    emFailureMessage.classList.add('hide');
    
    emSuccessMessage.classList.remove('hide');
  } 
  else{
    
    emFailureMessage.classList.remove('hide');
    
    emSuccessMessage.classList.add('hide');
  }
})

//변경 비밀번호
pwInputPasswordch.onkeyup = function(){

  // 비밀번호 양식(유효성 함수 검증)이 true면
  if(strongPassword(pwInputPasswordch.value)){
    pwMissMessagech.classList.add('hide');
    
    pwSuccessMessagech.classList.remove('hide');
  }
  // 비밀번호 양식(유효성 함수 검증)이 false면
  else{
    pwMissMessagech.classList.remove('hide');
    
    pwSuccessMessagech.classList.add('hide');
  }
  
  // 비밀번호 입력 값과 비밀번호 확인 입력 값이 같다면
  if(isMatch (pwInputPassword.value, pwInputPasswordRe.value)){
    pwFailureMessagech.classList.add('hide');
    
    pwSuccessMessageRech.classList.remove('hide');  
  }
  // 비밀번호 입력 값과 비밀번호 확인 입력 값이 다르면
  else{
    pwFailureMessagech.classList.remove('hide');
    
    pwSuccessMessageRech.classList.add('hide');
  }
}

//비밀번호 확인
pwInputPasswordRe.addEventListener('keyup', () => { //addEventHandler 함수 사용  keyup

  if(isMatch (pwInputPasswordch.value, pwInputPasswordch.value)){ //유효성 검사
    pwFailureMessagech.classList.add('hide');
    
    pwSuccessMessageRech.classList.remove('hide');
  }
  else{
    pwFailureMessagech.classList.remove('hide');
    
    pwSuccessMessageRech.classList.add('hide');
  }
})