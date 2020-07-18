//
//  Worker.swift
//  MyCare
//
//  Created by Cookie on 4/16/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import Foundation

struct InsertWorker: Encodable {
    var name: String
    var genderId: Int
    var passportNo: String
    var contactNo: String
    var nationalityCode: String
    var address: String
    var stateCode: String
    var districtCode: String
    var countryCode: String
    var mukimCode: String
    var zipCode: String
    var salary: Double
    var empName: String?
    var empAddress: String?
    var empCityCode: String?
    var empStateCode: String?
    var empCountryCode: String?
    var empZipCode: String?
    var empContactNo: String?
    var mealTypeId: Int
    var photoBase64: String
    
    // New
    var cityCode: String
    var empDistrictCode: String?
    var empMukimCode: String?
    var fwProfId: Int
    var maritalId: Int
    var occupationId: Int
    var placeOfBirth: String
    var raceCode, raceDesc: String
    var age, dob: String
    
    var occupationDesc: String
    
    var deviceInfo: DeviceInfo
    
    enum CodingKeys: String, CodingKey {
        case name, passportNo, contactNo, salary, empName, empContactNo, fwProfId, placeOfBirth, dob, age, raceDesc, deviceInfo
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
        case cityCode = "cityCd"
        case empDistrictCode = "empDistrictCd"
        case empMukimCode = "empMukimCd"
        case maritalId = "maritalStatMtdtId"
        case occupationId = "occupationMtdtId"
        case occupationDesc = "occupationMtdtDesc"
        case raceCode = "raceCd"
    }
}

/**
     {
     "addr": "SEREMBAN 2, 70300 SEREMBAN, NEGERI SEMBILAN, MALAYSIA",
     "age": 44,
     "cityCd": "525",
     "cntryCd": "MYS",
     "contactNo": "01123369696",
     "districtCd": "050008",
     "dob": "01/08/1975",
     "empAddr": "BLOCK 5, CORPORATE PARK, STAR CENTRAL, LINGKARAN CYBER POINT TIMUR, CYBER 12, 63000 CYBERJAYA, SELANGOR, MALAYSIA",
     "empCityCd": "1010",
     "empContactNo": "0386638663",
     "empDistrictCd": "100007",
     "empMukimCd": "MK1070001",
     "empName": "BESTINET SDN BHD",
     "empStateCd": "010",
     "empZipcode": "63000",
     "fwProfId": 0,
     "genderMtdtId": 8,
     "maritalStatMtdt": "MARRIED",
     "maritalStatMtdtId": 325,
     "mealTypeMtdtId": 348,
     "name": "FAISAL",
     "ntnltyCd": "MYS",
     "occupationMtdtDesc": "PRIVATE SECTOR",
     "occupationMtdtId": 351,
     "passportNo": "750801055663",
     "photo": "",
     "placeOfBirth": "SEREMBAN",
     "raceCd": "01",
     "raceDesc": "MALAY",
     "salary": 0,
     "stateCd": "005",
     "zipcode": "70300"
 }

 */
