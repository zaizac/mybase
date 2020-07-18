//
//  BSTCustomCrypto.swift
//  URP
//
//  Created by Sadham Bestinet on 04/03/2020.
//  Copyright © 2020 Bestinet. All rights reserved.
//

import Foundation
import CryptoSwift
import CryptoKit
import CommonCrypto

let SALT_KEY = "mw62SJ96!^54GKW)=@*HtQbK"

class BSTCustomCrypto: NSObject {
    let SHIFT = [24, 16, 8, 0]
    let EXTRA = [-2147483648, 8388608, 32768, 128]
    let B64     = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
    
    private var finalized   = false
    private var hashed      = false
    
    private var start = 0
    private var block = 0
    private var bytes = 0
    private var lastByteIndex = 0 //: Any?
    private var hBytes = 0
    
    private var blocks = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    private var  h0 = 0x67452301;
    private var  h1 = 0xefcdab89;
    private var  h2 = 0x98badcfe;
    private var  h3 = 0x10325476;
    private var  h4 = 0xc3d2e1f0;
    
    // MARK: -
    func finalizer() {
        if finalized {
            print("finali//zer return")
            return
        }
        
        print("finalizer()")
        
        finalized = true
        var tempBlocks = blocks
        let i = lastByteIndex
        
        tempBlocks[16] = block
        tempBlocks[i >> 2] |= EXTRA[i & 3]
        block = tempBlocks[16]
        if (i >= 56) {
            if (!hashed) {
                hash()
            }
            tempBlocks[0] = block
            tempBlocks[16]  = tempBlocks[1]
            tempBlocks[1] = tempBlocks[2]
            tempBlocks[2] = tempBlocks[3]
            tempBlocks[3] = tempBlocks[4]
            tempBlocks[4] = tempBlocks[5]
            tempBlocks[5] = tempBlocks[6]
            tempBlocks[6] = tempBlocks[7]
            tempBlocks[7] = tempBlocks[8]
            tempBlocks[8] = tempBlocks[9]
            tempBlocks[9] = tempBlocks[10]
            tempBlocks[10] = tempBlocks[11]
            tempBlocks[11] = tempBlocks[12]
            tempBlocks[12] = tempBlocks[13]
            tempBlocks[13] = tempBlocks[14]
            tempBlocks[14] = tempBlocks[15]
            tempBlocks[15] = 0
        }
        
        tempBlocks[14]  = (hBytes << 3) | (bytes >> 29)
        tempBlocks[15] = bytes << 3
        hash()
    }
    
