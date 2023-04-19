function initMap() {
	let travelLocation = new google.maps.LatLng(tLat, tLng);
	map = new google.maps.Map(
		document.getElementById('map'), {
			center: travelLocation,
			zoom: 12
		});
	marker = new google.maps.Marker({
		position: travelLocation,
		map: map,
		draggable: true,
		animation: google.maps.Animation.DROP,
	});
	marker.addListener("click", toggleBounce);
	setMarkers(map);
}

let markers = [];
//지도 마커
function setMarkers(map) {
	infoWindow = new google.maps.InfoWindow();
	var marker, i;
	for (let i = 0; i < likes.length; i++) {
		//좋아요 플레이스 일때 아이콘
		const like = likes[i];
		let iconImg = '';
		if (like.placeCate == 'RC'){
			iconImg = '/assets/img/finalproject-img/foodMark.png';
		}else if(like.placeCate == 'CF'){
			iconImg = '/assets/img/finalproject-img/tPlace.png';
		}else if(like.placeCate == 'AC'){
			iconImg = '/assets/img/finalproject-img/lodging.png';
		}else if(like.placeCate == 'P'){
			iconImg = '/assets/img/finalproject-img/parkingMark.png';
		}
		
		if(heartLikes.length > 0){
			for(let j = 0; j < heartLikes.length; j++){
				if(like.placeID == heartLikes[j].placeID){	
					iconImg = '/assets/img/finalproject-img/heartMark40.png';			
				}
			}
		}

		marker = new google.maps.Marker({
			position: {
				lat: like.latitude,
				lng: like.longitude
			},
			map: map,
			icon : iconImg,
			zIndex: i,
			title: like.placeName,
			id: like.placeID,
		});
		markers.push(marker);
		//Marker를 찍었을 때 클릭 이벤트
		google.maps.event.addListener(marker, 'click', (function (marker, i) {
			return function () {
				
				//기존 정보 삭제
				let reviewList = document.querySelector('.place_review_detail');
				let reviewCount = reviewList.children.length;
				if (reviewCount != 0) {
					for (let idx = 0 ; idx < reviewCount; idx++) {
						reviewList.children[0].remove();
					}
				}
				let starCount = document.querySelector('.review_star_all');
				if(starCount.children.length != 0) {
					for (let idx = 0; idx < 5; idx++){
						starCount.children[0].remove();
					}
				}
				////중심 위치를 클릭된 마커의 위치로 변경
				map.setCenter(this.getPosition());

				////마커 클릭 시의 줌 변화
				map.setZoom(18);

				//여행지 상세정보/후기
				$.ajax({
					url: '/planner/placeDetail',
					type: 'post',
					data: JSON.stringify({
						"placeId": this.id
					}),
					contentType: 'application/json',
					success: function (result) {
						makeMarkerpage(result);
					}
				}).fail(function (error) {
					console.log("마커 정보 실패");
				})


			}
		})(marker, i));
	}
}

// 마커 무빙
function toggleBounce() {
	if (marker.getAnimation() !== null) {
		marker.setAnimation(null);
	} else {
		marker.setAnimation(google.maps.Animation.BOUNCE);
	}
}

//여행지 상세정보
function makeMarkerpage(result) {
	//main 페이지 오른쪽 display: none;
	let appendPlace = document.getElementById('right_append');
	appendPlace.children[0].style.display = 'none';
	appendPlace.children[1].style.display = 'none';
	document.querySelector('.place_like').style.display = "none"
	document.querySelector('.checkList').style.display ="none";
	document.querySelector('.budget').style.display="none";

	//정보 변경할 부분 작업
	let markerTop = document.getElementById('marker_top');
	let markerInfo = document.getElementById('markerInfo');

	//markerTop
	markerTop.style.display = 'block';
	markerInfo.style.display = 'block';
	document.getElementById('head_Img').style.backgroundImage = "url('" + result.placeInfo.placeImg + "')";
	document.getElementById('trip_place').innerText = result.placeInfo.placeName;
	document.getElementById('trip_info').value = result.placeInfo.placeID;
	document.getElementById('trip_placeadd').innerText = result.placeInfo.placeAdd;
	document.getElementById('phone').innerText = result.placeInfo.placePhone;

	//출발일 ~ 도착일
	let start = startDate.substr(2, 2) + '/' + startDate.substr(5, 2) + '/' + startDate.substr(8, 2);
	let end = endDate.substr(2, 2) + '/' + endDate.substr(5, 2) + '/' + endDate.substr(8, 2);
	document.getElementById('trip_period').innerText = start + '~' + end;
	//나라
	document.getElementById('trip_country').innerText = planCountry;
	
	//리뷰 요약
	//별 평균
	document.getElementById('starAvg').innerText = result.starAvg;
	//별 그림
	for(let j = 1; j <= 5; j++){
		let starAll = document.querySelector('.review_star_all');
		let iTag = document.createElement('i');
		if(j <= Math.floor(result.starAvg)){
			iTag.setAttribute('class', 'fas fa-star');
			
		}else if(j > Math.floor(result.starAvg)){
			iTag.setAttribute('class', 'fas fa-star');
			iTag.setAttribute('style', 'color:black');
		}
		starAll.append(iTag);
	}
	//리뷰갯수
	document.getElementById('review_count').innerText ="리뷰 "+ result.reviewCount +"개";
	//별점별 그래프
		let tag;
		//기존 넓이 값 0으로
		document.querySelector('.one_star').style.width = 0 + '%';
		document.querySelector('.two_star').style.width = 0 + '%';
		document.querySelector('.three_star').style.width = 0 + '%';
		document.querySelector('.four_star').style.width = 0 + '%';
		document.querySelector('.five_star').style.width = 0 + '%';
		//기존 내부 텍스트 값 0으로
		document.querySelector('.one_star').innerText = 0 + '%';
		document.querySelector('.two_star').innerText = 0 + '%';
		document.querySelector('.three_star').innerText = 0 + '%';
		document.querySelector('.four_star').innerText = 0 + '%';
		document.querySelector('.five_star').innerText = 0 + '%';

		//값 조정
		if(result.reviewCount != null){
			for(let starI = 0; starI < result.starPercent.length; starI++){
				let reviewScore = result.starPercent[starI].reviewStar;
				switch(reviewScore){
					case 1 :
						tag = document.querySelector('.one_star');
						tag.style.width = result.starPercent[starI].starPercent + '%';
						tag.innerText =result.starPercent[starI].starPercent + '%';
					break;
					case 2 :
						tag = document.querySelector('.two_star');
						tag.style.width = result.starPercent[starI].starPercent + '%';
						tag.innerText =result.starPercent[starI].starPercent + '%';
						break;
					case 3 :
						tag = document.querySelector('.three_star');
						tag.style.width = result.starPercent[starI].starPercent + '%';
						tag.innerText =result.starPercent[starI].starPercent + '%';
						break;
					case 4 :
						tag = document.querySelector('.four_star');
						tag.style.width = result.starPercent[starI].starPercent + '%';
						tag.innerText =result.starPercent[starI].starPercent + '%';
						break;
					case 5 :
						tag = document.querySelector('.five_star');
						tag.style.width = result.starPercent[starI].starPercent + '%';
						tag.innerText =result.starPercent[starI].starPercent + '%';
						break;		
			}

		}
	}
	
	//markerInfo
	//markerInfo-reviewDetail
	let reviewdiv = document.querySelector('.place_review_detail');
	let review = result.reviewList;	
	//후기 append
	for (let i = 0; i < review.length; i++) {
		let template = document.getElementById('review_detail').cloneNode(true);
		template.style.display = 'block';
		template.querySelector('.place_review_date').children[0].innerText = review[i].reviewWriter;
		template.querySelector('.place_review_date').children[1].innerText = review[i].reviewDate.substr(2, 8);
		template.querySelector('.place_review_star_all').children[0].innerText = review[i].reviewStar;
		template.querySelector('.row_img').children[0].setAttribute('src', review[i].memberPhoto);
		template.setAttribute('id', review[i].reviewWriter);
		//별 붙이기
		for (let j = 1; j <= 5; j++) {
			let div = template.querySelector('.star_append');
			let star = review[i].reviewStar;
			let iTag = document.createElement('i');
			if (j <= star) {
				iTag.setAttribute("class", "fas fa-star");
			} else if (j > star) {
				iTag.setAttribute("class", "fas fa-star");
				iTag.setAttribute("style", "color:black");
			}
			div.append(iTag);
		}
		
		//이미지 정보 가지고 오기
		//controller 에서 @requestParam 사용시 보내는 타입
		$.ajax({
			url: '/planner/reviewImg',
			type: 'get',
			data: {
				"placeId": review[i].placeId,
				"userId": review[i].reviewWriter,
			},
			success: function (result) {
				if (result.length != 0) {
					if(result.length <= 4){
						for(let Idx = 0; Idx < result.length; Idx++){
							
							let div = document.createElement('div');
							div.style.backgroundImage = "url('" + result[Idx].photoLocation + "')";
							div.setAttribute('class', 'place_review_picture');
						
							//최종적으로 붙이는 장소
							template.querySelector('.review_pictureList').append(div);
							//setAttribute 로 onclick 시 'picturePopUp();'와 같은 형태로 입력해 주어야 함
							template.querySelector('.review_pictureList').children[Idx].setAttribute('onclick', 'picturePopUp();');
						}
					}else if(result.length > 4){
						for(let Idx = 0; Idx < 4; Idx++){
							let div = document.createElement('div');
							if (Idx < 3){
								div.style.backgroundImage = "url('" + result[Idx].photoLocation + "')";
								div.setAttribute('class', 'place_review_picture');
								div.setAttribute('onclick', 'picturePopUp();');
							}else if (Idx = 4){
								//+a 만큼 사진이 더있다.
								let spanTag = document.createElement('span');
								let reviewImgCount = (result.length - 4);
								spanTag.setAttribute('class', 'reviewImgCount');
								spanTag.innerText ="+"+ reviewImgCount;
								div.append(spanTag);
								//
								div.style.backgroundImage = "url('" + result[Idx].photoLocation + "')";
								div.setAttribute('class', 'place_review_picture');
								div.setAttribute('onclick', 'picturePopUp();');
							}
							//최종적으로 붙이는 장소
							template.querySelector('.review_pictureList').append(div);
						}
					}
				} //리뷰가 없을때 리뷰가 없습니다 띄워주기
			}
		}).fail(function (error) {
			console.log("후기 불러오기 실패");
		})
		template.querySelector('.reviewContent').innerText = review[i].reviewContent;
		reviewdiv.append(template);
	}
}


