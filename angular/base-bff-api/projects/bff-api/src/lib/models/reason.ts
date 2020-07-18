export class Reason {
    reasonId: number;
	reasonCd: string;
	reasonDesc: string;
	reasonType: string;
	status: boolean;

    constructor(args: {
        reasonId: number;
        reasonCd: string;
        reasonDesc: string;
        reasonType: string;
        status: boolean;
    }) {
        this.reasonId = args.reasonId;
        this.reasonCd = args.reasonCd;
        this.reasonDesc = args.reasonDesc;
        this.reasonType = args.reasonType;
        this.status = args.status;
    }
}
