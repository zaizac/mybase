import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { FileConfig } from 'projects/ui-master/src/lib/component/file/model/file.config';
import { ModalService } from 'projects/ui-master/src/public-api';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss']
})
export class FileUploadComponent implements OnInit {
  defaultImg = '../assets/default/no_image.jpg';

  supportDocList: any = {
    jobOrderId: 30,
    jobOrderNo: 'QUOTA_01/JO05',
    quotaRefNo: 'QUOTA_01',
    gender: 'M',
    sectorCode: 'BI',
    subSectorCode: 'TEW',
    jobCode: 'BB',
    additionalRmk: null,
    empToRaRmk: null,
    raDecisionRmk: null,
    embassyRmk: null,
    intrRltRmk: null,
    siteRltRmk: null,
    pymtId: null,
    pymtRmk: null,
    skillCode: null,
    expcArrivDt: 1604428200000,
    expcArrivFromDt: null,
    expcArrivToDt: null,
    wrkrCnt: 1,
    statusCd: 'EBAPR',
    statusDesc: null,
    raId: 1,
    empId: 1,
    cmpnyAddressId: null,
    empContractId: 37,
    statusInd: null,
    remarks: null,
    docRefNo: '2019DOC2207QUAPPR',
    createId: 'SYSTEM',
    createDt: 1567052412000,
    updateId: 'SYSTEM',
    updateDt: 1563861823000,
    empContractDtl: null,
    quotaDtl: null,
    jobOrderRcTyp: null,
    sector: null,
    refSkill: null,
    refStatus: null,
    refSubSector: null,
    jobOrderRcTyps: null,
    beWorkSiteAddressDtos: null,
    workSiteAddrDto: null,
    supportDocs: null,
    empCmpnyProfile: null,
    refJobCatg: null,
    totalWrkrAccepted: null,
    totalWrkrAssigned: null,
    trxnDocuments: null,
    fileUploadAddCntl: null,
    fileUploadProdct: null,
    fileUploadWrkrSite: null,
    fileUploadAcmd: null,
    appDate: null,
    weightFrom: null,
    weightTo: null,
    ageFrom: null,
    ageTo: null,
    heightFrom: null,
    heightTo: null,
    raProfile: null,
    interviewResFileUpldLst: null,
    refBenifits: null,
    empOtherBenifits: null,
    selectedRefBenitList: null,
    selectedOtherBenitList: null,
    permsInd: 'Approve',
    joTermsAndConList: null,
    fileUploadEmp: null,
    fileUploadDmnd: null,
    fileUploadPoa: null,
    fileUploadUdtl: null,
    fileUploadAttest: null,
    refDocPrdtLst: [
      {
        docId: 6,
        dmBucket: 'urp',
        docDescEn: 'PRODUCT CATALOGUE 1',
        docDescMy: '',
        compulsary: true,
        dimensionCompulsary: false,
        maxHeight: 500,
        maxWidth: 500,
        minHeight: 0,
        minWidth: 0,
        maxDocCnt: 5,
        size: 1000000,
        sortOrder: 0,
        title: 'PRODUCT CATALOGUE 1',
        trxnNo: 'PRDT',
        type: 'image/gif, image/jpeg, image/png, application/pdf',
        trxnDocuments: {
          docRefNo: '2019DOC2207QUAPPR',
          docId: 6,
          docMgtId: '5d351941409386269117d7fa',
          docContentType: 'application/pdf',
          quotaRefNo: 'QUOTA_01/JO05',
          size: 0
        }
      },
      {
        docId: 7,
        docDescEn: 'PRODUCT CATALOGUE 2',
        docDescMy: '',
        compulsary: true,
        dimensionCompulsary: false,
        maxHeight: 500,
        maxWidth: 500,
        minHeight: 0,
        minWidth: 0,
        size: 1000000,
        sortOrder: 0,
        title: 'PRODUCT CATALOGUE 2',
        trxnNo: 'PRDT',
        type: 'image/gif, image/jpeg, image/png, application/pdf',
        trxnDocuments: {
          docRefNo: '2019DOC2207QUAPPR',
          docId: 7,
          docMgtId: '5d351941409386269117d7fa',
          docContentType: '    ',
          quotaRefNo: 'QUOTA_01/JO05',
          size: 0
        }
      },
      {
        docId: 8,
        docDescEn: 'PRODUCT CATALOGUE 3',
        docDescMy: '',
        compulsary: true,
        dimensionCompulsary: false,
        maxHeight: 500,
        maxWidth: 500,
        minHeight: 0,
        minWidth: 0,
        size: 1000000,
        sortOrder: 0,
        title: 'PRODUCT CATALOGUE 3',
        trxnNo: 'PRDT',
        type: 'image/gif, image/jpeg, image/png, application/pdf',
        trxnDocuments: {
          docRefNo: '2019DOC2207QUAPPR',
          docId: 8,
          docMgtId: '5d351941409386269117d7fa',
          docContentType: '    ',
          quotaRefNo: 'QUOTA_01/JO05',
          size: 0
        }
      }
    ],
    refDocWrkrLst: [
      {
        docId: 16,
        docDescEn: 'WORK SITE PICTURE 01',
        docDescMy: '',
        compulsary: true,
        dimensionCompulsary: false,
        maxHeight: 500,
        maxWidth: 500,
        minHeight: 0,
        minWidth: 0,
        size: 1000000,
        sortOrder: 0,
        title: 'WORK SITE PICTURE 01',
        trxnNo: 'WRKSITE',
        type: 'image/gif, image/jpeg, image/png, application/pdf',
        trxnDocuments: {
          docRefNo: '2019DOC2207QUAPPR',
          docId: 16,
          docMgtId: '5d351941409386269117d7fa',
          docContentType: '    ',
          quotaRefNo: 'QUOTA_01/JO05',
          size: 0
        }
      },
      {
        docId: 17,
        docDescEn: 'WORK SITE PICTURE 02',
        docDescMy: '',
        compulsary: true,
        dimensionCompulsary: false,
        maxHeight: 500,
        maxWidth: 500,
        minHeight: 0,
        minWidth: 0,
        size: 1000000,
        sortOrder: 0,
        title: 'WORK SITE PICTURE 02',
        trxnNo: 'WRKSITE',
        type: 'image/gif, image/jpeg, image/png, application/pdf',
        trxnDocuments: {
          docRefNo: '2019DOC2207QUAPPR',
          docId: 17,
          docMgtId: '5d351941409386269117d7fa',
          docContentType: '    ',
          quotaRefNo: 'QUOTA_01/JO05',
          size: 0
        }
      },
      {
        docId: 18,
        docDescEn: 'WORK SITE PICTURE 03',
        docDescMy: '',
        compulsary: true,
        dimensionCompulsary: false,
        maxHeight: 500,
        maxWidth: 500,
        minHeight: 0,
        minWidth: 0,
        size: 1000000,
        sortOrder: 0,
        title: 'WORK SITE PICTURE 03',
        trxnNo: 'WRKSITE',
        type: 'image/gif, image/jpeg, image/png, application/pdf',
        trxnDocuments: {
          docRefNo: '2019DOC2207QUAPPR',
          docId: 18,
          docMgtId: '5d351941409386269117d7fa',
          docContentType: '    ',
          quotaRefNo: 'QUOTA_01/JO05',
          size: 0
        }
      }
    ],
    refDocAcmdLst: [
      {
        docId: 26,
        docDescEn: 'ACCOMMODATION 01',
        docDescMy: '',
        compulsary: true,
        dimensionCompulsary: false,
        maxHeight: 500,
        maxWidth: 500,
        minHeight: 0,
        minWidth: 0,
        size: 1000000,
        sortOrder: 0,
        title: 'ACCOMMODATION 01',
        trxnNo: 'ACMD',
        type: 'image/gif, image/jpeg, image/png, application/pdf',
        trxnDocuments: {
          docRefNo: '2019DOC2207QUAPPR',
          docId: 26,
          docMgtId: '5d351941409386269117d7fa',
          docContentType: '    ',
          quotaRefNo: 'QUOTA_01/JO05',
          size: 0
        }
      },
      {
        docId: 27,
        docDescEn: 'ACCOMMODATION 02',
        docDescMy: '',
        compulsary: true,
        dimensionCompulsary: false,
        maxHeight: 500,
        maxWidth: 500,
        minHeight: 0,
        minWidth: 0,
        size: 1000000,
        sortOrder: 0,
        title: 'ACCOMMODATION 02',
        trxnNo: 'ACMD',
        type: 'image/gif, image/jpeg, image/png, application/pdf',
        trxnDocuments: {
          docRefNo: '2019DOC2207QUAPPR',
          docId: 27,
          docMgtId: '5d351941409386269117d7fa',
          docContentType: '    ',
          quotaRefNo: 'QUOTA_01/JO05',
          size: 0
        }
      },
      {
        docId: 28,
        docDescEn: 'ACCOMMODATION 03',
        docDescMy: '',
        compulsary: true,
        dimensionCompulsary: false,
        maxHeight: 500,
        maxWidth: 500,
        minHeight: 0,
        minWidth: 0,
        size: 1000000,
        sortOrder: 0,
        title: 'ACCOMMODATION 03',
        trxnNo: 'ACMD',
        type: 'image/gif, image/jpeg, image/png, application/pdf',
        trxnDocuments: {
          docRefNo: '2019DOC2207QUAPPR',
          docId: 28,
          docMgtId: '5d351941409386269117d7fa',
          docContentType: '    ',
          quotaRefNo: 'QUOTA_01/JO05',
          size: 0
        }
      }
    ],
    refDocEmpCntl: {
      docId: 36,
      docDescEn: 'EMPLOYMENT CONTRACT LETTER',
      docDescMy: '',
      compulsary: true,
      dimensionCompulsary: false,
      maxHeight: 500,
      maxWidth: 500,
      minHeight: 0,
      minWidth: 0,
      size: 1000000,
      sortOrder: 0,
      title: 'EMPLOYMENT CONTRACT LETTER',
      trxnNo: 'EMPCNTL',
      type: 'image/gif, image/jpeg, image/png, application/pdf',
      trxnDocuments: {
        docRefNo: '2019DOC2207QUAPPR',
        docId: 36,
        docMgtId: '5d351941409386269117d7fa',
        docContentType: '    ',
        quotaRefNo: 'QUOTA_01/JO05',
        size: 0
      }
    },
    refDocDmndl: {
      docId: 38,
      docDescEn: 'DEMAND LETTER',
      docDescMy: '',
      compulsary: true,
      dimensionCompulsary: false,
      maxHeight: 500,
      maxWidth: 500,
      minHeight: 0,
      minWidth: 0,
      size: 1000000,
      sortOrder: 0,
      title: 'DEMAND LETTER',
      trxnNo: 'DMNDL',
      type: 'image/gif, image/jpeg, image/png, application/pdf',
      trxnDocuments: {
        docRefNo: '2019DOC2207QUAPPR',
        docId: 38,
        docMgtId: '5d351941409386269117d7fa',
        docContentType: '    ',
        quotaRefNo: 'QUOTA_01/JO05',
        size: 0
      }
    },
    refDocPowatl: {
      docId: 37,
      docDescEn: 'POWER OF ATTORNEY',
      docDescMy: '',
      compulsary: true,
      dimensionCompulsary: false,
      maxHeight: 500,
      maxWidth: 500,
      minHeight: 0,
      minWidth: 0,
      size: 1000000,
      sortOrder: 0,
      title: 'POWER OF ATTORNEY',
      trxnNo: 'POWATL',
      type: 'image/gif, image/jpeg, image/png, application/pdf',
      trxnDocuments: {
        docRefNo: '2019DOC2207QUAPPR',
        docId: 37,
        docMgtId: '5d351941409386269117d7fa',
        docContentType: '    ',
        quotaRefNo: 'QUOTA_01/JO05',
        size: 0
      }
    },
    refDocUdtl: null,
    refDocAttestl: null
  };
  doc1: any;
  refDoc: FileConfig;
  refDoc2 = new FileConfig({
    docId: 77,
    trxnNo: 'EMPATTESTN',
    title: 'Additional Contract Letter',
    type: 'image/gif, image/jpeg, image/png, application/pdf, text/plain',
    size: 1000000,
    compulsary: false,
    dimensionCompulsary: false,
    minWidth: 0,
    maxWidth: 0,
    minHeight: 0,
    maxHeight: 0,
    docDescEn: null,
    docDescMy: null,
    sortOrder: null,
    dmBucket: 'urp'
  });

  @ViewChild('content') content: TemplateRef<any>;
  fileLeft = new FormControl(null);
  fileRight = new FormControl(null);
  fileCenter = new FormControl(null);
  fileBtn = new FormControl(null);
  file = new FormControl(null);
  updFile = new FormControl([
    {
      docId: 77,
      docMgtId: '5dd268c7c39a073e3df26f84',
      filename: 'Desert.jpg',
      contentType: 'image/png'
    },
    {
      docId: 77,
      docMgtId: '5dd21304c39a073e3df26f44',
      filename: 'Hydrangeas.jpg',
      contentType: 'image/png'
    },
    {
      docId: 77,
      docMgtId: '5dd23ed0c39a073e3df26f4f',
      filename: 'Koala.jpg',
      contentType: 'image/png'
    }
  ]);

  constructor(public modalSvc: ModalService) { }

  ngOnInit() {
    this.doc1 = this.supportDocList.refDocDmndl;
    this.refDoc = this.refDoc2;
  }

  confirm() {
    this.modalSvc.openDefault(this.content);
  }

  getRefDocSix() {
    return (this.supportDocList.refDocPrdtLst as Array<any>).find(lst => lst.docId === 6);
  }
}
