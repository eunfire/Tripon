document.addEventListener('DOMContentLoaded', function() {
    var modal = document.getElementById('myModal');
    var modalBtn = document.getElementById('open');
    var closeBtn = document.getElementsByClassName('close')[0];
    var mapContainer = document.getElementById('map');
    var map;

    modalBtn.onclick = function() {
        modal.classList.add('open'); // 모달을 열 때 클래스 추가
        if (!map) {
            var modalRect = modal.getBoundingClientRect();
            var modalCenter = {
                x: modalRect.left + modalRect.width / 2,
                y: modalRect.top + modalRect.height / 2
            };
            var mapCenter = mapContainer.getBoundingClientRect();
            var mapWidth = mapContainer.offsetWidth;
            var mapHeight = mapContainer.offsetHeight;
            var mapCenterX = mapCenter.left + mapWidth / 2;
            var mapCenterY = mapCenter.top + mapHeight / 2;
            var offsetX = modalCenter.x - mapCenterX;
            var offsetY = modalCenter.y - mapCenterY;
            var center = map.getCenter();
            var newCenter = new kakao.maps.LatLng(center.getLat() + offsetY * 0.00001, center.getLng() + offsetX * 0.00001);
            map.setCenter(newCenter);
        }
    };

    closeBtn.onclick = function() {
        modal.classList.remove('open'); // 모달을 닫을 때 클래스 제거
    };

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.classList.remove('open'); // 모달 외부 클릭 시 모달 닫음
        }
    };

    function initMap() {
        var options = {
            center: new kakao.maps.LatLng(33.450701, 126.570667),
            level: 3
        };
        map = new kakao.maps.Map(mapContainer, options);
    }

    initMap();
});