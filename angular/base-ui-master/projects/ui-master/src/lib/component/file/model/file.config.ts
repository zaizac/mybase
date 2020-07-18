export class FileConfig {
  docId: number;
  trxnNo: string;
  title: string;
  type: string;
  size: number;
  compulsary: boolean;
  dimensionCompulsary: boolean;
  minWidth: number;
  maxWidth: number;
  minHeight: number;
  maxHeight: number;
  docDescEn: string;
  docDescMy: string;
  sortOrder: number;
  dmBucket: string;
  maxDocCnt?: number;

  constructor(args: {
    docId: number;
    trxnNo: string;
    title: string;
    type: string;
    size: number;
    compulsary: boolean;
    dimensionCompulsary: boolean;
    minWidth: number;
    maxWidth: number;
    minHeight: number;
    maxHeight: number;
    docDescEn: string;
    docDescMy: string;
    sortOrder: number;
    dmBucket: string;
    maxDocCnt?: number;
  }) {
    this.docId = args.docId;
    this.trxnNo = args.trxnNo;
    this.title = args.title;
    this.type = args.type;
    this.size = args.size;
    this.compulsary = args.compulsary;
    this.dimensionCompulsary = args.dimensionCompulsary;
    this.minWidth = args.minWidth;
    this.maxWidth = args.maxWidth;
    this.minHeight = args.minHeight;
    this.maxHeight = args.maxHeight;
    this.docDescEn = args.docDescEn;
    this.docDescMy = args.docDescMy;
    this.sortOrder = args.sortOrder;
    this.dmBucket = args.dmBucket;
    this.maxDocCnt = args.maxDocCnt;
  }
}
