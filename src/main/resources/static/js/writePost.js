// @ts-nocheck

// document.getElementById('post-form').addEventListener('submit', async (e) => {
//   e.preventDefault();

//   const postTitle = document.getElementById('postTitle').value;
//   const postContent = document.getElementById('postContent').value;
//   const postImage = document.getElementById('postImage').files[0];

//   const formData = new FormData();
//   formData.append('postTitle', postTitle);
//   formData.append('postContent', postContent);

//   if (postImage) {
//     formData.append('postImage', postImage);
//   }

//   try {
//     const response = await fetch('/api/posts', {
//       method: 'POST',
//       body: formData,
//     });

//     if (!response.ok) {
//       throw new Error('업로드에 실패했습니다.');
//     }

//     alert('게시물이 작성되었습니다.');
//     window.location.href = '/boards.html';
//   } catch (error) {
//     alert(error.message);
//   }
// });

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

document.addEventListener('DOMContentLoaded', function () {
  const startDateInput = document.getElementById('startDate');
  const endDateInput = document.getElementById('endDate');
  const resetStartDateBtn = document.getElementById('resetStartDate');
  const resetEndDateBtn = document.getElementById('resetEndDate');

  startDateInput.addEventListener('click', function () {
    showCalendar(startDateInput);
  });

  endDateInput.addEventListener('click', function () {
    showCalendar(endDateInput);
  });

  resetStartDateBtn.addEventListener('click', function () {
    startDateInput.value = '';
  });

  resetEndDateBtn.addEventListener('click', function () {
    endDateInput.value = '';
  });

  function showCalendar(inputElement) {
    let calendar = document.createElement('input');
    calendar.type = 'date';
    calendar.style.display = 'none';
    inputElement.parentNode.appendChild(calendar);

    calendar.click();

    calendar.addEventListener('change', function () {
      inputElement.value = calendar.value;
      inputElement.parentNode.removeChild(calendar);
    });
  }
});
