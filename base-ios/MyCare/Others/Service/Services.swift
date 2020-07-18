//
//  Services.swift
//  PagohCare
//
//  Created by Cookie on 4/16/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import Foundation

struct WEB_SERVICE_CONFIG {
    static let TIMEOUT              = 60
    static let X_MSG_ID             = "24234234"
    static let BASIC_AUTH_NAME      = "mycare-sgw"
    static let BASIC_AUTH_PWD       = "secret"
}

/**
enum MESSAGES_TO_USER {
    static let NO_INTERNET              = Constants.noInternet.localized()
    static let BAD_REQUEST              = Constants.unableToProceed.localized()
    static let BAD_RESPONSE             = Constants.enconterServerError.localized()
    static let CANT_READ_RESPONSE       = Constants.unableToProceed.localized()
    static let DONT_LOG_BASE64          = "Dont log Base64 req/resp. pls written in .txt file & send it to backend if issue" // no need to translate
}*/

enum HTTP_METHOD : String {
    case POST, GET
}

class Services: NSObject {
    
    // Create singleton instance
    class var shared : Services {
        struct Singleton {
            static let instance = Services()
        }
        return Singleton .instance
    }
    
    // Prepare request
    private func prepareServiceInputs(httpMethod: HTTP_METHOD, reqURL: String, isReqOrRespHaveBase64: Bool) -> Dictionary<WEB_SERVICE_SETTINGS, Any> {
        
        let basicAuth = WEB_SERVICE_CONFIG.BASIC_AUTH_NAME + ":" + WEB_SERVICE_CONFIG.BASIC_AUTH_PWD
        
        let dict : Dictionary<WEB_SERVICE_SETTINGS, Any> = [
            WEB_SERVICE_SETTINGS.X_MESSAGE_ID      : WEB_SERVICE_CONFIG.X_MSG_ID,
            WEB_SERVICE_SETTINGS.TIMEOUT           : WEB_SERVICE_CONFIG.TIMEOUT,
            WEB_SERVICE_SETTINGS.BASIC_AUTH        : basicAuth,
            WEB_SERVICE_SETTINGS.X_LANGUAGE        : "",
            WEB_SERVICE_SETTINGS.HTTP_METHOD       : httpMethod.rawValue,
            WEB_SERVICE_SETTINGS.REQ_URL           : reqURL,
            WEB_SERVICE_SETTINGS.IS_REQ_RESP_HV_BASE64  : isReqOrRespHaveBase64
        ]
        return dict
    }
    
    private func prepareServiceInputsWithCustomBasicAuth(httpMethod: HTTP_METHOD, reqURL: String, basicAuthPwd: String, isReqOrRespHaveBase64: Bool) -> Dictionary<WEB_SERVICE_SETTINGS, Any> {
        
        var basicAuth = WEB_SERVICE_CONFIG.BASIC_AUTH_NAME
        if basicAuthPwd != "" {
            basicAuth = WEB_SERVICE_CONFIG.BASIC_AUTH_NAME + ":" + basicAuthPwd
        } else {
            basicAuth = WEB_SERVICE_CONFIG.BASIC_AUTH_NAME + ":" + WEB_SERVICE_CONFIG.BASIC_AUTH_PWD
        }
        
        let dict : Dictionary<WEB_SERVICE_SETTINGS, Any> = [
            WEB_SERVICE_SETTINGS.X_MESSAGE_ID      : WEB_SERVICE_CONFIG.X_MSG_ID,
            WEB_SERVICE_SETTINGS.TIMEOUT           : WEB_SERVICE_CONFIG.TIMEOUT,
            WEB_SERVICE_SETTINGS.BASIC_AUTH        : basicAuth,
            WEB_SERVICE_SETTINGS.X_LANGUAGE        : "",
            WEB_SERVICE_SETTINGS.HTTP_METHOD       : httpMethod.rawValue,
            WEB_SERVICE_SETTINGS.REQ_URL           : reqURL,
            WEB_SERVICE_SETTINGS.IS_REQ_RESP_HV_BASE64  : isReqOrRespHaveBase64
        ]
        return dict
    }
    
