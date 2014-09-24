var associative_array = {'Accounting': 'accounting', 'Airport': 'airport',
    'Amusement Park': 'amusement_park', 'Aquarium': 'aquarium',
    'Art Gallery': 'art_gallery', 'ATM': 'atm', 'Bakery': 'bakery',
    'Bank': 'bank', 'Bar': 'bar', 'Beauty Salon': 'beauty_salon',
    'Bicycle Store': 'bicycle_store', 'Book Store': 'book_store',
    'Bowling Alley': 'bowling_alley', 'Bus Station': 'bus_station',
    'Cafe': 'cafe', 'Campground': 'campground', 'Car Dealer': 'car_dealer',
    'Car Rental': 'car_rental', 'Car Wash': 'car_wash', 'Casino': 'casino',
    'Cemetery': 'cemetery', 'Church': 'church', 'City Hall': 'city_hall',
    'Clothing Store': 'clothing_store', 'Convenience Store': 'convenience_store',
    'Courthouse': 'courthouse', 'Dentist': 'dentist', 'Department Store':
    'department_store', 'Doctor': 'doctor', 'Electrician': 'electrician',
    'Electronics Store': 'electronics_store', 'Embassy': 'embassy',
    'Establishment': 'establishment', 'Finance': 'finance',
    'Fire Station': 'fire_station', 'Florist': 'florist', 'Food': 'food',
    'Funeral Home': 'funeral_home', 'Furniture Store': 'furniture_store',
    'Gas Station': 'gas_station', 'General Contractor': 'general_contractor',
    'Grocery': 'grocery_or_supermarket', 'Gym': 'gym', 'Hair Care': 'hair_care',
    'Hardware Store': 'hardware_store', 'Health': 'health', 'Hindu Temple':
    'hindu_temple', 'Home Goods Store': 'home_goods_store', 'Hospital':
    'hospital', 'Insurance Agency': 'insurance_agency', 'Jewelry Store':
    'jewelry_store', 'Laundry': 'laundry', 'Lawyer': 'lawyer',
    'Library': 'library', 'Liquor Store': 'liquor_store',
    'Local Government Office': 'local_government_office', 'Locksmith':
    'locksmith', 'Lodging': 'lodging', 'Meal Delivery': 'meal_delivery',
    'Meal Takeaway': 'meal_takeaway', 'Mosque': 'mosque', 'Movie Rental':
    'movie_rental', 'Moving Company': 'moving_company', 'Museum': 'museum',
    'Night Club': 'night_club', 'Painter': 'painter', 'Park': 'park',
    'Parking': 'parking', 'Pet Store': 'pet_store', 'Pharmacy': 'pharmacy',
    'Physiotherapist': 'physiotherapist', 'Place of Worship':
    'place_of_worship', 'Plumber': 'plumber', 'Police': 'police',
    'Real Estate Agency': 'real_estate_agency', 'Restaurant': 'restaurant',
    'Roofing Contractor': 'roofing_contractor', 'RV Park': 'rv_park',
    'School': 'school', 'Shoe Store': 'shoe_store', 'Shopping Mall':
    'shopping_mall', 'Spa': 'spa', 'Stadium': 'stadium', 'Storage': 'storage',
    'Store': 'store', 'Subway Station': 'subway_station', 'Supermarket':
    'grocery_or_supermarket', 'Synagogue': 'synagogue', 'Taxi Stand':
    'taxi_stand', 'Train Station': 'train_station', 'Travel Agency':
    'travel_agency', 'University': 'university', 'Veterinary Care':
    'veterinary_care', 'Zoo': 'zoo'};

function changeValueToGoogleCode(element) {
    element.value = getValueFromKey(element);
}

function getValueFromKey(element) {
    var key = element.value;
    var value = null;
    for ( var i in associative_array )
    {
        if (i === key)
            value = associative_array[i];
    }
    return value;
}

var substringMatcher = function (strs) {
    return function findMatches(q, cb) {
        var matches, substrRegex;
        matches = [];
        substrRegex = new RegExp(q, 'i');
        $.each(strs, function (i, str) {
            if (substrRegex.test(str)) {
                matches.push({ value: str });
            }
        });
        cb(matches);
    };
};

function keys(obj) {
    var keys = [];
    for (var key in obj) {
        if(obj.hasOwnProperty(key)) {
            keys.push(key);
        }
    }
    return keys;
}

$('.typeahead').typeahead({
    hint: true,
    highlight: true,
    minLength: 1
},
{
    name: 'states',
    displayKey: 'value',
    source: substringMatcher(keys(associative_array))
});
