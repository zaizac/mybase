//
//  Notification.swift
//  PagohCare
//
//  Created by Cookie on 4/28/20.
//  Copyright © 2020 Bestinet. All rights reserved.
//

import Foundation

struct NotifyRequest: Encodable {
    var notifyTo: String?
    var deviceInfo: DeviceInfo
}

struct Notify:  Codable {
    var title: String
    var date: String
    var content: String
    
    enum CodingKeys: String, CodingKey {
        case title = "subject"
        case content = "details"
        case date = "createDt"
    }
}
