//
//  Races.swift
//  MyCare
//
//  Created by Sadham on 22/04/2020.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import Foundation

struct AllRacesResponse: Codable {
    /**
     "raceId": 1,
     "raceCd": "01",
     "raceDesc": "MALAY"
     */
    var raceID: Int
    var raceCode: String
    var raceDesc: String
    
    enum CodingKeys: String, CodingKey {
        case raceDesc
        case raceID = "raceId"
        case raceCode = "raceCd"
    }
}