    private func extractResponseBody(respData: Data) -> (isSuccess: Bool, respBody: Any) {
        do {
            let dictFullResponse = try JSONSerialization.jsonObject(with: respData, options: .allowFragments) as? [String:Any] ?? [:]
            let respBody = dictFullResponse["response"] as Any // Dict or Array
            // TODO: Add param in this method to know whether response have base64 or not. If yes, dont print but save it in .rtf / .txt
            //print("API RESPONSE: ", respBody)
            
            // Check Any is nil
            if case Optional<Any>.none = respBody {
                return(false, [:]) // u can send [] also. Nothing happened bcz of isSuccess is false
            }
            
            // if response is array & array count is zero
            if let arrayResp = dictFullResponse["response"] as? [AnyObject], arrayResp.count <= 0 {
                return (false, [])
            }
            
            // RespBody should be dictionary or array with objects
            return (true, respBody)
        } catch let error {
            print("ERROR WHEN READ JSON: ", error)
            return (false, [])
        }
    }
    
    private func getErrorMessage(fromCode code: String) -> String {
        // NOTE: Dont show "No Record Found" on anytime.
        switch code {
        case "E404SGW001":
            return Constants.myKadContactNumberIncorret.localized()
        case "E404SGW002":
            return "IC/PassportNo field is empty"
        case "E404SGW003":
            return "Contact Number field is empty"
        case "E404SGW004":
            return Constants.invalidLogin.localized()
        case "E409SGW001":
            return Constants.recordAlreadyExist.localized()
        case "E404SGW005", "E404SGW006", "I404IDM115", "I404IDM113", "I404IDM111":
            return Constants.userAlreadyLoginRelogin.localized()
        default:
            return "" // then service return server message to user
        }
    }
    
    // MARK: - Login | Logout | Register Status
    
    func loginService(requestObj: LoginRequest,
                      Success:@escaping ((_ userModelObj : LoginResponse, _ serverModalObj : ServerModel) ->Void),
                      Failure:@escaping ((_ serverError : String) -> Void))
    {
        print("++++++ SERVICE - STARTED ++++++")
        print("Request URL: \(Endpoint.login)")
        
        do {
            let reqEncodedData = try JSONEncoder().encode(requestObj)
            
            let dictInputs = prepareServiceInputs(httpMethod: .POST,
                                                  reqURL: Endpoint.login,
                                                  isReqOrRespHaveBase64: false)
            
            let serverCommObj = ServerCommunicationManager.serverCommManagerSharedInstance
            serverCommObj.handlePOSTWebService(reqSettings: dictInputs, postParamter: reqEncodedData, onSuccess: { (statusCode : Int, respData: Data) in
                
                do {
                    // Server Data
                    let serverData = try JSONDecoder().decode(ServerModel.self, from: respData)
                    if serverData.status != 200 {
                        let errorMsg = self.getErrorMessage(fromCode: serverData.code ?? "")
                        Failure(errorMsg != "" ? errorMsg : Constants.myKadContactNumberIncorret.localized())
                        return // important
                    }
                    
                    // Extract "response": {} from server response
                    let jsonValidate = self.extractResponseBody(respData: respData)
                    if jsonValidate.isSuccess {
                        let jsonData = try JSONSerialization.data(withJSONObject: jsonValidate.respBody, options: .prettyPrinted)
                        let finalRespData = try JSONDecoder().decode(LoginResponse.self, from: jsonData)
                        Success(finalRespData, serverData)
                    } else {
                        Failure(Constants.unableToProceed.localized())
                    }
                } catch let error {
                    print("ERROR WHEN DECODE: ", error)
                    Failure(Constants.unableToProceed.localized())
                }
            }, onFailure: { (error) in
                Failure(error)
            })
        }
        catch let error {
            print("ERROR WHEN ENCODE: ", error)
            Failure(Constants.unableToProceed.localized())
        }
    }
    
