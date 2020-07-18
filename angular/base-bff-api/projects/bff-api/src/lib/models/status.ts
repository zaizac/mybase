export class Status {
    statusId: number;
	status: boolean;
	statusCd: string;
	statusDesc: string;
	statusType: string;

    constructor(args: {
        statusId: number;
        status: boolean;
        statusCd: string;
        statusDesc: string;
        statusType: string;
    }) {
        this.statusId = args.statusId;
        this.status = args.status;
        this.statusCd = args.statusCd;
        this.statusDesc = args.statusDesc;
        this.statusType = args.statusType;
    }
}