    func hash() {
        print("hash()")
        
        var a = h0, b = h1, c = h2, d = h3, e = h4
        var f = 0, j = 0, t = 0
        var tempBlocks = blocks;
        print("super.blocks.count: ", blocks.count)
        print("super.blocks: ", blocks)
        
        // Doubt
        j = 17
        var last = 80
        var interval = 1
        let sequence1 = stride(from: j, to: last, by: interval)
        for element in sequence1 {
            print("for loop 1-element: ", element)
            t = tempBlocks[element - 3] ^ tempBlocks[element - 8] ^ tempBlocks[element - 14] ^ tempBlocks[element - 16]
            print("t: \(t)")
            //tempBlocks[element] = (t << 1) | (t >> 31)
            let result = (t << 1) | (t >> 31)
            if tempBlocks.count <= element {
                tempBlocks.append(result)
            } else {
                tempBlocks.insert(result, at: element)
            }
        }
        
        // Doubt
        j = 0
        last = 20
        interval = 5
        let sequence2 = stride(from: j, to: last, by: interval)
        for element in sequence2 {
            print("for loop 2")
            f = (b & c) | (~b & d);
            t = (a << 5) | (a >> 27);
            e = (t + f + e + 1518500249 + tempBlocks[element]) << 0;
            b = (b << 30) | (b >> 2);
            
            f = (a & b) | (~a & c);
            t = (e << 5) | (e >> 27);
            d = (t + f + d + 1518500249 + tempBlocks[element + 1]) << 0;
            a = (a << 30) | (a >> 2);
            
            f = (e & a) | (~e & b);
            t = (d << 5) | (d >> 27);
            c = (t + f + c + 1518500249 + tempBlocks[element + 2]) << 0;
            e = (e << 30) | (e >> 2);
            
            f = (d & e) | (~d & a);
            t = (c << 5) | (c >> 27);
            b = (t + f + b + 1518500249 + tempBlocks[element + 3]) << 0;
            d = (d << 30) | (d >> 2);
            
            f = (c & d) | (~c & e);
            t = (b << 5) | (b >> 27);
            a = (t + f + a + 1518500249 + tempBlocks[element + 4]) << 0;
            c = (c << 30) | (c >> 2);
        }
        
        last = 40
        interval = 5
        var sequence3 = stride(from: j, to: last, by: interval)
        for element in sequence3 {
            print("for loop 3")
            f = b ^ c ^ d;
            t = (a << 5) | (a >> 27);
            e = (t + f + e + 1859775393 + tempBlocks[element]) << 0;
            b = (b << 30) | (b >> 2);
            
            f = a ^ b ^ c;
            t = (e << 5) | (e >> 27);
            d = (t + f + d + 1859775393 + tempBlocks[element + 1]) << 0;
            a = (a << 30) | (a >> 2);
            
            f = e ^ a ^ b;
            t = (d << 5) | (d >> 27);
            c = (t + f + c + 1859775393 + tempBlocks[element + 2]) << 0;
            e = (e << 30) | (e >> 2);
            
            f = d ^ e ^ a;
            t = (c << 5) | (c >> 27);
            b = (t + f + b + 1859775393 + tempBlocks[element + 3]) << 0;
            d = (d << 30) | (d >> 2);
            
            f = c ^ d ^ e;
            t = (b << 5) | (b >> 27);
            a = (t + f + a + 1859775393 + tempBlocks[element + 4]) << 0;
            c = (c << 30) | (c >> 2);
        }
        
        last = 60
        interval = 5
        sequence3 = stride(from: j, to: last, by: interval)
        for element in sequence3 {
            print("for loop 4")
            f = (b & c) | (b & d) | (c & d);
            t = (a << 5) | (a >> 27);
            e = (t + f + e - 1894007588 + tempBlocks[element]) << 0;
            b = (b << 30) | (b >> 2);
            
            f = (a & b) | (a & c) | (b & c);
            t = (e << 5) | (e >> 27);
            d = (t + f + d - 1894007588 + tempBlocks[element + 1]) << 0;
            a = (a << 30) | (a >> 2);
            
            f = (e & a) | (e & b) | (a & b);
            t = (d << 5) | (d >> 27);
            c = (t + f + c - 1894007588 + tempBlocks[element + 2]) << 0;
            e = (e << 30) | (e >> 2);
            
            f = (d & e) | (d & a) | (e & a);
            t = (c << 5) | (c >> 27);
            b = (t + f + b - 1894007588 + tempBlocks[element + 3]) << 0;
            d = (d << 30) | (d >> 2);
            
            f = (c & d) | (c & e) | (d & e);
            t = (b << 5) | (b >> 27);
            a = (t + f + a - 1894007588 + tempBlocks[element + 4]) << 0;
            c = (c << 30) | (c >> 2);
        }
        
        last = 80
        interval = 5
        sequence3 = stride(from: j, to: last, by: interval)
        for element in sequence3 {
            print("for loop 5")
            f = b ^ c ^ d;
            t = (a << 5) | (a >> 27);
            e = (t + f + e - 899497514 + tempBlocks[element]) << 0;
            b = (b << 30) | (b >> 2);
            
            f = a ^ b ^ c;
            t = (e << 5) | (e >> 27);
            d = (t + f + d - 899497514 + tempBlocks[element + 1]) << 0;
            a = (a << 30) | (a >> 2);
            
            f = e ^ a ^ b;
            t = (d << 5) | (d >> 27);
            c = (t + f + c - 899497514 + tempBlocks[element + 2]) << 0;
            e = (e << 30) | (e >> 2);
            
            f = d ^ e ^ a;
            t = (c << 5) | (c >> 27);
            b = (t + f + b - 899497514 + tempBlocks[element + 3]) << 0;
            d = (d << 30) | (d >> 2);
            
            f = c ^ d ^ e;
            t = (b << 5) | (b >> 27);
            a = (t + f + a - 899497514 + tempBlocks[element + 4]) << 0;
            c = (c << 30) | (c >> 2);
        }
        
        h0 = (h0 + a) << 0;
        h1 = (h1 + b) << 0;
        h2 = (h2 + c) << 0;
        h3 = (h3 + d) << 0;
        h4 = (h4 + e) << 0;
    }
    