    func adminLoginService(requestObj: AdminLoginRequest,
                      Success:@escaping ((_ userModelObj : AdminLoginResponse, _ serverModalObj : ServerModel) ->Void),
                      Failure:@escaping ((_ serverError : String) -> Void))
    {
        print("++++++ SERVICE - STARTED ++++++")
        print("Request URL: \(Endpoint.adminLogin)")
        
        do {
            let reqEncodedData = try JSONEncoder().encode(requestObj)
            
            let dictInputs = prepareServiceInputs(httpMethod: .POST,
                                                  reqURL: Endpoint.adminLogin,
                                                  isReqOrRespHaveBase64: false)
            
            let serverCommObj = ServerCommunicationManager.serverCommManagerSharedInstance
            serverCommObj.handlePOSTWebService(reqSettings: dictInputs, postParamter: reqEncodedData, onSuccess: { (statusCode : Int, respData: Data) in
                
                do {
                    // Server Data
                    let serverData = try JSONDecoder().decode(ServerModel.self, from: respData)
                    if serverData.status != 200 {
                        let errorMsg = self.getErrorMessage(fromCode: serverData.code ?? "")
                        Failure(serverData.message ?? "")
                        return // important
                    }
                    
                    // Extract "response": {} from server response
                    let jsonValidate = self.extractResponseBody(respData: respData)
                    if jsonValidate.isSuccess {
                        let jsonData = try JSONSerialization.data(withJSONObject: jsonValidate.respBody, options: .prettyPrinted)
                        let finalRespData = try JSONDecoder().decode(AdminLoginResponse.self, from: jsonData)
                        Success(finalRespData, serverData)
                    } else {
                        Failure(Constants.unableToProceed.localized())
                    }
                } catch let error {
                    print("ERROR WHEN DECODE: ", error)
                    Failure(Constants.unableToProceed.localized())
                }
            }, onFailure: { (error) in
                Failure(error)
            })
        }
        catch let error {
            print("ERROR WHEN ENCODE: ", error)
            Failure(Constants.unableToProceed.localized())
        }
    }
    
    func logoutService(reqModelObj: LogoutRequest, Success:@escaping ((_ serverModalObj : ServerModel) ->Void), Failure:@escaping ((_ serverError: String) -> Void))
    {
        print("++++++ SERVICE - STARTED ++++++")
        print("Request URL: \(Endpoint.logout)")
        
        do {
            let reqEncodedData = try JSONEncoder().encode(reqModelObj)
            
            let accessToken = AppData.accessToken
            let dictInputs = prepareServiceInputsWithCustomBasicAuth(httpMethod: .POST,
                                                                     reqURL: Endpoint.logout,
                                                                     basicAuthPwd: accessToken,
                                                                     isReqOrRespHaveBase64: false)
            
            let serverCommObj = ServerCommunicationManager.serverCommManagerSharedInstance
            serverCommObj.handlePOSTWebService(reqSettings: dictInputs, postParamter: reqEncodedData, onSuccess: { (statusCode : Int, respData: Data) in
                
                do {
                    // Server Data
                    let serverData = try JSONDecoder().decode(ServerModel.self, from: respData)
                    if serverData.status != 200 {
                        let errorMsg = self.getErrorMessage(fromCode: serverData.code ?? "")
                        Failure(errorMsg != "" ? errorMsg : Constants.userAlreadyLoginRelogin.localized())
                        return // important
                    } else {
                        Success(serverData)
                    }
                } catch let error {
                    print("ERROR WHEN DECODE: ", error)
                    Failure(Constants.unableToProceed.localized())
                }
            }, onFailure: { (error) in
                Failure(error)
            })
        }
        catch let error {
            print("ERROR WHEN ENCODE: ", error)
            Failure(Constants.unableToProceed.localized())
        }
    }
    
    func registerStatusService(requestObj: RegisterStatusRequest,
                      Success:@escaping ((_ userModelObj : RegisterStatusResponse, _ serverModalObj : ServerModel) ->Void),
                      Failure:@escaping ((_ serverError : String) -> Void))
    {
        print("++++++ SERVICE - STARTED ++++++")
        print("Request URL: \(Endpoint.registerStatus)")
        
        do {
            let reqEncodedData = try JSONEncoder().encode(requestObj)
            
            let dictInputs = prepareServiceInputs(httpMethod: .POST,
                                                  reqURL: Endpoint.registerStatus,
                                                  isReqOrRespHaveBase64: false)
            
            let serverCommObj = ServerCommunicationManager.serverCommManagerSharedInstance
            serverCommObj.handlePOSTWebService(reqSettings: dictInputs, postParamter: reqEncodedData, onSuccess: { (statusCode : Int, respData: Data) in
                
                do {
                    // Server Data
                    let serverData = try JSONDecoder().decode(ServerModel.self, from: respData)
                    if serverData.status != 200 {
                        let errorMsg = self.getErrorMessage(fromCode: serverData.code ?? "")
                        Failure(errorMsg != "" ? errorMsg : serverData.message ?? Constants.unableToProceed.localized())
                        return // important
                    }
                    
                    // Extract "response": {} from server response
                    let jsonValidate = self.extractResponseBody(respData: respData)
                    if jsonValidate.isSuccess {
                        let jsonData = try JSONSerialization.data(withJSONObject: jsonValidate.respBody, options: .prettyPrinted)
                        let finalRespData = try JSONDecoder().decode(RegisterStatusResponse.self, from: jsonData)
                        Success(finalRespData, serverData)
                    } else {
                        Failure(Constants.unableToProceed.localized())
                    }
                } catch let error {
                    print("ERROR WHEN DECODE: ", error)
                    Failure(Constants.unableToProceed.localized())
                }
            }, onFailure: { (error) in
                Failure(error)
            })
        }
        catch let error {
            print("ERROR WHEN ENCODE: ", error)
            Failure(Constants.unableToProceed.localized())
        }
    }
    
