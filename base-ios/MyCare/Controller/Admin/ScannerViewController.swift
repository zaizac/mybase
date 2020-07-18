//
//  ScannerViewController.swift
//  PagohCare
//
//  Created by Cookie on 5/7/20.
//  Copyright © 2020 Bestinet. All rights reserved.
//

import AVFoundation
import UIKit

class ScannerViewController: UIViewController, AVCaptureMetadataOutputObjectsDelegate {
    
    var captureSession: AVCaptureSession!
    var previewLayer: AVCaptureVideoPreviewLayer!
    private var isScanned: Bool = false

    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialSetup()
    }
    
    private func initialSetup() {
        
        view.backgroundColor = UIColor.black
        
        captureSession = AVCaptureSession()

        guard let videoCaptureDevice = AVCaptureDevice.default(for: .video) else { return }
        let videoInput: AVCaptureDeviceInput

        do {
            videoInput = try AVCaptureDeviceInput(device: videoCaptureDevice)
        } catch {
            return
        }

        if (captureSession.canAddInput(videoInput)) {
            captureSession.addInput(videoInput)
        } else {
            failed()
            return
        }

        let metadataOutput = AVCaptureMetadataOutput()
        
        if (captureSession.canAddOutput(metadataOutput)) {
            captureSession.addOutput(metadataOutput)

            metadataOutput.setMetadataObjectsDelegate(self, queue: DispatchQueue.main)
            metadataOutput.metadataObjectTypes = [.qr]
        } else {
            failed()
            return
        }

        previewLayer = AVCaptureVideoPreviewLayer(session: captureSession)
        previewLayer.frame = view.layer.bounds
        previewLayer.videoGravity = .resizeAspectFill
        view.layer.addSublayer(previewLayer)

        self.addScanButton()
        
        captureSession.startRunning()
    }

    private func addScanButton() {
        
        let qrFrameIV = UIImageView()
        qrFrameIV.translatesAutoresizingMaskIntoConstraints = false
        qrFrameIV.contentMode = .scaleAspectFill
        qrFrameIV.image = UIImage(named: "qrScannerFrame")
        
        let scanBtn = CorneredButton()
        scanBtn.setImage(UIImage(named: "redCircledClose"), for: .normal)
        scanBtn.addTarget(self, action: #selector(backHandler), for: .touchUpInside)
        
        view.addSubview(qrFrameIV)
        view.bringSubviewToFront(qrFrameIV)
        view.addSubview(scanBtn)
        view.bringSubviewToFront(scanBtn)
        
        qrFrameIV.topAnchor.constraint(equalTo: view.topAnchor).isActive = true
        qrFrameIV.bottomAnchor.constraint(equalTo: view.bottomAnchor).isActive = true
        qrFrameIV.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
        qrFrameIV.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
        
        let padding: CGFloat = 16
        if #available(iOS 11, *) {
          let guide = view.safeAreaLayoutGuide
            scanBtn.topAnchor.constraint(equalTo: guide.topAnchor, constant: padding).isActive = true
            scanBtn.trailingAnchor.constraint(equalTo: guide.trailingAnchor, constant: -padding).isActive = true
        } else {
            scanBtn.topAnchor.constraint(equalTo: topLayoutGuide.topAnchor, constant: padding).isActive = true
            scanBtn.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -padding).isActive = true
        }
        
        scanBtn.heightAnchor.constraint(equalToConstant: 28.0).isActive = true
        scanBtn.widthAnchor.constraint(equalToConstant: 28.0).isActive = true
    }
    
    @objc
    private func backHandler() {
        self.dismiss(animated: true, completion: nil)
    }
    
    func failed() {
        let ac = UIAlertController(title: "Scanning not supported", message: "Your device does not support scanning a code from an item. Please use a device with a camera.", preferredStyle: .alert)
        ac.addAction(UIAlertAction(title: "OK", style: .default))
        present(ac, animated: true)
        captureSession = nil
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)

        self.isScanned = false
        if (captureSession?.isRunning == false) {
            captureSession.startRunning()
        }
    }

    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)

        if (captureSession?.isRunning == true) {
            captureSession.stopRunning()
        }
    }

    func metadataOutput(_ output: AVCaptureMetadataOutput, didOutput metadataObjects: [AVMetadataObject], from connection: AVCaptureConnection) {
        
        if !self.isScanned {
            self.isScanned = true
            if let metadataObject = metadataObjects.first {
                DispatchQueue.main.asyncAfter(deadline: .now() + 1, execute: {
                    self.captureSession.stopRunning()
                    guard let readableObject = metadataObject as? AVMetadataMachineReadableCodeObject else { return }
                    guard let stringValue = readableObject.stringValue else { return }
                    AudioServicesPlaySystemSound(SystemSoundID(kSystemSoundID_Vibrate))
                    self.found(code: stringValue)
                })
            }
        }
    }

    func found(code: String) {
        print(code)
        guard let vc = UIStoryboard(name: "Admin", bundle: nil).instantiateViewController(withIdentifier: String(describing: ResidentDetailsViewController.self)) as? ResidentDetailsViewController else {
            return
        }
        
        vc.modalPresentationStyle = .fullScreen
        vc.fwProfId = code
        
        self.present(vc, animated: true, completion: nil)
    }

    override var prefersStatusBarHidden: Bool {
        return true
    }

    override var supportedInterfaceOrientations: UIInterfaceOrientationMask {
        return .portrait
    }
}