    func update(message: String) {
        print("UPDATE()")
        if finalized { return }
        
        var code = 0
        var index = 0
        var i = 0
        var tempBlocks = blocks
        
        var length = 0
        if message.count > 0 {
            length = message.count
        }
        
        while (index < length) {
            print("update - while loop")
            if hashed {
                hashed = false
                tempBlocks[0]   = block
                tempBlocks[16]  = tempBlocks[1]
                tempBlocks[1] = tempBlocks[2]
                tempBlocks[2] = tempBlocks[3]
                tempBlocks[3] = tempBlocks[4]
                tempBlocks[4] = tempBlocks[5]
                tempBlocks[5] = tempBlocks[6]
                tempBlocks[6] = tempBlocks[7]
                tempBlocks[7] = tempBlocks[8]
                tempBlocks[8] = tempBlocks[9]
                tempBlocks[9] = tempBlocks[10]
                tempBlocks[10] = tempBlocks[11]
                tempBlocks[11] = tempBlocks[12]
                tempBlocks[12] = tempBlocks[13]
                tempBlocks[13] = tempBlocks[14]
                tempBlocks[14] = tempBlocks[15]
                tempBlocks[15] = 0
            }
            
            print("TEMP BLOCKS: ", tempBlocks)
            
            let last = 64
            let interval = 1
            let sequence1 = stride(from: start, to: last, by: interval)
            for element in sequence1 {
                // doubt
                if index >= length {
                    i = element
                    print("for loop break")
                    break
                }
                
                print("for loop not break: index: \(index) length: \(length)")
                
                let charCode : Character = Array(message)[index]
                code = Int(charCode.unicodeScalarCodePoint())
                if (code < 0x80) {
                    let z = i + 1
                    blocks[i >> 2] |= code << SHIFT[z & 3];
                    i = z + 1
                } else if (code < 0x800) {
                    let z = i + 1
                    blocks[i >> 2] |= (0xc0 | (code >> 6)) << SHIFT[z & 3];
                    blocks[i >> 2] |= (0x80 | (code & 0x3f)) << SHIFT[z & 3];
                    i = z + 1
                } else if (code < 0xd800 || code >= 0xe000) {
                    let z = i + 1
                    blocks[i >> 2] |= (0xe0 | (code >> 12)) << SHIFT[z & 3];
                    blocks[i >> 2] |= (0x80 | ((code >> 6) & 0x3f)) << SHIFT[z & 3];
                    blocks[i >> 2] |= (0x80 | (code & 0x3f)) << SHIFT[i & 3];
                    i = z + 1
                } else {
                    let z = i + 1
                    let newIndex = index + 1
                    let char : Character = Array(message)[newIndex]
                    let charCode2 = Int(char.unicodeScalarCodePoint())
                    code = 0x10000 + (((code & 0x3ff) << 10) | (charCode2 & 0x3ff));
                    blocks[i >> 2] |= (0xf0 | (code >> 18)) << SHIFT[z & 3];
                    blocks[i >> 2] |= (0x80 | ((code >> 12) & 0x3f)) << SHIFT[z & 3];
                    blocks[i >> 2] |= (0x80 | ((code >> 6) & 0x3f)) << SHIFT[z & 3];
                    blocks[i >> 2] |= (0x80 | (code & 0x3f)) << SHIFT[z & 3];
                    i = z + 1
                }
                
                index = index + 1
            }
            
            print("After for loop break")
            
            lastByteIndex = i
            bytes += i - start;
            if (i >= 64) {
              block = tempBlocks[16];
              start = i - 64;
              hash()
              hashed = true;
            } else {
              start = i;
            }
        }
        
        print("while loop exit")
        
        if (bytes > 4294967295) {
          hBytes += (bytes / 4294967296) << 0;
          bytes = bytes % 4294967296;
        }
        
        print("update() returned")
        return
    }
    
    func digest() -> ContiguousArray<UInt8> {
        print("digest")
        finalizer()
        
        var buffer : ContiguousArray<UInt8> = []
        buffer.append(UInt8(h0))
        buffer.append(UInt8(h1))
        buffer.append(UInt8(h2))
        buffer.append(UInt8(h3))
        buffer.append(UInt8(h4))
        return buffer
    }
    
    func encode(input: [UInt8]) -> String {
        print("encode")
        var output = "";
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4 : UInt8
        let i = 0;
        
        while (i < input.count) {
            print("while loop")
            let z = i+1
            chr1 = input[z];
            chr2 = input[z];
            chr3 = input[z];
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            
            let flt1 = Float(chr2)
            let flt2 = Float(chr3)
            if (flt1.isNaN) {
                enc3 = 64
                enc4 = 64
            } else if (flt2.isNaN) {
                enc4 = 64
            }
            
            let strReady = String(Array(B64)[Int(enc1)]) + String(Array(B64)[Int(enc2)]) + String(Array(B64)[Int(enc3)]) + String(Array(B64)[Int(enc4)])
            output = output + strReady
        }
        return output
    }
    
    // MARK: -
    func hashWithSalt(password: String, salt: String) -> String {
        print("HASH WITH SALT")
        
        // Append password and salt
        let strUserInfoToHash = password + salt
        
        // Convert salt to bytes array
        let saltBytes   = salt.utf8
        let saltBuffer  = [UInt8](saltBytes)

        // Convert Password to bytes array
        let pwdBytes = strUserInfoToHash.utf8
        let pwdBuffer = [UInt8](pwdBytes)
        
        // Create Digest SHA1 for Password(bytes array)
        let digestSHA1 = Digest.sha1(pwdBuffer)
        
        // Create final Hash value
        var c : [UInt8] = []
        c.append(contentsOf: digestSHA1)
        c.append(contentsOf: saltBuffer)
        return "{SSHA}" + c.toBase64()!
    }
}

// MARK: - Extensions
extension String {
    subscript(i: Int) -> String {
        return String(self[index(startIndex, offsetBy: i)])
    }
}

extension Character
{
    func unicodeScalarCodePoint() -> UInt32
    {
        let characterString = String(self)
        let scalars = characterString.unicodeScalars

        return scalars[scalars.startIndex].value
    }
}


