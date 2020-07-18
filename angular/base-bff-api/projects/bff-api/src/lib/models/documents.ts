export class Documents {
    
    docId: number;
	dmBucket: string;
	docTrxnNo: string;
	docDesc: string;
	order: number;
	type: string;
	size: number;
	compulsary: boolean;
	dimensionCompulsary: number;
	maxHeight: number;
	maxWidth: number;
	minHeight: number;
	minWidth: number;

    constructor(args: {
         docId: number;
	     dmBucket: string;
	     docTrxnNo: string;
	     docDesc: string;
	     order: number;
	     type: string;
	     size: number;
	     compulsary: boolean;
	     dimensionCompulsary: number;
	     maxHeight: number;
	     maxWidth: number;
	     minHeight: number;
	     minWidth: number;
    }) {
        this.docId = args.docId;
        this.dmBucket = args.dmBucket;
        this.docTrxnNo = args.docTrxnNo;
        this.docDesc = args.docDesc;
        this.order = args.order;
        this.type = args.type;
        this.size = args.size;
        this.compulsary = args.compulsary;
        this.dimensionCompulsary = args.dimensionCompulsary;
        this.maxHeight = args.maxHeight;
        this.maxWidth = args.maxWidth;
        this.minHeight = args.minHeight;
        this.minWidth = args.minWidth;
    }
}