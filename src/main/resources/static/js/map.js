
    // AJAX 요청으로 데이터 가져오기
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/map/data', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onload = function () {
        if (xhr.status === 200) {
            var shopData = JSON.parse(xhr.responseText);
            // 데이터를 가져온 후에 getImgData 함수 호출
            getImgData(shopData);
        } else {
            console.error('데이터 가져오기 실패');
        }
    };
    xhr.send();
    function getImgData(shopData) {
    var xhr = new XMLHttpRequest();
        xhr.open('GET', '/map/imgdata', true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.onload = function () {
            if (xhr.status === 200) {
                var shopImgData = JSON.parse(xhr.responseText);
                // 데이터를 가져온 후에 showMap 함수 호출
                showMap(shopData, shopImgData);
                // 리스트 만들기
                createList(shopData, shopImgData);
            } else {
                console.error('이미지 데이터 가져오기 실패');
            }
        };
        xhr.send();
        }

        var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(37.451994538324236, 126.65825314503434), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };
        // 지도를 생성합니다
        var map = new kakao.maps.Map(mapContainer, mapOption);

        var sakeClusterer = null;
        var foodClusterer = null;
        var coffeeClusterer = null;
        var allClusterer = null;

    function showMap(shopData, shopImgData) {

        var markers = [];
        var data = [];
        var markerCount=0;

        var itemsPerPage = 5; // 페이지 당 아이템 수
        var currentPage = 1;  // 현재 페이지


        var sakeShopPositions = [];
        var sakeShopAddress = [];
        var sakeShopAddress2 = [];
        var sakeShopTell = [];
        var sakeShopName = [];
        var sakeShopImgSrc = [];
        var sakeData = [];

        var foodShopPositions = [];
        var foodShopAddress = [];
        var foodShopAddress2 = [];
        var foodShopTell = [];
        var foodShopName = [];
        var foodShopImgSrc = [];
        var foodData = [];

        var coffeeShopPositions = [];
        var coffeeShopAddress = [];
        var coffeeShopAddress2 = [];
        var coffeeShopTell = [];
        var coffeeShopName = [];
        var coffeeShopImgSrc = [];
        var coffeeData = [];

        var markerImageSrc = '../img/ctgc.png';

        sakeMarkers = [];
        foodMarkers = [];
        coffeeMarkers = [];


        // 주소-좌표 변환 객체를 생성합니다
        var geocoder = new kakao.maps.services.Geocoder();

        // 데이터를 순회하며 주소 정보를 가져오기
        for (var i = 0; i < shopData.length; i++) {
                (function (shopItem) {
                //var shopItem = shopData[i];
                var registmember = shopItem.member_id;
                var shopAddress = shopItem.saddrMain;
                var shopAddress2 = shopItem.saddrSub;
                var shopId = shopItem.sid;
                var shopName = shopItem.sname;
                var shopTell = shopItem.stell;
                var shopCtg = shopItem.cat;
                var imgSrc = "";
                //표 만들 데이터
                var data = [{sname: shopName, saddrMain: shopAddress, saddrSub: shopAddress2, stell: shopTell, shop_imgUrl: imgSrc}];

                for (var j = 0; j < shopImgData.length; j++) {
                    if (shopImgData[j].shop_id === shopId) {
                             if(shopImgData[j].shop_repImgYn === "Y") {
                                imgSrc = shopImgData[j].shop_imgUrl
                                //표만들 데이터에 넣기
                                data[0].shop_imgUrl=imgSrc;
                                break;
                             }
                    }
                }

                // 주소로 좌표를 검색합니다
                geocoder.addressSearch(shopAddress, function(result, status) {
                    // 정상적으로 검색이 완료됐으면
                    if (status === kakao.maps.services.Status.OK) {
                        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                        if (shopCtg === '술집') {
                            sakeShopPositions.push(coords);
                            sakeShopAddress.push(shopAddress);
                            sakeShopAddress2.push(shopAddress2);
                            sakeShopTell.push(shopTell);
                            sakeShopName.push(shopName);
                            sakeShopImgSrc.push(imgSrc);
                            sakeData.push(data);
                        } else if (shopCtg === '음식점') {
                            foodShopPositions.push(coords);
                            foodShopAddress.push(shopAddress);
                            foodShopAddress2.push(shopAddress2);
                            foodShopTell.push(shopTell);
                            foodShopName.push(shopName);
                            foodShopImgSrc.push(imgSrc);
                            foodData.push(data);
                        } else if (shopCtg === '카페') {
                            coffeeShopPositions.push(coords);
                            coffeeShopAddress.push(shopAddress);
                            coffeeShopAddress2.push(shopAddress2);
                            coffeeShopTell.push(shopTell);
                            coffeeShopName.push(shopName);
                            coffeeShopImgSrc.push(imgSrc);
                            coffeeData.push(data);
                        }
                    markerCount++; // 마커가 생성될 때마다 카운트 증가
                    if (markerCount === shopData.length) {
                        createsakeMarkers(sakeShopAddress, sakeShopAddress2, sakeShopName, sakeShopTell, sakeShopPositions, markerImageSrc, sakeShopImgSrc, map);
                        createfoodMarkers(foodShopAddress, foodShopAddress2, foodShopName, foodShopTell, foodShopPositions, markerImageSrc, foodShopImgSrc, map);
                        createcoffeeMarkers(coffeeShopAddress, coffeeShopAddress2, coffeeShopName, coffeeShopTell, coffeeShopPositions, markerImageSrc, coffeeShopImgSrc, map);
                        changeMarker('all');
                        }
                    }
                });
            })(shopData[i]);//클로저
        }
    }
    // 마커이미지의 주소와, 크기, 옵션으로 마커 이미지를 생성하여 리턴하는 함수입니다
    function createMarkerImage(src, size, options) {
        var markerImage = new kakao.maps.MarkerImage(src, size, options);
        return markerImage;
    }

    // 좌표와 마커이미지를 받아 마커를 생성하여 리턴하는 함수입니다
    function createMarker(position, image) {
        var marker = new kakao.maps.Marker({
            position: position,
            image: image
        });
    return marker;
    }
    function createsakeMarkers(sakeShopAddress, sakeShopAddress2, sakeShopName, sakeShopTell, sakeShopPositions, markerImageSrc, sakeShopImgSrc, map) {
        for (var i = 0; i < sakeShopPositions.length; i++) {
            (function (index) { // IIFE를 사용하여 새로운 스코프를 만듭니다.
                var imageSize = new kakao.maps.Size(24, 32),
                    imageOptions = {
                        spriteOrigin: new kakao.maps.Point(7, 0),
                        spriteSize: new kakao.maps.Size(36, 98)
                    };

                // 마커이미지와 마커를 생성합니다
                var markerImage = createMarkerImage(markerImageSrc, imageSize, imageOptions);
                var marker = createMarker(sakeShopPositions[index], markerImage);

                var content ='<div class="wrap">' +
                            '   <div class="info">' +
                            '       <div class="title">' +
                            sakeShopName[index] +
                            '           <div class="close" onclick="closeOverlay()" title="닫기"></div>' +
                            '       </div>' +
                            '       <div class="body">' +
                            '           <div class="img">' +
                            '               <img src="'+ sakeShopImgSrc[index] +'" width="73" height="70">' +
                            '           </div>' +
                            '           <div class="desc">' +
                            '               <div class="ellipsis">'+ sakeShopAddress[index] +'</div>' +
                            '               <div class="jibun ellipsis">'+ sakeShopAddress2[index] +'</div>' +
                            '               <div class="jibun ellipsis">'+ sakeShopTell[index] +'</div>' +
                            '               <div class="jibun ellipsis">상세보기</div>' +
                            '           </div>' +
                            '       </div>' +
                            '   </div>' +
                            '</div>';

                var overlay = new kakao.maps.CustomOverlay({
                    content: content,
                    map: map,
                    position: marker.getPosition()
                });

                overlay.setMap(null);

                kakao.maps.event.addListener(marker, 'click', function() {
                    overlay.setMap(map);
                });

                kakao.maps.event.addListener(map, 'click', function() {
                    overlay.setMap(null);
                })
                // 생성된 마커를 술집 마커 배열에 추가합니다
                sakeMarkers.push(marker);

            })(i);//클로저
        }
    }

    function createfoodMarkers(foodShopAddress, foodShopAddress2, foodShopName, foodShopTell, foodShopPositions, markerImageSrc, foodShopImgSrc, map) {
        for (var i = 0; i < foodShopPositions.length; i++) {
            (function (index) { // IIFE를 사용하여 새로운 스코프를 만듭니다.
                var imageSize = new kakao.maps.Size(24, 32),
                    imageOptions = {
                        spriteOrigin: new kakao.maps.Point(7, 64),
                        spriteSize: new kakao.maps.Size(36, 98)
                    };

                // 마커이미지와 마커를 생성합니다
                var markerImage = createMarkerImage(markerImageSrc, imageSize, imageOptions);
                var marker = createMarker(foodShopPositions[index], markerImage);

                var content ='<div class="wrap">' +
                            '   <div class="info">' +
                            '       <div class="title">' +
                            foodShopName[index] +
                            '           <div class="close" onclick="closeOverlay()" title="닫기"></div>' +
                            '       </div>' +
                            '       <div class="body">' +
                            '           <div class="img">' +
                            '               <img src="'+ foodShopImgSrc[index] +'" width="73" height="70">' +
                            '           </div>' +
                            '           <div class="desc">' +
                            '               <div class="ellipsis">'+ foodShopAddress[index] +'</div>' +
                            '               <div class="jibun ellipsis">'+ foodShopAddress2[index] +'</div>' +
                            '               <div class="jibun ellipsis">'+ foodShopTell[index] +'</div>' +
                            '               <div class="jibun ellipsis">상세보기</div>' +
                            '           </div>' +
                            '       </div>' +
                            '   </div>' +
                            '</div>';

                var overlay = new kakao.maps.CustomOverlay({
                    content: content,
                    map: map,
                    position: marker.getPosition()
                });

                overlay.setMap(null);

                kakao.maps.event.addListener(marker, 'click', function() {
                    overlay.setMap(map);
                });

                kakao.maps.event.addListener(map, 'click', function() {
                    overlay.setMap(null);
                })
                // 생성된 마커를 커피숍 마커 배열에 추가합니다
                foodMarkers.push(marker);

            })(i);//클로저
        }
    }

    function createcoffeeMarkers(coffeeShopAddress, coffeeShopAddress2, coffeeShopName, coffeeShopTell, coffeeShopPositions, markerImageSrc, coffeeShopImgSrc, map) {
        for (var i = 0; i < coffeeShopPositions.length; i++) {
            (function (index) { // IIFE를 사용하여 새로운 스코프를 만듭니다.
                var imageSize = new kakao.maps.Size(24, 32),
                    imageOptions = {
                        spriteOrigin: new kakao.maps.Point(7, 33),
                        spriteSize: new kakao.maps.Size(36, 98)
                    };

                // 마커이미지와 마커를 생성합니다
                var markerImage = createMarkerImage(markerImageSrc, imageSize, imageOptions);
                var marker = createMarker(coffeeShopPositions[index], markerImage);

                var content ='<div class="wrap">' +
                            '   <div class="info">' +
                            '       <div class="title">' +
                            coffeeShopName[index] +
                            '           <div class="close" onclick="closeOverlay()" title="닫기"></div>' +
                            '       </div>' +
                            '       <div class="body">' +
                            '           <div class="img">' +
                            '               <img src="'+ coffeeShopImgSrc[index] +'" width="73" height="70">' +
                            '           </div>' +
                            '           <div class="desc">' +
                            '               <div class="ellipsis">'+ coffeeShopAddress[index] +'</div>' +
                            '               <div class="jibun ellipsis">'+ coffeeShopAddress2[index] +'</div>' +
                            '               <div class="jibun ellipsis">'+ coffeeShopTell[index] +'</div>' +
                            '               <div class="jibun ellipsis">상세보기</div>' +
                            '           </div>' +
                            '       </div>' +
                            '   </div>' +
                            '</div>';

                var overlay = new kakao.maps.CustomOverlay({
                    content: content,
                    map: map,
                    position: marker.getPosition()
                });

                overlay.setMap(null);

                kakao.maps.event.addListener(marker, 'click', function() {
                    overlay.setMap(map);
                });

                kakao.maps.event.addListener(map, 'click', function() {
                    overlay.setMap(null);
                })
                // 생성된 마커를 커피숍 마커 배열에 추가합니다
                coffeeMarkers.push(marker);

            })(i);//클로저
        }
    }

    // 술집 마커들의 지도 표시 여부를 설정하는 함수입니다
    function setsakeMarkers(map) {
        for (var i = 0; i < sakeMarkers.length; i++) {
            sakeMarkers[i].setMap(map);
        }
        if (map) {
                // 클러스터러를 생성하고 술집 마커들을 추가
                sakeClusterer = new kakao.maps.MarkerClusterer({
                    map: map,
                    averageCenter: false,
                    minLevel: 10
                });
                sakeClusterer.addMarkers(sakeMarkers);
            } else {
                // 클러스터러를 제거
                if (sakeClusterer) {
                    sakeClusterer.clear();
                }
            }
    }

    // 맛집 마커들의 지도 표시 여부를 설정하는 함수입니다
    function setfoodMarkers(map) {
        for (var i = 0; i < foodMarkers.length; i++) {
            foodMarkers[i].setMap(map);
        }
        if (map) {
                // 클러스터러를 생성하고 맛집 마커들을 추가
                foodClusterer = new kakao.maps.MarkerClusterer({
                    map: map,
                    averageCenter: false,
                    minLevel: 10
                });
                foodClusterer.addMarkers(foodMarkers);
            } else {
                // 클러스터러를 제거
                if (foodClusterer) {
                    foodClusterer.clear();
                }
            }
    }

    function setcoffeeMarkers(map) {
        for (var i = 0; i < coffeeMarkers.length; i++) {
                coffeeMarkers[i].setMap(map);
        }
        if (map) {
                // 클러스터러를 생성하고 커피숍 마커들을 추가
                coffeeClusterer = new kakao.maps.MarkerClusterer({
                    map: map,
                    averageCenter: false,
                    minLevel: 10
                });
                coffeeClusterer.addMarkers(coffeeMarkers);
            } else {
                // 클러스터러를 제거
                if (coffeeClusterer) {
                    coffeeClusterer.clear();
                }
            }
    }

    function setallMarkers(map) {
        for (var i = 0; i < sakeMarkers.length; i++) {
                sakeMarkers[i].setMap(map);
        }
        for (var i = 0; i < foodMarkers.length; i++) {
                foodMarkers[i].setMap(map);
        }
        for (var i = 0; i < coffeeMarkers.length; i++) {
                coffeeMarkers[i].setMap(map);
        }
        if (map) {
                // 클러스터러를 생성하고 커피숍 마커들을 추가
               allClusterer = new kakao.maps.MarkerClusterer({
                    map: map,
                    averageCenter: false,
                    minLevel: 10
                });
               allClusterer.addMarkers(sakeMarkers);
               allClusterer.addMarkers(foodMarkers);
               allClusterer.addMarkers(coffeeMarkers);

               } else {
                    // 클러스터러를 제거
                    if (allClusterer) {
                        allClusterer.clear();
                    }
               }
        }

    function changeMarker(type){

        var sakeMenu = document.getElementById('sakeMenu');
        var foodMenu = document.getElementById('foodMenu');
        var coffeeMenu = document.getElementById('coffeeMenu');
        var all = document.getElementById('all');

        if (sakeClusterer) {
                sakeClusterer.clear();
            }
            if (foodClusterer) {
                foodClusterer.clear();
            }
            if (coffeeClusterer) {
                coffeeClusterer.clear();
            }
            if (allClusterer) {
                allClusterer.clear();
            }

    // 커피숍 카테고리가 클릭됐을 때
        if (type === 'sake') {

            // 커피숍 카테고리를 선택된 스타일로 변경하고
            sakeMenu.className = 'menu_selected';
            foodMenu.className = '';
            coffeeMenu.className = '';
            all.className = '';

            // 커피숍 마커들만 지도에 표시하도록 설정합니다
            setallMarkers(null);
            setsakeMarkers(map);
            setfoodMarkers(null);
            setcoffeeMarkers(null);
        } else if (type === 'food') {
            sakeMenu.className = '';
            foodMenu.className = 'menu_selected';
            coffeeMenu.className = '';
            all.className = '';

            setallMarkers(null);
            setsakeMarkers(null);
            setfoodMarkers(map);
            setcoffeeMarkers(null);
        } else if (type ==='coffee') {
            sakeMenu.className = '';
            foodMenu.className = '';
            coffeeMenu.className = 'menu_selected';
            all.className = '';

            setallMarkers(null);
            setsakeMarkers(null);
            setfoodMarkers(null);
            setcoffeeMarkers(map);
        } else if (type==='all') {
            sakeMenu.className = '';
            foodMenu.className = '';
            coffeeMenu.className = '';
            all.className = 'menu_selected';

            setsakeMarkers(null);
            setfoodMarkers(null);
            setcoffeeMarkers(null);
            setallMarkers(map);
        }
    }
    function createList(shopData, shopImgData) {
        const listContainer = document.querySelector('#listContainer');
        listContainer.innerHTML = ''; // 리스트 내용 초기화

        shopData.forEach(shopItem => {
            const listItem = document.createElement('tr');
            listItem.innerHTML = `
                <td>
                    <div class="wrap2">
                        <div class="info2">
                            <div class="title">${shopItem.sname}</div>
                            <div class="body">
                                <div class="img">
                                    <img src="${getShopImgSrc(shopItem, shopImgData)}" width="73" height="70" alt="${shopItem.sname} Image">
                                </div>
                                <div class="desc">
                                    <div class="ellipsis">${shopItem.saddrMain}</div>
                                    <div class="jibun ellipsis">${shopItem.saddrSub}</div>
                                    <div class="jibun ellipsis">${shopItem.stell}</div>
                                    <div class="jibun ellipsis">상세보기</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </td>
            `;

            // 리스트 아이템에 클릭 이벤트 추가
            listItem.addEventListener('click', function () {
                // 리스트 아이템이 클릭되면 해당 마커를 지도에서 표시
                showSingleMarker(shopItem, shopImgData);
            });

            // 리스트를 컨테이너에 추가
            listContainer.appendChild(listItem);
        });
    }

    function showSingleMarker(shopItem, shopImgData) {
        const coords = new kakao.maps.LatLng(shopItem.latitude, shopItem.longitude);
        const markerImageSrc = getShopImgSrc(shopItem, shopImgData);
        const markerImage = createMarkerImage(markerImageSrc, new kakao.maps.Size(24, 32), {
            spriteOrigin: new kakao.maps.Point(7, 0),
            spriteSize: new kakao.maps.Size(36, 98)
        });
        const marker = createMarker(coords, markerImage);

        // 기존에 표시된 마커 제거
        if (singleMarker) {
            singleMarker.setMap(null);
        }

        // 새로운 마커 지도에 표시
        marker.setMap(map);
        singleMarker = marker;

        // 마커가 표시된 위치로 지도 이동
        map.setCenter(coords);

        // 리스트 업데이트
        createList([shopItem], shopImgData);
    }

    function getShopImgSrc(shopItem, shopImgData) {
        const shopId = shopItem.sid;
        for (const imgData of shopImgData) {
            if (imgData.shop_id === shopId && imgData.shop_repImgYn === 'Y') {
                return imgData.shop_imgUrl;
            }
        }
        // 대표 이미지가 없는 경우 기본 이미지 또는 다른 처리를 할 수 있습니다.
        return '/img/default.png';
    }