    // MARK: - Insert Worker
        func insertWorkerService(requestObj: InsertWorker,
                          Success:@escaping ((_ serverModalObj : ServerModel) ->Void),
                          Failure:@escaping ((_ serverError : String) -> Void))
        {
            print("++++++ SERVICE - STARTED ++++++")
            print("Request URL: \(Endpoint.insertWorker)")
            
            do {
                let reqEncodedData = try JSONEncoder().encode(requestObj)
                
                let dictInputs = prepareServiceInputs(httpMethod: .POST,
                                                      reqURL: Endpoint.insertWorker,
                                                      isReqOrRespHaveBase64: false)
                
                let serverCommObj = ServerCommunicationManager.serverCommManagerSharedInstance
                serverCommObj.handlePOSTWebService(reqSettings: dictInputs, postParamter: reqEncodedData, onSuccess: { (statusCode : Int, respData: Data) in
                    
                    do {
                        // Server Data
                        let serverData = try JSONDecoder().decode(ServerModel.self, from: respData)
                        if serverData.status != 200 {
                            let errorMsg = self.getErrorMessage(fromCode: serverData.code ?? "")
                            Failure(errorMsg != "" ? errorMsg : serverData.message ?? Constants.unableToProceed.localized())
                            return // important
                        }
                        Success(serverData)
                    } catch let error {
                        print("ERROR WHEN DECODE: ", error)
                        Failure(Constants.unableToProceed.localized())
                    }
                }, onFailure: { (error) in
                    Failure(error)
                })
            }
            catch let error {
                print("ERROR WHEN ENCODE: ", error)
                Failure(Constants.unableToProceed.localized())
            }
        }
    
    // MARK: - View & Update Profile
    func viewProfile(requestObj: ProfileRequest,
                      Success:@escaping ((_ userModelObj : ProfileResponse, _ serverModalObj : ServerModel) ->Void),
                      Failure:@escaping ((_ serverError : String) -> Void))
    {
        print("++++++ SERVICE - STARTED ++++++")
        print("Request URL: \(Endpoint.viewProfile)")
        
        do {
            let reqEncodedData = try JSONEncoder().encode(requestObj)
            
            let accessToken = AppData.accessToken
            let dictInputs = prepareServiceInputsWithCustomBasicAuth(httpMethod: .POST,
                                                                     reqURL: Endpoint.viewProfile,
                                                                     basicAuthPwd: accessToken,
                                                                     isReqOrRespHaveBase64: false)
            
            let serverCommObj = ServerCommunicationManager.serverCommManagerSharedInstance
            serverCommObj.handlePOSTWebService(reqSettings: dictInputs, postParamter: reqEncodedData, onSuccess: { (statusCode : Int, respData: Data) in
                
                do {
                    // Server Data
                    let serverData = try JSONDecoder().decode(ServerModel.self, from: respData)
                    if serverData.status != 200 {
                        let errorMsg = self.getErrorMessage(fromCode: serverData.code ?? "")
                        Failure(errorMsg != "" ? errorMsg : serverData.message ?? Constants.enconterServerError.localized())
                        return // important
                    }
                    
                    // Extract "response": {} from server response
                    let jsonValidate = self.extractResponseBody(respData: respData)
                    if jsonValidate.isSuccess {
                        let jsonData = try JSONSerialization.data(withJSONObject: jsonValidate.respBody, options: .prettyPrinted)
                        let finalRespData = try JSONDecoder().decode(ProfileResponse.self, from: jsonData)
                        Success(finalRespData, serverData)
                    } else {
                        Failure(Constants.unableToProceed.localized())
                    }
                } catch let error {
                    print("ERROR WHEN DECODE: ", error)
                    Failure(Constants.unableToProceed.localized())
                }
            }, onFailure: { (error) in
                Failure(error)
            })
        }
        catch let error {
            print("ERROR WHEN ENCODE: ", error)
            Failure(Constants.unableToProceed.localized())
        }
    }
    
