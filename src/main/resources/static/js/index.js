let reconnectInterval = 1000; // Initial reconnect interval in milliseconds
const maxReconnectInterval = 30000; // Maximum reconnect interval in milliseconds

var map;
var stompClient;
var markers = {};

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 21.0362213, lng: 105.7826543},
        zoom: 15
    });

    connectWebSocket();
    fetchAllCarLocations();
    setInterval(checkOnlineStatusAndRemoveMarkers, 60000); // Check every minute (adjust as needed)
}

function connectWebSocket() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        reconnectInterval = 1000; // Reset the reconnect interval on successful connection
        stompClient.subscribe('/topic/car-location', function (location) {
            let locationObj = JSON.parse(location.body);
            updateMap(locationObj);
        });
    });

    socket.onclose = function () {
        console.log('WebSocket connection closed.');
        // Attempt to reconnect after a delay
        setTimeout(function () {
            reconnect();
        }, reconnectInterval);
    };
}

function reconnect() {
    if (reconnectInterval < maxReconnectInterval) {
        reconnectInterval *= 2; // Exponential backoff strategy
    }

    console.log('Attempting to reconnect in ' + reconnectInterval + ' milliseconds...');
    connectWebSocket();
}

function updateMap(location) {
    // Update the map markers based on the received location
    var carId = location.carId;
    var latitude = location.latitude;
    var longitude = location.longitude;

    // Check if the marker already exists
    if (markers[carId]) {
        markers[carId].setMap(null);
    }
    markers[carId] = new google.maps.Marker({
        position: {lat: latitude, lng: longitude},
        map: map,
        title: `CarID - ` + carId,
        label: carId,
        icon: './img/car.png',
        optimized: true,
    });

}

function fetchAllCarLocations() {
    // Fetch all car locations from your backend API
    // Adjust the API endpoint based on your application structure
    fetch('/api/all-locations')
        .then(response => response.json())
        .then(locations => {
            Object.values(locations).forEach(location => {
                updateMap(location);
            });
        })
        .catch(error => console.error('Error fetching car locations:', error));
}

function checkOnlineStatusAndRemoveMarkers() {
    // Fetch the latest online/offline status from the server
    fetch('/api/online-status')
        .then(response => response.json())
        .then(onlineStatus => {
            // Iterate through the markers and remove those of offline cars
            Object.keys(markers).forEach(carId => {
                if (!onlineStatus[carId]) {
                    markers[carId].setMap(null);
                    delete markers[carId];
                    console.log('Remove those of offline car ' + carId);
                }
            });
        })
        .catch(error => console.error('Error fetching online status:', error));
}
