<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>GMaps.js &mdash; Context menu</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=true"></script>
    <script type="text/javascript" src="/CS2340Servlet/js/gmaps.js"></script>
    <link rel="stylesheet" href="http://twitter.github.com/bootstrap/1.3.0/bootstrap.min.css" />
    <script>
        $(document).ready(function () {
            new GMaps({
                div: '#map',
                width: '500px',
                height: '500px',
                lat: -12.043333,
                lng: -77.028333
            });
        });
    </script>
</head>
<body>
<div id="map"></div>
</body>
</html>