    func updateProfileService(requestObj: UpdateProfileRequest,
                      Success:@escaping ((_ serverModalObj : ServerModel) ->Void),
                      Failure:@escaping ((_ serverError : String) -> Void))
    {
        print("++++++ SERVICE - STARTED ++++++")
        print("Request URL: \(Endpoint.updateProfile)")
        
        do {
            let reqEncodedData = try JSONEncoder().encode(requestObj)
            
            let accessToken = AppData.accessToken
            let dictInputs = prepareServiceInputsWithCustomBasicAuth(httpMethod: .POST,
                                                                     reqURL: Endpoint.updateProfile,
                                                                     basicAuthPwd: accessToken,
                                                                     isReqOrRespHaveBase64: false)
            
            let serverCommObj = ServerCommunicationManager.serverCommManagerSharedInstance
            serverCommObj.handlePOSTWebService(reqSettings: dictInputs, postParamter: reqEncodedData, onSuccess: { (statusCode : Int, respData: Data) in
                
                do {
                    // Server Data
                    let serverData = try JSONDecoder().decode(ServerModel.self, from: respData)
                    if serverData.status != 200 {
                        let errorMsg = self.getErrorMessage(fromCode: serverData.code ?? "")
                        Failure(errorMsg != "" ? errorMsg : serverData.message ?? Constants.enconterServerError.localized())
                        return // important
                    } else {
                        Success(serverData)
                    }
                } catch let error {
                    print("ERROR WHEN DECODE: ", error)
                    Failure(Constants.unableToProceed.localized())
                }
            }, onFailure: { (error) in
                Failure(error)
            })
        }
        catch let error {
            print("ERROR WHEN ENCODE: ", error)
            Failure(Constants.unableToProceed.localized())
        }
    }
    
    func receivedFood(requestObj: ReceivedFoodRequest,
                      Success:@escaping ((_ serverModalObj : ServerModel) ->Void),
                      Failure:@escaping ((_ serverError : String) -> Void))
    {
        print("++++++ SERVICE - STARTED ++++++")
        print("Request URL: \(Endpoint.receiveFood)")
        
        do {
            let reqEncodedData = try JSONEncoder().encode(requestObj)
            
            let accessToken = AppData.accessToken
            let dictInputs = prepareServiceInputsWithCustomBasicAuth(httpMethod: .POST,
                                                                     reqURL: Endpoint.receiveFood,
                                                                     basicAuthPwd: accessToken,
                                                                     isReqOrRespHaveBase64: false)
            
            let serverCommObj = ServerCommunicationManager.serverCommManagerSharedInstance
            serverCommObj.handlePOSTWebService(reqSettings: dictInputs, postParamter: reqEncodedData, onSuccess: { (statusCode : Int, respData: Data) in
                
                do {
                    // Server Data
                    let serverData = try JSONDecoder().decode(ServerModel.self, from: respData)
                    if serverData.status != 200 {
                        let errorMsg = self.getErrorMessage(fromCode: serverData.code ?? "")
                        Failure(errorMsg != "" ? errorMsg : serverData.message ?? Constants.enconterServerError.localized())
                        return // important
                    } else {
                        Success(serverData)
                    }
                } catch let error {
                    print("ERROR WHEN DECODE: ", error)
                    Failure(Constants.unableToProceed.localized())
                }
            }, onFailure: { (error) in
                Failure(error)
            })
        }
        catch let error {
            print("ERROR WHEN ENCODE: ", error)
            Failure(Constants.unableToProceed.localized())
        }
    }
    
