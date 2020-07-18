export class Metadata {
    mtdtId: number;
	mtdtCd: string;
	mtdtDesc: string;
	mtdtType: string;
	status: boolean;

    constructor(args: {
        mtdtId: number;
        mtdtCd: string;
        mtdtDesc: string;
        mtdtType: string;
        status: boolean;
    }) {
        this.mtdtId = args.mtdtId;
        this.mtdtCd = args.mtdtCd;
        this.mtdtDesc = args.mtdtDesc;
        this.mtdtType = args.mtdtType;
        this.status = args.status;
    }
}
