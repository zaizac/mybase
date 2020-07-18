export class File {
  filename: string;
  size: number;
  contentType: string;
  content: any;
  docId: number;
  docMgtId: string;
  compulsary: boolean;
  refNo: string;
  remove?: boolean;
  dmBucket: string;

  constructor(args: {
    filename: string;
    size: number;
    contentType: string;
    content?: [];
    docId: number;
    docMgtId?: string;
    compulsary?: boolean;
    refNo?: string;
    remove?: boolean;
    dmBucket: string;
  }) {
    this.filename = args.filename;
    this.size = args.size;
    this.contentType = args.contentType;
    this.content = args.content;
    this.docId = args.docId;
    this.docMgtId = args.docMgtId;
    this.compulsary = args.compulsary;
    this.refNo = args.refNo;
    this.remove = args.remove;
    this.dmBucket = args.dmBucket;
  }
}
