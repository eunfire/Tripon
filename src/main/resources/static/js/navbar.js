const domesticBtn = document.querySelector('.domestic');
const innerDomsestic = document.querySelector('.innerdomsestic');

const overseasBtn = document.querySelector('.overseas');
const innerOverseas = document.querySelector('.inneroverseas');

const questionfreeBtn = document.querySelector('.questionfree');
const innerQuestionfree = document.querySelector('.innerquestionfree');

const innernoticeBtn = document.querySelector('.notice');
const innerNotice = document.querySelector('.innernotice');

let isMouseInside = false;

// domesticBtn에 마우스 오버 이벤트 리스너 추가
domesticBtn.addEventListener('mouseover', () => {
	innerDomsestic.classList.toggle('active'); // innerDomsestic 요소에 active 클래스를 추가 또는 제거하여 토글(toggle)합니다.
	innerOverseas.classList.remove('active'); // innerOverseas 요소의 active 클래스를 제거합니다.
	innerQuestionfree.classList.remove('active'); // innerQuestionfree 요소의 active 클래스를 제거합니다.
	innerNotice.classList.remove('active'); // innerNotice 요소의 active 클래스를 제거합니다.
});

// domesticBtn에 마우스 이탈 이벤트 리스너 추가
domesticBtn.addEventListener('mouseleave', () => {
	innerDomsestic.classList.remove('active'); // innerDomsestic 요소의 active 클래스를 제거합니다.
});

// innerDomsestic에 마우스 진입 이벤트 리스너 추가
innerDomsestic.addEventListener('mouseenter', () => {
	innerDomsestic.classList.add('active'); // innerDomsestic 요소에 active 클래스를 추가하여 표시합니다.
});

innerDomsestic.addEventListener('mouseleave', () => {
	innerDomsestic.classList.remove('active'); // innerDomsestic 요소의 active 클래스를 제거합니다.
});

// innerDomsestic 내부의 요소에 대한 이벤트 처리
innerDomsestic.addEventListener('mouseenter', () => {
	innerDomsestic.classList.add('active'); // innerDomsestic 요소에 active 클래스를 추가하여 표시합니다.
});

innerDomsestic.addEventListener('mouseleave', () => {
	// innerDomsestic에서 마우스가 완전히 벗어나면 active 클래스를 제거합니다.
	const isMouseOutside = !innerDomsestic.contains(event.relatedTarget);
	if (isMouseOutside) {
		innerDomsestic.classList.remove('active');
	}
});

document.addEventListener('scroll', () => {
	// 스크롤 이벤트가 발생한 요소가 innerDomsestic를 포함하지 않으면 (즉, innerDomsestic 외부에서 스크롤 이벤트가 발생하면) active 클래스를 제거합니다.
	const isMouseOutside = !innerDomsestic.contains(event.target);
	if (isMouseOutside) {
		innerDomsestic.classList.remove('active');
	}
});


overseasBtn.addEventListener('mouseover', () => {
	innerOverseas.classList.toggle('active');
	innerDomsestic.classList.remove('active');
	innerQuestionfree.classList.remove('active');
	innerNotice.classList.remove('active');
});

// overseasBtn에 마우스 이탈 이벤트 리스너 추가
overseasBtn.addEventListener('mouseleave', () => {
	innerOverseas.classList.remove('active'); // innerOverseas 요소의 active 클래스를 제거합니다.
});

// innerOverseas에 마우스 진입 이벤트 리스너 추가
innerOverseas.addEventListener('mouseenter', () => {
	innerOverseas.classList.add('active'); // innerOverseas 요소에 active 클래스를 추가하여 표시합니다.
});

innerOverseas.addEventListener('mouseleave', () => {
	innerOverseas.classList.remove('active'); // innerOverseas 요소의 active 클래스를 제거합니다.
});

// innerOverseas 내부의 요소에 대한 이벤트 처리
innerOverseas.addEventListener('mouseenter', () => {
	innerOverseas.classList.add('active'); // innerOverseas 요소에 active 클래스를 추가하여 표시합니다.
});

innerOverseas.addEventListener('mouseleave', () => {
	// innerOverseas에서 마우스가 완전히 벗어나면 active 클래스를 제거합니다.
	const isMouseOutside = !innerOverseas.contains(event.relatedTarget);
	if (isMouseOutside) {
		innerOverseas.classList.remove('active');
	}
});