//===============================
//사진 크게 보기
function picturePopUp(){
	//document.querySelector('.search-overlay').setAttribute('class', 'search-overlay search-overlay-active');

}

//사진 팝업 없애기
function pictureDelete(){
	document.querySelector('.search-overlay').setAttribute('class', 'search-overlay');
}
//===============================

let getHref = window.location.pathname.split('/');
let planId = getHref[3];
//장소 찜 기능
function addLike() {
	let placeId=document.getElementById('trip_info').value;
	//window.location.pathname //planner/main
	//let likeOrNot = confirm('장소를 찜 하시겠습니까?');
	swal({
		 title: "알림",
		text: "장소를 찜 하시겠습니까?",
		icon: "info",
		buttons: true,
		dangerMode: true,
	}).then((willDelete) => {
		if (willDelete) {
			$.ajax({
				url: '/planner/insertLikePlace',
				type: 'post',
				data: {
					"planId" : planId,
					"placeId": placeId,
				},
				success: function (result) {
					if(result == 1){
						for(let i = 0; i < markers.length; i++){
							if(markers[i].id == placeId){
								markers[i].setMap(null);
								markers.splice(i, 1);
							}
						}
						$.ajax({
							url: '/planner/getPlace',
							type: 'post',
							data: {
								"placeId": placeId,
							},
							success: function (result) {
								marker = new google.maps.Marker({
									position: {
										lat: result.latitude,
										lng: result.longitude
									},
									map: map,
									icon : '/assets/img/finalproject-img/heartMark40.png',
									zIndex: 1,
									title: result.placeName,
									id: placeId,
								});
								markers.push(marker);
								marker.addListener("click", () => {
									//기존 정보 삭제
									let reviewList = document.querySelector('.place_review_detail');
									let reviewCount = reviewList.children.length;
									if (reviewCount != 0) {
										for (let idx = 0 ; idx < reviewCount; idx++) {
											reviewList.children[0].remove();
										}
									}
									let starCount = document.querySelector('.review_star_all');
									if(starCount.children.length != 0) {
										for (let idx = 0; idx < 5; idx++){
											starCount.children[0].remove();
										}
									}
									map.setCenter(new google.maps.LatLng(result.latitude, result.longitude));
									map.setZoom(18);

									//여행지 상세정보/후기
										$.ajax({
											url: '/planner/placeDetail',
											type: 'post',
											data: JSON.stringify({
												"placeId": placeId,
											}),
											contentType: 'application/json',
											success: function (result) {
												makeMarkerpage(result);
											}
										}).fail(function (error) {
											console.log("마커 정보 실패");
										})
								});
							}
						}).fail(function (error) {
								console.log(error);
						})
							
						swal({
							title: "알림",
							text: "찜 목록에 추가 되었습니다."+'\n'+"찜 목록으로 가시겠습니까?",
							icon: "success",
							buttons: true,
							dangerMode: true,
							})
							.then((willDelete) => {
								likeList();
							});
						
						/*let goLikeList = confirm("찜 목록에 추가 되었습니다."+'\n'+"찜 목록으로 가시겠습니까?")
						if(goLikeList == true){
							likeList();
						}*/
					}else if (result == 2){
						swal("알림", "이미 찜한 장소입니다.", "warning");
					}else {
						swal("에러", "오류가 발생했습니다.", "error");
					}
				}
			}).fail(function (error) {
				console.log(error);
			})
		}
	});
}

//찜 리스트
function likeList(){
	//페이지 정리
	document.getElementById('marker_top').style.display = 'none';
	document.getElementById('markerInfo').style.display = 'none';
	document.getElementById('main').style.display = 'none';
	document.querySelector('.first').style.display = "block";
	document.querySelector('.checkList').style.display ="none";
	document.querySelector('.budget').style.display="none";
	
	//찜플레이스 페이지
	document.querySelector('.place_like').style.display = "block";
	//기존 찜플레이스 리스트 삭제
	let children = document.getElementById('Likeplaces').children.length
	if(children > 2){
		for(let cLike = 2;  cLike < children; cLike++){
			document.getElementById('Likeplaces').children[2].remove();
		}
	}
	
	//찜플레이스 리스트 호출
	$.ajax({
		url: '/planner/placeLike',
		type: 'get',
		data: {
			"planId" : planId,
		},
		success: function (result) {
			MakeLikeList(result);
		}
	}).fail(function (error) {
		console.log(error);
	})
}


