//
//  NotificationTVC.swift
//  PagohCare
//
//  Created by Cookie on 4/27/20.
//  Copyright © 2020 Bestinet. All rights reserved.
//

import UIKit
import Localize_Swift

class NotificationTVC: UITableViewController, Alert {

    var isNoAccessToken: Bool = false
    private var notifications: [Notify] = [Notify]()
        
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialSetup()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.fetchNotifications()
    }
    
    private func initialSetup() {
        
        title = Constants.message.localized()
        
        tableView.register(UINib(nibName: "BasicNotificationCell", bundle: nil), forCellReuseIdentifier: "basicNotificationCell")
        tableView.rowHeight = UITableView.automaticDimension
        tableView.estimatedRowHeight = 100.0
        tableView.separatorStyle = .none
        
//        self.addData()
    }
    
    private func addData() {
        notifications.append(Notify(title: "Bantuan Makanan Fasa 1", date: "Ahad, 26 Apr 2020, 8:36 pagi", content: "Agihan bantuan makanan akan dijalankan seperti berikut:" +
        "\n\nHari-Khamis 30/4/2020\nMasa-10:00pagi\nTempat-Dewan Serbaguna Kg. " +
        "Rahmat"))
        notifications.append(Notify(title: "Bantuan Makanan PagohCare", date: "Isnin, 27 Apr 2020, 10:00 pagi", content: "Permohanan anda telah diluluskan.\nAnda akan dimaklumkan tarikh dan masa" +
        " untuk mengambil makanan tajaan anda.\n\nTerima Kasih"))
        notifications.append(Notify(title: "Bantuan Makanan Fasa 1", date: "Selasa, 28 Apr 2020, 9:15 pagi", content: "Sila harap maklum bahawa program Bantuan Makanan PagohCare seperti " +
        "dibawah telah di tunda ke tarikh yang akan di beritahu kelak."))
    }
}

// MARK: - Table view data source
extension NotificationTVC {
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if notifications.count == 0 {
            let emptyLabel = UILabel(frame: CGRect(x: 0, y: 0, width: self.view.bounds.size.width, height: self.view.bounds.size.height))
            emptyLabel.text = Constants.noMessages.localized()
            emptyLabel.textAlignment = NSTextAlignment.center
            self.tableView.backgroundView = emptyLabel
            self.tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
            return 0
        }
        
        self.tableView.backgroundView = nil
        return notifications.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: "basicNotificationCell", for: indexPath) as? BasicNotificationCell
            else {
                return UITableViewCell()
        }
        
        cell.selectionStyle = .none
        cell.updateUI(notifications[indexPath.row])
        
//        if Localize.currentLanguage() == "en" {
//            cell.titleLabel.text = "PagohCare Food Assistance"
//            cell.contentLabel.text = "Your request has been approved.\nYou will be notified of the dates and times to receive your sponsored meals.\nThank you."
//
//        } else {
//            cell.titleLabel.text = "Bantuan Makanan PagohCare"
//            cell.contentLabel.text = "Permohanan anda telah diluluskan.\nAnda akan dimaklumkan tarikh dan masa untuk mengambil makanan tajaan anda.\nTerima Kasih"
//        }
//        cell.dateLabel.text = "Tue, Apr 28 2020. 09:30"
        
        return cell
    }
}

//MARK: - Http Request
extension NotificationTVC {
    private func fetchNotifications() {
        Spinner.shared.show(onView: self.view, text: Constants.loading.localized(), blurEffect: .dark, frame: CGRect(x: 0, y: 0, width: 96, height: 96))
        
        let notiRequest = NotifyRequest(notifyTo: AppData.contactNo, deviceInfo: Utilities.createDeviceInfo())
        let service = Services.shared
        service.getNotifications(self.isNoAccessToken, requestObj: notiRequest, Success: { (notifies, serverObjc) in
            DispatchQueue.main.async {
                self.notifications = notifies
                self.tableView.reloadData()
            }
        }) { (error) in
            DispatchQueue.main.async {
                if error == Constants.userAlreadyLoginRelogin.localized() {
                    self.popBasicAlert(withTitle: nil, message: error) {
                        AppData.resetDefaults()
                        self.view.window?.rootViewController?.dismiss(animated: true, completion: nil)
                    }
                } else {
                    print(error)
                    self.notifications.removeAll()
                    self.tableView.reloadData()
                    //self.popBasicAlert(withTitle: nil, message: error)
                }                
            }
        }
    }
}