document.addEventListener('scroll', () => {
	// 스크롤 이벤트가 발생한 요소가 innerOverseas를 포함하지 않으면 (즉, innerOverseas 외부에서 스크롤 이벤트가 발생하면) active 클래스를 제거합니다.
	const isMouseOutside = !innerOverseas.contains(event.target);
	if (isMouseOutside) {
		innerOverseas.classList.remove('active');
	}
});


questionfreeBtn.addEventListener('mouseover', () => {
	innerQuestionfree.classList.toggle('active');
	innerDomsestic.classList.remove('active');
	innerOverseas.classList.remove('active');
	innerNotice.classList.remove('active');
});

// questionfreeBtn에 마우스 이탈 이벤트 리스너 추가
questionfreeBtn.addEventListener('mouseleave', () => {
	innerQuestionfree.classList.remove('active'); // innerQuestionfree 요소의 active 클래스를 제거합니다.
});

// innerQuestionfree에 마우스 진입 이벤트 리스너 추가
innerQuestionfree.addEventListener('mouseenter', () => {
	innerQuestionfree.classList.add('active'); // innerQuestionfree 요소에 active 클래스를 추가하여 표시합니다.
});

innerQuestionfree.addEventListener('mouseleave', () => {
	innerQuestionfree.classList.remove('active'); // innerQuestionfree 요소의 active 클래스를 제거합니다.
});

// innerQuestionfree 내부의 요소에 대한 이벤트 처리
innerQuestionfree.addEventListener('mouseenter', () => {
	innerQuestionfree.classList.add('active'); // innerQuestionfree 요소에 active 클래스를 추가하여 표시합니다.
});

innerQuestionfree.addEventListener('mouseleave', () => {
	// innerQuestionfree에서 마우스가 완전히 벗어나면 active 클래스를 제거합니다.
	const isMouseOutside = !innerQuestionfree.contains(event.relatedTarget);
	if (isMouseOutside) {
		innerQuestionfree.classList.remove('active');
	}
});

document.addEventListener('scroll', () => {
	// 스크롤 이벤트가 발생한 요소가 innerQuestionfree를 포함하지 않으면 (즉, innerQuestionfree 외부에서 스크롤 이벤트가 발생하면) active 클래스를 제거합니다.
	const isMouseOutside = !innerQuestionfree.contains(event.target);
	if (isMouseOutside) {
		innerQuestionfree.classList.remove('active');
	}
});


innernoticeBtn.addEventListener('mouseover', () => {
	innerNotice.classList.toggle('active');
	innerDomsestic.classList.remove('active');
	innerOverseas.classList.remove('active');
	innerQuestionfree.classList.remove('active');
});

// innernoticeBtn에 마우스 이탈 이벤트 리스너 추가
innernoticeBtn.addEventListener('mouseleave', () => {
	innerNotice.classList.remove('active'); // innerNotice 요소의 active 클래스를 제거합니다.
});

// innerDomsestic에 마우스 진입 이벤트 리스너 추가
innerNotice.addEventListener('mouseenter', () => {
	innerNotice.classList.add('active'); // innerNotice 요소에 active 클래스를 추가하여 표시합니다.
});

innerNotice.addEventListener('mouseleave', () => {
	innerNotice.classList.remove('active'); // innerNotice 요소의 active 클래스를 제거합니다.
});

// innerNotice 내부의 요소에 대한 이벤트 처리
innerNotice.addEventListener('mouseenter', () => {
	innerNotice.classList.add('active'); // innerNotice 요소에 active 클래스를 추가하여 표시합니다.
});

innerNotice.addEventListener('mouseleave', () => {
	// innerNotice에서 마우스가 완전히 벗어나면 active 클래스를 제거합니다.
	const isMouseOutside = !innerNotice.contains(event.relatedTarget);
	if (isMouseOutside) {
		innerNotice.classList.remove('active');
	}
});

document.addEventListener('scroll', () => {
	// 스크롤 이벤트가 발생한 요소가 innerNotice를 포함하지 않으면 (즉, innerNotice 외부에서 스크롤 이벤트가 발생하면) active 클래스를 제거합니다.
	const isMouseOutside = !innerNotice.contains(event.target);
	if (isMouseOutside) {
		innerNotice.classList.remove('active');
	}
});