//찜플레이스 리스트만들기
function MakeLikeList(result){
	for(let likeIdx = 0; likeIdx < result.length; likeIdx++){
		let div = document.createElement('div');
		div.setAttribute('class', 'heart');
		div.setAttribute('id', result[likeIdx].placeID)
		let iTag = document.createElement('i');
		iTag.setAttribute('class', 'fas fa-heart');
		iTag.setAttribute('id', 'heart');
		//onclick에서 this를 가지고 오고 싶으면 func에 event 적어주기
		iTag.setAttribute('onclick', 'likeDelete(event);')
		div.append(iTag);
		let spanTag = document.createElement('span');
		spanTag.setAttribute('class', 'likePlace');
		spanTag.setAttribute('id', result[likeIdx].placeID);
		spanTag.innerText = result[likeIdx].placeName;
		spanTag.setAttribute('onclick', 'likeDetail(event);')
		div.append(spanTag);
		let hrTag = document.createElement('hr');
		div.append(hrTag);

		document.getElementById('Likeplaces').append(div);

	}
}

//찜 플레이스 삭제
function likeDelete(event){
	likeId = event.target.parentElement.children[1].id;
	//찜 플레이스 삭제
	//let delPlace = confirm('해당 장소를 찜 플레이스 에서 삭제하시겠습니까?')
	swal({
		title: "알림",
		text: "해당 장소를 찜 플레이스 에서 삭제하시겠습니까?",
		icon: "info",
		buttons: true,
		dangerMode: true,
	  })
	  .then((willDelete) => {
		if(willDelete){
			$.ajax({
				url: '/planner/deleteLikePlace',
				type: 'post',
				data: {
					"planId" : planId,
					"placeId" : likeId,
				},
				success: function (result) {
					swal("알림", "삭제 되었습니다.", "success")
					event.target.parentElement.remove();
					for(let i = 0; i < markers.length; i++){
						if(markers[i].id == likeId){
							markers[i].setMap(null);
							markers.splice(i, 1);
						}
					}
					$.ajax({
						url: '/planner/getPlace',
						type: 'post',
						data: {
							"placeId": likeId,
						},
						success: function (result) {
							let iconImg = '';
							if (result.placeCate == 'RC'){
								iconImg = '/assets/img/finalproject-img/foodMark.png';
							}else if(result.placeCate == 'CF'){
								iconImg = '/assets/img/finalproject-img/tPlace.png';
							}else if(result.placeCate == 'AC'){
								iconImg = '/assets/img/finalproject-img/lodging.png';
							}else if(result.placeCate == 'P'){
								iconImg = '/assets/img/finalproject-img/parkingMark.png';
							}
							marker = new google.maps.Marker({
								position: {
									lat: result.latitude,
									lng: result.longitude
								},
								map: map,
								icon : iconImg,
								zIndex: 1,
								title: result.placeName,
								id: likeId,
							});
							markers.push(marker);
							marker.addListener("click", () => {
								//기존 정보 삭제
								let reviewList = document.querySelector('.place_review_detail');
								let reviewCount = reviewList.children.length;
								if (reviewCount != 0) {
									for (let idx = 0 ; idx < reviewCount; idx++) {
										reviewList.children[0].remove();
									}
								}
								let starCount = document.querySelector('.review_star_all');
								if(starCount.children.length != 0) {
									for (let idx = 0; idx < 5; idx++){
										starCount.children[0].remove();
									}
								}
								map.setCenter(new google.maps.LatLng(result.latitude, result.longitude));
								map.setZoom(18);

								//여행지 상세정보/후기
									$.ajax({
										url: '/planner/placeDetail',
										type: 'post',
										data: JSON.stringify({
											"placeId": likeId,
										}),
										contentType: 'application/json',
										success: function (result) {
											makeMarkerpage(result);
										}
									}).fail(function (error) {
										console.log("마커 정보 실패");
									})
							});
						}
					}).fail(function (error) {
							console.log(error);
					})
				}
			}).fail(function (error) {
				console.log(error);
			})
		}
	  });
	
	
	/*if(delPlace == true){
		$.ajax({
			url: '/planner/deleteLikePlace',
			type: 'post',
			data: {
				"planId" : planId,
				"placeId" : likeId,
			},
			success: function (result) {
				swal("알림", "삭제 되었습니다.", "success")
				event.target.parentElement.remove();
			}
		}).fail(function (error) {
			console.log(error);
		})
	}*/
}

//찜 플레이스 후기
function likeDetail(event){
	//기존 정보 삭제
	let tagRemove = document.querySelector('.place_like');
	if(tagRemove.querySelector('.lrTemplate') != null){
		tagRemove.querySelector('.lrTemplate').nextSibling.remove();
		tagRemove.querySelector('.lrTemplate').remove();
	}
	let likeId = event.target.id;
	$.ajax({
		url: '/planner/likePlaceReview',
		type: 'get',
		data: {
			"placeId" : likeId,
		},
		success: function (result) {
			MakeLikeReview(result, likeId);
			popTopPlaceInfo(likeId);
		}
	}).fail(function (error) {
		console.log(error);
	})

}
//찜 클릭시 상단 정보 노출
function popTopPlaceInfo(likeId){
	$.ajax({
		url: '/planner/placeDetail',
		type: 'post',
		data: JSON.stringify({
			"placeId": likeId,
		}),
		contentType: 'application/json',
		success: function (result) {
			//중심 위치를 클릭된 마커의 위치로 변경
			//했다!
			map.setCenter(new google.maps.LatLng(result.placeInfo.latitude, result.placeInfo.longitude));
			////마커 클릭 시의 줌 변화
			map.setZoom(18);

			let appendPlace = document.getElementById('right_append');
			appendPlace.children[0].style.display = 'none';
			let markerTop = document.getElementById('marker_top');
			markerTop.style.display = 'block';

			document.getElementById('head_Img').style.backgroundImage = "url('" + result.placeInfo.placeImg + "')";
			document.getElementById('trip_place').innerText = result.placeInfo.placeName;
			document.getElementById('trip_info').value = result.placeInfo.placeID;
			document.getElementById('trip_placeadd').innerText = result.placeInfo.placeAdd;
			document.getElementById('phone').innerText = result.placeInfo.placePhone;
			//출발일 ~ 도착일
			let start = startDate.substr(2, 2) + '/' + startDate.substr(5, 2) + '/' + startDate.substr(8, 2);
			let end = endDate.substr(2, 2) + '/' + endDate.substr(5, 2) + '/' + endDate.substr(8, 2);
			document.getElementById('trip_period').innerText = start + '~' + end;
	
		}
	}).fail(function (error) {
		console.log("마커 정보 실패");
	})

}

