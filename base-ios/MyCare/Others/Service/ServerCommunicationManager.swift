//
//  ServerCommunicationManager.swift
//  BSTBaseFramework
//
//  Created by xamarin developer on 13/12/2019.
//  Copyright © 2018 xamarin developer. All rights reserved.
//

import UIKit

public enum WEB_SERVICE_SETTINGS : String {
    case X_MESSAGE_ID, HTTP_METHOD, BASIC_AUTH, X_LANGUAGE, TIMEOUT, REQ_URL, IS_REQ_RESP_HV_BASE64
}

public class ServerCommunicationManager: NSObject {
    public typealias successCompletion = (_ statusCode: Int, _ respData: Data) -> Void // , _ dictResp: Dictionary<String, Any>
    public typealias failureCompletion = (_ error : String) -> Void
    
    public override init() {}
    
    public class var serverCommManagerSharedInstance : ServerCommunicationManager {
        struct Singleton {
            static let instance = ServerCommunicationManager()
        }
        return Singleton .instance
    }
    
    public func handlePOSTWebService(reqSettings: Dictionary<WEB_SERVICE_SETTINGS, Any>, postParamter : Data, onSuccess :@escaping successCompletion, onFailure : @escaping failureCompletion){
        
        // Internet Connection Avail?
        if !Reachability.isInternetAvailable() {
            Spinner.shared.hide()
            onFailure(Constants.noInternet.localized())
            return
        }
        
        // URL Request String
        let urlString               = reqSettings[WEB_SERVICE_SETTINGS.REQ_URL] as! String
        guard let requestURL = URL(string: urlString) else {
            onFailure("Invalid URL")
            return
        }
        
        // URL Request Settings
        var urlRequest              = URLRequest(url: requestURL)
        urlRequest.httpBody         = postParamter
        urlRequest.httpMethod       = "POST"
        urlRequest.cachePolicy      = .reloadIgnoringCacheData
        
        let intTimeout              = Int(reqSettings[WEB_SERVICE_SETTINGS.TIMEOUT] as? Int ?? 0)
        urlRequest.timeoutInterval  = TimeInterval(intTimeout)
        
        urlRequest.setValue("application/json",      forHTTPHeaderField:"Content-Type")
        urlRequest.setValue(reqSettings[WEB_SERVICE_SETTINGS.X_LANGUAGE] as? String,   forHTTPHeaderField:"X-Language")
        urlRequest.setValue(reqSettings[WEB_SERVICE_SETTINGS.X_MESSAGE_ID] as? String,   forHTTPHeaderField:"X-Message-Id")
        
        if reqSettings[WEB_SERVICE_SETTINGS.BASIC_AUTH] as? String != "" {
            let loginString         = reqSettings[WEB_SERVICE_SETTINGS.BASIC_AUTH] as! String
            guard let loginData     = loginString.data(using: String.Encoding.utf8) else { return }
            let base64LoginString   = loginData.base64EncodedString()
            urlRequest.setValue("Basic \(base64LoginString)",      forHTTPHeaderField:"Authorization")
        }
        
        // Request Session Config
        let urlSessionConfig    = URLSessionConfiguration.default
        let urlSession          = URLSession(configuration: urlSessionConfig)
        let task    = urlSession.dataTask(with: urlRequest, completionHandler: {
            (data, response, error) in
            
            Spinner.shared.hide()
            
            if error != nil {
                onFailure(error!.localizedDescription)
            } else if let respData = data {
                let httpResponse = response as! HTTPURLResponse
                let statusCode = httpResponse.statusCode
                
                // TODO: Write Req & Resp in .txt file
                if (reqSettings[WEB_SERVICE_SETTINGS.IS_REQ_RESP_HV_BASE64] as? Bool == true) {
                    
                }
                
                onSuccess(statusCode, respData)
            } else {
                onFailure(Constants.unableToProceed.localized())
            }
            
        })
        task.resume();
    }
    
    public func handleGETWebService(reqSettings: Dictionary<WEB_SERVICE_SETTINGS, Any>, onSuccess :@escaping successCompletion, onFailure : @escaping failureCompletion){
        
        // Internet Connection Avail?
        if !Reachability.isInternetAvailable() {
            Spinner.shared.hide()
            onFailure(Constants.noInternet.localized())
            return
        }
        
        // URL Request String
        let urlString               = reqSettings[WEB_SERVICE_SETTINGS.REQ_URL] as! String
        guard let requestURL = URL(string: urlString) else {
            onFailure("Invalid URL")
            return
        }
        
        // URL Request Settings
        var urlRequest              = URLRequest(url: requestURL)
        urlRequest.httpMethod       = "GET"
        urlRequest.cachePolicy      = .reloadIgnoringCacheData
        
        let intTimeout              = Int(reqSettings[WEB_SERVICE_SETTINGS.TIMEOUT] as? Int ?? 0)
        urlRequest.timeoutInterval  = TimeInterval(intTimeout)
        
        urlRequest.setValue("application/json",      forHTTPHeaderField:"Content-Type")
        urlRequest.setValue(reqSettings[WEB_SERVICE_SETTINGS.X_LANGUAGE] as? String,   forHTTPHeaderField:"X-Language")
        urlRequest.setValue(reqSettings[WEB_SERVICE_SETTINGS.X_MESSAGE_ID] as? String,   forHTTPHeaderField:"X-Message-Id")
        
        if reqSettings[WEB_SERVICE_SETTINGS.BASIC_AUTH] as? String != "" {
            let loginString         = reqSettings[WEB_SERVICE_SETTINGS.BASIC_AUTH] as! String
            guard let loginData     = loginString.data(using: String.Encoding.utf8) else { return }
            let base64LoginString   = loginData.base64EncodedString()
            urlRequest.setValue("Basic \(base64LoginString)",      forHTTPHeaderField:"Authorization")
        }
        
        // Request Session Config
        let urlSessionConfig    = URLSessionConfiguration.default
        let urlSession          = URLSession(configuration: urlSessionConfig)
        let task    = urlSession.dataTask(with: urlRequest, completionHandler: {
            (data, response, error) in
            
            Spinner.shared.hide()
            
            if error != nil {
                onFailure(error!.localizedDescription)
            }
            else if let respData = data {
                let httpResponse = response as! HTTPURLResponse
                let statusCode = httpResponse.statusCode
                
                onSuccess(statusCode, respData)
            } else {
                onFailure(Constants.unableToProceed.localized())
            }
        })
        task.resume();
    }
}
