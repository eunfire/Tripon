// @ts-nocheck
const imageInput = document.getElementById('imageInput');
const editableDiv = document.getElementById('editableDiv');

// 이미지가 선택될 때마다 미리보기를 표시하는 이벤트 리스너
imageInput.addEventListener('change', function (event) {
  const files = event.target.files;

  if (files) {
    Array.from(files).forEach((file) => {
      // 파일 형식 검증
      if (file.type.startsWith('image/')) {
        showImagePreview(file);
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
    editableDiv.appendChild(img);
  };
  reader.readAsDataURL(file);
}