//찜 후기 append
function MakeLikeReview(result, likeId){
	let template = document.getElementById('likeReviewTemplate').cloneNode(true);
	template.style.display = 'block';
	if(result == 0){
		let divTag = document.createElement('div');
		divTag.setAttribute('class',  'review_none');
		divTag.innerText = '후기가 존재하지 않습니다.'

		template.querySelector('.review_scroll').append(divTag);

		let parent = document.getElementById('Likeplaces');
		let hr = document.createElement('hr');
		for(let i = 2; i < parent.children.length; i++){
			if(parent.children[i].id == likeId){
				parent.children[i].append(template);
				parent.children[i].append(hr);
			}
		}
		document.querySelector('.like_review').style.height = '300px';
		document.querySelector('.review_scroll').style.height = '200px'; 	

	}else if (result != 0){
		for(let j = 0; j < result.length; j++ ){
			//문서 전체를 탐색할 때는 getElementById, querySeletore 사용가능
			//특정 element에서 탐색시 querySeletore 만 사용가능
			let innertemplate = document.querySelector('.review_boxed').cloneNode(true);
			innertemplate.style.display = 'block';
			innertemplate.querySelector('.writerId').innerText = result[j].reviewWriter;
			innertemplate.querySelector('.writeDate').innerText = result[j].reviewDate.substr(2, 8);
			innertemplate.querySelector('.likeStar').innerText = result[j].reviewStar;
			innertemplate.querySelector('.likeReviewContent').innerText = result[j].reviewContent;
			innertemplate.querySelector('.likeReviewUser').setAttribute('src', result[j].memberPhoto);
			//별 붙이기
			for (let i = 1; i <= 5; i++) {
				let div = innertemplate.querySelector('.review_star_all');
				let star = result[j].reviewStar;
				let iTag = document.createElement('i');
				if (i <= star) {
					iTag.setAttribute("class", "fas fa-star");
				} else if (i > star) {
					iTag.setAttribute("class", "fas fa-star");
					iTag.setAttribute("style", "color:black");
				}
				div.append(iTag);
	
			}
			//사진 붙이기
			$.ajax({
				url: '/planner/reviewImg',
				type: 'get',
				data: {
					"placeId": likeId,
					"userId": result[j].reviewWriter,
				},
				success: function (result) {
					if (result.length != 0) {
						if(result.length <= 4){
							for(let Idx = 0; Idx < result.length; Idx++){
								
								let div = document.createElement('div');
								div.style.backgroundImage = "url('" + result[Idx].photoLocation + "')";
								div.setAttribute('class', 'like_review_picture');
							
								//최종적으로 붙이는 장소
								innertemplate.querySelector('.mini_review_picture').append(div);
								//setAttribute 로 onclick 시 'picturePopUp();'와 같은 형태로 입력해 주어야 함
								innertemplate.querySelector('.mini_review_picture').children[Idx].setAttribute('onclick', 'picturePopUp();');
							}
						}else if(result.length > 4){
							for(let Idx = 0; Idx < 4; Idx++){
								let div = document.createElement('div');
								if (Idx < 3){
									div.style.backgroundImage = "url('" + result[Idx].photoLocation + "')";
									div.setAttribute('class', 'like_review_picture');
									div.setAttribute('onclick', 'picturePopUp();');
								}else if (Idx = 4){
									//+a 만큼 사진이 더있다.
									let spanTag = document.createElement('span');
									let reviewImgCount = (result.length - 4);
									spanTag.setAttribute('class', 'likeReviewImgCount');
									spanTag.innerText ="+"+ reviewImgCount;
									div.append(spanTag);
									//
									div.style.backgroundImage = "url('" + result[Idx].photoLocation + "')";
									div.setAttribute('class', 'like_review_picture');
									div.setAttribute('onclick', 'picturePopUp();');
								}
								//최종적으로 붙이는 장소
								innertemplate.querySelector('.mini_review_picture').append(div);
							}
						}
					} //리뷰가 없을때 리뷰가 없습니다 띄워주기
				}
			}).fail(function (error) {
				console.log("후기 불러오기 실패");
			})
	
			//템플릿에 붙이기
			template.querySelector('.review_scroll').append(innertemplate);
		}
		let parent = document.getElementById('Likeplaces');
		let hr = document.createElement('hr');
		for(let i = 2; i < parent.children.length; i++){
			if(parent.children[i].id == likeId){
				parent.children[i].append(template);
				parent.children[i].append(hr);
			}
		}		
	}
}

//체크리스트
function checkListPage(){
	//화면 정리
	document.getElementById('marker_top').style.display = 'none';
	document.getElementById('markerInfo').style.display = 'none';
	document.getElementById('main').style.display = 'none';
	document.querySelector('.first').style.display = "block";
	document.querySelector('.place_like').style.display ="none";
	document.querySelector('.checkList').style.display ="block";
	document.querySelector('.budget').style.display="none";

	allCheckList();
}
function showAllCheck(){
	allCheckList();
}

function showPerCheck(){
	personalCheckList();
}


//전체 체크리스트 페이지 생성
function allCheckList(){
	$.ajax({
		url: '/planner/allCheckList',
		type: 'get',
		data: {
			"planId": planId,
		},
		success: function (result) {
			MakeAllCheckList(result);
		}
	}).fail(function (error) {
		console.log("체크리스트 호출 실패");

	});

}

//전체 체크리스트 만들기
function MakeAllCheckList(result){
	document.querySelector('.allCheckList').style.display = 'block';
	document.querySelector('.personalCheckList').style.display = 'none';
	if(document.querySelector('.allCheck').children.length > 0){
		let length = document.querySelector('.allCheck').children.length;
		for(let i = 0; i < length; i++){
			document.querySelector('.allCheck').children[0].remove();
		}
	}
	for(let i = 0; i < result.length; i++){
		let tr = document.createElement('tr');
		tr.setAttribute('class', 'checkdetail');
		tr.setAttribute('id', result[i].checkId);
		//준비물
		let td = document.createElement('td');
		td.setAttribute('id', result[i].checkId);
		let input = document.createElement('input');
		if(result[i].checkOrNot == 'C'){
			input.setAttribute('class', 'checkCheckbox');
			input.setAttribute('type', 'checkbox');
			input.setAttribute('onclick', 'updateChecked(event);');
			input.checked = true;
		}else {
			input.setAttribute('class', 'checkCheckbox');
			input.setAttribute('onclick', 'updateChecked(event);');
			input.setAttribute('type', 'checkbox');
		}
		td.append(input);
		let span = document.createElement('span');
		span.setAttribute('class', 'stuff');
		span.setAttribute('style', 'margin-left: 25px;');
		span.innerText = result[i].checkContent;
		td.append(span);
		tr.append(td);
		//유저
		td = document.createElement('td');
		span = document.createElement('span');
		span.setAttribute('class', 'userId');
		span.innerText = result[i].writerId;
		td.append(span)
		tr.append(td);
		//버튼
		td = document.createElement('td');
		let button = document.createElement('button');
		button.setAttribute('class', 'btn checkUpdate');
		//수정 버튼
		button.setAttribute('onclick', 'updateAllcheckContent(event);')
		let img =document.createElement('img');
		img.setAttribute('src', '/assets/img/finalproject-img/edit.png');
		button.append(img);
		td.append(button);
		//삭제버튼
		button = document.createElement('button');
		button.setAttribute('class', 'btn checkDelete');
		button.setAttribute('onclick', 'delAllCheck(event);');
		img =document.createElement('img');
		img.setAttribute('src', '/assets/img/finalproject-img/minus.png');
		button.append(img);
		td.append(button);
		//숨김처리된 수정 완료 버튼
		button = document.createElement('button');
		button.setAttribute('class', 'btn checkComplete');
		button.setAttribute('style', 'display:none;');
		button.setAttribute('onclick', 'completAllCheckUpdate(event);');
		img =document.createElement('img');
		img.setAttribute('src', '/assets/img/finalproject-img/edit.png');
		button.append(img);
		td.append(button);
		//숨김처리된 수정 취소 버튼
		button = document.createElement('button');
		button.setAttribute('class', 'btn checkCancel');
		button.setAttribute('style', 'display:none;');
		button.setAttribute('onclick', 'cancelAllCheckUpdate(event);')
		img =document.createElement('img');
		img.setAttribute('src', '/assets/img/finalproject-img/minus.png');
		button.append(img);
		td.append(button);
		tr.append(td);
		
		document.querySelector('.allCheck').append(tr);
	}

}