    // MARK: - Static List
    // Services: State, City, Mukim
    func listOfStatesService(requestObj: StateRequest,
                      Success:@escaping ((_ userModelObj : [AllStates], _ serverModalObj : ServerModel) ->Void),
                      Failure:@escaping ((_ serverError : String) -> Void))
    {
        print("++++++ SERVICE - STARTED ++++++")
        print("Request URL: \(Endpoint.state)")
        
        do {
            let reqEncodedData = try JSONEncoder().encode(requestObj)
            
            let dictInputs = prepareServiceInputs(httpMethod: .POST,
                                                  reqURL: Endpoint.state,
                                                  isReqOrRespHaveBase64: false)
            
            let serverCommObj = ServerCommunicationManager.serverCommManagerSharedInstance
            serverCommObj.handlePOSTWebService(reqSettings: dictInputs, postParamter: reqEncodedData, onSuccess: { (statusCode : Int, respData: Data) in
                
                do {
                    // Server Data
                    let serverData = try JSONDecoder().decode(ServerModel.self, from: respData)
                    if serverData.status != 200 {
                        Failure(serverData.message ?? Constants.enconterServerError.localized())
                        return // important
                    }
                    
                    // Extract "response": {} from server response
                    let jsonValidate = self.extractResponseBody(respData: respData)
                    if jsonValidate.isSuccess {
                        let jsonData = try JSONSerialization.data(withJSONObject: jsonValidate.respBody, options: .prettyPrinted)
                        let finalRespData = try JSONDecoder().decode([AllStates].self, from: jsonData)
                        Success(finalRespData, serverData)
                    } else {
                        Failure(Constants.unableToProceed.localized())
                    }
                } catch let error {
                    print("ERROR WHEN DECODE: ", error)
                    Failure(Constants.unableToProceed.localized())
                }
            }, onFailure: { (error) in
                Failure(error)
            })
        }
        catch let error {
            print("ERROR WHEN ENCODE: ", error)
            Failure(Constants.unableToProceed.localized())
        }
    }
    
    func listOfDistrictService(requestObj: DistrictRequest,
                      Success:@escaping ((_ userModelObj : [AllDistricts], _ serverModalObj : ServerModel) ->Void),
                      Failure:@escaping ((_ serverError : String) -> Void))
    {
        print("++++++ SERVICE - STARTED ++++++")
        print("Request URL: \(Endpoint.district)")
        
        do {
            let reqEncodedData = try JSONEncoder().encode(requestObj)
            
            let dictInputs = prepareServiceInputs(httpMethod: .POST,
                                                  reqURL: Endpoint.district,
                                                  isReqOrRespHaveBase64: false)
            
            let serverCommObj = ServerCommunicationManager.serverCommManagerSharedInstance
            serverCommObj.handlePOSTWebService(reqSettings: dictInputs, postParamter: reqEncodedData, onSuccess: { (statusCode : Int, respData: Data) in
                
                do {
                    // Server Data
                    let serverData = try JSONDecoder().decode(ServerModel.self, from: respData)
                    if serverData.status != 200 {
                        Failure(serverData.message ?? Constants.enconterServerError.localized())
                        return // important
                    }
                    
                    // Extract "response": {} from server response
                    let jsonValidate = self.extractResponseBody(respData: respData)
                    if jsonValidate.isSuccess {
                        let jsonData = try JSONSerialization.data(withJSONObject: jsonValidate.respBody, options: .prettyPrinted)
                        let finalRespData = try JSONDecoder().decode([AllDistricts].self, from: jsonData)
                        Success(finalRespData, serverData)
                    } else {
                        Failure(Constants.unableToProceed.localized())
                    }
                } catch let error {
                    print("ERROR WHEN DECODE: ", error)
                    Failure(Constants.unableToProceed.localized())
                }
            }, onFailure: { (error) in
                Failure(error)
            })
        }
        catch let error {
            print("ERROR WHEN ENCODE: ", error)
            Failure(Constants.unableToProceed.localized())
        }
    }
    
    func listOfCityService(requestObj: CityRequest,
                      Success:@escaping ((_ userModelObj : [AllCities], _ serverModalObj : ServerModel) ->Void),
                      Failure:@escaping ((_ serverError : String) -> Void))
    {
        print("++++++ SERVICE - STARTED ++++++")
        print("Request URL: \(Endpoint.city)")
        
        do {
            let reqEncodedData = try JSONEncoder().encode(requestObj)
            
            let dictInputs = prepareServiceInputs(httpMethod: .POST,
                                                  reqURL: Endpoint.city,
                                                  isReqOrRespHaveBase64: false)
            
            let serverCommObj = ServerCommunicationManager.serverCommManagerSharedInstance
            serverCommObj.handlePOSTWebService(reqSettings: dictInputs, postParamter: reqEncodedData, onSuccess: { (statusCode : Int, respData: Data) in
                
                do {
                    // Server Data
                    let serverData = try JSONDecoder().decode(ServerModel.self, from: respData)
                    if serverData.status != 200 {
                        Failure(serverData.message ?? Constants.enconterServerError.localized())
                        return // important
                    }
                    
                    // Extract "response": {} from server response
                    let jsonValidate = self.extractResponseBody(respData: respData)
                    if jsonValidate.isSuccess {
                        let jsonData = try JSONSerialization.data(withJSONObject: jsonValidate.respBody, options: .prettyPrinted)
                        let finalRespData = try JSONDecoder().decode([AllCities].self, from: jsonData)
                        Success(finalRespData, serverData)
                    } else {
                        Failure(Constants.unableToProceed.localized())
                    }
                } catch let error {
                    print("ERROR WHEN DECODE: ", error)
                    Failure(Constants.unableToProceed.localized())
                }
            }, onFailure: { (error) in
                Failure(error)
            })
        }
        catch let error {
            print("ERROR WHEN ENCODE: ", error)
            Failure(Constants.unableToProceed.localized())
        }
    }
    
