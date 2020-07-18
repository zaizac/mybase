//
//  UserLocationManager.swift
//  BSTBaseFramework
//
//  Created by xamarin developer on 22/11/2018.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

// FRAMEWORK DONT WANT LOCATION PERMISSION.
// THE PROJECT WHICH USING THIS FRAMEWORK IS ENOUGH TO ADD PERMISSION IN .plist.

import CoreLocation

public enum GEO_DETAILS : String {
    case LATITUDE, LONGITUDE, NAME, LOCALITY, SUBLOCALITY, ADMINISTRATIVE, SUB_ADMINISTRATIVE, POSTAL_CODE
}

public protocol LocationUpdateProtocol {
    // Required delegate methods
    func didChangeAuthorizationInLocationSettings(status: CLAuthorizationStatus)
    
    // Optional delegate methods - 100% swift way
    func readUpdatedLocationInfo()
    func didFailWithErrorsWhenReadLocations(errors : String)
}

public extension LocationUpdateProtocol {
    // Optional delegate methods: those below methods are just an empty implementation
    func readUpdatedLocationInfo() {
    }
    
    func didFailWithErrorsWhenReadLocations(errors : String) {
        
    }
}

public class UserLocationManager: NSObject, CLLocationManagerDelegate {
    
    // MARK: - Properties
    public static let shared = UserLocationManager()
    public var delegate : LocationUpdateProtocol!
    
    public var currentLocation : CLLocation?
    public var dictUpdatedLocDetails : Dictionary<GEO_DETAILS, Any>?
    
    private var locationManager = CLLocationManager()
    
    // MARK: - CLLocationManagerDelegate
    public func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        self.currentLocation = locations.last! // Coordinates only
        
        self.fetchCountryAndCity(gpsError: { (error) in }, gpsResults: { (dictResults) in
            self.dictUpdatedLocDetails = dictResults
        })
        
        self.delegate.readUpdatedLocationInfo()
    }
    
    public func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        self.delegate.didChangeAuthorizationInLocationSettings(status: status)
    }
    
    public func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        self.delegate.didFailWithErrorsWhenReadLocations(errors: error.localizedDescription)
    }
    
    // MARK: - Custom Methods
    public func startLocationManager() {
        self.locationManager.delegate = self
        self.locationManager.desiredAccuracy = kCLLocationAccuracyBest
        self.locationManager.requestWhenInUseAuthorization()
        self.locationManager.startUpdatingLocation()
    }
    
    public func stopLocationManager() {
        self.locationManager.stopUpdatingLocation()
    }
    
    public func fetchCountryAndCity(gpsError: @escaping (String) -> (), gpsResults: @escaping (Dictionary<GEO_DETAILS, Any>) -> ()) {
            
        CLGeocoder().reverseGeocodeLocation(self.currentLocation ?? CLLocation())
        {
            placemarks, error -> Void in
            
            // Place details
            guard let placeMark = placemarks?.first else {
                gpsError("NO PLACEMARK AVAILABLE")
                return
            }
            
            if let error = error {
                gpsError(error.localizedDescription)
                return
            }
            
            var dictGpsFullDetails : Dictionary<GEO_DETAILS, Any>?
            dictGpsFullDetails = [
                GEO_DETAILS.LATITUDE   : "\(String(describing: self.currentLocation?.coordinate.latitude))",
                GEO_DETAILS.LONGITUDE  : "\(String(describing: self.currentLocation?.coordinate.longitude))"
            ]

            if let name = placeMark.name {
                dictGpsFullDetails?[GEO_DETAILS.NAME] = name
            }
            
            if let subLocality = placeMark.subLocality {
                dictGpsFullDetails?[GEO_DETAILS.SUBLOCALITY] = subLocality
            }
            
            if let locality = placeMark.locality {
                dictGpsFullDetails?[GEO_DETAILS.LOCALITY] = locality
            }
            
            if let subAdministrativeArea = placeMark.subAdministrativeArea {
                dictGpsFullDetails?[GEO_DETAILS.SUB_ADMINISTRATIVE] = subAdministrativeArea
            }
            
            if let administrativeArea = placeMark.administrativeArea {
                dictGpsFullDetails?[GEO_DETAILS.ADMINISTRATIVE] = administrativeArea
            }
            
            if let postalCode = placeMark.postalCode {
                dictGpsFullDetails?[GEO_DETAILS.POSTAL_CODE] = postalCode
            }
            
            gpsResults(dictGpsFullDetails ?? [:])
        }
    }
}