//checked 여부 수정
function updateChecked(event){
	let checkId = event.target.parentElement.parentElement.id
	$.ajax({
		url: '/planner/updateChecked',
		type: 'post',
		data: {
			"checkId": checkId,
		},
		success: function (result) {
			console.log(result);
		}
	}).fail(function (error) {
		console.log("체크리스트 호출 실패");

	});
}

//전체 체크리스트 추가 tr만들기
function addAllCheckBox(){
	let tr = document.createElement('tr');
		tr.setAttribute('class', 'checkdetail');
		//tr.setAttribute('id', result[i].checkId);
		//준비물
		let td = document.createElement('td');
		let input = document.createElement('input');
		input.setAttribute('class', 'checkCheckbox');
		input.setAttribute('type', 'checkbox');
		td.append(input);
		input = document.createElement('input');
		input.setAttribute('type', 'text');
		input.setAttribute('class', 'checkText');
		td.append(input);
		tr.append(td);
		//담당유저 등록 버튼도 만들기
		td = document.createElement('td');
		input = document.createElement('input');
		input.setAttribute('class', 'checkUser');
		input.setAttribute('type', 'text');
		input.setAttribute('style', 'width: 100px;');
		td.append(input);
		tr.append(td);
		//등록버튼
		td = document.createElement('td');
		let button = document.createElement('button');
		button.setAttribute('class', 'btn checkUpdate');
		img =document.createElement('img');
		img.setAttribute('src', '/assets/img/finalproject-img/plus.png');
		button.setAttribute('onclick', 'insertAllCheckList(event);');
		button.append(img);
		td.append(button);
		//붙이기
		//삭제 버튼
		button = document.createElement('button');
		button.setAttribute('class', 'btn checkDelete');
		img =document.createElement('img');
		img.setAttribute('src', '/assets/img/finalproject-img/minus.png');
		button.setAttribute('onclick', 'cancelInsertAllCheck(event);');
		button.append(img);
		td.append(button);
		//붙이기
	
		tr.append(td);
		//붙이기
		
		document.querySelector('.allCheck').prepend(tr);
}

// 개인 체크리스트 페이지 생성
function personalCheckList(){
	$.ajax({
		url: '/planner/checkList',
		type: 'get',
		data: {
			"planId": planId,
		},
		success: function (result) {
			MakePersonCheckList(result);
		}
	}).fail(function (error) {
		console.log("체크리스트 호출 실패");

	});
}

//개인 체크리스트 만들기
function MakePersonCheckList(result){
	document.querySelector('.personalCheckList').style.display = 'block';
	document.querySelector('.allCheckList').style.display = 'none';
	if(document.querySelector('.personalCheck').children.length > 0){
		let length = document.querySelector('.personalCheck').children.length;
		for(let i = 0; i < length; i++){
			document.querySelector('.personalCheck').children[0].remove();
		}
	}
	for(let i = 0; i < result.length; i++){
		let tr = document.createElement('tr');
		tr.setAttribute('class', 'checkdetail');
		tr.setAttribute('id', result[i].checkId);
		//준비물
		let td = document.createElement('td');
		td.setAttribute('id', result[i].checkId);
		let input = document.createElement('input');
		if(result[i].checkOrNot == 'C'){
			input.setAttribute('class', 'checkCheckbox');
			input.setAttribute('type', 'checkbox');
			input.setAttribute('onclick', 'updateChecked(event);');
			input.checked = true;
		}else {
			input.setAttribute('class', 'checkCheckbox');
			input.setAttribute('onclick', 'updateChecked(event);');
			input.setAttribute('type', 'checkbox');
		}
		td.append(input);
		let span = document.createElement('span');
		span.setAttribute('class', 'stuff');
		span.setAttribute('style', 'margin-left: 25px;');
		span.innerText = result[i].checkContent;
		td.append(span);
		//버튼
		//수정버튼
		let button = document.createElement('button');
		button.setAttribute('class', 'btn checkUpdate');
		button.setAttribute('onclick', 'updateCheckContent(event);')
		let img =document.createElement('img');
		img.setAttribute('src', '/assets/img/finalproject-img/edit.png');
		button.append(img);
		td.append(button);
		//삭제버튼
		button = document.createElement('button');
		button.setAttribute('class', 'btn checkDelete');		
		button.setAttribute('onclick', 'delCheck(event);')
		img =document.createElement('img');
		img.setAttribute('src', '/assets/img/finalproject-img/minus.png');
		button.append(img);
		td.append(button);
		//숨김처리된 수정 완료 버튼
		button = document.createElement('button');
		button.setAttribute('class', 'btn checkComplete');
		button.setAttribute('style', 'display:none;');
		button.setAttribute('onclick', 'completCheckUpdate(event);');
		img =document.createElement('img');
		img.setAttribute('src', '/assets/img/finalproject-img/edit.png');
		button.append(img);
		td.append(button);
		//숨김처리된 수정 취소 버튼
		button = document.createElement('button');
		button.setAttribute('class', 'btn checkCancel');
		button.setAttribute('style', 'display:none;');
		button.setAttribute('onclick', 'cancelCheckUpdate(event);')
		img =document.createElement('img');
		img.setAttribute('src', '/assets/img/finalproject-img/minus.png');
		button.append(img);
		td.append(button);
		tr.append(td);
		document.querySelector('.personalCheck').append(tr);
	}

}

//개인 추가 체크리스트 입력 창 만들기
function addPersonalCheckBox(){
	let tr = document.createElement('tr');
		tr.setAttribute('class', 'checkdetail');
		//tr.setAttribute('id', result[i].checkId);
		//준비물
		let td = document.createElement('td');
		let input = document.createElement('input');
		input.setAttribute('class', 'checkCheckbox');
		input.setAttribute('type', 'checkbox');
		td.append(input);
		input = document.createElement('input');
		input.setAttribute('type', 'text');
		input.setAttribute('class', 'checkText');
		td.append(input);
		//등록버튼
		let button = document.createElement('button');
		button.setAttribute('class', 'btn checkUpdate');
		img =document.createElement('img');
		img.setAttribute('src', '/assets/img/finalproject-img/plus.png');
		button.setAttribute('onclick', 'insertPersonalCheckList(event);');
		button.append(img);
		td.append(button);
		button = document.createElement('button');
		button.setAttribute('class', 'btn checkDelete');
		img =document.createElement('img');
		img.setAttribute('src', '/assets/img/finalproject-img/minus.png');
		button.setAttribute('onclick', 'cancelInsertCheck(event);');
		button.append(img);
		td.append(button);
		tr.append(td);
		
		document.querySelector('.personalCheck').prepend(tr);
}

//해당 내용 개인 체크리스트 db 추가
function insertPersonalCheckList(event){
	let checkContent = event.target.parentElement.parentElement.querySelector('.checkText').value;
	let checkCate = "P";
	if (checkContent == null || checkContent == undefined || checkContent == ''){
		swal("알림", "내용을 입력하세요", "warning")
	 	//alert("내용을 입력하세요");
	}else{
		$.ajax({
			url: '/planner/addCheckList',
			type: 'post',
			data: {
				"checkContent": checkContent,
				"planId": planId,
				"checkCate": checkCate,
			},
			success: function (result) {
				if(result == 1){
					//성공시 리스트 새로 불러오기
					personalCheckList();
				}
			}
		}).fail(function (error) {
			console.log("체크리스트 호출 실패");
		});
	}
}

