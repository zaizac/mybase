//
//  Profile.swift
//  MyCare
//
//  Created by Cookie on 4/16/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import Foundation

//Used for viewing the profile.
struct ProfileRequest: Encodable {
    var fwProfId: Int
    var deviceInfo: DeviceInfo
}

struct ProfileResponse: Codable {
    var name: String
    var genderId: String?
    var passportNo: String?
    var contactNo: String
    var nationalityCode: String
    var address: String
    var stateCode: String
    var districtCode: String
    var countryCode: String?
    var mukimCode: String
    var zipCode: String
    var salary: String
    var empName: String
    var empAddress: String
    var empCityCode: String
    var empStateCode: String
    var empCountryCode: String?
    var empZipCode: String
    var empContactNo: String
    var mealTypeId: Int?
    var photoBase64: String?
    var maritialStatus: Int?
    var raceCode: String?
    var occupationId: Int?
    var placeOfBirth: String?
    var age: Int?
    var raceDesc: String?
    var dob: String?
    var state: String
    var district: String
    var mukim: String
    
    enum CodingKeys: String, CodingKey {
        case name, passportNo, contactNo, salary, empName, empContactNo, placeOfBirth
        case state, district, mukim
        case age, raceDesc, dob
        case genderId = "genderMtdtId"
        case nationalityCode = "ntnltyCd"
        case address = "addr"
        case stateCode = "stateCd"
        case districtCode = "districtCd"
        case mukimCode = "mukimCd"
        case countryCode = "cntryCd"
        case zipCode = "zipcode"
        case empAddress = "empAddr"
        case empCityCode = "empCityCd"
        case empStateCode = "empStateCd"
        case empCountryCode = "empCntryCd"
        case empZipCode = "empZipcode"
        case mealTypeId = "mealTypeMtdtId"
        case photoBase64 = "photo"
        case maritialStatus = "maritalStatMtdtId"
        case raceCode = "raceCd"
        case occupationId = "occupationMtdtId"
    }
}

struct UpdateProfileRequest: Encodable {
    var fwProfId: Int
    var name: String
    var address: String
    var stateCode: String
    var districtCode: String
    var countryCode: String?
    var mukimCode: String
    var zipCode: String
    var photoBase64: String?
    var mealTypeId: Int
    var deviceInfo: DeviceInfo
    
    enum CodingKeys: String, CodingKey {
        case name, deviceInfo, fwProfId
        case address = "addr"
        case stateCode = "stateCd"
        case districtCode = "districtCd"
        case mukimCode = "mukimCd"
        case countryCode = "cntryCd"
        case zipCode = "zipcode"
        case mealTypeId = "mealTypeMtdtId"
        case photoBase64 = "photo"
    }
}