    func listOfMukimService(requestObj: MukimRequest,
                      Success:@escaping ((_ userModelObj : [AllMukim], _ serverModalObj : ServerModel) ->Void),
                      Failure:@escaping ((_ serverError : String) -> Void))
    {
        print("++++++ SERVICE - STARTED ++++++")
        print("Request URL: \(Endpoint.mukim)")
        
        do {
            let reqEncodedData = try JSONEncoder().encode(requestObj)
            
            let dictInputs = prepareServiceInputs(httpMethod: .POST,
                                                  reqURL: Endpoint.mukim,
                                                  isReqOrRespHaveBase64: false)
            
            let serverCommObj = ServerCommunicationManager.serverCommManagerSharedInstance
            serverCommObj.handlePOSTWebService(reqSettings: dictInputs, postParamter: reqEncodedData, onSuccess: { (statusCode : Int, respData: Data) in
                
                do {
                    // Server Data
                    let serverData = try JSONDecoder().decode(ServerModel.self, from: respData)
                    if serverData.status != 200 {
                        Failure(serverData.message ?? Constants.enconterServerError.localized())
                        return // important
                    }
                    
                    // Extract "response": {} from server response
                    let jsonValidate = self.extractResponseBody(respData: respData)
                    if jsonValidate.isSuccess {
                        let jsonData = try JSONSerialization.data(withJSONObject: jsonValidate.respBody, options: .prettyPrinted)
                        let finalRespData = try JSONDecoder().decode([AllMukim].self, from: jsonData)
                        Success(finalRespData, serverData)
                    } else {
                        Failure(Constants.unableToProceed.localized())
                    }
                } catch let error {
                    print("ERROR WHEN DECODE: ", error)
                    Failure(Constants.unableToProceed.localized())
                }
            }, onFailure: { (error) in
                Failure(error)
            })
        }
        catch let error {
            print("ERROR WHEN ENCODE: ", error)
            Failure(Constants.unableToProceed.localized())
        }
    }
    
    func listOfRacesService(Success:@escaping ((_ userModelObj : [AllRacesResponse], _ serverModalObj : ServerModel) ->Void),
                      Failure:@escaping ((_ serverError : String) -> Void))
    {
        print("++++++ SERVICE - STARTED ++++++")
        print("Request URL: \(Endpoint.races)")
        
        let dictInputs = prepareServiceInputs(httpMethod: .GET,
                                              reqURL: Endpoint.races,
                                              isReqOrRespHaveBase64: false)
        
        let serverCommObj = ServerCommunicationManager.serverCommManagerSharedInstance
        serverCommObj.handleGETWebService(reqSettings: dictInputs, onSuccess: { (statusCode : Int, respData: Data) in
            
            do {
                // Server Data
                let serverData = try JSONDecoder().decode(ServerModel.self, from: respData)
                if serverData.status != 200 {
                    Failure(serverData.message ?? Constants.enconterServerError.localized())
                    return // important
                }
                
                // Extract "response": {} from server response
                let jsonValidate = self.extractResponseBody(respData: respData)
                if jsonValidate.isSuccess {
                    let jsonData = try JSONSerialization.data(withJSONObject: jsonValidate.respBody, options: .prettyPrinted)
                    let finalRespData = try JSONDecoder().decode([AllRacesResponse].self, from: jsonData)
                    Success(finalRespData, serverData)
                } else {
                    Failure(Constants.unableToProceed.localized())
                }
            } catch let error {
                print("ERROR WHEN DECODE: ", error)
                Failure(Constants.unableToProceed.localized())
            }
        }, onFailure: { (error) in
            Failure(error)
        })
    }
    