//전체 체크리스트 db 추가
function insertAllCheckList(event){
	let checkContent = event.target.parentElement.parentElement.parentElement.querySelector('.checkText').value;
	let checkCate = "A";
	let userId = event.target.parentElement.parentElement.parentElement.querySelector('.checkUser').value;
	if (checkContent == null || checkContent == undefined || checkContent == ''){
		swal("알림", "내용을 입력하세요", "warning");
	 	//alert("내용을 입력하세요.");
	}else if(userId == null || userId == undefined || userId == ''){
		swal("알림", "해당 유저를 입력하세요", "warning");
		//alert("해당 유저를 입력하세요.");
	}else{
		$.ajax({
			url: '/planner/addAllCheckList',
			type: 'post',
			data: {
				"checkContent": checkContent,
				"planId": planId,
				"checkCate": checkCate,
				"memberId": userId,
			},
			success: function (result) {
				if(result == 1){
					//성공시 리스트 새로 불러오기
					allCheckList();
				}
			}
		}).fail(function (error) {
			console.log("체크리스트 호출 실패");
		});
	}
}


let contentText;
//개인 체크리스트 수정
function updateCheckContent(event){
	let content = event.target.parentElement.parentElement.querySelector('.stuff');
	contentText = content.innerText;
	content.remove();
	
	let appendSpot = event.target.parentElement.parentElement.querySelector('.checkCheckbox');
	let input = document.createElement('input');
	input.setAttribute('type', 'text');
	input.setAttribute('class', 'checkText');
	input.setAttribute('value', contentText);
	appendSpot.after(input);
	
	let parent = event.target.parentElement.parentElement;
	
	parent.querySelector('.checkUpdate').style.display = 'none';
	parent.querySelector('.checkDelete').style.display = 'none';
	parent.querySelector('.checkComplete').style.display = '';
	parent.querySelector('.checkCancel').style.display = '';

}

//체크리스트 수정 확인 버튼
function completCheckUpdate(event){
	let checkId = event.target.parentElement.parentElement.id;
	let checkContent = event.target.parentElement.parentElement.querySelector('.checkText').value;
	$.ajax({
		url: '/planner/updateCheckContent',
		type: 'post',
		data: {
			"checkId" : checkId,
			"checkContent": checkContent,
		},
		success: function (result) {
			if(result == 1){
				//성공시 리스트 새로 불러오기
				personalCheckList();
			}
		}
	}).fail(function (error) {
		console.log("체크리스트 호출 실패");

	});
}

let userText;
//전체 수정 입력창으로 변경
function updateAllcheckContent(event){
	if(event.target.nodeName != 'IMG') {
		return;
	}
	let content = event.target.parentElement.parentElement.parentElement.querySelector('.stuff');
	let user = event.target.parentElement.parentElement.parentElement.querySelector('.userId');
	contentText = content.innerText;
	userText = user.innerText;
	content.remove();
	user.remove();
	
	let appendSpot = event.target.parentElement.parentElement.parentElement;
	let input = document.createElement('input');
	input.setAttribute('type', 'text');
	input.setAttribute('class', 'checkText');
	input.setAttribute('value', contentText);
	appendSpot.children[0].append(input);
	input = document.createElement('input');
	input.setAttribute('type', 'text');
	input.setAttribute('class', 'userText');
	input.setAttribute('style', 'width: 50%;');
	input.setAttribute('value', userText);
	appendSpot.children[1].append(input);
	
	appendSpot.querySelector('.checkUpdate').style.display = 'none';
	appendSpot.querySelector('.checkDelete').style.display = 'none';
	appendSpot.querySelector('.checkComplete').style.display = '';
	appendSpot.querySelector('.checkCancel').style.display = '';
	
}

//체크리스트 전체 수정 확인 버튼
function completAllCheckUpdate(event){
	if(event.target.nodeName != 'IMG') {
		return;
	}
	let content = event.target.parentElement.parentElement.parentElement.querySelector('.checkText').value;
	let userId = event.target.parentElement.parentElement.parentElement.querySelector('.userText').value;
	let checkId = event.target.parentElement.parentElement.parentElement.id;
	if (content == null || content == undefined || content == ''){
		swal("알림", "내용을 입력하세요", "warning");
		//alert("내용을 입력하세요.");
   }else if(userId == null || userId == undefined || userId == ''){
	   swal("알림", "담당 유저를 입력하세요", "warning");
	   //alert("담당 유저를 입력하세요.");
   }else{
		$.ajax({
			url: '/planner/updateAllCheckContent',
			type: 'post',
			data: {
				"checkId" : checkId,
				"checkContent": content,
				"userId" : userId,
			},
			success: function (result) {
				if(result == 1){
					//성공시 리스트 새로 불러오기
					allCheckList();
				}
			}
		}).fail(function (error) {
			console.log("체크리스트 호출 실패");

		});
	}
}



//체크리스트 수정 취소
function cancelCheckUpdate(event){
	let content = event.target.parentElement.parentElement.querySelector('.checkText');
	content.remove();

	let appendSpot = event.target.parentElement.parentElement.querySelector('.checkCheckbox');
	let span = document.createElement('span');
	span.setAttribute('class', 'stuff');
	span.setAttribute('style', 'margin-left: 25px;');
	span.innerText = contentText;
	appendSpot.after(span);
	
	let parent = event.target.parentElement.parentElement;

	parent.querySelector('.checkUpdate').style.display = '';
	parent.querySelector('.checkDelete').style.display = '';
	parent.querySelector('.checkComplete').style.display = 'none';
	parent.querySelector('.checkCancel').style.display = 'none';

}

//전체 수정 취소 버튼
function cancelAllCheckUpdate(event){
	if(event.target.nodeName != 'IMG') {
		return;
	}
	let content = event.target.parentElement.parentElement.parentElement.querySelector('.checkText');
	let user = event.target.parentElement.parentElement.parentElement.querySelector('.userText');
	content.remove();
	user.remove();

	let appendSpot = event.target.parentElement.parentElement.parentElement;
	//체크물건
	let span = document.createElement('span');
	span.setAttribute('class', 'stuff');
	span.setAttribute('style', 'margin-left: 25px;');
	span.innerText = contentText;
	appendSpot.children[0].append(span);
	//담당 유저
	span = document.createElement('span');
	span.setAttribute('class', 'userId');
	span.innerText = userText;
	appendSpot.children[1].append(span);
	
	appendSpot.querySelector('.checkUpdate').style.display = '';
	appendSpot.querySelector('.checkDelete').style.display = '';
	appendSpot.querySelector('.checkComplete').style.display = 'none';
	appendSpot.querySelector('.checkCancel').style.display = 'none';

}


//체크리스트 입력 취소
function cancelInsertCheck(event){
	let remove = event.target.parentElement.parentElement;
	remove.remove();
}

//체크리스트 전체 입력 취소
function cancelInsertAllCheck(event){
	if(event.target.nodeName != 'IMG') {
		return;
	}
	let remove = event.target.parentElement.parentElement.parentElement;
	remove.remove();
}

