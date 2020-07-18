//
//  City.swift
//  MyCare
//
//  Created by Cookie on 4/16/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import Foundation

struct CityRequest: Encodable {
    var stateCode: String
    var deviceInfo: DeviceInfo
    
    enum CodingKeys: String, CodingKey {
        case stateCode = "stateCd"
    }
}

struct AllCities: Decodable {
    var id: Int
    var code: String
    var description: String
    
    enum CodingKeys: String, CodingKey {
        case id = "cityId"
        case code = "cityCd"
        case description = "cityDesc"
    }
}
