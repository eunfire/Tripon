const imageInput = document.getElementById('imageInput');
const editableDiv = document.getElementById('editableDiv');
let accumulatedFiles = []; // 사용자가 선택한 파일들을 저장할 배열

// 이미지가 선택될 때마다 미리보기를 표시하는 이벤트 리스너
imageInput.addEventListener('change', function (event) {
  const files = event.target.files;

  if (files) {
    Array.from(files).forEach((file) => {
      // 파일 형식 검증
      if (file.type.startsWith('image/')) {
        // 이미지를 추가하기 전에 이미지 개수가 5개인지 확인
        if (accumulatedFiles.length >= 5) {
          alert('이미지는 최대 5개까지만 선택할 수 있습니다.');
          return;
        }
        showImagePreview(file);
        // 새로 선택된 파일들을 accumulatedFiles 배열에 추가
        accumulatedFiles.push(file);
      }
    });
  }
});

// 이미지 미리보기 표시 함수
function showImagePreview(file) {
  const reader = new FileReader();
  reader.onload = (event) => {
    const img = document.createElement('img');
    img.src = event.target.result;
    img.style.maxWidth = '30%';
    img.style.maxHeight = '30%';
    img.style.margin = '5px';

    // 이미지를 클릭했을 때 삭제되도록 이벤트 리스너 추가
    img.addEventListener('click', function() {
      this.remove(); // 클릭된 이미지를 삭제
      // 해당 이미지를 accumulatedFiles 배열에서도 삭제
      const index = accumulatedFiles.indexOf(file);
      if (index !== -1) {
        accumulatedFiles.splice(index, 1);
      }
    });

    document.getElementById('imagePreviewContainer').appendChild(img); // 이미지 미리보기를 표시할 div에 이미지 추가
  };
  reader.readAsDataURL(file);
}

$('#submitButton').click(function(event) {
  event.preventDefault(); // 폼의 기본 동작인 submit을 막습니다.

  // FormData 객체 생성
  let formData = new FormData();

  // 폼 데이터에 파일 추가
  accumulatedFiles.forEach(function(file) {
    formData.append('images', file);
  });

  // 폼 데이터에 다른 필드 추가
  formData.append('memId', $('#memId').val()); // memId 필드 추가
  formData.append('cateId', $('#post-category-option').val()); // cateId 필드 추가
  formData.append('title', $('#postTitle').val()); // title 필드 추가
  formData.append('content', $('#postContent').val()); // content 필드 추가
  formData.append('tStart', $('#startDate').val()); // tStart 필드 추가
  formData.append('tEnd', $('#endDate').val()); // tEnd 필드 추가

  // AJAX를 사용하여 서버로 데이터 전송
  $.ajax({
    type: "POST",
    url: "/Community/write", // 파일 업로드를 처리할 서버의 엔드포인트
    data: formData,
    processData: false, // 데이터 처리를 jQuery가 처리하지 않도록 설정
    contentType: false, // 컨텐츠 타입을 false로 설정하여 jQuery가 Content-Type 헤더를 설정하지 않도록 함
    success: function(response) {
      // 성공적으로 파일을 업로드한 후에 실행되는 코드
      console.log('파일 업로드 성공:', response);
      window.history.back();
    },
    error: function(xhr, status, error) {
      // 파일 업로드 중에 에러가 발생한 경우에 실행되는 코드
      console.error('파일 업로드 에러:', error);
    }
  });
});

/* 이미지 폼 */
function dragEnter(ev) {
  ev.preventDefault();
}

function drag(ev) {
  ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev) {
  ev.preventDefault();
  var data = ev.dataTransfer.getData("text"); // img태그 아이디를 가져옴
  ev.target.appendChild(document.getElementById(data)); // 다른 div태그에 img를 추가함(옮김. 드래그처리)
}

const columns = document.querySelectorAll(".column");
columns.forEach((column) => {
  new Sortable(column, {
    group: "shared",
    animation: 150,
    ghostClass: "blue-background-class"
  });
});