//전체 체크리스트 삭제
function delAllCheck(event){
	if(event.target.nodeName != 'IMG') {
		return;
	}
	let removeId = event.target.parentElement.parentElement.parentElement;
	swal({
		  title: "알림",
		  text: "체크리스트를 삭제하시겠습니까?",
		  icon: "info",
		  buttons: true,
		  dangerMode: true,
		})
		.then((willDelete) => {
		  if (willDelete) {
		      $.ajax({
					url: '/planner/deleteCheckList',
					type: 'post',
					data: {
						"checkId" : removeId.id,
					},
					success: function (result) {
						removeId.remove();
					}
				}).fail(function (error) {
					console.log(error);
				});
		  }
		});
	
	
	/*let delCheck = confirm('체크리스트를 삭제하시겠습니까?')
	if(delCheck == true){
		$.ajax({
			url: '/planner/deleteCheckList',
			type: 'post',
			data: {
				"checkId" : removeId.id,
			},
			success: function (result) {
				console.log(removeId);
				removeId.remove();
			}
		}).fail(function (error) {
			console.log(error);
		})
	}*/
}

//개인 체크리스트 삭제
function delCheck(event){
	if(event.target.nodeName != 'IMG') {
		return;
	}
	let removeId = event.target.parentElement.parentElement;
	//let delCheck = confirm('체크리스트를 삭제하시겠습니까?')
	swal({
		title: "알림",
		text: "체크리스트를 삭제하시겠습니까?",
		icon: "info",
		buttons: true,
		dangerMode: true,
	  })
	  .then((willDelete) => {
		if(willDelete){
			$.ajax({
				url: '/planner/deleteCheckList',
				type: 'post',
				data: {
					"checkId" : removeId.id,
				},
				success: function (result) {
					swal("성공", "삭제 되었습니다.", "success");
					//alert('삭제 되었습니다.');
					removeId.remove();
				}
			}).fail(function (error) {
				console.log(error);
			})
		}
	  });
	/*if(delCheck == true){
		$.ajax({
			url: '/planner/deleteCheckList',
			type: 'post',
			data: {
				"checkId" : removeId.id,
			},
			success: function (result) {
				swal("성공", "삭제 되었습니다.", "success");
				//alert('삭제 되었습니다.');
				removeId.remove();
			}
		}).fail(function (error) {
			console.log(error);
		})
	}*/
}


//장부
//장부페이지 불러오기
function planBudget(){
	//페이지 정리
	document.getElementById('marker_top').style.display = 'none';
	document.getElementById('markerInfo').style.display = 'none';
	document.getElementById('main').style.display = 'none';
	document.querySelector('.first').style.display = "block";
	document.querySelector('.checkList').style.display ="none";
	document.querySelector('.budget').style.display="block";
	document.querySelector('.place_like').style.display ="none";
	//여행 일자별 버튼 생성
	dateAppend = document.querySelector('.travel_date');
	let childrenLength = dateAppend.children.length;
	if(childrenLength > 0){
		for(let i = 0; i < childrenLength; i++){
			dateAppend.children[0].remove();
		}
	}
	let start = startDate.substr(0,10);
	for(let i = 0; i < tripPeriod+1; i++){
		let button = document.createElement('button')
		let startDay = new Date(start);
		//날짜 계산하는 법
		
		let calDate = new Date(startDay.setDate(startDay.getDate()+i));
		//getMonth 인덱스와 같이 0 ~ 11로 가지고 온다.
		let day = (calDate.getMonth()+1)+'/'+calDate.getDate();
		button.setAttribute('id', day);
		if (i == 0){
			button.setAttribute('class', 'btn firstbtn');
			document.querySelector('.budgetDate').id = day;
		}else {
			button.setAttribute('class', 'btn otherbtn');
		}
		button.setAttribute('onclick', 'callBudgetList(event);')
		button.innerText = day;
		document.querySelector('.travel_date').append(button)

	}
	
	//시작일 장부 등록
	//장부 리스트 불러오기
	let date = start.substr(6,1)+'/'+start.substr(8, 2);
	console.log(date);
	$.ajax({
		url: '/planner/budgetList',
		type: 'get',
		data: {
			"planId": planId,
			"budgetDate": date,

		},
		success: function (result) {
			if(result.length != 0){
				makeBudgetList(result, date);	
				dayBySum();	
			}
		}
	}).fail(function (error) {
		console.log("체크리스트 호출 실패");

	});
}

//장부 불러오기
function callBudgetList(event){
	let day = event.target.id;
	console.log(event.target);
	//장부 리스트 불러오기
	$.ajax({
		url: '/planner/budgetList',
		type: 'get',
		data: {
			"planId": planId,
			"budgetDate": day,

		},
		success: function (result) {
			makeBudgetList(result, day);	
		}
	}).fail(function (error) {
		console.log("체크리스트 호출 실패");

	});
}

function makeBudgetList(result, day){
	let parent = document.querySelector('.budgetDate');
	parent.setAttribute('id', day);
	let length = parent.children.length;
	if (length > 1){
		for(let i = 0; i < length; i++){
			parent.children[0].remove();
		}	
	}
		
	for(let i =0; i < result.length; i++){
		let template = document.querySelector('.budgetTemplate').cloneNode(true);
		template.setAttribute('class', 'budgetElement');
		template.style.display="";
		template.id = result[i].budgetId;
		template.querySelector('.info').dataset.default = result[i].budgetList;
		template.querySelector('.won').dataset.default = result[i].budgetPrice;
		template.querySelector('.exchangeRate').dataset.default = result[i].budgetPrice;
		template.querySelector('.detail').dataset.default = result[i].budgetContent;
		template.querySelector('.memo').dataset.default = result[i].budgetMemo;

		template.querySelector('.info').innerText = result[i].budgetList;
		template.querySelector('.won').innerText = result[i].budgetPrice;
		template.querySelector('.exchangeRate').innerText = result[i].budgetPrice;
		template.querySelector('.detail').innerText = result[i].budgetContent;
		template.querySelector('.memo').innerText = result[i].budgetMemo;

		parent.append(template);
	}
	sumBudget();
}

//장부 수정 기능 더블클릭
function doubleClick(event){
	//전체에서 이벤트 사용중인거 가지고 와서 다 false 시킴
	/*Array.from(contents).forEach(function(defaultVal){
	//저장하지 않는 내용이라고 판단하여 data태그의 기본값으로 되돌린다.
	defaultVal.textContent = defaultVal.dataset.default;

	//수정불가 상태로 되돌린다.
	defaultVal.contentEditable = false;
	});*/

	if(event.target.isContentEditable == false){

	//편집 가능 상태로 변경
	event.target.contentEditable = true;
	//포커스 지정
	event.target.focus();
	}
}

//엔터키 누를 시 이벤트
function enterPress(event){
	//엔터키 입력시 실행
	if(event.keyCode == 13){
		//입력값이 빈값인지 체크한다.
		if(event.target.textContent == "" || event.target.textContent == null
		|| event.target.textContent == undefined || (event.target.textContent != null
		&& typeof event.target.textContent == "object" && !Object.keys(event.target.textContent).length == "")){
			//내용이 존재하지 않으면 기본값으로 되돌린다.
			event.target.textContent = event.target.dataset.default;
		}else{
			//내용의 수정이 완료 되었다면 data태그의 기본값도 바꿔준다.
			event.target.dataset.default = event.target.textContent;
			updateBudgetInfo(event.target.parentElement.id, event.target.className, event.target.dataset.default)
	
			}
		
		//수정 불가능 상태로 되돌린다.
		event.target.contentEditable = false;
		event.target.style.border="1px solid lightgray";
		sumBudget();
	}

}

//장부 수정 통신
function updateBudgetInfo(id, className, content){	
	$.ajax({
		url: '/planner/updateBudget',
		type: 'post',
		data: {
			"budgetId": id,
			"className": className,
			"content": content,

		},
		success: function (result) {
			console.log(result);
		}
	}).fail(function (error) {
		console.log("장부 수정 실패");

	});

}