    // MARK: - Notification
    func getNotifications(_ isNoAccessToken: Bool, requestObj: NotifyRequest,
                      Success:@escaping ((_ userModelObj : [Notify], _ serverModalObj : ServerModel) ->Void),
                      Failure:@escaping ((_ serverError : String) -> Void))
    {
        print("++++++ SERVICE - STARTED ++++++")
        print("Request URL: \(Endpoint.notifications)")
        
        do {
            let reqEncodedData = try JSONEncoder().encode(requestObj)
            
            let accessToken = AppData.accessToken
            var dictInputs: [WEB_SERVICE_SETTINGS: Any]!
            
            if isNoAccessToken {
                dictInputs = prepareServiceInputs(httpMethod: .POST,
                reqURL: Endpoint.notifications,
                isReqOrRespHaveBase64: false)
            } else {
                dictInputs = prepareServiceInputsWithCustomBasicAuth(httpMethod: .POST,
                reqURL: Endpoint.notifications,
                basicAuthPwd: accessToken,
                isReqOrRespHaveBase64: false)
            }
            
            let serverCommObj = ServerCommunicationManager.serverCommManagerSharedInstance
            serverCommObj.handlePOSTWebService(reqSettings: dictInputs, postParamter: reqEncodedData, onSuccess: { (statusCode : Int, respData: Data) in
                
                do {
                    // Server Data
                    let serverData = try JSONDecoder().decode(ServerModel.self, from: respData)
                    if serverData.status != 200 {
                        let errorMsg = self.getErrorMessage(fromCode: serverData.code ?? "")
                        Failure(errorMsg != "" ? errorMsg : serverData.message ?? Constants.enconterServerError.localized())
                        return // important
                    } else {
                        // Extract "response": {} from server response
                        let jsonValidate = self.extractResponseBody(respData: respData)
                        if jsonValidate.isSuccess {
                            let jsonData = try JSONSerialization.data(withJSONObject: jsonValidate.respBody, options: .prettyPrinted)
                            let finalRespData = try JSONDecoder().decode([Notify].self, from: jsonData)
                            Success(finalRespData, serverData)
                        } else {
                            Failure(Constants.unableToProceed.localized())
                        }
                    }
                } catch let error {
                    print("ERROR WHEN DECODE: ", error)
                    Failure(Constants.unableToProceed.localized())
                }
            }, onFailure: { (error) in
                Failure(error)
            })
        }
        catch let error {
            print("ERROR WHEN ENCODE: ", error)
            Failure(Constants.unableToProceed.localized())
        }
    }
    
    // MARK: - Admin
    func getEventLists(Success:@escaping ((_ userModelObj : [AdminEvent], _ serverModalObj : ServerModel) ->Void), Failure:@escaping ((_ serverError : String) -> Void))
    {
        print("++++++ SERVICE - STARTED ++++++")
        print("Request URL: \(Endpoint.listEvents)")
        
        let deviceInfo = Utilities.createDeviceInfo()
        
        do {
            let reqEncodedData = try JSONEncoder().encode(deviceInfo)
            
            let dictInputs = prepareServiceInputs(httpMethod: .POST,
                                                               reqURL: Endpoint.listEvents,
                                                               isReqOrRespHaveBase64: false)
            
            let serverCommObj = ServerCommunicationManager.serverCommManagerSharedInstance
            serverCommObj.handlePOSTWebService(reqSettings: dictInputs, postParamter: reqEncodedData, onSuccess: { (statusCode : Int, respData: Data) in
                
                do {
                    // Server Data
                    let serverData = try JSONDecoder().decode(ServerModel.self, from: respData)
                    if serverData.status != 200 {
                        let errorMsg = self.getErrorMessage(fromCode: serverData.code ?? "")
                        Failure(errorMsg != "" ? errorMsg : serverData.message ?? Constants.enconterServerError.localized())
                        return // important
                    } else {
                        // Extract "response": {} from server response
                        let jsonValidate = self.extractResponseBody(respData: respData)
                        if jsonValidate.isSuccess {
                            let jsonData = try JSONSerialization.data(withJSONObject: jsonValidate.respBody, options: .prettyPrinted)
                            let finalRespData = try JSONDecoder().decode([AdminEvent].self, from: jsonData)
                            Success(finalRespData, serverData)
                        } else {
                            Failure(Constants.unableToProceed.localized())
                        }
                    }
                } catch let error {
                    print("ERROR WHEN DECODE: ", error)
                    Failure(Constants.unableToProceed.localized())
                }
            }, onFailure: { (error) in
                Failure(error)
            })
        }
        catch let error {
            print("ERROR WHEN ENCODE: ", error)
            Failure(Constants.unableToProceed.localized())
        }
    }
}
