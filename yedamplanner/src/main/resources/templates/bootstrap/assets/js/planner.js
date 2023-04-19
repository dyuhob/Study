//기본 지도 출력
let map;
let service;
let infoWindow;

function initMap() {
  let travelLocation = new google.maps.LatLng(35.8714354, 128.601445);
  let seachlocation = new google.maps.LatLng(-33.867, 151.195);
  map = new google.maps.Map(
    document.getElementById('map'), {center: travelLocation, zoom: 15});

  let marker = new google.maps.Marker({
    position: travelLocation,
    map: map,
    draggable: true,
    animation: google.maps.Animation.DROP,
  });
  marker.addListener("click", toggleBounce);
  setMarkers(map);
}

//====================
//     지도 마커
//====================
const likes = [
  ["2.28 기념중앙공원", 35.869973033293284, 128.5977431214526, 4],
  ["경북대학교병원", 35.86663755311095, 128.6044924846811, 3],
  ["스파크랜드", 35.868828528170724, 128.598720370879, 2],
  ["스타벅스 대구종로고택점", 35.868437755546836, 128.59247071026917, 1],
];

function setMarkers(map){
  const image = {
    url: "assets/img/icon/like_mark64.png",
    size: new google.maps.Size(64, 64),
    origin: new google.maps.Point(0, 0),
    anchor: new google.maps.Point(0, 32),
  };
  const shape = {
    coords: [1, 1, 1, 20, 18, 20, 18, 1],
    type: "poly",
  };

  infoWindow = new google.maps.InfoWindow();

  for (let i = 0; i < likes.length; i++) {
    const like = likes[i];

    new google.maps.Marker({
      position: { lat: like[1], lng: like[2] },
      map,
      icon: image,
      shape: shape,
      title: like[0],
      zIndex: like[3],
    });

  }
}
//====================
function toggleBounce() {
  if (marker.getAnimation() !== null) {
    marker.setAnimation(null);
  } else {
    marker.setAnimation(google.maps.Animation.BOUNCE);
  }
}

window.initMap = initMap;