//테이블 체크박스 전체체크 전체해제
function allChecked(event){
	let tbody = document.querySelector('.budgetDate');
	let cBox = tbody.querySelectorAll('.tdCheck');
	if (event.target.checked == true){
		for(let i = 0; i< cBox.length; i++){
			cBox[i].checked=true;
		}
	}else {
		for(let i = 0; i< cBox.length; i++){
			cBox[i].checked=false;
		}
	}
}

//삭제 버튼 클릭
function deletBtn(){
	let tbody = document.querySelector('.budgetDate');
	let cBox = tbody.querySelectorAll('.tdCheck');
	let delTr = [];
	for(let i = 0; i< cBox.length; i++){
		if(cBox[i].checked == true){
			let delObject = {"budgetId" : cBox[i].parentElement.parentElement.id};
			delTr.push(delObject);
		}
	}
	delBudget(delTr);
}

//삭제하시겠습니까 물어보기
//장부 삭제 통신
function delBudget(delTr){
	swal({
		title: "알림",
		text: "선택하신 행을 삭제하시겠습니까?",
		icon: "info",
		buttons: true,
		dangerMode: true,
	  })
	  .then((willDelete) => {
		if(willDelete){
			$.ajax({
				url: '/planner/deleteBudget',
				type: 'post',
				data: JSON.stringify(delTr),
				contentType: 'application/json',
				success: function (result) {
					if(result.result == 1){
						reloadBudget(document.querySelector('.budgetDate').id);
					}else if(result.result == 0){
						swal("알림", "삭제중 문제가 발생했습니다. 장부를 다시 출력합니다.", "info");
						//alert("삭제중 문제가 발생했습니다. 장부를 다시 출력합니다.");
						reloadBudget(document.querySelector('.budgetDate').id);
					}
					
				}
			}).fail(function (error) {
				swal("알림", "장부를 삭제하는데 문제가 발생했습니다. 다시 시도하세요.", "info");
				//alert("장부를 삭제하는데 문제가 발생했습니다. 다시 시도하세요.");
		
			});
		}
	  });
	

}


//장부 추가하기 위한 입력창 추가
function insertBudget(){
	let tbody = document.querySelector('.budgetDate');
	let trTag = document.createElement('tr');
	trTag.setAttribute('class', 'tempo');
	for (let i = 0; i < 6; i++){
		let tdTag = document.createElement('td');
		if(i == 2){
			//환율 부분
		}else if (i == 5){
			//추가하기 버튼
			let button = document.createElement('button');
			button.setAttribute('onclick', "insertBtn(event);");
			button.setAttribute('style', 'font-size: 10px; color: black; background: white; text-align: center;');
			button.innerText = '+';
			tdTag.append(button);			
		}else if(i == 1){
			let input =	document.createElement('input');
			input.setAttribute('style', 'border: 0px; width: 100%; text-align: center;');
			input.setAttribute('placeholder', '숫자만 입력하세요.');
			tdTag.append(input);
		}else{
			let input =	document.createElement('input');
			input.setAttribute('style', 'border: 0px; width: 100%;');
			tdTag.append(input);
		}
		trTag.append(tdTag);
	}
	tbody.prepend(trTag);
}

//장부 추가
function insertBtn(event){
	let element = event.target.parentElement.parentElement;
	let id = document.querySelector('.budgetDate').id;
	let budgetList = element.children[0].firstChild.value;
	let budgetPrice = element.children[1].firstChild.value;
	let budgetContent = element.children[3].firstChild.value;
	let budgetMemo = element.children[4].firstChild.value;

	let isNumeric = n => !isNaN(n);
	if(budgetPrice == null || budgetPrice == undefined || budgetPrice == ''){
		budgetPrice = 0;
	}
	
	if(budgetList == null || budgetList == undefined || budgetList == '' ||
		budgetPrice == null || budgetPrice == undefined || budgetPrice == ''||
		budgetContent == null || budgetContent == undefined || budgetContent == ''||
		budgetMemo == null || budgetMemo == undefined || budgetMemo == ''
	){
		swal("알림", "항목을 입력해 주세요", "warning");
		//alert("항목을 입력해 주세요");
	}else {
			if(isNumeric(budgetPrice) == true){
				$.ajax({
					url: '/planner/insertBudget',
					type: 'post',
					data: {
						"planId" : planId,
						"budgetDate": id,
						"budgetList": budgetList,
						"budgetPrice": budgetPrice,
						"budgetContent": budgetContent,
						"budgetMemo": budgetMemo,
			
					},
					success: function (result) {
						if (result == 1){
							reloadBudget(id);
						}
					}
				}).fail(function (error) {
					console.log("장부 수정 실패");
			
				});		
			}else{
				swal("알림", "원화 란에는 숫자만 입력 가능합니다.", "warning");
				//alert("원화 란에는 숫자만 입력 가능합니다.");
			}
	}
}

//장부 다시 출력
function reloadBudget(date){
	$.ajax({
		url: '/planner/budgetList',
		type: 'get',
		data: {
			"planId": planId,
			"budgetDate": date,

		},
		success: function (result) {
			makeBudgetList(result, date);
		}
	}).fail(function (error) {
		console.log("장부 reload 실패");

	});
}

function sumBudget(){
	let sum  = 0;
	let element = document.querySelectorAll('.budgetElement')
	for(let i = 0; i < element.length; i++){
		sum += Number(element[i].children[1].dataset.default);
	}
	document.querySelector('.total').innerText = sum.toLocaleString('ko-KR');
}

//일자별 합계 가지고 오기
function dayBySum(){
	let start = startDate.substr(0,10);
	let dayList = [];
	for(let i = 0; i < tripPeriod+1; i++){
		let button = document.createElement('button')
		let startDay = new Date(start);
		//날짜 계산하는 법
		let calDate = new Date(startDay.setDate(startDay.getDate()+i));
		//getMonth 인덱스와 같이 0 ~ 11로 가지고 온다.
		let day = {"budgetDate" : (calDate.getMonth()+1)+'/'+calDate.getDate()};
		//json은 객체 객체로 보내야한다.
		dayList.push(day);
	}
	dayList.push({"planId" : planId});
	//json으로 보낼때는 무조건 post로 보내기 >> why?
	$.ajax({
		url: '/planner/dayBySum',
		type: 'post',
		data: JSON.stringify(dayList),
		contentType: 'application/json',
		success: function (result) {
			if(result.length != 0){
				dayBySumResult(result);		
			}
		}
	}).fail(function (error) {
		console.log("일자별 합계 호출 실패");

	});
}

//장부 합계 부분 생성
function dayBySumResult(result){
	let appendSpot = document.querySelector('.sumResultTotal')
	if (appendSpot.children.length > 0 ){
		for(let i = 0; i < appendSpot.children.length; i++){
			appendSpot.children[0].empty();
		}
	}
	let sum = 0;
	for(let i =0; i < result.length; i++){
		let tr = document.createElement('tr');
		let td = document.createElement('td');
		td.innerText = result[i].budgetDate;
		tr.append(td);
		td = document.createElement('td');
		td.innerText = result[i].sum.toLocaleString('ko-KR');
		tr.append(td);
		document.querySelector('.sumResult').append(tr);
		//총액 계산
		sum += Number(result[i].sum);
	}
	document.querySelector('.sumResultTotal').innerText = sum.toLocaleString('ko-KR');

}

