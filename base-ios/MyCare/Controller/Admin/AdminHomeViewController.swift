//
//  AdminHomeViewController.swift
//  PagohCare
//
//  Created by Cookie on 5/7/20.
//  Copyright © 2020 Bestinet. All rights reserved.
//

import UIKit

class AdminHomeViewController: UIViewController, Alert {
    
    @IBOutlet private weak var tableViewHolder: UIView!
    @IBOutlet private weak var tableView: UITableView!
    @IBOutlet private weak var buttonsHolderView: CorneredShadowedView!
    
    @IBOutlet private weak var nameLbl: UILabel!
    @IBOutlet private weak var adminTitleLbl: UILabel!
    @IBOutlet private weak var welcomeLbl: UILabel!
    @IBOutlet private weak var descLabel: UILabel!
    
    private var eventLists: [AdminEvent] = []
    private var selectEventId: Int = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialSetup()
        self.tableViewSetup()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.setText()
        self.fetchEventLists()
    }
    
    private func initialSetup() {
        self.tableViewHolder.backgroundColor = .clear
        self.tableViewHolder.addShadow(withOpacity: 0.3, shadowRadius: 3)
        self.buttonsHolderView.backgroundColor = .clear
        
        self.nameLbl.textColor = Colors.blueBlack
        self.adminTitleLbl.textColor = Colors.blueBlack
        self.welcomeLbl.textColor = Colors.blueBlack
        self.descLabel.textColor = Colors.blueBlack
    }
    
    private func tableViewSetup() {
        tableView.delegate = self
        tableView.dataSource = self
        tableView.separatorStyle = .none
        
        tableView.addCornerRadius(5)
        tableView.backgroundColor = .white
        
        tableView.register(UINib(nibName: "AdminEventCell", bundle: nil), forCellReuseIdentifier: "adminEventCell")
    }
    
    private func setText() {
        self.welcomeLbl.text = Constants.welcome.localized()
        self.descLabel.text = Constants.pleaseSelectTodayEvent.localized()
        self.nameLbl.text = AppData.fullname
        self.adminTitleLbl.text = "\(AppData.adminRoleGroup)"
    }
    
    //MARK: - Action and Handlers
    @IBAction
    private func buttonAction(_ sender: UIButton) {
        switch sender.tag {
        case 1:
            print("SCANNED!")
            
            guard self.selectEventId != 0 else {
                self.popBasicAlert(withTitle: nil, message: Constants.pleaseSelectTodayEvent.localized())
                return
            }
            
            let scanVC = ScannerViewController()
            scanVC.modalPresentationStyle = .fullScreen
            self.present(scanVC, animated: true, completion: nil)
        case 2:
            self.confirmationAlert(withTitle: Constants.logout.localized(), message: Constants.sureWant2Logout.localized()) {
                self.logout()
            }
        default:
            print("NOTHINGNESS")
        }
    }
}

extension AdminHomeViewController: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.eventLists.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: "adminEventCell", for: indexPath) as? AdminEventCell else {
            return UITableViewCell()
        }
        
        cell.selectionStyle = .none
        cell.updateUI(event: self.eventLists[indexPath.row])
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let headerView = UIView.init(frame: CGRect.init(x: 0, y: 0, width: tableView.frame.width, height: 50))
        headerView.backgroundColor = Colors.blueBlack
        
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.text = Constants.todayEvent.localized()
        label.font = UIFont.systemFont(ofSize: 17.0, weight: .bold)
        label.textColor = .white

        headerView.addSubview(label)
        
        label.centerYAnchor.constraint(equalTo: headerView.centerYAnchor).isActive = true
        label.leadingAnchor.constraint(equalTo: headerView.leadingAnchor, constant: 10).isActive = true
        label.trailingAnchor.constraint(equalTo: headerView.trailingAnchor, constant: -10).isActive = true
        
        return headerView
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 100.0
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 50
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if let _ = tableView.cellForRow(at: indexPath) as? AdminEventCell {
            self.selectEventId = self.eventLists[indexPath.row].id
            AppData.deliveryEventId = "\(self.selectEventId)"
            AppData.eventName = self.eventLists[indexPath.row].name
            AppData.eventDate = "\(self.eventLists[indexPath.row].date). \(self.eventLists[indexPath.row].time)"
        }
    }
}

//MARK: - Http Requests
extension AdminHomeViewController {
    private func fetchEventLists() {
        let service = Services.shared
        service.getEventLists(Success: { (events, serverObj) in
            DispatchQueue.main.async {
                self.eventLists = events.sorted(by: { $0.time < $1.time })
                self.tableView.reloadData()
            }
        }) { (error) in
            DispatchQueue.main.async {
                self.popBasicAlert(withTitle: nil, message: Utilities.isItCommonErrorMessage(errMsg: error) ? error : Constants.failed.localized())
            }
        }
    }
    
    private func logout() {
        Spinner.shared.show(onView: self.view, text: Constants.loading.localized().uppercased())

        let logoutReq = LogoutRequest(deviceInfo: Utilities.createDeviceInfo())
        let service = Services.shared
        service.logoutService(reqModelObj: logoutReq, Success: { [weak self] (serverObj) in
            DispatchQueue.main.async {
                print("SUCCESS LOGOUT", serverObj.status!)
                AppData.resetDefaults()
                self?.view.window?.rootViewController?.dismiss(animated: true, completion: nil)
            }
        }) { (error) in
            DispatchQueue.main.async {
                // Even error, allow user to logout
                AppData.resetDefaults()
                self.view.window?.rootViewController?.dismiss(animated: true, completion: nil)
            }
        }
    }
}
